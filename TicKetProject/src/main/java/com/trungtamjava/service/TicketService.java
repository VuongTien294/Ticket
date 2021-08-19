package com.trungtamjava.service;

import java.util.List;

import com.trungtamjava.model.TicketDTO;
import com.trungtamjava.model.SearchTicketTO;

public interface TicketService {

	Long countTotal(SearchTicketTO searchProductDTO);

	Long count(SearchTicketTO searchProductDTO);

	List<TicketDTO> find(SearchTicketTO searchProductDTO);

	TicketDTO getTicketId(Long id);

	void delete(Long id);

	void updateTicket(TicketDTO productDTO);

	void addTicket(TicketDTO productDTO);



}
