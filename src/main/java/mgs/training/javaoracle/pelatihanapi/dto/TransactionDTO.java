package mgs.training.javaoracle.pelatihanapi.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDTO {

	String id;
	Date createdAt;
	String createdBy;
	String status;
	
	@NotEmpty(message="ClientRef harus diisi")
	String clientRef;
	
	@NotEmpty(message="Tipe Transaksi harus diisi")
	String transactionType;
	
	@NotNull(message = "Amount harus diisi")
	@Min(value=1, message="Minimum amount adalah 1")
	BigDecimal amount;
	
	BigDecimal fee;
	BigDecimal amountFee;
	
	@NotNull(message = "CustomerID harus diisi")
	Long customerId;
	
	String customerName;

}
