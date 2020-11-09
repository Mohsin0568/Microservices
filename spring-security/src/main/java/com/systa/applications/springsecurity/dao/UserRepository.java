package com.systa.applications.springsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systa.applications.springsecurity.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByLoginid(String loginId);
	User findByEmail(String email);
}
