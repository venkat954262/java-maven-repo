package com.arbiva.apfgc.invoice.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.arbiva.apfgc.invoice.businessservice.InvoiceGeneratorBusinessService;
import com.arbiva.apfgc.invoice.dto.BillInfoDTO;
import com.arbiva.apfgc.invoice.dto.EnterpriseBillInfoDTO;
import com.arbiva.apfgc.invoice.dto.RevenueShareDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * 
 * @author srinivasa
 *
 */
public class InvoiceEngineFuture implements Callable<Boolean> {
	
	private static final Logger LOGGER = Logger.getLogger(InvoiceEngineFuture.class);
	
	private InvoiceGeneratorBusinessService invoiceGeneratorBusinessService;
	
	private String invNumber;
	
	private String billPeriod;
	
	private String pmntCustId;
	
	private String baseFilePath;
	
	private String districtId;
	
	private String custTypeLOV;
	
	public InvoiceEngineFuture() {
	}
	
	public InvoiceEngineFuture(InvoiceGeneratorBusinessService invoiceGeneratorBusinessService,
			String invNumber, String pmntCustId, String billPeriod, String baseFilePath, String districtId, String yearMonth,String custTypeLOV) {
		this.invoiceGeneratorBusinessService = invoiceGeneratorBusinessService;
		this.invNumber = invNumber;
		this.pmntCustId = pmntCustId;
		//this.billPeriod = billPeriod;
		this.baseFilePath = baseFilePath;
		this.districtId = districtId;
		this.billPeriod = yearMonth;
		this.custTypeLOV = custTypeLOV;
	}

	/*@Override
	public Boolean call() throws Exception {
		InvoiceJsonRequest request = null;
		String individualInvNos []= null;
		BillInfoDTO billInfoDTO = null;
		EnterpriseBillInfoDTO enterpriseBillInfoDTO = null;
		
		try {
			individualInvNos = invNumber.split(",");
			if("INDIVIDUAL".equalsIgnoreCase(custTypeLOV))
			{
				if(individualInvNos.length == 1){//INDIVIDUAL 
					billInfoDTO = invoiceGeneratorBusinessService.getIndividualInvoiceDetails(invNumber);
					
					request = new InvoiceJsonRequest("I"+pmntCustId, billPeriod, billInfoDTO, baseFilePath, districtId);
					JsonUtils.writeJson(request);
				}
				else if(individualInvNos.length > 1){//ENTERPRISE
					for(int i=0;i<individualInvNos.length;i++)
					{
						billInfoDTO = invoiceGeneratorBusinessService.getIndividualInvoiceDetails(individualInvNos[i]);
						
						request = new InvoiceJsonRequest("I"+pmntCustId+"-"+i, billPeriod, billInfoDTO, baseFilePath, districtId);
						JsonUtils.writeJson(request);
					}
				}
			}
			else{
				
				enterpriseBillInfoDTO = invoiceGeneratorBusinessService.getConsolidatedInvoiceDetails(invNumber);
				
				request = new InvoiceJsonRequest("E"+pmntCustId, billPeriod, enterpriseBillInfoDTO, baseFilePath, districtId);
				JsonUtils.writeJson(request);
			}
			
		}
		catch (Exception e) {
			LOGGER.error(
					String.format("Error occured while generating invoice json for an invoice:%s on billPeriod:%s",
							invNumber, billPeriod));
		}
		finally{
			request = null;
			individualInvNos = null;
		}
		return true;
	}*/
	
	///*	
	@Override
	public Boolean call() throws Exception {
		String individualInvNos []= null;
		BillInfoDTO billInfoDTO = null;
		EnterpriseBillInfoDTO enterpriseBillInfoDTO = null;
		Map<String,RevenueShareDTO> revenueShareDTO = null;
		
		try {
			individualInvNos = invNumber.split(",");
			if("INDIVIDUAL".equalsIgnoreCase(custTypeLOV))
			{
				if(individualInvNos.length == 1){//INDIVIDUAL 
					billInfoDTO = invoiceGeneratorBusinessService.getIndividualInvoiceDetails(invNumber,billPeriod);
					/** modified for Changing PDF bill as per revenue share mohit*/
					revenueShareDTO = invoiceGeneratorBusinessService.getRevenueShareDetails(invNumber);
					billInfoDTO.setRevenueShareDTO(revenueShareDTO);
					/***/
					LOGGER.info("In Individual");
					PDFBuilder pdfBuilder = new PDFBuilder();
					Document doc = new Document();
					//PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("F://APFIBER_Revised_Logo1.pdf"));
					String directory = baseFilePath+billPeriod+File.separator+districtId;
					String fileLocation = directory+File.separator+"I"+pmntCustId+"_bill_period_"+billPeriod+".pdf";
					File f = new File(directory);
					if(!f.exists())
					{
						f.mkdirs();
					}
					LOGGER.info(f.getAbsolutePath()+"Absoulte Path");
					PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileLocation));
					LOGGER.info("Before calling");
					//billInfoDTO.setBillType("I");
					pdfBuilder.generatePDF(billInfoDTO,doc,writer);
					invoiceGeneratorBusinessService.updatePdfPathInCustInv(billInfoDTO.getInvYear(),billInfoDTO.getInvMonth(),billInfoDTO.getParentCustCode(),fileLocation);
				}
				else if(individualInvNos.length > 1){//ENTERPRISE
					/*for(int i=0;i<individualInvNos.length;i++)
					{
						billInfoDTO = invoiceGeneratorBusinessService.getIndividualInvoiceDetails(individualInvNos[i]);
						
						LOGGER.info("In Individual");
						PDFBuilder pdfBuilder = new PDFBuilder();
						Document doc = new Document();
						//PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("F://APFIBER_Revised_Logo1.pdf"));
						String directory = baseFilePath+billPeriod+"//billsSample//"+districtId;
						File f = new File(directory);
						if(!f.exists())
						{
							f.mkdirs();
						}
						LOGGER.info(f.getAbsolutePath()+"Absoulte Path");
						PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(directory+"//"+"I"+pmntCustId+"-"+i+"_bill_period_"+billPeriod+".pdf"));
						LOGGER.info("Before calling");
						pdfBuilder.generatePDF(billInfoDTO,doc,writer);
						LOGGER.info("After calling");
					}*/
					
					enterpriseBillInfoDTO = invoiceGeneratorBusinessService.getConsolidatedInvoiceDetails(invNumber);
					
					LOGGER.info("In Consolidated");
					PDFBuilder pdfBuilder = new PDFBuilder();
					Document doc = new Document();
					String directory = baseFilePath+billPeriod+File.separator+districtId;
					String fileLocation = directory+File.separator+"C"+pmntCustId+"_bill_period_"+billPeriod+".pdf";
					File f = new File(directory);
					if(!f.exists())
					{
						f.mkdirs();
					}
					PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileLocation));
					enterpriseBillInfoDTO.setBillType("C");
					pdfBuilder.generatePDFForEnterprise(enterpriseBillInfoDTO,doc,writer);
					invoiceGeneratorBusinessService.updatePdfPathInCustInv(enterpriseBillInfoDTO.getInvYear(),enterpriseBillInfoDTO.getInvMonth(),enterpriseBillInfoDTO.getPaymentCustomerId(),fileLocation);
				}
			}
			else{
				
				enterpriseBillInfoDTO = invoiceGeneratorBusinessService.getConsolidatedInvoiceDetails(invNumber);
				
				
					
					LOGGER.info("In ENTERPRISE");
					PDFBuilder pdfBuilder = new PDFBuilder();
					Document doc = new Document();
					String directory = baseFilePath+billPeriod+File.separator+districtId;
					String fileLocation = directory+File.separator+"E"+pmntCustId+"_bill_period_"+billPeriod+".pdf";
					File f = new File(directory);
					if(!f.exists())
					{
						f.mkdirs();
					}
					PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileLocation));
					enterpriseBillInfoDTO.setBillType("E");
					pdfBuilder.generatePDFForEnterprise(enterpriseBillInfoDTO,doc,writer);
					
					invoiceGeneratorBusinessService.updatePdfPathInCustInv(enterpriseBillInfoDTO.getInvYear(),enterpriseBillInfoDTO.getInvMonth(),enterpriseBillInfoDTO.getPaymentCustomerId(),fileLocation);
				}
				
			
		}
		catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(
					String.format("Error occured while generating invoice json for an invoice:%s on billPeriod:%s",
							invNumber, billPeriod));
		}
		finally{
			individualInvNos = null;
		}
		return true;
	}
//*/
	}
