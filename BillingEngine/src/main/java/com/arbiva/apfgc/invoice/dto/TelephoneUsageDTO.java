package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;

import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class TelephoneUsageDTO {

	public TelephoneUsageDTO() {
	}

	private String units;

	private String duration;

	private String typeOfUsage;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal charges;

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTypeOfUsage() {
		return typeOfUsage;
	}

	public void setTypeOfUsage(String typeOfUsage) {
		this.typeOfUsage = typeOfUsage;
	}

	public BigDecimal getCharges() {
		return charges;
	}

	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

}
