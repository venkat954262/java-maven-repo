package com.arbiva.apfgc.invoice.utils;

/**
 * 
 * @author srinivasa
 *
 */
public class InvoiceJsonRequest {

	public InvoiceJsonRequest() {
	}

	public InvoiceJsonRequest(String accountNumber, String billPeriod,
			Object json, String baseFilePath, String districtId) {
		setAccountNumber(accountNumber);
		setBillPeriod(billPeriod);
		setJson(json);
		setBaseFilePath(baseFilePath);
		setDistrictId(districtId);
	}

	private String accountNumber;

	private String billPeriod;

	private Object json;

	private String baseFilePath;
	
	private String districtId;
	
	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBillPeriod() {
		return billPeriod;
	}

	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}

	public Object getJson() {
		return json;
	}

	public void setJson(Object json) {
		this.json = json;
	}

	public String getBaseFilePath() {
		return baseFilePath;
	}

	public void setBaseFilePath(String baseFilePath) {
		this.baseFilePath = baseFilePath;
	}

	public String getInvoiceJsonFilePath() {
		return String.format(InvoiceEngineConstants.INVOICE_JSON_FILE_PATH, baseFilePath, billPeriod, "jsons", districtId, 
				accountNumber, billPeriod);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result
				+ ((baseFilePath == null) ? 0 : baseFilePath.hashCode());
		result = prime * result
				+ ((billPeriod == null) ? 0 : billPeriod.hashCode());
		result = prime * result + ((json == null) ? 0 : json.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceJsonRequest other = (InvoiceJsonRequest) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (baseFilePath == null) {
			if (other.baseFilePath != null)
				return false;
		} else if (!baseFilePath.equals(other.baseFilePath))
			return false;
		if (billPeriod == null) {
			if (other.billPeriod != null)
				return false;
		} else if (!billPeriod.equals(other.billPeriod))
			return false;
		if (json == null) {
			if (other.json != null)
				return false;
		} else if (!json.equals(other.json))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvoiceJsonRequest [accountNumber=" + accountNumber
				+ ", billPeriod=" + billPeriod + ", json=" + json
				+ ", baseFilePath=" + baseFilePath + "]";
	}

}
