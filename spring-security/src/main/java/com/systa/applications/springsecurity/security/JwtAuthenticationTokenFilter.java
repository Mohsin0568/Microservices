package com.systa.applications.springsecurity.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.systa.applications.springsecurity.util.Constants;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;    

    /**
     * All the request to be authorized through this filter
     * If success, this method sets the authentication context till the log in session expires
     * 
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(Constants.AUTHORIZATION);       
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {        	
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);	       
	        if (userDetails != null && jwtTokenUtil.validateToken(authToken)) {
	            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            logger.info("authenticated user " + username + ", setting security context");
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }
        }
        if(request.getMethod().equalsIgnoreCase("Options")){
        	response.addHeader("Allow","GET, POST"); 
        }       
        chain.doFilter(request, response);
    }
}