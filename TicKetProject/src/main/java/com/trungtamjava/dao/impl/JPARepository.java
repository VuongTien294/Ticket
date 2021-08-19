package com.trungtamjava.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class JPARepository<T> {
	@PersistenceContext
	protected EntityManager entityManager;
	
	public void add(T t) {
		entityManager.persist(t);
	}

	public void update(T t) {
		entityManager.merge(t);
	}

	public void delete(T t) {
		entityManager.remove(t);
	}

}
