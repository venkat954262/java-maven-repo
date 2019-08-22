package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;

import com.arbiva.apfgc.invoice.utils.JsonDateSerializer;
import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class PaymentDTO {


	public PaymentDTO() {
	}
	
	@JsonSerialize(using = JsonDateSerializer.class)
	private String date;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal amount;

	private String description;

	private String cafno;
	
	
	public String getCafno() {
		return cafno;
	}

	public void setCafno(String cafno) {
		this.cafno = cafno;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
