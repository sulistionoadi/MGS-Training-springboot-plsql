package mgs.training.javaoracle.pelatihanapi.service;

import java.io.Writer;
import java.util.Date;

import mgs.training.javaoracle.pelatihanapi.dto.TransactionDTO;
import mgs.training.javaoracle.pelatihanapi.dto.http.HttpRespModel;

public interface TransactionService {

	public HttpRespModel save(TransactionDTO dto);
	public HttpRespModel getData(String nama, Date start, Date end, Integer pageNumber, Integer pageSize);
	public void exportCsv(String nama, Date start, Date end, Writer writer);
	
}
