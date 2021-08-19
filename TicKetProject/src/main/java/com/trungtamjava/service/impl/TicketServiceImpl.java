package com.trungtamjava.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trungtamjava.dao.DepartmentDao;
import com.trungtamjava.dao.TicketDao;
import com.trungtamjava.dao.UserDao;
import com.trungtamjava.entity.Department;
import com.trungtamjava.entity.Ticket;
import com.trungtamjava.entity.User;
import com.trungtamjava.model.TicketDTO;
import com.trungtamjava.model.SearchTicketTO;
import com.trungtamjava.model.TicketDTO;
import com.trungtamjava.service.TicketService;
import com.trungtamjava.ultil.DateTimeUtils;


@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketDao ticketDao;

	@Autowired
	DepartmentDao departmentDao;
	
	@Autowired
	UserDao userDao;

	@Override
	public void addTicket(TicketDTO ticketDTO) {
		Ticket ticket = new Ticket();
		ticket.setEnabled(ticketDTO.getEnabled());
        ticket.setAnswer(ticketDTO.getAnswer());
        ticket.setQuestion(ticketDTO.getQuestion());
        ticket.setNumber(ticketDTO.getNumber());

		Department department = departmentDao.getId(ticketDTO.getDepartmentId());
		ticket.setDepartment(department);
		
		User user = userDao.getUserId(ticketDTO.getCustomerId());
		ticket.setUser(user);

		ticketDao.add(ticket);
		ticketDTO.setId(ticket.getId());
	}

	
	@Override
	public void updateTicket(TicketDTO ticketDTO) {
		Ticket ticket = new Ticket();
		ticket.setId(ticketDTO.getId());
		ticket.setEnabled(ticketDTO.getEnabled());
        ticket.setAnswer(ticketDTO.getAnswer());
        ticket.setQuestion(ticketDTO.getQuestion());
        ticket.setNumber(ticketDTO.getNumber());

		Department department = departmentDao.getId(ticketDTO.getDepartmentId());
		ticket.setDepartment(department);
		
		User user = userDao.getUserId(ticketDTO.getCustomerId());
		ticket.setUser(user);


		ticketDao.update(ticket);
		ticketDTO.setId(ticket.getId());

	}

	
	@Override
	public void delete(Long id) {
		Ticket ticket = ticketDao.getTicketId(id);
		ticketDao.delete(ticket);
	}
	
	
	@Override
	public TicketDTO getTicketId(Long id) {
		Ticket ticket = ticketDao.getTicketId(id);
		return convertDTO(ticket);
	}
	
	
	@Override
	public List<TicketDTO> find(SearchTicketTO searchTicketDTO){
		List<Ticket> listProducts = ticketDao.find(searchTicketDTO);
		List<TicketDTO> listProductDTOs = new ArrayList<TicketDTO>();
		listProducts.forEach(products -> {
			listProductDTOs.add(convertDTO(products));
		});
		return listProductDTOs;
	}
	
	
	@Override
	public Long count(SearchTicketTO searchTicketTO) {
		return ticketDao.count(searchTicketTO);
	}

	
	
	@Override
	public Long countTotal(SearchTicketTO searchTicketTO) {
		return ticketDao.countTotal(searchTicketTO);
	}

	private TicketDTO convertDTO(Ticket ticket) {
		TicketDTO ticketDTO = new TicketDTO();
		ticketDTO.setId(ticket.getId());
		ticketDTO.setNumber(ticket.getNumber());

		ticketDTO.setAnswer(ticket.getAnswer());
		ticketDTO.setQuestion(ticket.getQuestion());
		
		ticketDTO.setCreatedDate(DateTimeUtils.formatDate(ticket.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
		ticketDTO.setFinishDate( DateTimeUtils.DD_MM_YYYY_HH_MM);

		
		if (ticketDTO.getDepartmentId() != null) {
			ticketDTO.setDepartmentId(ticket.getDepartment().getId());
			ticketDTO.setDepartmentName(ticket.getDepartment().getName());
		}
		
		if(ticketDTO.getCustomerId() != null) {
			ticketDTO.setCustomerId(ticket.getUser().getId());
		}
		return ticketDTO;
	}
	
	
}
