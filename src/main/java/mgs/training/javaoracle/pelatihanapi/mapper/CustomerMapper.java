package mgs.training.javaoracle.pelatihanapi.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mgs.training.javaoracle.pelatihanapi.dto.CustomerDTO;

public class CustomerMapper implements RowMapper<CustomerDTO> {

	@Override
	public CustomerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomerDTO dto = new CustomerDTO();
		
		dto.setId(rs.getLong("ID"));
		dto.setCreatedBy(rs.getString("created_by"));
		dto.setCreatedAt(rs.getDate("CREATED_AT"));
		
		if(rs.getInt("IS_ACTIVE") == 1) {
			dto.setIsActive(true);
		} else {
			dto.setIsActive(false);
		}
		dto.setNama(rs.getString("Nama"));
		dto.setPhoneNumber(rs.getString("PHONENUMBER"));
		dto.setEmail(rs.getString("EMAIL"));
		dto.setBalance(rs.getBigDecimal("BALANCE"));
		
		return dto;
	}

}
