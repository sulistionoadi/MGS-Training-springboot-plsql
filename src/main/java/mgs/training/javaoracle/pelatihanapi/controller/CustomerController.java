package mgs.training.javaoracle.pelatihanapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mgs.training.javaoracle.pelatihanapi.dto.CustomerDTO;
import mgs.training.javaoracle.pelatihanapi.service.CustomerService;

@RestController
public class CustomerController {

	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/api/customer")
	public void getDataCustomer(@RequestParam(required = false) String filter) {
		customerService.getData(filter, null);
		
	}
	
}
