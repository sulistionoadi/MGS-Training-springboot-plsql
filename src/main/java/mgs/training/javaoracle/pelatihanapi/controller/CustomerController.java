package mgs.training.javaoracle.pelatihanapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mgs.training.javaoracle.pelatihanapi.service.CustomerService;

@RestController
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;
	
	@GetMapping("/api/customer")
	public void getDataCustomer(@RequestParam(required = false) String filter) {
		customerService.getData(filter, null);
		
	}
	
}
