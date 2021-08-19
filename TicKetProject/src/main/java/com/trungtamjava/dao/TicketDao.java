package com.trungtamjava.dao;

import java.util.List;

import com.trungtamjava.entity.Ticket;
import com.trungtamjava.model.SearchTicketTO;

public interface TicketDao {
	void add(Ticket ticket);
	
	void update(Ticket ticket);
	
	void delete(Ticket ticket);

	Long countTotal(SearchTicketTO searchProductDTO);

	Long count(SearchTicketTO searchProductDTO);

	List<Ticket> find(SearchTicketTO searchProductDTO);

	Ticket getTicketId(Long id);

}
