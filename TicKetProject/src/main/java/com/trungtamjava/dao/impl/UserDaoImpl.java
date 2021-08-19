package com.trungtamjava.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trungtamjava.dao.UserDao;
import com.trungtamjava.entity.User;


@Repository
@Transactional
public class UserDaoImpl extends JPARepository<User> implements UserDao {
	
	@Autowired
	EntityManager entityManager;
	
	@Override
	public User getUserId(Long id) {
		return entityManager.find(User.class, id);
	}
	

	
	@Override
	public User getByUsername(String username) {
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
			Root<User> root = criteriaQuery.from(User.class);
			criteriaQuery.where(builder.equal(builder.lower(root.get("username")), username));
			TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
			return typedQuery.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	@Override
	public User getByEmail(String email) {
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
			Root<User> root = criteriaQuery.from(User.class);
			criteriaQuery.where(builder.equal(builder.lower(root.get("email")), email));
			TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
			return typedQuery.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	@Override
	public User getByName(String name) {
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
			Root<User> root = criteriaQuery.from(User.class);
			criteriaQuery.where(builder.equal(builder.lower(root.get("name")),name));
			TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
			return typedQuery.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	

}
