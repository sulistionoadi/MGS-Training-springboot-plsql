package mgs.training.javaoracle.pelatihanapi.service;

import mgs.training.javaoracle.pelatihanapi.dto.CustomerDTO;
import mgs.training.javaoracle.pelatihanapi.dto.http.HttpRespModel;

public interface CustomerService {

	public HttpRespModel getData(String filter, Integer pageNumber, Integer pageSize);
	public HttpRespModel saveOrUpdate(CustomerDTO dto);
	
}
