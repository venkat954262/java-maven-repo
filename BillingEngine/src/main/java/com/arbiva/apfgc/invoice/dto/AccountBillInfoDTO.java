package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;
import java.util.List;

import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class AccountBillInfoDTO {

	public AccountBillInfoDTO() {
	}

	private String accountNumber;

	private List<PackageDTO> packages;

	private SummaryDTO summary;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal totalRecurringCharges = BigDecimal.ZERO;

	private List<RecurringDTO> recurringCharges;

	private List<DataUsageDTO> dataUsages;

	private List<TelephoneUsageDTO> telephoneUsages;

	private List<UsageDTO> offnetLocalSummary;

	private List<UsageDTO> offnetSTDSummary;

	private List<UsageDTO> offnetISDSummary;
	
	private List<UsageDTO> offnetMobileSummary;

	private List<OtherChargesDTO> vodOrMovieCharges;

	private List<OnetimeChargesDTO> onetimeCharges;

	private List<AdjustmentDTO> discOrAdjustemnts;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<PackageDTO> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageDTO> packages) {
		this.packages = packages;
	}

	public SummaryDTO getSummary() {
		return summary;
	}

	public void setSummary(SummaryDTO summary) {
		this.summary = summary;
	}

	public BigDecimal getTotalRecurringCharges() {
		return totalRecurringCharges;
	}

	public void setTotalRecurringCharges(BigDecimal totalRecurringCharges) {
		this.totalRecurringCharges = totalRecurringCharges;
	}

	public List<RecurringDTO> getRecurringCharges() {
		return recurringCharges;
	}

	public void setRecurringCharges(List<RecurringDTO> recurringCharges) {
		this.recurringCharges = recurringCharges;
	}

	public List<DataUsageDTO> getDataUsages() {
		return dataUsages;
	}

	public void setDataUsages(List<DataUsageDTO> dataUsages) {
		this.dataUsages = dataUsages;
	}

	public List<TelephoneUsageDTO> getTelephoneUsages() {
		return telephoneUsages;
	}

	public void setTelephoneUsages(List<TelephoneUsageDTO> telephoneUsages) {
		this.telephoneUsages = telephoneUsages;
	}

	public List<UsageDTO> getOffnetLocalSummary() {
		return offnetLocalSummary;
	}

	public void setOffnetLocalSummary(List<UsageDTO> offnetLocalSummary) {
		this.offnetLocalSummary = offnetLocalSummary;
	}

	public List<UsageDTO> getOffnetSTDSummary() {
		return offnetSTDSummary;
	}

	public void setOffnetSTDSummary(List<UsageDTO> offnetSTDSummary) {
		this.offnetSTDSummary = offnetSTDSummary;
	}

	public List<UsageDTO> getOffnetISDSummary() {
		return offnetISDSummary;
	}

	public void setOffnetISDSummary(List<UsageDTO> offnetISDSummary) {
		this.offnetISDSummary = offnetISDSummary;
	}

	public List<UsageDTO> getOffnetMobileSummary() {
		return offnetMobileSummary;
	}

	public void setOffnetMobileSummary(List<UsageDTO> offnetMobileSummary) {
		this.offnetMobileSummary = offnetMobileSummary;
	}

	public List<OtherChargesDTO> getVodOrMovieCharges() {
		return vodOrMovieCharges;
	}

	public void setVodOrMovieCharges(List<OtherChargesDTO> vodOrMovieCharges) {
		this.vodOrMovieCharges = vodOrMovieCharges;
	}

	public List<OnetimeChargesDTO> getOnetimeCharges() {
		return onetimeCharges;
	}

	public void setOnetimeCharges(List<OnetimeChargesDTO> onetimeCharges) {
		this.onetimeCharges = onetimeCharges;
	}

	public List<AdjustmentDTO> getDiscOrAdjustemnts() {
		return discOrAdjustemnts;
	}

	public void setDiscOrAdjustemnts(List<AdjustmentDTO> discOrAdjustemnts) {
		this.discOrAdjustemnts = discOrAdjustemnts;
	}
		
}
