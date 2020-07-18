package com.systa.applications.springsecurity.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.systa.applications.springsecurity.dao.UserPasswordRepository;
import com.systa.applications.springsecurity.entity.UserPassword;

@Repository
public class UserPasswordRepositoryImpl implements UserPasswordRepository {

	 @PersistenceContext
	 private EntityManager entityManager;
	
	@Override
	public void save(UserPassword password) {
		entityManager.persist(password);
		
	}

	@Override
	public UserPassword getUserActivePassword(Integer userId) {
		String strQuery = "select up from UserPassword up where up.active = 1 and up.user.id = " + userId;
		TypedQuery<UserPassword> query = entityManager.createQuery(strQuery, UserPassword.class);
		return query.getSingleResult();		
	}

	

}
