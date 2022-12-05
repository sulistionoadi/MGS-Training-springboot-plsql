package mgs.training.javaoracle.pelatihanapi.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mgs.training.javaoracle.pelatihanapi.constant.ErrorCode;
import mgs.training.javaoracle.pelatihanapi.dto.TransactionDTO;
import mgs.training.javaoracle.pelatihanapi.dto.http.HttpRespModel;
import mgs.training.javaoracle.pelatihanapi.mapper.TransactionMapper;
import mgs.training.javaoracle.pelatihanapi.service.TransactionService;
import oracle.jdbc.OracleTypes;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
	
	private final DataSource datasource;
	
	@Override
	public HttpRespModel save(TransactionDTO dto) {
	
		String procedureName = "save_transaksi";
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(datasource)
				.withCatalogName("p_transaksi")
				.withProcedureName(procedureName);
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_clientref", OracleTypes.VARCHAR));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_tipetransaksi", OracleTypes.VARCHAR));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_amount", OracleTypes.NUMBER));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_fee", OracleTypes.NUMBER));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_custid", OracleTypes.NUMBER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errcode", OracleTypes.INTEGER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errmsg", OracleTypes.VARCHAR));

		SqlParameterSource inParam = new MapSqlParameterSource()
				.addValue("p_in_clientref", dto.getClientRef())
				.addValue("p_in_tipetransaksi", dto.getTransactionType())
				.addValue("p_in_amount", dto.getAmount())
				.addValue("p_in_fee", dto.getFee())
				.addValue("p_in_custid", dto.getCustomerId());
		
		Map<String, Object> result = jdbcCall.execute(inParam);
		Integer errCode = (Integer) result.get("p_out_errcode");
		String errMessage = (String) result.get("p_out_errmsg");

		log.info("Executing procedure P_TRANSAKSI.{} get result (code:{}, message:{})", procedureName, errCode, errMessage);
		
		if(errCode == 0) {
			return HttpRespModel.ok(null);
		} else if (errCode == 99){
			return HttpRespModel.error(ErrorCode.ERROR_OTHER, "Internal System Error.");
		} else {
			return HttpRespModel.error(errCode, errMessage);
		}
	}

	@Override
	public HttpRespModel getData(String nama, Date startDate, Date endDate, Integer pageNumber, Integer pageSize) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(datasource)
				.withCatalogName("p_transaksi")
				.withProcedureName("get_data")
				.returningResultSet("p_out_data", new TransactionMapper());
		
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_nama", OracleTypes.VARCHAR));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_sdate", OracleTypes.DATE));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_edate", OracleTypes.DATE));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_start", OracleTypes.NUMBER));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_end", OracleTypes.NUMBER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errcode", OracleTypes.INTEGER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errmsg", OracleTypes.VARCHAR));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_total_row", OracleTypes.INTEGER));
		
		SqlParameterSource inParam = new MapSqlParameterSource()
				.addValue("p_in_nama", nama)
				.addValue("p_in_sdate", startDate)
				.addValue("p_in_edate", endDate)
				.addValue("p_in_start", ( pageNumber * pageSize ) + 1)
				.addValue("p_in_end", ( pageNumber + 1 ) * pageSize);
		
		Map<String, Object> result = jdbcCall.execute(inParam);
		Integer errCode = (Integer) result.get("p_out_errcode");
		String errMessage = (String) result.get("p_out_errmsg");
		Integer totalRow = (Integer) result.get("p_total_row");
		
		log.info("Executing procedure P_TRANSAKSI.get_data get result (code:{}, message:{}, totalRow:{})", errCode, errMessage, totalRow);
		
		if(errCode == 0) {
			List<TransactionDTO> data = (List<TransactionDTO>) result.get("p_out_data");
			return HttpRespModel.ok(data, totalRow);
		} else {
			return HttpRespModel.error(errCode, errMessage);
		}
	}
	
	private Integer countData(String nama, Date startDate, Date endDate) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(datasource)
				.withCatalogName("p_transaksi")
				.withProcedureName("count_data");
		
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_nama", OracleTypes.VARCHAR));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_sdate", OracleTypes.DATE));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_edate", OracleTypes.DATE));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errcode", OracleTypes.INTEGER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errmsg", OracleTypes.VARCHAR));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_total_row", OracleTypes.INTEGER));
		
		SqlParameterSource inParam = new MapSqlParameterSource()
				.addValue("p_in_nama", nama)
				.addValue("p_in_sdate", startDate)
				.addValue("p_in_edate", endDate);
		
		Map<String, Object> result = jdbcCall.execute(inParam);
		Integer errCode = (Integer) result.get("p_out_errcode");
		String errMessage = (String) result.get("p_out_errmsg");
		Integer totalRow = (Integer) result.get("p_total_row");
		
		log.info("Executing procedure P_TRANSAKSI.get_data get result (code:{}, message:{}, totalRow:{})", errCode, errMessage, totalRow);
		
		if(errCode == 0) {
			return totalRow;
		} else {
			throw new RuntimeException("Error code:" + errCode + ", message:" + errMessage);
		}
	}

	@Override
	public void exportCsv(String nama, Date startDate, Date endDate, Writer writer) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(datasource)
				.withCatalogName("p_transaksi")
				.withProcedureName("get_data")
				.returningResultSet("p_out_data", new TransactionMapper());
		
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_nama", OracleTypes.VARCHAR));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_sdate", OracleTypes.DATE));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_edate", OracleTypes.DATE));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_start", OracleTypes.NUMBER));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_in_end", OracleTypes.NUMBER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errcode", OracleTypes.INTEGER));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_out_errmsg", OracleTypes.VARCHAR));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_total_row", OracleTypes.INTEGER));
		
		SqlParameterSource inParam = new MapSqlParameterSource()
				.addValue("p_in_nama", nama)
				.addValue("p_in_sdate", startDate)
				.addValue("p_in_edate", endDate)
				.addValue("p_in_start", 0)
				.addValue("p_in_end", countData(nama, startDate, endDate)); //Harusnya ambil dari count data dulu
		
		Map<String, Object> result = jdbcCall.execute(inParam);
		Integer errCode = (Integer) result.get("p_out_errcode");
		String errMessage = (String) result.get("p_out_errmsg");
		Integer totalRow = (Integer) result.get("p_total_row");
		
		log.info("Executing procedure P_TRANSAKSI.get_data get result (code:{}, message:{}, totalRow:{})", errCode, errMessage, totalRow);
		
		if(errCode == 0) {
			List<TransactionDTO> data = (List<TransactionDTO>) result.get("p_out_data");
			try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
	            for (TransactionDTO dto : data) {
	                csvPrinter.printRecord(
	                		dto.getId(), 
	                		dto.getCreatedAt(), 
	                		dto.getTransactionType(), 
	                		dto.getClientRef(), 
	                		dto.getCustomerName(), 
	                		dto.getAmount(), 
	                		dto.getFee(), 
	                		dto.getAmountFee());
	            }
	        } catch (IOException e) {
	            log.error("Error While writing CSV ", e);
	            throw new RuntimeException(e);
	        }
		} else {
			throw new RuntimeException("Error code:" + errCode + ", message:" + errMessage);
		}
	}

}
