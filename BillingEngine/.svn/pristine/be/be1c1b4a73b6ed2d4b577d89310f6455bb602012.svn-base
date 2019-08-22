package com.arbiva.apfgc.invoice.businessserviceimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.arbiva.apfgc.invoice.businessservice.InvoiceGeneratorBusinessService;
import com.arbiva.apfgc.invoice.dao.CommonDAO;
import com.arbiva.apfgc.invoice.dto.BillInfoDTO;
import com.arbiva.apfgc.invoice.dto.CafDetailsDTO;
import com.arbiva.apfgc.invoice.dto.CorpusUpdate;
import com.arbiva.apfgc.invoice.dto.EnterpriseBillInfoDTO;
import com.arbiva.apfgc.invoice.dto.ErrorMessageDTO;
import com.arbiva.apfgc.invoice.dto.InvCustIdDTO;
import com.arbiva.apfgc.invoice.dto.RevenueShareDTO;
import com.arbiva.apfgc.invoice.exception.InvoiceEngineException;
import com.arbiva.apfgc.invoice.utils.DateUtil;
import com.arbiva.apfgc.invoice.utils.InvoiceEngineConstants;
import com.arbiva.apfgc.invoice.utils.InvoiceEngineErrorCode.InvoiceEngineErrorCodes;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.arbiva.apfgc.invoice.utils.InvoiceEngineFuture;
import com.arbiva.apfgc.invoice.utils.JsonUtils;
import com.arbiva.apfgc.invoice.utils.PDFBuilder;
import com.arbiva.apfgc.invoice.utils.VelocityEngineUtils;

/**
 * 
 * 
 * @author srinivasa
 *
 */
@Component("invoiceGeneratorBusinessServiceImpl")
public class InvoiceGeneratorBusinessServiceImpl implements InvoiceGeneratorBusinessService {

	private static final Logger LOGGER = Logger.getLogger(InvoiceGeneratorBusinessServiceImpl.class);

	@Autowired
	private CommonDAO commonDao;

	@Autowired
	private VelocityEngineUtils velocityEngineUtils;

	@Value("${threadpool.max.size}")
	private String threadpoolMaxSize;

	@Value("${instance.name}")
	private String instance;

	@Value("${invoice.bill.period}")
	private String billPeriod;

	@Value("${invoice.base.filepath}")
	private String invoiceBaseFilePath;
	
	

	/**
	 * 
	 */
	@Override
	public void processInvoiceChargeCalculations(String districtId, int yearMonth) {
		LOGGER.info("Process of Caf Charge Calculations job is started........!");
		ThreadPoolExecutor executor = null;
		List<String> billingCafList = new ArrayList<>();
		try {
			// int invChargeCafsCount =
			// getInvoiceChargeCafsCount(districtId,yearMonth);
			List<String> cafList = getCafChargeDetails(districtId, yearMonth);
			List<String> noBillCafList = getNoBillCafChargeDetails();
			for (String str : cafList) {
				if (!noBillCafList.contains(str))
					billingCafList.add(str);
			}
			
			int invChargeCafsCount = billingCafList.size();
			if (invChargeCafsCount > 0) {
				// int start = 0;
				int count = (invChargeCafsCount > Integer.valueOf(threadpoolMaxSize))
						? Integer.valueOf(threadpoolMaxSize) : invChargeCafsCount;
				executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);
				// while(start < invChargeCafsCount) {
				// List<String> cafList =
				// getCafChargeDetails(districtId,yearMonth);
				// start += count;
				
				for (String cafNo : billingCafList) {
					// CAFChargeCalcEngineFuture future = new
					// CAFChargeCalcEngineFuture(this, districtId,
					// Long.valueOf(cafNo), yearMonth);
					// executor.submit(future);
					this.executeCAFChargeProcess(districtId, Long.valueOf(cafNo), yearMonth);
				}
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while generating caf invoices", e);
		} finally {
			if (executor != null) {
				executor.shutdown();
			}
		}

		LOGGER.info("Generation of caf invoices job is completed........!");
	}
	
	

	@Override
	public List<CorpusUpdate> getCustomerID(String customerID){
		
		
		List<CorpusUpdate> subList = getSubList(customerID);
		
		return subList;
	}
	
	public String customerPDFGeneration(String customerId, int month, int year,String custTypeLOV, boolean detailedflag, String detailType, int districtId){
		String individualInvNos []= null;
		BillInfoDTO billInfoDTO = null;
		EnterpriseBillInfoDTO enterpriseBillInfoDTO = null;
		Map<String,RevenueShareDTO> revenueShareDTO = null;
		LOGGER.info("InvoiceGeneratorBusinessServiceImpl::customerPDFGeneration::getInvoiceNumbersOFIndividuals_CSS::Calling::START");
		InvCustIdDTO invNumbers = getInvoiceNumbersOFIndividuals_CSS(customerId, month, year);
		LOGGER.info("InvoiceGeneratorBusinessServiceImpl::customerPDFGeneration::getInvoiceNumbersOFIndividuals_CSS::Calling::END");
		String fileLocation ="";
		String invNumber=invNumbers.getInvno();
		String yearMonth = "";
		String filePath = null;
		try {
			yearMonth = yearMonth+""+year;
			if(month < 10) {
				yearMonth = yearMonth+"0"+month;
			}else {
				yearMonth = yearMonth+""+month;
			}
			if(invNumber!=null) {
				individualInvNos = invNumber.split(",");
				if("INDIVIDUAL".equalsIgnoreCase(custTypeLOV))
				{
					if(individualInvNos.length == 1){//INDIVIDUAL
						LOGGER.info("In Individual");
						PDFBuilder pdfBuilder = new PDFBuilder();
						Document doc = new Document();
						String directory = invoiceBaseFilePath+yearMonth+File.separator+districtId;
						File f = new File(directory);
						if(!f.exists())
						{
							f.mkdirs();
						}
						
						if(detailType.equalsIgnoreCase("null")) {
							fileLocation = directory+File.separator+"I"+customerId+"_bill_period_"+yearMonth+".pdf";
							filePath = fileLocation.replace('\\', '/');
							f = new File(fileLocation);
							if(!f.exists()) {
								
								LOGGER.info("InvoiceGeneratorBusinessServiceImpl::customerPDFGeneration::getIndividualInvoiceDetails_css::Calling::START");
								billInfoDTO = getIndividualInvoiceDetails_css(invNumber,yearMonth);
								LOGGER.info("InvoiceGeneratorBusinessServiceImpl::customerPDFGeneration::getIndividualInvoiceDetails_css::Calling::END");
								
								/** modified for Changing PDF bill as per revenue share mohit*/
								LOGGER.info("InvoiceGeneratorBusinessServiceImpl::customerPDFGeneration::getRevenueShareDetails::Calling::START");
								revenueShareDTO = getRevenueShareDetails(invNumber);
								billInfoDTO.setRevenueShareDTO(revenueShareDTO);
								LOGGER.info("InvoiceGeneratorBusinessServiceImpl::customerPDFGeneration::getRevenueShareDetails::Calling::START");
								/***/
								
								LOGGER.info(filePath+"Absoulte Path");
								PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileLocation));
								LOGGER.info("Before calling");
								//billInfoDTO.setBillType("I");
								pdfBuilder.generatePDFWithRevShare(billInfoDTO,doc,writer,detailedflag,detailType);
								updatePdfPathInCustInv(billInfoDTO.getInvYear(),billInfoDTO.getInvMonth(),billInfoDTO.getParentCustCode(),filePath);
							}
						} else {
							fileLocation = directory+File.separator+"I"+customerId+"_bill_period_"+yearMonth+"_"+detailType+".pdf";
							filePath = fileLocation.replace('\\', '/');
							f = new File(fileLocation);
							if(!f.exists()) {
								billInfoDTO = getIndividualInvoiceDetails_css_Itemised(invNumber,detailType,yearMonth);
								LOGGER.info(filePath+"Absoulte Path");
								PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileLocation));
								LOGGER.info("Before calling");
								//billInfoDTO.setBillType("I");
								pdfBuilder.generatePDFWithRevShare(billInfoDTO,doc,writer,detailedflag,detailType);
							}
						}
						/**Commented for not updating in custinv**/
						//updatePdfPathInCustInv(billInfoDTO.getInvYear(),billInfoDTO.getInvMonth(),billInfoDTO.getParentCustCode(),fileLocation);
					}
					else if(individualInvNos.length > 1){//ENTERPRISE
						
						
						enterpriseBillInfoDTO = getConsolidatedInvoiceDetails(invNumber);
						
						LOGGER.info("In Consolidated");
						PDFBuilder pdfBuilder = new PDFBuilder();
						Document doc = new Document();
						String directory = invoiceBaseFilePath+yearMonth+File.separator+districtId;
						fileLocation = directory+File.separator+"C"+customerId+"_bill_period_"+yearMonth+".pdf";
						File f = new File(directory);
						filePath = fileLocation.replace('/', '\\');
						if(!f.exists())
						{
							f.mkdirs();
							PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileLocation));
							enterpriseBillInfoDTO.setBillType("C");
							pdfBuilder.generatePDFForEnterpriseWithRevShare(enterpriseBillInfoDTO,doc,writer);
						}
						/**Commented for not updating in custinv**/
						//updatePdfPathInCustInv(enterpriseBillInfoDTO.getInvYear(),enterpriseBillInfoDTO.getInvMonth(),enterpriseBillInfoDTO.getPaymentCustomerId(),fileLocation);
					}
				}else{
					
					enterpriseBillInfoDTO = getConsolidatedInvoiceDetails(invNumber);
					
					
						
						LOGGER.info("In ENTERPRISE");
						PDFBuilder pdfBuilder = new PDFBuilder();
						Document doc = new Document();
						//String directory = baseFilePath+billPeriod+File.separator+districtId;
						//String fileLocation = directory+File.separator+"E"+pmntCustId+"_bill_period_"+billPeriod+".pdf";
						String directory = invoiceBaseFilePath;
						fileLocation = directory+"Customer.pdf";
						File f = new File(directory);
						if(!f.exists())
						{
							f.mkdirs();
						}
						PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileLocation));
						enterpriseBillInfoDTO.setBillType("E");
						pdfBuilder.generatePDFForEnterpriseWithRevShare(enterpriseBillInfoDTO,doc,writer);
						/**Commented for not updating in custinv**/
						//updatePdfPathInCustInv(enterpriseBillInfoDTO.getInvYear(),enterpriseBillInfoDTO.getInvMonth(),enterpriseBillInfoDTO.getPaymentCustomerId(),fileLocation);
					}
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
		return filePath;
	
	}
	
	
	@Override
	public void processEnterpriseGovt(int yearMonth) {
		LOGGER.info("Process of Caf Charge Calculations job is started........!");
		ThreadPoolExecutor executor = null;
		List<CafDetailsDTO> billingCafList = new ArrayList<>();
		try {
			String yearMon = String.valueOf(yearMonth);

			String year = yearMon.substring(0, 4);
			String month = yearMon.substring(4, 6);

			List<CafDetailsDTO> cafList = getEntCafDetails(year, month);
			List<String> noBillCafList = getNoBillCafChargeDetails();
			
			for (CafDetailsDTO str : cafList) {
				if (!noBillCafList.contains(str.getCafNo()))
					billingCafList.add(str);
			}
			// int invChargeCafsCount = getInvoiceEntCafsCount(year,month);

			int invChargeCafsCount = billingCafList.size();

			if (invChargeCafsCount > 0) {
				// int start = 0;
				int count = (invChargeCafsCount > Integer.valueOf(threadpoolMaxSize))
						? Integer.valueOf(threadpoolMaxSize) : invChargeCafsCount;
				executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);
				// while(start < invChargeCafsCount) {

				// start += count;
				for (CafDetailsDTO cafDetailsDTOObj : billingCafList) {
					// CAFChargeCalcEngineFuture future = new
					// CAFChargeCalcEngineFuture(this,cafDetailsDTOObj.getDistrict(),Long.valueOf(cafDetailsDTOObj.getCafNo()),yearMonth);
					// executor.submit(future);
					this.executeCAFChargeProcess(cafDetailsDTOObj.getDistrict(),
							Long.valueOf(cafDetailsDTOObj.getCafNo()), yearMonth);
				}
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while generating caf invoices", e);
		} finally {
			if (executor != null) {
				executor.shutdown();
			}
		}

		LOGGER.info("Generation of caf invoices job is completed........!");
	}

	@Override
	public void processEnterprisePrivate(int yearMonth) {
		LOGGER.info("Process of Caf Charge Calculations job is started........!");
		ThreadPoolExecutor executor = null;
		List<CafDetailsDTO> billingCafList = new ArrayList<>();
		try {
			String yearMon = String.valueOf(yearMonth);

			String year = yearMon.substring(0, 4);
			String month = yearMon.substring(4, 6);
			List<CafDetailsDTO> cafList = getEntPrivateCafDetails(year, month);
			List<String> noBillCafList = getNoBillCafChargeDetails();
			
			for (CafDetailsDTO str : cafList) {
				if (!noBillCafList.contains(str.getCafNo()))
					billingCafList.add(str);
			}
			// int invChargeCafsCount =
			// getInvoiceEntPrivateCafsCount(year,month);
			int invChargeCafsCount = billingCafList.size();
			if (invChargeCafsCount > 0) {
				// int start = 0;
				int count = (invChargeCafsCount > Integer.valueOf(threadpoolMaxSize))
						? Integer.valueOf(threadpoolMaxSize) : invChargeCafsCount;
				executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);
				// while(start < invChargeCafsCount) {

				// start += count;
				for (CafDetailsDTO cafDetailsDTOObj : billingCafList) {
					// CAFChargeCalcEngineFuture future = new
					// CAFChargeCalcEngineFuture(this,cafDetailsDTOObj.getDistrict(),Long.valueOf(cafDetailsDTOObj.getCafNo()),yearMonth);
					// executor.submit(future);
					this.executeCAFChargeProcess(cafDetailsDTOObj.getDistrict(),
							Long.valueOf(cafDetailsDTOObj.getCafNo()), yearMonth);
				}
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while generating caf invoices", e);
		} finally {
			if (executor != null) {
				executor.shutdown();
			}
		}

		LOGGER.info("Generation of caf invoices job is completed........!");
	}

	/**
	 * 
	 */
	@Override
	public void generateCafInvoices(String districtId, int month, int year, String yearMonth) {
		LOGGER.info("Generation of caf invoices job started........!");
		// int currMonth =
		// Integer.valueOf(DateUtil.getCurrentMonth(InvoiceEngineConstants.MONTH_PATTERN_IN_VALUE));
		// int currYear =
		// Integer.valueOf(DateUtil.getCurrentMonth(InvoiceEngineConstants.YEAR_PATTERN));
		// Date startDate = DateUtil.getStartDateOfMonth(month-1);
		// Date endDate = DateUtil.getEndDateOfMonth(month-1);
		ThreadPoolExecutor executor = null;
		try {
			int invCount = getInvoiceNumberCount(districtId, month, year);
			if (invCount > 0) {
				// int start = 0;
				int count = (invCount > Integer.valueOf(threadpoolMaxSize)) ? Integer.valueOf(threadpoolMaxSize)
						: invCount;
				executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);
				String consolidatedBillPeriod = getConsolidatedBillPeriod();
				// new
				// File(getInvoiceInfoJsonBasePath(consolidatedBillPeriod)).mkdirs();
				// while(start < invCount) {
				List<InvCustIdDTO> invNumbers = getInvoiceNumbers(districtId, month, year);
				// start += count;
				for (InvCustIdDTO inv : invNumbers) {
					InvoiceEngineFuture future = new InvoiceEngineFuture(this, inv.getInvno(), inv.getPmntcustid(),
							consolidatedBillPeriod, invoiceBaseFilePath, districtId, yearMonth, "INDIVIDUAL");
					// executor.submit(future);
					future.call();
				}
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while generating caf invoices", e);
		} finally {
			// startDate = null;
			// endDate = null;
			if (executor != null) {
				executor.shutdown();
			}
		}

		LOGGER.info("Generation of caf invoices job is completed........!");
	}

	@Override
	public void generateEntGovtCafInvoices(int month, int year, String yearMonth) {
		LOGGER.info("Generation of caf invoices job started........!");
		// int currMonth =
		// Integer.valueOf(DateUtil.getCurrentMonth(InvoiceEngineConstants.MONTH_PATTERN_IN_VALUE));
		// int currYear =
		// Integer.valueOf(DateUtil.getCurrentMonth(InvoiceEngineConstants.YEAR_PATTERN));
		// Date startDate = DateUtil.getStartDateOfMonth(month-1);
		// Date endDate = DateUtil.getEndDateOfMonth(month-1);
		ThreadPoolExecutor executor = null;
		try {
			int invCount = getEntGovtInvoiceNumberCount(month, year);
			if (invCount > 0) {
				// int start = 0;
				int count = (invCount > Integer.valueOf(threadpoolMaxSize)) ? Integer.valueOf(threadpoolMaxSize)
						: invCount;
				executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);
				String consolidatedBillPeriod = getConsolidatedBillPeriod();
				// new
				// File(getInvoiceInfoJsonBasePath(consolidatedBillPeriod)).mkdirs();
				// while(start < invCount) {
				List<InvCustIdDTO> invNumbers = getInvoiceNumbersEntGovt(month, year);
				// start += count;
				for (InvCustIdDTO inv : invNumbers) {
					InvoiceEngineFuture future = new InvoiceEngineFuture(this, inv.getInvno(), inv.getPmntcustid(),
							consolidatedBillPeriod, invoiceBaseFilePath, "EntGovt", yearMonth, "ENTERPRISE");
					// executor.submit(future);
					future.call();
				}
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while generating caf invoices", e);
		} finally {
			// startDate = null;
			// endDate = null;
			if (executor != null) {
				executor.shutdown();
			}
		}

		LOGGER.info("Generation of caf invoices job is completed........!");
	}

	@Override
	public void generateEntPrivateCafInvoices(int month, int year, String yearMonth) {
		LOGGER.info("Generation of caf invoices job started........!");
		// int currMonth =
		// Integer.valueOf(DateUtil.getCurrentMonth(InvoiceEngineConstants.MONTH_PATTERN_IN_VALUE));
		// int currYear =
		// Integer.valueOf(DateUtil.getCurrentMonth(InvoiceEngineConstants.YEAR_PATTERN));
		// Date startDate = DateUtil.getStartDateOfMonth(month-1);
		// Date endDate = DateUtil.getEndDateOfMonth(month-1);
		ThreadPoolExecutor executor = null;
		try {
			int invCount = getEntPrivateInvoiceNumberCount(month, year);
			if (invCount > 0) {
				// int start = 0;
				int count = (invCount > Integer.valueOf(threadpoolMaxSize)) ? Integer.valueOf(threadpoolMaxSize)
						: invCount;
				executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);
				String consolidatedBillPeriod = getConsolidatedBillPeriod();
				// new
				// File(getInvoiceInfoJsonBasePath(consolidatedBillPeriod)).mkdirs();
				// while(start < invCount) {
				List<InvCustIdDTO> invNumbers = getInvoiceNumbersEntPrivate(month, year);
				// start += count;
				for (InvCustIdDTO inv : invNumbers) {
					InvoiceEngineFuture future = new InvoiceEngineFuture(this, inv.getInvno(), inv.getPmntcustid(),
							consolidatedBillPeriod, invoiceBaseFilePath, "EntPrivate", yearMonth, "ENTERPRISE");
					// executor.submit(future);
					future.call();
				}
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while generating caf invoices", e);
		} finally {
			// startDate = null;
			// endDate = null;
			if (executor != null) {
				executor.shutdown();
			}
		}

		LOGGER.info("Generation of caf invoices job is completed........!");
	}

	/**
	 * 
	 */
	@Override
	public String getInvoiceTemplate(String accountNumber, String billPeriod, String districtuid) {
		String template = null;
		InputStream is1 = null;
		InputStream is2 = null;
		BillInfoDTO billInfoDTO = new BillInfoDTO();
		EnterpriseBillInfoDTO enterpriseBillInfoDTO = new EnterpriseBillInfoDTO();
		VelocityContext context = new VelocityContext();
		if (StringUtils.isBlank(accountNumber)) {
			throw new InvoiceEngineException(
					new ErrorMessageDTO(InvoiceEngineErrorCodes.GAE002, "account number should not be null"));
		}
		if (StringUtils.isBlank(billPeriod)) {
			throw new InvoiceEngineException(
					new ErrorMessageDTO(InvoiceEngineErrorCodes.GAE002, "bill period should not be null"));
		}
		String invoiceInfoJsonFilePath = getInvoiceInfoJsonFilePath(accountNumber, billPeriod, districtuid);
		String custType = accountNumber.substring(0, 1);

		try {
			is1 = InvoiceGeneratorBusinessServiceImpl.class.getResourceAsStream("/templates/APSFL _Revised_Logo.png");
			is2 = InvoiceGeneratorBusinessServiceImpl.class.getResourceAsStream("/templates/APFIBER_Revised_Logo.png");
			final byte[] content = Base64.encodeBase64(IOUtils.toByteArray(is1));
			final byte[] content1 = Base64.encodeBase64(IOUtils.toByteArray(is2));
			if (custType.equalsIgnoreCase("I")) {
				billInfoDTO = JsonUtils.readJson(invoiceInfoJsonFilePath, BillInfoDTO.class);
				context.put("bill", billInfoDTO);
				context.put("StringUtils", new StringUtils());
				context.put("image_content", new String(content));
				context.put("image_content_1", new String(content1));
				context.put("custType", custType);
				template = velocityEngineUtils.getTemplateData(context, "templates/velocity.vm");
			} else if (custType.equalsIgnoreCase("E")) {
				enterpriseBillInfoDTO = JsonUtils.readJson(invoiceInfoJsonFilePath, EnterpriseBillInfoDTO.class);
				context.put("bill", enterpriseBillInfoDTO);
				context.put("StringUtils", new StringUtils());
				context.put("image_content", new String(content));
				context.put("image_content_1", new String(content1));
				context.put("custType", custType);
				template = velocityEngineUtils.getTemplateData(context, "templates/entvelocity.vm");
			} else if (custType.equalsIgnoreCase("C")) {
				enterpriseBillInfoDTO = JsonUtils.readJson(invoiceInfoJsonFilePath, EnterpriseBillInfoDTO.class);
				context.put("bill", enterpriseBillInfoDTO);
				context.put("StringUtils", new StringUtils());
				context.put("image_content", new String(content));
				context.put("image_content_1", new String(content1));
				context.put("custType", custType);
				template = velocityEngineUtils.getTemplateData(context, "templates/entvelocity.vm");
			}
			if (billInfoDTO == null && enterpriseBillInfoDTO == null) {
				throw new InvoiceEngineException(new ErrorMessageDTO(InvoiceEngineErrorCodes.INV001,
						String.format("Invoice data is not found for account number:%s on bill period:%s",
								accountNumber, billPeriod)));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			billInfoDTO = null;
			enterpriseBillInfoDTO = null;
			context = null;
			invoiceInfoJsonFilePath = null;
			custType = null;
			is1 = null;
			is2 = null;
		}
		return template;
	}

	@Override
	public int getInvoiceNumberCount(String districtId, int month, int year) {
		int count = 0;
		try {
			count = commonDao.invoiceAcctNumberCount(districtId, month, year);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@Override
	public int getEntGovtInvoiceNumberCount(int month, int year) {
		int count = 0;
		try {
			count = commonDao.getEntGovtInvoiceNumberCount(month, year);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@Override
	public int getEntPrivateInvoiceNumberCount(int month, int year) {
		int count = 0;
		try {
			count = commonDao.getEntPrivateInvoiceNumberCount(month, year);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@Override
	public List<InvCustIdDTO> getInvoiceNumbers(String districtId, int month, int year) {
		List<InvCustIdDTO> invNumbers = null;
		try {
			invNumbers = commonDao.getInvoiceNumbers(districtId, month, year);
		} catch (Exception e) {
			throw e;
		}
		return invNumbers;
	}
	
	@Override
	public InvCustIdDTO getInvoiceNumbersOFIndividuals_CSS(String customerID, int month, int year) {
		InvCustIdDTO invNumbers = null;
		try {
			invNumbers = commonDao.getInvoiceNumbersIndividualCSS(customerID, month, year);
			//invNumbers = commonDao.getInvoiceNumbers(districtId, month, year);
		} catch (Exception e) {
			throw e;
		}
		return invNumbers;
	}

	@Override
	public List<InvCustIdDTO> getInvoiceNumbersEntGovt(int month, int year) {
		List<InvCustIdDTO> invNumbers = null;
		try {
			invNumbers = commonDao.getInvoiceNumbersEntGovt(month, year);
		} catch (Exception e) {
			throw e;
		}
		return invNumbers;
	}

	@Override
	public List<InvCustIdDTO> getInvoiceNumbersEntPrivate(int month, int year) {
		List<InvCustIdDTO> invNumbers = null;
		try {
			invNumbers = commonDao.getInvoiceNumbersEntPrivate(month, year);
		} catch (Exception e) {
			throw e;
		}
		return invNumbers;
	}

	/**
	 * 
	 * @param billPeriod
	 * @return
	 */
	public String getInvoiceInfoJsonBasePath(String billPeriod) {
		return String.format("%s/%s", invoiceBaseFilePath, billPeriod);
	}

	/**
	 * 
	 * @param accountNumber
	 * @param billPeriod
	 * @return
	 */
	public String getInvoiceInfoJsonFilePath(String accountNumber, String billPeriod, String districtid) {
		return String.format(InvoiceEngineConstants.INVOICE_JSON_FILE_PATH, invoiceBaseFilePath, billPeriod, "jsons",
				districtid, accountNumber, billPeriod);
	}

	@Override
	public BillInfoDTO getIndividualInvoiceDetails(final String invNumber,String yearMonth) {
		BillInfoDTO dto = null;
		try {
			dto = commonDao.getIndividualInvoiceDetails(Long.valueOf(invNumber),yearMonth);
		} catch (Exception ex) {
			LOGGER.error("Exception occured in getIndividualInvoiceDetails: ", ex);
		}

		return dto;
	}

	@Override
	public EnterpriseBillInfoDTO getConsolidatedInvoiceDetails(String invNumber) {
		EnterpriseBillInfoDTO entDto = null;
		try {
			entDto = commonDao.getConsolidatedInvoiceDetails(invNumber);
		} catch (Exception ex) {
			LOGGER.error("Exception occured in getConsolidatedInvoiceDetails: ", ex);
		}

		return entDto;
	}

	/**
	 * 
	 * @return
	 */
	private String getConsolidatedBillPeriod() {
		String consilidatedBillPeriod = null;
		int month = DateUtil.getCurrentMonth();
		int year = DateUtil.getCurrentYear();
		int lastDayOfMonth = DateUtil.getLastDayOfMonth();
		try {
			consilidatedBillPeriod = String.format(billPeriod, month, year, lastDayOfMonth, month, year);
		} catch (Exception e) {
			LOGGER.error("Error occured while building current bill period", e);
		}
		return consilidatedBillPeriod;
	}

	@Override
	public int getInvoiceChargeCafsCount(String districtId, int yearMonth) {
		int count = 0;
		try {
			count = commonDao.getInvoiceChargeCafsCount(districtId, yearMonth);
		} catch (Exception e) {
			LOGGER.error("Error occured while processing getInvoiceChargeCafsCount ", e);
			throw e;
		}
		return count;
	}

	@Override
	public int getInvoiceEntCafsCount(String year, String month) {
		int count = 0;
		try {
			count = commonDao.getInvoiceEntCafsCount(year, month);
		} catch (Exception e) {
			LOGGER.error("Error occured while processing getInvoiceEntCafsCount ", e);
			throw e;
		}
		return count;
	}

	@Override
	public int getInvoiceEntPrivateCafsCount(String year, String month) {
		int count = 0;
		try {
			count = commonDao.getInvoiceEntPrivateCafsCount(year, month);
		} catch (Exception e) {
			LOGGER.error("Error occured while processing getInvoiceEntCafsCount ", e);
			throw e;
		}
		return count;
	}

	@Override
	public List<String> getCafChargeDetails(String districtId, int yearMonth) {
		List<String> list = null;
		try {
			list = commonDao.getCafChargeDetails(districtId, yearMonth);
		} catch (Exception e) {
			LOGGER.error("Error occured while processing getCafChargeDetails ", e);
			throw e;
		}
		return list;
	}
	
	@Override
	public List<String> getNoBillCafChargeDetails() {
		List<String> list = null;
		try {
			list = commonDao.getNoBillCafChargeDetails();
		} catch (Exception e) {
			LOGGER.error("Error occured while processing getNoBillCafChargeDetails ", e);
			throw e;
		}
		return list;
	}

	@Override
	public List<CafDetailsDTO> getEntCafDetails(String year, String month) {
		List<CafDetailsDTO> list = null;
		try {
			list = commonDao.getEntCafDetails(year, month);
		} catch (Exception e) {
			LOGGER.error("Error occured while processing getCafEntDetails ", e);
			throw e;
		}
		return list;
	}
	
	@Override
	public List<CorpusUpdate> getSubList(String customerID) {
		List<CorpusUpdate> subList = null;
		try {
			subList = commonDao.getSubList(customerID);
		} catch (Exception e) {
			LOGGER.error("Error occured while processing getCafEntDetails ", e);
			throw e;
		}
		return subList;
	}
	
	

	@Override
	public List<CafDetailsDTO> getEntPrivateCafDetails(String year, String month) {
		List<CafDetailsDTO> list = null;
		try {
			list = commonDao.getEntPrivateCafDetails(year, month);
		} catch (Exception e) {
			LOGGER.error("Error occured while processing getEntPrivateCafDetails ", e);
			throw e;
		}
		return list;
	}

	@Override
	public String executeCAFChargeProcess(String districtId, Long cafNo, int yearMonth) {
		String output = null;
		try {
			output = commonDao.executeCAFChargeProcess(districtId, cafNo, yearMonth);
		} catch (Exception e) {
			LOGGER.error("Error occured while processing executeCAFChargeProcess ", e);
			throw e;
		}
		return output;
	}

	// Added By Gowthami

	@Override
	public void generateBillInvoices(String districtId, int month, int year, String yearMonth) {
		LOGGER.info("Generation of caf invoices job started........!");
		// int currMonth =
		// Integer.valueOf(DateUtil.getCurrentMonth(InvoiceEngineConstants.MONTH_PATTERN_IN_VALUE));
		// int currYear =
		// Integer.valueOf(DateUtil.getCurrentMonth(InvoiceEngineConstants.YEAR_PATTERN));
		// Date startDate = DateUtil.getStartDateOfMonth(month-1);
		// Date endDate = DateUtil.getEndDateOfMonth(month-1);
		ThreadPoolExecutor executor = null;
		try {
			int invCount = getInvoiceNumberCount(districtId, month, year);
			if (invCount > 0) {
				// int start = 0;
				int count = (invCount > Integer.valueOf(threadpoolMaxSize)) ? Integer.valueOf(threadpoolMaxSize)
						: invCount;
				executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);
				String consolidatedBillPeriod = getConsolidatedBillPeriod();
				// new
				// File(getInvoiceInfoJsonBasePath(consolidatedBillPeriod)).mkdirs();
				// while(start < invCount) {
				List<InvCustIdDTO> invNumbers = getInvoiceNumbers(districtId, month, year);
				// start += count;
				for (InvCustIdDTO inv : invNumbers) {
					InvoiceEngineFuture future = new InvoiceEngineFuture(this, inv.getInvno(), inv.getPmntcustid(),
							consolidatedBillPeriod, invoiceBaseFilePath, districtId, yearMonth, "ENTERPRISE");
					executor.submit(future);
				}
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while generating caf invoices", e);
		} finally {
			// startDate = null;
			// endDate = null;
			if (executor != null) {
				executor.shutdown();
			}
		}

		LOGGER.info("Generation of caf invoices job is completed........!");
	}

	@Override
	public List<BillInfoDTO> sendInvoiceSMS(int year, int month) {
		LOGGER.info("Send SMS invoices job started........!");
		// ThreadPoolExecutor executor = null;
		List<BillInfoDTO> billinfoDTOSMSList = new ArrayList<>();
		try {
			// executor = (ThreadPoolExecutor)
			// Executors.newFixedThreadPool(Integer.valueOf(threadpoolMaxSize));

			List<BillInfoDTO> billinfoDTOList = commonDao.getAllCustomerInvoiceSummaryForSMS(year, month);

			for (BillInfoDTO b : billinfoDTOList) {

				b.setTextMessage(smsContruct(b));
				b.setEmailText(emailConstruct(b));
				billinfoDTOSMSList.add(b);
				// executor.submit(future);

			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while Sending SMS  invoices", e);
		} finally {
			// if (executor != null) {
			// executor.shutdown();
			// }
		}
		return billinfoDTOSMSList;
	}

	private String smsContruct(BillInfoDTO b) {

		String name = b.getFullName();
		if (b.getFullName().indexOf("&") >= 0) {
			String tmpNme = "";
			String[] tmp = b.getFullName().split("&");
			for (int i = 0; i < tmp.length; i++) {
				tmpNme = tmpNme + tmp[i];
			}
			name = tmpNme;
		}

		StringBuilder sb = new StringBuilder();

		// sb.append("Bill Details For "+name.trim()+", Bill No:
		// "+b.getBillNumber()+", Bill Date: "+b.getBillDate()+",");
		// sb.append(" This Month's Due Rs " +
		// b.getInvamtwithtax().toString()+", Tot. Due:
		// "+b.getTotalBalance()+",");
		// sb.append(" Due Date: " + b.getDueDate() +" -- Team APSFL");
		// sb.append(" To view bill details or to pay online,click
		// http://183.82.105.224:8080/cssa/");

		/*sb.append("Dear Customer, You have a new bill generated for Customer Id: " + b.getCustomerId() + " and Bill No: "
				+ b.getBillNumber() + ", for an amount of Rs." + b.getTotalBalance() + ". Due date is " + b.getDueDate()
				+ " APSFL");*/
		
		sb.append("Dear Customer,You have a new bill generated for Account No:" + b.getCustomerId() + " and Bill No:"
				+ b.getBillNumber() + ",for an amount of Rs." + b.getTotalBalance() + ".Due date is " + b.getDueDate()
				+ ".APSFL ");

		return sb.toString();
	}

	private String emailConstruct(BillInfoDTO b) {
		StringBuilder sb = new StringBuilder();
		// sb.append("Dear <b>" + b.getFullName() + "</b><br/>");
		// sb.append("<br/>");

		// sb.append("We are pleased to present your latest APSFL eBill.<br/>");
		// sb.append("<br/>");
		// sb.append("Customer Number: " + b.getCustomerId() + "<br/>");
		// sb.append("Bill Period: " + b.getBillPeriodFrom() + " to " +
		// b.getBillPeriodTo() + "<br/>");
		// sb.append("Current Month Charges: " + b.getInvamtwithtax() +
		// "<br/>");
		// sb.append("Total Amount Due: " + b.getTotalBalance() + "<br/>");
		// sb.append("Payment Due Date: " + b.getDueDate() + "<br/>");

		/*sb.append("Dear Customer,<br/>");
		sb.append("<p>Your APSFL Bill for Customer Id:" + b.getCustomerId() + " is generated on " + b.getBillDate()
				+ " for the peroid of " + b.getBillPeriodFrom() + " to " + b.getBillPeriodTo()
				+ " Amount Payable is Rs." + b.getTotalBalance() + " and the due date is " + b.getDueDate()
				+ ". The detailed bill is attached.</p>");*/
		
		sb.append("Dear Customer,<br/>");
		sb.append("Your APSFL Bill for Account No:" + b.getCustomerId() + " is generated on " + b.getBillDate()
				+ " for the period of " + b.getBillPeriodFrom() + " to " + b.getBillPeriodTo()
				+ ".The Amount Payable is Rs." + b.getTotalBalance() + " and the due date is " + b.getDueDate()
				+ ".The detailed bill is attached.");
		
		return sb.toString();
	}

	@Override
	@Transactional
	public void updatePdfPathInCustInv(String year, String month, String custId, String fileLocation) {
		commonDao.updatePdfPathInCustInv(year, month, custId, fileLocation);
	}

	@Override
	public void updateCustinvForSmsAndEmail(BillInfoDTO bdto) {
		LOGGER.info("updateCustinvForSmsAndEmail started[service]........!");
		commonDao.updateCustinvForSmsAndEmail(bdto);
		LOGGER.info("updateCustinvForSmsAndEmail End[service]........!");
	}

	
	@Override
	public void generateCafInvoicesForSingleCustomer(String districtId, int month, int year, String yearMonth,String customerId) {
		LOGGER.info("Generation of caf invoices job For Single Customer started........!");
		ThreadPoolExecutor executor = null;
		try {
			int invCount = getInvoiceNumberCount(districtId, month, year, Integer.parseInt(customerId));
			if (invCount > 0) {
				int count = (invCount > Integer.valueOf(threadpoolMaxSize)) ? Integer.valueOf(threadpoolMaxSize)
						: invCount;
				executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);
				String consolidatedBillPeriod = getConsolidatedBillPeriod();
				List<InvCustIdDTO> invNumbers = getInvoiceNumbers(districtId, month, year, Integer.parseInt(customerId));
				for (InvCustIdDTO inv : invNumbers) {
					InvoiceEngineFuture future = new InvoiceEngineFuture(this, inv.getInvno(), inv.getPmntcustid(),
							consolidatedBillPeriod, invoiceBaseFilePath, districtId, yearMonth, "INDIVIDUAL");
					future.call();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while generating caf invoices", e);
		} finally {
			if (executor != null) {
				executor.shutdown();
			}
		}

		LOGGER.info("Generation of caf invoices job is completed........!");
	}
	
	@Override
	public int getInvoiceNumberCount(String districtId, int month, int year, int customerId) {
		int count = 0;
		try {
			count = commonDao.invoiceAcctNumberCount(districtId, month, year, customerId);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}
	
	@Override
	public List<InvCustIdDTO> getInvoiceNumbers(String districtId, int month, int year, int customerId) {
		List<InvCustIdDTO> invNumbers = null;
		try {
			invNumbers = commonDao.getInvoiceNumbers(districtId, month, year, customerId);
		} catch (Exception e) {
			throw e;
		}
		return invNumbers;
	}
	
	
	@Override
	public void generateEntGovtCafInvoices(int month, int year, String yearMonth,int customerId) {
		LOGGER.info("Generation of caf invoices job started........!");
		ThreadPoolExecutor executor = null;
		try {
			int invCount = getEntGovtInvoiceNumberCount(month, year,customerId);
			if (invCount > 0) {
				// int start = 0;
				int count = (invCount > Integer.valueOf(threadpoolMaxSize)) ? Integer.valueOf(threadpoolMaxSize)
						: invCount;
				executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);
				String consolidatedBillPeriod = getConsolidatedBillPeriod();
				
				List<InvCustIdDTO> invNumbers = getInvoiceNumbersEntGovt(month, year, customerId);
			
				for (InvCustIdDTO inv : invNumbers) {
					InvoiceEngineFuture future = new InvoiceEngineFuture(this, inv.getInvno(), inv.getPmntcustid(),
							consolidatedBillPeriod, invoiceBaseFilePath, "EntGovt", yearMonth, "ENTERPRISE");
					// executor.submit(future);
					future.call();
				}
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while generating caf invoices", e);
		} finally {
			// startDate = null;
			// endDate = null;
			if (executor != null) {
				executor.shutdown();
			}
		}

		LOGGER.info("Generation of caf invoices job is completed........!");
	}

	@Override
	public void generateEntPrivateCafInvoices(int month, int year, String yearMonth,int customerId) {
		LOGGER.info("Generation of caf invoices job started........!");
		
		ThreadPoolExecutor executor = null;
		try {
			int invCount = getEntPrivateInvoiceNumberCount(month, year,customerId);
			if (invCount > 0) {
				// int start = 0;
				int count = (invCount > Integer.valueOf(threadpoolMaxSize)) ? Integer.valueOf(threadpoolMaxSize)
						: invCount;
				executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);
				String consolidatedBillPeriod = getConsolidatedBillPeriod();
				
				List<InvCustIdDTO> invNumbers = getInvoiceNumbersEntPrivate(month, year,customerId);
				// start += count;
				for (InvCustIdDTO inv : invNumbers) {
					InvoiceEngineFuture future = new InvoiceEngineFuture(this, inv.getInvno(), inv.getPmntcustid(),
							consolidatedBillPeriod, invoiceBaseFilePath, "EntPrivate", yearMonth, "ENTERPRISE");
					
					future.call();
				}
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured while generating caf invoices", e);
		} finally {
			
			if (executor != null) {
				executor.shutdown();
			}
		}

		LOGGER.info("Generation of caf invoices job is completed........!");
	}
	
	@Override
	public int getEntGovtInvoiceNumberCount(int month, int year,int customerId) {
		int count = 0;
		try {
			count = commonDao.getEntGovtInvoiceNumberCount(month, year,customerId);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@Override
	public int getEntPrivateInvoiceNumberCount(int month, int year,int customerId) {
		int count = 0;
		try {
			count = commonDao.getEntPrivateInvoiceNumberCount(month, year,customerId);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}
	
	@Override
	public List<InvCustIdDTO> getInvoiceNumbersEntGovt(int month, int year,int custId) {
		List<InvCustIdDTO> invNumbers = null;
		try {
			invNumbers = commonDao.getInvoiceNumbersEntGovt(month, year,custId);
		} catch (Exception e) {
			throw e;
		}
		return invNumbers;
	}

	@Override
	public List<InvCustIdDTO> getInvoiceNumbersEntPrivate(int month, int year,int custId) {
		List<InvCustIdDTO> invNumbers = null;
		try {
			invNumbers = commonDao.getInvoiceNumbersEntPrivate(month, year,custId);
		} catch (Exception e) {
			throw e;
		}
		return invNumbers;
	}
	
	/**Added for Changing PDF bill as per revenue share */
	@Override
	public Map<String,RevenueShareDTO> getRevenueShareDetails(String invnumber){
		Map<String,RevenueShareDTO> revenueShareDetails = null;
		try {
			revenueShareDetails = commonDao.getRevenueShareDetails(invnumber);
		}catch (Exception e) {
			throw e;
		}
		return revenueShareDetails;
	}
	
	@Override
	public BillInfoDTO getIndividualInvoiceDetails_css(final String invNumber, String yearMonth) {
		BillInfoDTO dto = null;
		try {
			dto = commonDao.getIndividualInvoiceDetails_css(Long.valueOf(invNumber),yearMonth);
		} catch (Exception ex) {
			LOGGER.error("Exception occured in getIndividualInvoiceDetails: ", ex);
		}

		return dto;
	}
	
	@Override
	public BillInfoDTO getIndividualInvoiceDetails_css_Itemised(final String invNumber,String detailType, String yearMonth) {
		BillInfoDTO dto = null;
		try {
			dto = commonDao.getIndividualInvoiceDetails_css_Itemised(Long.valueOf(invNumber),detailType,yearMonth);
		} catch (Exception ex) {
			LOGGER.error("Exception occured in getIndividualInvoiceDetails: ", ex);
		}

		return dto;
	}

}
