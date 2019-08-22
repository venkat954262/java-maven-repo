
package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;

import com.arbiva.apfgc.invoice.utils.JsonDateSerializer;
import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
Gowthami
*/
public class ValueAddedServiceDTO {
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	private String date;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal charges = BigDecimal.ZERO;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

