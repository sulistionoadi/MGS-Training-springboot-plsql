package mgs.training.javaoracle.pelatihanapi.service;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

public interface CustomerService {

	public void getData(String filter, Pageable page);
	
}
