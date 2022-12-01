package mgs.training.javaoracle.pelatihanapi.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerDTO {

	Long id;
	String createdBy;
	Date createdAt;
	Boolean isActive;
	String nama;
	String phoneNumber;
	String email;
	BigDecimal balance;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", isActive="
				+ isActive + ", nama=" + nama + ", phoneNumber=" + phoneNumber + ", email=" + email + ", balance="
				+ balance + "]";
	}
	
}
