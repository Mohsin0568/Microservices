package com.systa.applications.sample.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systa.applications.sample.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByLoginid(String loginId);
}
