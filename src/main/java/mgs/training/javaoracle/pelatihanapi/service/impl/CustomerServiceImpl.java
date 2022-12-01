package mgs.training.javaoracle.pelatihanapi.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgs.training.javaoracle.pelatihanapi.dto.CustomerDTO;
import mgs.training.javaoracle.pelatihanapi.dto.CustomerMapper;
import mgs.training.javaoracle.pelatihanapi.service.CustomerService;
import oracle.jdbc.OracleTypes;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	
	private final DataSource datasource;
	
	@Override
	public void getData(String filter, Pageable page) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(datasource)
				.withCatalogName("P_CUSTOMER")
				.withProcedureName("get_data")
				.returningResultSet("p_out_data", new CustomerMapper());
		
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_phonenumber", OracleTypes.VARCHAR));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errcode", OracleTypes.INTEGER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errmsg", OracleTypes.VARCHAR));
		
		SqlParameterSource inParam = new MapSqlParameterSource()
				.addValue("p_in_phonenumber", filter);
		
		Map<String, Object> result = jdbcCall.execute(inParam);
		log.info("Executing procedure P_CUSTOMER.get_data get result ");
		log.info(" Code = " + result.get("p_out_errcode"));
		log.info(" Mesg = " + result.get("p_out_errmsg"));
		
		List<CustomerDTO> data = null;
		if((Integer) result.get("p_out_errcode") == 0) {
			data = (List<CustomerDTO>) result.get("p_out_data");
			log.info("Result Data Customer: ");
			data.stream().forEach(x -> {
				log.info("-> " + x.toString());
			});
		}
		
	}

}
