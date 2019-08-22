package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.arbiva.apfgc.invoice.utils.JsonDateSerializer;
import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class EnterpriseBillInfoDTO {

	public EnterpriseBillInfoDTO() {
	}

	private String enterpriseCustomerId;
	
	private String paymentCustomerId;
	
	private String customerType;
	
	private String phoneNumber;
	
	private String billNumber;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	private String billDate;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	private String billFromDate;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	private String billToDate;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	private String dueDate;

	private String fullName;

	private String address;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal prevBalance = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal lastPayment = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal balanceAmount = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal adjustments = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal currentBillAmount = BigDecimal.ZERO;

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal amountPayable = BigDecimal.ZERO;

	private List<PaymentDTO> payments;
	
	private List<CurrentChargesSummaryDTO> allAccountCurrentCharges;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal serviceTax = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal otherTax = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal entTax = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal allAccountCurrentChargesTotalAmount = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal allAccountCurrentChargesTotalTax = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal allAccountCurrentChargesTotalofTotalAmount = BigDecimal.ZERO;
	
	private List<BillInfoDTO> individualBillInfoList;

	private String invMonth;
	
	private String invYear;
	
	private String billType;
	
	/**Added for customer subtype**/
	private String customerSubType;
	
	
	public String getInvMonth() {
		return invMonth;
	}

	public void setInvMonth(String invMonth) {
		this.invMonth = invMonth;
	}

	public String getInvYear() {
		return invYear;
	}

	public void setInvYear(String invYear) {
		this.invYear = invYear;
	}

	public BigDecimal getEntTax() {
		return entTax;
	}

	public void setEntTax(BigDecimal entTax) {
		this.entTax = entTax;
	}

	public String getEnterpriseCustomerId() {
		return enterpriseCustomerId;
	}

	public void setEnterpriseCustomerId(String enterpriseCustomerId) {
		this.enterpriseCustomerId = enterpriseCustomerId;
	}

	public String getPaymentCustomerId() {
		return paymentCustomerId;
	}

	public void setPaymentCustomerId(String paymentCustomerId) {
		this.paymentCustomerId = paymentCustomerId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getPrevBalance() {
		return prevBalance;
	}

	public void setPrevBalance(BigDecimal prevBalance) {
		this.prevBalance = prevBalance;
	}

	public BigDecimal getLastPayment() {
		return lastPayment;
	}

	public void setLastPayment(BigDecimal lastPayment) {
		this.lastPayment = lastPayment;
	}

	public BigDecimal getBalanceAmount() {
		balanceAmount = prevBalance.subtract(lastPayment);
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public BigDecimal getAdjustments() {
		return adjustments;
	}

	public void setAdjustments(BigDecimal adjustments) {
		this.adjustments = adjustments;
	}

	public BigDecimal getCurrentBillAmount() {
		return currentBillAmount;
	}

	public void setCurrentBillAmount(BigDecimal currentBillAmount) {
		this.currentBillAmount = currentBillAmount;
	}

	public BigDecimal getAmountPayable() {
		amountPayable = currentBillAmount.add(balanceAmount).subtract(adjustments);
		return amountPayable;
	}

	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}

	public List<PaymentDTO> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentDTO> payments) {
		this.payments = payments;
	}

	public List<CurrentChargesSummaryDTO> getAllAccountCurrentCharges() {
		return allAccountCurrentCharges;
	}

	public void setAllAccountCurrentCharges(List<CurrentChargesSummaryDTO> allAccountCurrentCharges) {
		this.allAccountCurrentCharges = allAccountCurrentCharges;
	}

	public BigDecimal getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(BigDecimal serviceTax) {
		this.serviceTax = serviceTax;
	}

	public BigDecimal getOtherTax() {
		return otherTax;
	}

	public void setOtherTax(BigDecimal otherTax) {
		this.otherTax = otherTax;
	}

	public BigDecimal getAllAccountCurrentChargesTotalAmount() {
		return allAccountCurrentChargesTotalAmount;
	}

	public void setAllAccountCurrentChargesTotalAmount(BigDecimal allAccountCurrentChargesTotalAmount) {
		this.allAccountCurrentChargesTotalAmount = allAccountCurrentChargesTotalAmount;
	}

	public BigDecimal getAllAccountCurrentChargesTotalTax() {
		return allAccountCurrentChargesTotalTax;
	}

	public void setAllAccountCurrentChargesTotalTax(BigDecimal allAccountCurrentChargesTotalTax) {
		this.allAccountCurrentChargesTotalTax = allAccountCurrentChargesTotalTax;
	}

	public BigDecimal getAllAccountCurrentChargesTotalofTotalAmount() {
		return allAccountCurrentChargesTotalofTotalAmount;
	}

	public void setAllAccountCurrentChargesTotalofTotalAmount(BigDecimal allAccountCurrentChargesTotalofTotalAmount) {
		this.allAccountCurrentChargesTotalofTotalAmount = allAccountCurrentChargesTotalofTotalAmount;
	}

	public List<BillInfoDTO> getIndividualBillInfoList() {
		return individualBillInfoList;
	}

	public void setIndividualBillInfoList(List<BillInfoDTO> individualBillInfoList) {
		this.individualBillInfoList = individualBillInfoList;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getBillFromDate() {
		return billFromDate;
	}

	public void setBillFromDate(String billFromDate) {
		this.billFromDate = billFromDate;
	}

	public String getBillToDate() {
		return billToDate;
	}

	public void setBillToDate(String billToDate) {
		this.billToDate = billToDate;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}
	
	/**Added for Customer Sub type**/
	public String getCustomerSubType() {
		return customerSubType;
	}

	public void setCustomerSubType(String customerSubType) {
		this.customerSubType = customerSubType;
	}
	/****/
	
	
}
