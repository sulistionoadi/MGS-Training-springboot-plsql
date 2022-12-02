package mgs.training.javaoracle.pelatihanapi.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mgs.training.javaoracle.pelatihanapi.dto.CustomerDTO;
import mgs.training.javaoracle.pelatihanapi.dto.http.HttpRespModel;
import mgs.training.javaoracle.pelatihanapi.service.CustomerService;

@RestController
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService customerService;
	
	@GetMapping("/api/customer")
	public HttpRespModel getDataCustomer(@RequestParam(required = false) String filter, 
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		return customerService.getData(filter, page, size);
	}
	
	@PostMapping("/api/customer")
	public HttpRespModel saveDataCustomer(@RequestBody @Valid CustomerDTO dto) {
		return customerService.saveOrUpdate(dto);
	}
	
}
