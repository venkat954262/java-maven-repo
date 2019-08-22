package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;

import com.arbiva.apfgc.invoice.utils.JsonDateSerializer;
import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class RecurringDTO {

	public RecurringDTO() {
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	private String startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	private String endDate;

	private String description;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal charges = BigDecimal.ZERO;
	
	private String chargeType;
	
	private String chargeTypeFlag;

	public String getChargeTypeFlag() {
		return chargeTypeFlag;
	}

	public void setChargeTypeFlag(String chargeTypeFlag) {
		this.chargeTypeFlag = chargeTypeFlag;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

}
