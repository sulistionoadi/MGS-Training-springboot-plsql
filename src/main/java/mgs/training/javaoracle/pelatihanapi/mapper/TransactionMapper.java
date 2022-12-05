package mgs.training.javaoracle.pelatihanapi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mgs.training.javaoracle.pelatihanapi.dto.TransactionDTO;

public class TransactionMapper implements RowMapper<TransactionDTO> {

	@Override
	public TransactionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		TransactionDTO dto = new TransactionDTO();
		
		dto.setId(rs.getString("ID"));
		dto.setClientRef(rs.getString("CLIENT_REF"));
		dto.setCreatedBy(rs.getString("created_by"));
		dto.setCreatedAt(rs.getDate("CREATED_AT"));
		dto.setStatus(rs.getString("STATUS"));
		dto.setTransactionType(rs.getString("TIPE_TRANSAKSI"));
		dto.setAmount(rs.getBigDecimal("AMOUNT"));
		dto.setFee(rs.getBigDecimal("FEE"));
		dto.setAmountFee(rs.getBigDecimal("AMOUNT_FEE"));
		dto.setCustomerId(rs.getLong("ID_CUSTOMER"));
		dto.setCustomerName(rs.getString("NAMA_CUSTOMER"));
		
		return dto;
	}

}
