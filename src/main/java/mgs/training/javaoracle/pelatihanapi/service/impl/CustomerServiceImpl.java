package mgs.training.javaoracle.pelatihanapi.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgs.training.javaoracle.pelatihanapi.constant.ErrorCode;
import mgs.training.javaoracle.pelatihanapi.dto.CustomerDTO;
import mgs.training.javaoracle.pelatihanapi.dto.http.HttpRespModel;
import mgs.training.javaoracle.pelatihanapi.mapper.CustomerMapper;
import mgs.training.javaoracle.pelatihanapi.service.CustomerService;
import oracle.jdbc.OracleTypes;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	
	private final DataSource datasource;
	
	@Override
	public HttpRespModel getData(String filter, Integer pageNumber, Integer pageSize) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(datasource)
				.withCatalogName("P_CUSTOMER")
				.withProcedureName("get_data")
				.returningResultSet("p_out_data", new CustomerMapper());
		
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_phonenumber", OracleTypes.VARCHAR));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_start", OracleTypes.NUMBER));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_end", OracleTypes.NUMBER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errcode", OracleTypes.INTEGER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errmsg", OracleTypes.VARCHAR));
		
		SqlParameterSource inParam = new MapSqlParameterSource()
				.addValue("p_in_phonenumber", filter)
				.addValue("p_in_start", ( pageNumber * pageSize ) + 1)
				.addValue("p_in_end", ( pageNumber + 1 ) * pageSize);
		
		Map<String, Object> result = jdbcCall.execute(inParam);
		log.info("Executing procedure P_CUSTOMER.get_data get result ");
		log.info(" Code = " + result.get("p_out_errcode"));
		log.info(" Mesg = " + result.get("p_out_errmsg"));
		
		if((Integer) result.get("p_out_errcode") == 0) {
			List<CustomerDTO> data = (List<CustomerDTO>) result.get("p_out_data");
			return HttpRespModel.ok(data);
		} else {
			return HttpRespModel.error((Integer) result.get("p_out_errcode"), (String) result.get("p_out_errmsg"));
		}
	}

	@Override
	public HttpRespModel saveOrUpdate(CustomerDTO dto) {
		SimpleJdbcCall jdbcCall = null;
		SqlParameterSource inParam = null;
		String procedureName = "";
		
		if(dto.getId() != null) {
			procedureName = "update_data";
			jdbcCall = new SimpleJdbcCall(datasource)
					.withCatalogName("P_CUSTOMER")
					.withProcedureName(procedureName);
			jdbcCall.addDeclaredParameter(new SqlParameter("p_in_id", OracleTypes.NUMBER));
			jdbcCall.addDeclaredParameter(new SqlParameter("p_in_nama", OracleTypes.VARCHAR));
			jdbcCall.addDeclaredParameter(new SqlParameter("p_in_phonenumber", OracleTypes.VARCHAR));
			jdbcCall.addDeclaredParameter(new SqlParameter("p_in_email", OracleTypes.VARCHAR));
			
			inParam = new MapSqlParameterSource()
					.addValue("p_in_id", dto.getId())
					.addValue("p_in_nama", dto.getNama())
					.addValue("p_in_phonenumber", dto.getPhoneNumber())
					.addValue("p_in_email", dto.getEmail());
		} else {
			procedureName = "save_data";
			jdbcCall = new SimpleJdbcCall(datasource)
					.withCatalogName("P_CUSTOMER")
					.withProcedureName(procedureName);
			jdbcCall.addDeclaredParameter(new SqlParameter("p_in_nama", OracleTypes.VARCHAR));
			jdbcCall.addDeclaredParameter(new SqlParameter("p_in_phonenumber", OracleTypes.VARCHAR));
			jdbcCall.addDeclaredParameter(new SqlParameter("p_in_email", OracleTypes.VARCHAR));

			inParam = new MapSqlParameterSource()
					.addValue("p_in_nama", dto.getNama())
					.addValue("p_in_phonenumber", dto.getPhoneNumber())
					.addValue("p_in_email", dto.getEmail());
		}
		
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errcode", OracleTypes.INTEGER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errmsg", OracleTypes.VARCHAR));
		
		Map<String, Object> result = jdbcCall.execute(inParam);
		Integer errCode = (Integer) result.get("p_out_errcode");
		String errMessage = (String) result.get("p_out_errmsg");

		log.info("Executing procedure P_CUSTOMER.{} get result (code:{}, message:{})", procedureName, errCode, errMessage);
		
		if(errCode == 0) {
			return HttpRespModel.ok(null);
		} else if (errCode == 99){
			return HttpRespModel.error(ErrorCode.ERROR_OTHER, "Internal System Error.");
		} else {
			return HttpRespModel.error(errCode, errMessage);
		}
	}

}
