package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;

import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class TaxDTO {
	
	public TaxDTO(){
		
	}
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal serviceTax = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal swatchTax = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal kisantax = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal enttax = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal allTax = BigDecimal.ZERO;

	
	public BigDecimal getAllTax() {
		return allTax;
	}

	public void setAllTax(BigDecimal allTax) {
		this.allTax = allTax;
	}

	public BigDecimal getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(BigDecimal serviceTax) {
		this.serviceTax = serviceTax;
	}

	public BigDecimal getSwatchTax() {
		return swatchTax;
	}

	public void setSwatchTax(BigDecimal swatchTax) {
		this.swatchTax = swatchTax;
	}

	public BigDecimal getKisantax() {
		return kisantax;
	}

	public void setKisantax(BigDecimal kisantax) {
		this.kisantax = kisantax;
	}

	public BigDecimal getEnttax() {
		return enttax;
	}

	public void setEnttax(BigDecimal enttax) {
		this.enttax = enttax;
	}
	
	
	

}
