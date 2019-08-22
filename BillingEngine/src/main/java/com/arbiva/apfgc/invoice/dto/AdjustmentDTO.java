package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;

import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class AdjustmentDTO {

	public AdjustmentDTO() {
	}

	private String description;

	private String date;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal charges = BigDecimal.ZERO;

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the charges
	 */
	public BigDecimal getCharges() {
		return charges;
	}

	/**
	 * @param charges
	 *            the charges to set
	 */
	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}

}
