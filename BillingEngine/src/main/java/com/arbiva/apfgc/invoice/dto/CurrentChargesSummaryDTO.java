package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;

import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class CurrentChargesSummaryDTO {

	public CurrentChargesSummaryDTO() {
	}

	private String customerId;
	
	private String accountNumber;
	
	private String accountName;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal amount;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal tax;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal totalAmount;
	
	private BillInfoDTO individualDTO;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getTotalAmount() {
		totalAmount = amount.add(tax);
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BillInfoDTO getIndividualDTO() {
		return individualDTO;
	}

	public void setIndividualDTO(BillInfoDTO individualDTO) {
		this.individualDTO = individualDTO;
	}
}
