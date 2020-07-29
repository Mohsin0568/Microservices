package com.systa.microservices.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.systa.microservices.user.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = -2550185165626007488L;
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	private final String SECRET = "somevalue";
	
	public String getMobileNumberFromToken(String token){
		Claims claims = getAllClaimsFromToken(token);
		return (String) claims.get("mobileNumber");
	}
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(User userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("mobileNumber", userDetails.getMobileNumber());
		return doGenerateToken(claims, userDetails.getUserName());
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
		.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}
		
	public Boolean validateToken(String token, User userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUserName()) && !isTokenExpired(token));
	}	
	
	public static void main(String argss[]){
		User user = new User();
		user.setUserName("Johny");
		user.setMobileNumber("92929292");
		JwtUtil jwtUtil = new JwtUtil();
		String token = jwtUtil.generateToken(user);
		System.out.println(token);
	}
}
