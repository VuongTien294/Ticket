package com.trungtamjava.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.trungtamjava.dao.TicketDao;
import com.trungtamjava.entity.Department;
import com.trungtamjava.entity.Ticket;
import com.trungtamjava.model.SearchTicketTO;



@Repository
@Transactional
public class TicketDaoImpl extends JPARepository<Ticket> implements TicketDao {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public Ticket getTicketId(Long id) {
		return entityManager.find(Ticket.class, id);
	}
	
	@Override
	public List<Ticket> find(SearchTicketTO searchTicketDTO){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery< Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
		Root<Ticket> root = criteriaQuery.from(Ticket.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(searchTicketDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), 
					"%"+ searchTicketDTO.getKeyword().toLowerCase()+"%");
			predicates.add(predicate);
		}
		
		if(searchTicketDTO.getDepartmentId()!= null) {
			Join<Ticket, Department> cateJoin= root.join("department");
			Predicate predicate = criteriaBuilder.equal(cateJoin.get("id"), searchTicketDTO.getDepartmentId());
			predicates.add(predicate);
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		// sap xep
		if (StringUtils.equals(searchTicketDTO.getSortBy().getData(), "id")) {
			if (searchTicketDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchTicketDTO.getSortBy().getData(), "number")) {
			if (searchTicketDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("number")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("number")));
			}
		}
		
		TypedQuery<Ticket> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		if(searchTicketDTO.getStart()!= null) {
			typedQuery.setFirstResult(searchTicketDTO.getStart());
			typedQuery.setMaxResults(searchTicketDTO.getLength());
		}
		return typedQuery.getResultList();
		
	}
	
	@Override
	public Long count(SearchTicketTO searchTicketDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Ticket> root = criteriaQuery.from(Ticket.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(searchTicketDTO.getKeyword())) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
					"%"+searchTicketDTO.getKeyword().toLowerCase()+"%");
			predicates.add(predicate);
		}
		
		if(searchTicketDTO.getDepartmentId()!=null) {
			Join<Ticket, Department> category = root.join("category");
			Predicate predicate = criteriaBuilder.equal(category.get("id"), searchTicketDTO.getDepartmentId());
			predicates.add(predicate);
		}
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}
	
	@Override
	public Long countTotal(SearchTicketTO searchTicketDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Ticket> root = criteriaQuery.from(Ticket.class);
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
		
	}
	
}
