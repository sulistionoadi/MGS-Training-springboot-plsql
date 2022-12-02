package mgs.training.javaoracle.pelatihanapi.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	Long id;
	String createdBy;
	Date createdAt;
	Boolean isActive;
	
	@NotBlank(message="Nama harus diisi")
	@Pattern(regexp = "^[a-zA-Z]{3,50}$", message = "Invalid Nama value")
	String nama;
	
	@NotBlank(message="Phonenumber harus diisi")
	@Pattern(regexp = "^[0-9]{8,15}$", message = "Invalid Phonenumber Format")
	String phoneNumber;
	
	@NotBlank(message="Email harus diisi")
	String email;
	BigDecimal balance;

}
