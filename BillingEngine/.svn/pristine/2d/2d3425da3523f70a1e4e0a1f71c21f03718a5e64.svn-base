package com.arbiva.apfgc.invoice.businessservice;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.arbiva.apfgc.invoice.dto.BillInfoDTO;
import com.arbiva.apfgc.invoice.dto.CafDetailsDTO;
import com.arbiva.apfgc.invoice.dto.CorpusUpdate;
import com.arbiva.apfgc.invoice.dto.EnterpriseBillInfoDTO;
import com.arbiva.apfgc.invoice.dto.InvCustIdDTO;
import com.arbiva.apfgc.invoice.dto.RevenueShareDTO;

/**
 *
 * 
 * @author srinivasa
 *
 */
@Component("invoiceGeneratorBusinessService")
public interface InvoiceGeneratorBusinessService {

	public int getInvoiceNumberCount(String districtId, int month, int year);
	
	public BillInfoDTO getIndividualInvoiceDetails(final String invNumber,String yearMonth);
	
	public String getInvoiceTemplate(String accountNumber, String billPeriod, String districtuid);
	
	public List<InvCustIdDTO> getInvoiceNumbers(String districtId, int month, int year);
	
	public int getInvoiceChargeCafsCount(String districtId,int yearMonth);
	
	public int getInvoiceEntCafsCount(String year,String month);
	
	public int getInvoiceEntPrivateCafsCount(String year,String month);
	
	public List<CafDetailsDTO> getEntCafDetails(String year,String month);
	
	public List<CafDetailsDTO> getEntPrivateCafDetails(String year,String month);
	
	public List<String> getCafChargeDetails(String districtId,int yearMonth);
	
	public String executeCAFChargeProcess(String districtId, Long cafNo, int yearMonth);
	
	public void processInvoiceChargeCalculations(String districtId, int yearMonth);
	
	public void processEnterpriseGovt(int yearMonth);
	
	public void processEnterprisePrivate(int yearMonth);
	
	public void generateCafInvoices(String districtId, int month, int year, String yearMonth);
	
	public void generateBillInvoices(String districtId, int month, int year, String yearMonth);
	
	public void generateEntGovtCafInvoices(int month, int year, String yearMonth);
	
	public void generateEntPrivateCafInvoices(int month, int year, String yearMonth);
	
	public List<InvCustIdDTO> getInvoiceNumbersEntPrivate(int month, int year);
	
	public int getEntGovtInvoiceNumberCount(int month, int year);
	
	public List<InvCustIdDTO> getInvoiceNumbersEntGovt(int month, int year);
	
	public EnterpriseBillInfoDTO getConsolidatedInvoiceDetails(final String invNumber);
	
	public int getEntPrivateInvoiceNumberCount(int month, int year);
	
	public void updatePdfPathInCustInv(String year, String month, String custId, String fileLocation);
	
	public InvCustIdDTO getInvoiceNumbersOFIndividuals_CSS(String customerID, int month, int year);
	 

	List<BillInfoDTO> sendInvoiceSMS(int year, int tmonth);
	
	public List<CorpusUpdate> getCustomerID(String customerID);
	public List<CorpusUpdate> getSubList(String customerID) ;
	void updateCustinvForSmsAndEmail(BillInfoDTO bdto);

	List<String> getNoBillCafChargeDetails();

	public void generateCafInvoicesForSingleCustomer(String districtuid, int month, int year, String yearMonth,
			String customerId);

	int getInvoiceNumberCount(String districtId, int month, int year, int customerId);

	List<InvCustIdDTO> getInvoiceNumbers(String districtId, int month, int year, int customerId);
	
	public void generateEntGovtCafInvoices(int month, int year, String yearMonth, int customerId);
	
	public void generateEntPrivateCafInvoices(int month, int year, String yearMonth, int customerId);

	int getEntGovtInvoiceNumberCount(int month, int year, int customerId);

	int getEntPrivateInvoiceNumberCount(int month, int year, int customerId);

	List<InvCustIdDTO> getInvoiceNumbersEntGovt(int month, int year, int custId);

	List<InvCustIdDTO> getInvoiceNumbersEntPrivate(int month, int year, int custId);
	
	public String customerPDFGeneration(String customerId, int month, int year,String custTypeLOV,boolean detailedflag, String detailType, int districtId);
	
	/**Added for Showing revenue share in PDF*/
	Map<String,RevenueShareDTO> getRevenueShareDetails(String invnumber);
	
	public BillInfoDTO getIndividualInvoiceDetails_css(final String invNumber, String yearMonth);
	public BillInfoDTO getIndividualInvoiceDetails_css_Itemised(final String invNumber,String detailType, String yearMonth);

}
