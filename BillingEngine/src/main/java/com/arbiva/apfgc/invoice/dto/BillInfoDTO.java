package com.arbiva.apfgc.invoice.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.arbiva.apfgc.invoice.utils.JsonDateSerializer;
import com.arbiva.apfgc.invoice.utils.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class BillInfoDTO {

	public BillInfoDTO() {
	}

	private String customerId;
	
	private String customerType;

	private String accountNumber;

	private String billNumber;

	private String fullName;

	private String address;

	private String phoneNumber;
	
	private String parentCustCode;
	
	private String totalBalance;
	

	@JsonSerialize(using = JsonDateSerializer.class)
	private String billDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	private String billPeriodFrom;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	private String billPeriodTo;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	private String dueDate;

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

	private List<PackageDTO> packages;

	private SummaryDTO summary;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal totalRecurringCharges = BigDecimal.ZERO;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal totalOneTimeCharges = BigDecimal.ZERO;

	private List<RecurringDTO> recurringCharges;
	private List<ValueAddedServiceDTO> valueaddedCharges;

	private List<DataUsageDTO> dataUsages;

	private List<TelephoneUsageDTO> telephoneUsages;

	private List<UsageDTO> offnetLocalSummary;

	private List<UsageDTO> offnetSTDSummary;

	private List<UsageDTO> offnetISDSummary;
	
	private List<UsageDTO> offnetMobileSummary;

	private List<OtherChargesDTO> vodOrMovieCharges;

	private List<OnetimeChargesDTO> onetimeCharges;

	private List<AdjustmentDTO> discOrAdjustemnts;
	
	private TaxDTO taxDTO;
	
	private String invMonth;
	
	private String invYear;
	
	private String email;

	private String textMessage;
	
	private String filePath;
	
	private int smsflag;
	
	private int emailflag;
	
	private BigInteger pmntCustId;
	
	private String emailText;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal invamtwithtax = BigDecimal.ZERO;
	
	/**Added for Changing PDF bill as per revenue share */
	private Map<String,RevenueShareDTO> revenueShareDTO;
	
	public String getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getSmsflag() {
		return smsflag;
	}

	public void setSmsflag(int smsflag) {
		this.smsflag = smsflag;
	}

	public int getEmailflag() {
		return emailflag;
	}

	public void setEmailflag(int emailflag) {
		this.emailflag = emailflag;
	}

	public BigInteger getPmntCustId() {
		return pmntCustId;
	}

	public void setPmntCustId(BigInteger pmntCustId) {
		this.pmntCustId = pmntCustId;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

	public BigDecimal getInvamtwithtax() {
		return invamtwithtax;
	}

	public void setInvamtwithtax(BigDecimal invamtwithtax) {
		this.invamtwithtax = invamtwithtax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public BigDecimal getTotalOneTimeCharges() {
		totalOneTimeCharges = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(onetimeCharges)) {
			for (OnetimeChargesDTO onetimeChargesDTO : onetimeCharges) {
				totalOneTimeCharges = totalOneTimeCharges.add(onetimeChargesDTO.getCharges());
			}
		}
		return totalOneTimeCharges;
	}

	public void setTotalOneTimeCharges(BigDecimal totalOneTimeCharges) {
		this.totalOneTimeCharges = totalOneTimeCharges;
	}

	public TaxDTO getTaxDTO() {
		return taxDTO;
	}

	public void setTaxDTO(TaxDTO taxDTO) {
		this.taxDTO = taxDTO;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}	

	public String getParentCustCode() {
		return parentCustCode;
	}

	public void setParentCustCode(String parentCustCode) {
		this.parentCustCode = parentCustCode;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getBillPeriodFrom() {
		return billPeriodFrom;
	}

	public void setBillPeriodFrom(String billPeriodFrom) {
		this.billPeriodFrom = billPeriodFrom;
	}

	public String getBillPeriodTo() {
		return billPeriodTo;
	}

	public void setBillPeriodTo(String billPeriodTo) {
		this.billPeriodTo = billPeriodTo;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
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
	
	/**
	 * @return the offnetMobileSummary
	 */
	public List<UsageDTO> getOffnetMobileSummary() {
		return offnetMobileSummary;
	}

	/**
	 * @param offnetMobileSummary the offnetMobileSummary to set
	 */
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
	
	public List<ValueAddedServiceDTO> getValueaddedCharges() {
		return valueaddedCharges;
	}

	public void setValueaddedCharges(List<ValueAddedServiceDTO> valueaddedCharges) {
		this.valueaddedCharges = valueaddedCharges;
	}

	/**
	 * @return the totalRecurringCharges
	 */
	public BigDecimal getTotalRecurringCharges() {
		totalRecurringCharges = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(recurringCharges)) {
			for (RecurringDTO recurringDTO : recurringCharges) {
				totalRecurringCharges = totalRecurringCharges.add(recurringDTO.getCharges());
			}
		}
		return totalRecurringCharges;
	}

	/**
	 * @param totalRecurringCharges the totalRecurringCharges to set
	 */
	public void setTotalRecurringCharges(BigDecimal totalRecurringCharges) {
		this.totalRecurringCharges = totalRecurringCharges;
	}


	public Map<String, RevenueShareDTO> getRevenueShareDTO() {
		return revenueShareDTO;
	}

	public void setRevenueShareDTO(Map<String, RevenueShareDTO> revenueShareDTO) {
		this.revenueShareDTO = revenueShareDTO;
	}

	@Override
	public String toString() {
		return "BillInfoDTO [customerId=" + customerId + ", customerType=" + customerType + ", accountNumber="
				+ accountNumber + ", billNumber=" + billNumber + ", fullName=" + fullName + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", parentCustCode=" + parentCustCode + ", totalBalance="
				+ totalBalance + ", billDate=" + billDate + ", billPeriodFrom=" + billPeriodFrom + ", billPeriodTo="
				+ billPeriodTo + ", dueDate=" + dueDate + ", prevBalance=" + prevBalance + ", lastPayment="
				+ lastPayment + ", balanceAmount=" + balanceAmount + ", adjustments=" + adjustments
				+ ", currentBillAmount=" + currentBillAmount + ", amountPayable=" + amountPayable + ", payments="
				+ payments + ", packages=" + packages + ", summary=" + summary + ", totalRecurringCharges="
				+ totalRecurringCharges + ", totalOneTimeCharges=" + totalOneTimeCharges + ", recurringCharges="
				+ recurringCharges + ", valueaddedCharges=" + valueaddedCharges + ", dataUsages=" + dataUsages
				+ ", telephoneUsages=" + telephoneUsages + ", offnetLocalSummary=" + offnetLocalSummary
				+ ", offnetSTDSummary=" + offnetSTDSummary + ", offnetISDSummary=" + offnetISDSummary
				+ ", offnetMobileSummary=" + offnetMobileSummary + ", vodOrMovieCharges=" + vodOrMovieCharges
				+ ", onetimeCharges=" + onetimeCharges + ", discOrAdjustemnts=" + discOrAdjustemnts + ", taxDTO="
				+ taxDTO + ", invMonth=" + invMonth + ", invYear=" + invYear + ", email=" + email + ", textMessage="
				+ textMessage + ", filePath=" + filePath + ", smsflag=" + smsflag + ", emailflag=" + emailflag
				+ ", pmntCustId=" + pmntCustId + ", emailText=" + emailText + ", invamtwithtax=" + invamtwithtax
				+ ", revenueShareDTO=" + revenueShareDTO + "]";
	}


	/*@Override
	public String toString() {
		return "BillInfoDTO [customerId=" + customerId + ", accountNumber="
				+ accountNumber + ", billNumber=" + billNumber + ", fullName="
				+ fullName + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", billDate=" + billDate + ", billPeriodFrom="
				+ billPeriodFrom + ", billPeriodTo=" + billPeriodTo + ", dueDate=" + dueDate + ", prevBalance="
				+ prevBalance + ", lastPayment=" + lastPayment
				+ ", balanceAmount=" + balanceAmount + ", adjustments="
				+ adjustments + ", currentBillAmount=" + currentBillAmount
				+ ", amountPayable=" + amountPayable + ", payments=" + payments
				+ ", packages=" + packages + ", summary=" + summary
				+ ", recurringCharges=" + recurringCharges + ", dataUsage="
				+ dataUsages + ", telephoneUsage=" + telephoneUsages
				+ ", offnetLocalSummary=" + offnetLocalSummary
				+ ", offnetSTDSummary=" + offnetSTDSummary
				+ ", offnetISDSummary=" + offnetISDSummary
				+ ", vodOrMovieCharges=" + vodOrMovieCharges
				+ ", onetimeCharges=" + onetimeCharges + ", discOrAdjustemnts="
				+ discOrAdjustemnts + "]";
	}*/
}
