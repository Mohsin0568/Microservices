package com.systa.microservices.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systa.microservices.user.entity.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {

}
