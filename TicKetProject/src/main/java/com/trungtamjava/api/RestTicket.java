package com.trungtamjava.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trungtamjava.model.TicketDTO;
import com.trungtamjava.model.ResponseDTO;
import com.trungtamjava.model.SearchTicketTO;
import com.trungtamjava.service.TicketService;
import com.trungtamjava.ultil.FileStore;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class RestTicket {
	@Autowired
	TicketService ticketService;
	
	//chuc nang tim kim ve ticket
	@PostMapping(value = "/ticket/search")
	public ResponseDTO<TicketDTO> find(@RequestBody SearchTicketTO searchTicketDTO){
		ResponseDTO<TicketDTO> responseDTO = new ResponseDTO<TicketDTO>();
		responseDTO.setData(ticketService.find(searchTicketDTO));
		responseDTO.setRecordsFiltered(ticketService.count(searchTicketDTO));
		responseDTO.setRecordsTotal(ticketService.countTotal(searchTicketDTO));
		return responseDTO;
		
	}
	
	//Chuc nang them ve ticket cua khach hang
	@PostMapping(value="/ticket/add")
	public TicketDTO add(@ModelAttribute TicketDTO ticketDTO) {
		ticketService.addTicket(ticketDTO);
		return ticketDTO;
	}
	
	//sau khi xem ticket thi user(hay nhan vien phong ban) se tra loi sau do goi lai phuong thuc update
	@PostMapping(value = "/admin/ticket/update")
	public void update(@ModelAttribute TicketDTO ticketDTO) {
		ticketService.updateTicket(ticketDTO);
	}
	
	//Khach hang se xem ticket cua mk co trang thai ra sao qua api nay tra ve 1 ticket
	@GetMapping(value = "/ticket/{id}")
	public TicketDTO get(@PathVariable(name = "id") Long id) {
		return ticketService.getTicketId(id);
	}
	
	

}
