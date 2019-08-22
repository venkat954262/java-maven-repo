package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;

import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class OtherChargesDTO {

	public OtherChargesDTO() {
	}

	private String vodName;

	private String date;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal charges;

	public String getVodName() {
		return vodName;
	}

	public void setVodName(String vodName) {
		this.vodName = vodName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

}
