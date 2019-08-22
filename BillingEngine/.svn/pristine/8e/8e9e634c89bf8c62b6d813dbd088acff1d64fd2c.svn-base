package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;

import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SummaryDTO {

	public SummaryDTO() {
	}

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal recurringCharges = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal valueAddedCharges = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal usageCharges = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal internetusageCharges = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal onetimeCharges = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal discountsOrAdjustments = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal totalTax = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal lateFee = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal serviceTax = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal sbAndCESSTax = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal totalCharges = BigDecimal.ZERO;
	
	private String chargeTypeFlag ;

	public String getChargeTypeFlag() {
		return chargeTypeFlag;
	}

	public void setChargeTypeFlag(String chargeTypeFlag) {
		this.chargeTypeFlag = chargeTypeFlag;
	}

	public BigDecimal getRecurringCharges() {
		return recurringCharges;
	}

	public void setRecurringCharges(BigDecimal recurringCharges) {
		this.recurringCharges = recurringCharges;
	}

	public BigDecimal getUsageCharges() {
		return usageCharges;
	}

	public void setUsageCharges(BigDecimal usageCharges) {
		this.usageCharges = usageCharges;
	}

	public BigDecimal getOnetimeCharges() {
		return onetimeCharges;
	}

	public void setOnetimeCharges(BigDecimal onetimeCharges) {
		this.onetimeCharges = onetimeCharges;
	}

	public BigDecimal getDiscountsOrAdjustments() {
		return discountsOrAdjustments;
	}

	public void setDiscountsOrAdjustments(BigDecimal discountsOrAdjustments) {
		this.discountsOrAdjustments = discountsOrAdjustments;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public BigDecimal getLateFee() {
		return lateFee;
	}

	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}

	public BigDecimal getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(BigDecimal serviceTax) {
		this.serviceTax = serviceTax;
	}

	public BigDecimal getSbAndCESSTax() {
		return sbAndCESSTax;
	}

	public void setSbAndCESSTax(BigDecimal sbAndCESSTax) {
		this.sbAndCESSTax = sbAndCESSTax;
	}

	/**
	 * @return the totalCharges
	 */
	public BigDecimal getTotalCharges() {
		return recurringCharges.add(usageCharges).add(onetimeCharges)
				.add(discountsOrAdjustments).add(totalTax).add(lateFee);
	}

	/**
	 * @param totalCharges the totalCharges to set
	 */
	public void setTotalCharges(BigDecimal totalCharges) {
		this.totalCharges = totalCharges;
	}

	public BigDecimal getValueAddedCharges() {
		return valueAddedCharges;
	}

	public void setValueAddedCharges(BigDecimal valueAddedCharges) {
		this.valueAddedCharges = valueAddedCharges;
	}

	public BigDecimal getInternetusageCharges() {
		return internetusageCharges;
	}

	public void setInternetusageCharges(BigDecimal internetusageCharges) {
		this.internetusageCharges = internetusageCharges;
	}

}
