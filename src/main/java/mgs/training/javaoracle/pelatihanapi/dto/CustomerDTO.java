package mgs.training.javaoracle.pelatihanapi.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class CustomerDTO {

	Long id;
	String createdBy;
	Date createdAt;
	Boolean isActive;
	String nama;
	String phoneNumber;
	String email;
	BigDecimal balance;

}
