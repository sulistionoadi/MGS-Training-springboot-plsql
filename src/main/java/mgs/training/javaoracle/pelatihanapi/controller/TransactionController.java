package mgs.training.javaoracle.pelatihanapi.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mgs.training.javaoracle.pelatihanapi.dto.TransactionDTO;
import mgs.training.javaoracle.pelatihanapi.dto.http.HttpRespModel;
import mgs.training.javaoracle.pelatihanapi.service.TransactionService;

@RestController
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService service;
	
	@PostMapping("/api/transaction")
	public HttpRespModel saveTransaction(@RequestBody @Valid TransactionDTO dto) {
		return service.save(dto);
	}
	
	@GetMapping("/api/transaction")
	public HttpRespModel getTransaction(
			@RequestParam(required = false) String nama,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date startDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate,
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		return service.getData(nama, startDate, endDate, page, size);
	}
	
}
