package com.arbiva.apfgc.invoice.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.arbiva.apfgc.invoice.businessservice.InvoiceGeneratorBusinessService;
import com.arbiva.apfgc.invoice.dto.BillInfoDTO;
import com.arbiva.apfgc.invoice.dto.CorpusUpdate;
import com.arbiva.apfgc.invoice.dto.ErrorMessageDTO;
import com.arbiva.apfgc.invoice.dto.MailDTO;
import com.arbiva.apfgc.invoice.exception.InvoiceEngineException;
import com.arbiva.apfgc.invoice.utils.ApsflHelper;
import com.arbiva.apfgc.invoice.utils.DateUtil;
import com.arbiva.apfgc.invoice.utils.InvoiceEngineErrorCode.InvoiceEngineErrorCodes;
import com.arbiva.apfgc.invoice.utils.IpAddressValues;

/**
 * 
 * @author srinivasa
 *
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceEngineController {

	@Value("${invoice.base.filepath}")
	private String invoiceBaseFilePath;

	@Autowired
	private InvoiceGeneratorBusinessService invoiceGeneratorBusinessService;

	@Autowired
	IpAddressValues ipAddressValues;

	RestTemplate restTemplate = new RestTemplate();

	/**
	 * 
	 * @param districtuid
	 * @return
	 */

	private static final Logger LOGGER = Logger.getLogger(InvoiceEngineController.class);

	@RequestMapping(value = "/processcharge/{districtuid}/{yearMonth}", method = RequestMethod.GET)
	public ResponseEntity<String> processInvoiceChargeCalculations(@PathVariable("districtuid") String districtuid,
			@PathVariable("yearMonth") int yearMonth) {
		LOGGER.info("processInvoiceChargeCalculations");
		try {
			invoiceGeneratorBusinessService.processInvoiceChargeCalculations(districtuid, yearMonth);
		} catch (Exception e) {
			if (e instanceof InvoiceEngineException) {
				throw (InvoiceEngineException) e;
			} else {
				throw new InvoiceEngineException(new ErrorMessageDTO(InvoiceEngineErrorCodes.GAE001,
						String.format("Error occured while processing processInvoiceChargeCalculations "), e));
			}
		}
		return ResponseEntity.ok().body("Success");
	}

	@RequestMapping(value = "/processEnterpriseGovt/{yearMonth}", method = RequestMethod.GET)
	public ResponseEntity<String> processEnterpriseGovt(@PathVariable("yearMonth") int yearMonth) {

		LOGGER.info("Inside processEnterpriseGovt");

		try {
			invoiceGeneratorBusinessService.processEnterpriseGovt(yearMonth);
		} catch (Exception e) {
			if (e instanceof InvoiceEngineException) {
				throw (InvoiceEngineException) e;
			} else {
				throw new InvoiceEngineException(new ErrorMessageDTO(InvoiceEngineErrorCodes.GAE001,
						String.format("Error occured while processing processInvoiceChargeCalculations "), e));
			}
		}
		return ResponseEntity.ok().body("Success");
	}
	
	/*new*/
	@RequestMapping(value = "/customerPDFGeneration/{customerId}/{month}/{year}/{custTypeLOV}/{detailedflag}/{detailType}/{districtId}", method = RequestMethod.GET)
	public ResponseEntity<String> customerPDFGeneration(@PathVariable("customerId") String customerId,@PathVariable("month") int month,
			@PathVariable("year") int year,@PathVariable("custTypeLOV") String custTypeLOV,
			@PathVariable("detailedflag") boolean detailedflag,@PathVariable("detailType") String detailType,
			@PathVariable("districtId") int districtId) {

		LOGGER.info("InvoiceEngineController::customerPDFGeneration::START");
		String path="";
		try {
			path = invoiceGeneratorBusinessService.customerPDFGeneration(customerId,month,year,custTypeLOV,detailedflag,detailType,districtId);
		} catch (Exception e) {
			if (e instanceof InvoiceEngineException) {
				throw (InvoiceEngineException) e;
			} else {
				throw new InvoiceEngineException(new ErrorMessageDTO(InvoiceEngineErrorCodes.GAE001,
						String.format("Error occured while processing customerPDFGeneration "), e));
			}
		}
		LOGGER.info("InvoiceEngineController::customerPDFGeneration::END");
		return ResponseEntity.ok().body(path);
	}

	@RequestMapping(value = "/processEnterprisePrivate/{yearMonth}", method = RequestMethod.GET)
	public ResponseEntity<String> processEnterprisePrivate(@PathVariable("yearMonth") int yearMonth) {

		LOGGER.info("Inside processEnterpriseGovt");

		try {
			invoiceGeneratorBusinessService.processEnterprisePrivate(yearMonth);
		} catch (Exception e) {
			if (e instanceof InvoiceEngineException) {
				throw (InvoiceEngineException) e;
			} else {
				throw new InvoiceEngineException(new ErrorMessageDTO(InvoiceEngineErrorCodes.GAE001,
						String.format("Error occured while processing processInvoiceChargeCalculations "), e));
			}
		}
		return ResponseEntity.ok().body("Success");
	}

	/**
	 * 
	 * @param districtuid
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/generateinvoices/{districtuid}/{yearMonth}",
	 * method = RequestMethod.GET) public ResponseEntity<String>
	 * generateCafInvoices(@PathVariable("districtuid") String
	 * districtuid,@PathVariable("yearMonth") String yearMonth) { int year =
	 * Integer.parseInt(yearMonth.substring(0,4)); int month =
	 * Integer.parseInt(yearMonth.substring(4,6)); try {
	 * invoiceGeneratorBusinessService.generateCafInvoices(districtuid, month,
	 * year, yearMonth); } catch (Exception e) { if (e instanceof
	 * InvoiceEngineException) { throw (InvoiceEngineException) e; } else {
	 * throw new InvoiceEngineException(new
	 * ErrorMessageDTO(InvoiceEngineErrorCodes.GAE001, String.format(
	 * "Error occured while processing generateCafInvoices "), e)); } } return
	 * ResponseEntity.ok().body("Success"); }
	 */
	/*
	 * @RequestMapping(value = "/generateinvoicesEntGovt/{yearMonth}", method =
	 * RequestMethod.GET) public ResponseEntity<String>
	 * generateinvoicesEntGovt(@PathVariable("yearMonth") String yearMonth) {
	 * int year = Integer.parseInt(yearMonth.substring(0,4)); int month =
	 * Integer.parseInt(yearMonth.substring(4,6)); try {
	 * invoiceGeneratorBusinessService.generateEntGovtCafInvoices(month,year,
	 * yearMonth); } catch (Exception e) { if (e instanceof
	 * InvoiceEngineException) { throw (InvoiceEngineException) e; } else {
	 * throw new InvoiceEngineException(new
	 * ErrorMessageDTO(InvoiceEngineErrorCodes.GAE001, String.format(
	 * "Error occured while processing generateCafInvoices "), e)); } } return
	 * ResponseEntity.ok().body("Success"); }
	 */

	/*
	 * @RequestMapping(value = "/generateinvoicesEntPrivate/{yearMonth}", method
	 * = RequestMethod.GET) public ResponseEntity<String>
	 * generateinvoicesEntPrivate(@PathVariable("yearMonth") String yearMonth) {
	 * int year = Integer.parseInt(yearMonth.substring(0,4)); int month =
	 * Integer.parseInt(yearMonth.substring(4,6)); try {
	 * invoiceGeneratorBusinessService.generateEntPrivateCafInvoices(month,year,
	 * yearMonth); } catch (Exception e) { if (e instanceof
	 * InvoiceEngineException) { throw (InvoiceEngineException) e; } else {
	 * throw new InvoiceEngineException(new
	 * ErrorMessageDTO(InvoiceEngineErrorCodes.GAE001, String.format(
	 * "Error occured while processing generateCafInvoices "), e)); } } return
	 * ResponseEntity.ok().body("Success"); }
	 */

	/**
	 * 
	 * @param accountNumber
	 * @param billPeriod
	 * @param request
	 * @param response
	 */
	/*
	 * @RequestMapping(value = "/getsinglebill", method = RequestMethod.GET)
	 * public void downloadInvoiceDetails(
	 * 
	 * @RequestParam(value = "account_number", required = true) String
	 * accountNumber,
	 * 
	 * @RequestParam(value = "bill_period", required = true) String billPeriod,
	 * HttpServletRequest request, HttpServletResponse response) { String
	 * invoiceTemplate = null; OutputStream outStream = null; try {
	 * invoiceTemplate =
	 * invoiceGeneratorBusinessService.getInvoiceTemplate(accountNumber,
	 * billPeriod, "EntGovt"); //invoiceTemplate = getInvoiceTemplate(); // set
	 * content attributes for the response
	 * response.setContentType("application/octet-stream");
	 * response.setContentLength((int) invoiceTemplate.length());
	 * 
	 * // set headers for the response response.setHeader("Content-Disposition",
	 * getHeaderValue(accountNumber, billPeriod));
	 * 
	 * // get output stream of the response outStream =
	 * response.getOutputStream();
	 * 
	 * IOUtils.write(invoiceTemplate, outStream, "UTF-8"); } catch (Exception e)
	 * { // TODO: handle exception } finally { IOUtils.closeQuietly(outStream);
	 * } }
	 */

	/*
	 * @RequestMapping(value = "/getallbills/{districtuid}/{bill_period}",
	 * method = RequestMethod.GET) public ResponseEntity<String>
	 * printallbills(@PathVariable("districtuid") String
	 * districtuid, @PathVariable("bill_period") String billPeriod,
	 * HttpServletRequest request, HttpServletResponse response) { String
	 * invoiceTemplate = null; String accountNumber = null; String newFileName =
	 * null; String jsonFileName = null; String billFileName = null; try { File
	 * folder = new
	 * File(invoiceBaseFilePath+"\\"+billPeriod+"\\jsons\\"+districtuid); File[]
	 * listOfFiles = folder.listFiles(); for (int i = 0; i < listOfFiles.length;
	 * i++) { accountNumber = listOfFiles[i].getName().split("_")[0];
	 * //jsonFileName =
	 * invoiceBaseFilePath+billPeriod+"/jsons/"+districtuid+"/"+listOfFiles[i].
	 * getName(); billFileName =
	 * invoiceBaseFilePath+billPeriod+"/bills/"+districtuid+"/"+listOfFiles[i].
	 * getName().replace(".json",".html"); LOGGER.info("INSIDE ::: -->> "
	 * +billFileName); invoiceTemplate =
	 * invoiceGeneratorBusinessService.getInvoiceTemplate(accountNumber,
	 * billPeriod, districtuid); new
	 * File(invoiceBaseFilePath+billPeriod+"/bills/"+districtuid+"/").mkdirs();
	 * try (BufferedWriter bw = new BufferedWriter(new
	 * FileWriter(billFileName))) { bw.write(invoiceTemplate); } catch
	 * (Exception e) { e.printStackTrace(); } } } catch (Exception e) { // TODO:
	 * handle exception } finally { invoiceTemplate = null; accountNumber =
	 * null; newFileName = null; } return ResponseEntity.ok().body("Success"); }
	 */

	/*
	 * @RequestMapping(value = "/getallEntGovtbills/{bill_period}", method =
	 * RequestMethod.GET) public ResponseEntity<String>
	 * printallEntGovtbills(@PathVariable("bill_period") String billPeriod,
	 * HttpServletRequest request, HttpServletResponse response) { String
	 * invoiceTemplate = null; String accountNumber = null; String newFileName =
	 * null; String jsonFileName = null; String billFileName = null;
	 * BufferedWriter bw = null; try { LOGGER.info("Before File"); File folder =
	 * new File(invoiceBaseFilePath+"/"+billPeriod+"/jsons/"+"EntGovt");
	 * LOGGER.info("Before File"); File[] listOfFiles = folder.listFiles();
	 * LOGGER.info("Files Length"+listOfFiles.length); for (int i = 0; i <
	 * listOfFiles.length; i++) { accountNumber =
	 * listOfFiles[i].getName().split("_")[0]; //jsonFileName =
	 * invoiceBaseFilePath+billPeriod+"/jsons/"+"EntGovt"+"/"+listOfFiles[i].
	 * getName(); billFileName =
	 * invoiceBaseFilePath+billPeriod+"/bills/"+"EntGovt"+"/"+listOfFiles[i].
	 * getName().replace(".json",".html"); LOGGER.info("INSIDE111222 ::: -->> "
	 * +billFileName); invoiceTemplate =
	 * invoiceGeneratorBusinessService.getInvoiceTemplate(accountNumber,
	 * billPeriod, "EntGovt"); new
	 * File(invoiceBaseFilePath+billPeriod+"/bills/"+"EntGovt"+"/").mkdirs();
	 * try { bw = new BufferedWriter(new FileWriter(billFileName));
	 * bw.write(invoiceTemplate); } catch (IOException e) { e.printStackTrace();
	 * } bw.flush(); bw.close(); accountNumber = null; invoiceTemplate = null;
	 * billFileName = null; } } catch (Exception e) { // TODO: handle exception
	 * LOGGER.info(e); e.printStackTrace(); } finally { invoiceTemplate = null;
	 * accountNumber = null; newFileName = null; billFileName = null; } return
	 * ResponseEntity.ok().body("Success"); }
	 */

	/*
	 * @RequestMapping(value = "/getallEntPrivatebills/{bill_period}", method =
	 * RequestMethod.GET) public ResponseEntity<String>
	 * printallEntPrivatebills(@PathVariable("bill_period") String billPeriod,
	 * HttpServletRequest request, HttpServletResponse response) { String
	 * invoiceTemplate = null; String accountNumber = null; String newFileName =
	 * null; String jsonFileName = null; String billFileName = null; try { File
	 * folder = new
	 * File(invoiceBaseFilePath+"\\"+billPeriod+"\\jsons\\"+"EntPrivate");
	 * File[] listOfFiles = folder.listFiles(); for (int i = 0; i <
	 * listOfFiles.length; i++) { accountNumber =
	 * listOfFiles[i].getName().split("_")[0]; //jsonFileName =
	 * invoiceBaseFilePath+billPeriod+"/jsons/"+"EntGovt"+"/"+listOfFiles[i].
	 * getName(); billFileName =
	 * invoiceBaseFilePath+billPeriod+"/bills/"+"EntPrivate"+"/"+listOfFiles[i].
	 * getName().replace(".json",".html"); LOGGER.info("INSIDE ::: -->> "
	 * +billFileName); invoiceTemplate =
	 * invoiceGeneratorBusinessService.getInvoiceTemplate(accountNumber,
	 * billPeriod, "EntPrivate"); new
	 * File(invoiceBaseFilePath+billPeriod+"/bills/"+"EntPrivate"+"/").mkdirs();
	 * try (BufferedWriter bw = new BufferedWriter(new
	 * FileWriter(billFileName))) { bw.write(invoiceTemplate); } catch
	 * (IOException e) { e.printStackTrace(); } } } catch (Exception e) { //
	 * TODO: handle exception } finally { invoiceTemplate = null; accountNumber
	 * = null; newFileName = null; } return ResponseEntity.ok().body("Success");
	 * }
	 */

	/**
	 * 
	 * @param accountNumber
	 * @param billPeriod
	 * @return
	 */
	/*
	 * private String getHeaderValue(String accountNumber, String billPeriod) {
	 * return String.format("attachment; filename=\"%s\"",
	 * getInvoiveFileName(accountNumber, billPeriod)); }
	 */

	/**
	 * 
	 * @param accountNumber
	 * @param billPeriod
	 * @return
	 */
	/*
	 * private String getInvoiveFileName(String accountNumber, String
	 * billPeriod) { return
	 * String.format(InvoiceEngineConstants.INVOICE_REPORT_FILE_NAME,
	 * accountNumber, billPeriod); }
	 */

	/**
	 * Added Gowthami
	 */
	/*
	 * @RequestMapping(value = "/downloadPDF", method = RequestMethod.GET)
	 * public ModelAndView downloadExcel() {
	 * 
	 * // return a view which will be resolved by an excel view resolver return
	 * new ModelAndView("pdfView", "listBooks", "listBooks"); }
	 */

	/**
	 * Added Gowthami
	 */
	@RequestMapping(value = "/generateBillIndividual/{districtuid}/{yearMonth}", method = RequestMethod.GET)
	public ResponseEntity<String> generateBill(@PathVariable("districtuid") String districtuid,
			@PathVariable("yearMonth") String yearMonth) {
		int year = Integer.parseInt(yearMonth.substring(0, 4));
		int month = Integer.parseInt(yearMonth.substring(4, 6));
		/*
		 * List<String> mobileNosList=new ArrayList<String>(); String smsMsg=
		 * "Hi h r u";
		 */
		try {
			invoiceGeneratorBusinessService.generateCafInvoices(districtuid, month, year, yearMonth);
			/*
			 * mobileNosList.add("9494766164"); mobileNosList.add("9959610558");
			 * mobileNosList.add("9133660878");
			 * 
			 * SMSThread thread = new SMSThread(ipAddressValues, restTemplate,
			 * mobileNosList, smsMsg); thread.start();
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}

	@RequestMapping(value = "/generateBillEnterpriseGovt/{yearMonth}", method = RequestMethod.GET)
	public ResponseEntity<String> generateBillEnterpriseGovt(@PathVariable("yearMonth") String yearMonth) {
		int year = Integer.parseInt(yearMonth.substring(0, 4));
		int month = Integer.parseInt(yearMonth.substring(4, 6));
		try {
			invoiceGeneratorBusinessService.generateEntGovtCafInvoices(month, year, yearMonth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}

	@RequestMapping(value = "/generateBillEnterprisePrivate/{yearMonth}", method = RequestMethod.GET)
	public ResponseEntity<String> generateBillEnterprisePrivate(@PathVariable("yearMonth") String yearMonth) {
		int year = Integer.parseInt(yearMonth.substring(0, 4));
		int month = Integer.parseInt(yearMonth.substring(4, 6));
		try {
			invoiceGeneratorBusinessService.generateEntPrivateCafInvoices(month, year, yearMonth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}

	@RequestMapping(value = "/sendSmsAndEmailOnInvoices/{yearMonth}", method = RequestMethod.GET)
	public ResponseEntity<String> sendInvoiceSMSAndEmail(@PathVariable("yearMonth") String yearMonth
			) {
		int year = Integer.parseInt(yearMonth.substring(0, 4));
		int month = Integer.parseInt(yearMonth.substring(4, 6));
		try {

			List<BillInfoDTO> billinfoDTOSMSList = invoiceGeneratorBusinessService.sendInvoiceSMS(year, month);

			for (BillInfoDTO b : billinfoDTOSMSList) {
				ByteArrayInputStream bais=null;
				try {
					if (b.getSmsflag() == 0 && b.getPhoneNumber() != null) {
						HttpEntity<String> httpEntity = ApsflHelper.getHttpEntity(ipAddressValues.getComUserName(),
								ipAddressValues.getComPwd());
						String url = ipAddressValues.getComURL() + "sendSMS?mobileNo=" + b.getPhoneNumber() + "&msg="
								+ b.getTextMessage();
						restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

						LOGGER.info("SMS Sent Successfully To ======>  " + b.getPhoneNumber());
						b.setSmsflag(1);
					}

					if (b.getEmail() != null && b.getEmailflag() == 0 && b.getFilePath() != null) {
						// if ( b.getEmailflag() == 0 ) {
						MailDTO mailDTO = new MailDTO();
						mailDTO.setTo(b.getEmail());
						mailDTO.setSubject("APSFL E-Bill for the month " + DateUtil.getMMMYY(b.getBillPeriodFrom()));
						mailDTO.setMsg(b.getEmailText());
						mailDTO.setFileName("CUSTOMER INVOICE.pdf");
						bais = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(b.getFilePath())));
						mailDTO.setFile(IOUtils.toByteArray(bais));
						HttpEntity<MailDTO> httpEntity1 = ApsflHelper.getHttpEntity(ipAddressValues.getUmsUserName(),
								ipAddressValues.getUmsPwd(), mailDTO);
						String url1 = ipAddressValues.getUmsURL() + "sendMailWithAttachment";
						LOGGER.info("URL1:" + url1);
						restTemplate.exchange(url1, HttpMethod.POST, httpEntity1, String.class);
						LOGGER.info("Email Sent Successfully To ======>  " + b.getEmail());
						b.setEmailflag(1);
					}
					// b.setEmailflag(0);
					invoiceGeneratorBusinessService.updateCustinvForSmsAndEmail(b);
					// break;
				}catch(Exception e) {
					LOGGER.error("error"+b.getCustomerId()+"--"+b.getEmail());
					e.printStackTrace();
				}finally{
					if(bais!=null)
					bais.close();
				}
			} 

		} catch (Exception e) {
			LOGGER.error("Problem with customer list" );
			e.printStackTrace();
		}

		return ResponseEntity.ok().body("Success");
	}
	
	
	@RequestMapping(value = "/sendNotificationToCorpus/{yearMonth}", method = RequestMethod.GET)
	public ResponseEntity<String> sendNotificationToCorpus(@PathVariable("yearMonth") String yearMonth) {
		try{
			HttpHeaders headers = new HttpHeaders();
		    
			List<CorpusUpdate> subscriberList= null;
			
			int year = Integer.parseInt(yearMonth.substring(0, 4));
			int month = Integer.parseInt(yearMonth.substring(4, 6));

				List<BillInfoDTO> billinfoDTOSMSList = invoiceGeneratorBusinessService.sendInvoiceSMS(year, month);
				
				
				headers.add("username", ipAddressValues.getCorpusUserName());
				headers.add("apikey", ipAddressValues.getCorpusApiKey());
				
				for (BillInfoDTO b : billinfoDTOSMSList) {
					String customerID = b.getCustomerId();
					
					subscriberList = invoiceGeneratorBusinessService.getCustomerID(customerID);
					
					/*CorpusUpdate corpusUpdate = new CorpusUpdate();
					corpusUpdate.setSubscribercode(subscriberID);
					corpusUpdate.setSubject("APSFL E-Bill for the month "+DateUtil.getMMMYY(yearMonth));
					corpusUpdate.setMessage(b.getTextMessage());
					
					subscriberList.add(corpusUpdate);*/
					
					for (CorpusUpdate corpusUpdate : subscriberList) {
						corpusUpdate.setSubject("APSFL E-Bill for the month "+DateUtil.getMMMYY(yearMonth));
						corpusUpdate.setMessage(b.getTextMessage());
						HttpEntity<CorpusUpdate> httpEntity = new HttpEntity<CorpusUpdate>(corpusUpdate, headers);
						String url = ipAddressValues.getCorpusAlertsMessages();
						ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
						String status = response.getBody();
						LOGGER.info(status+"status");
					}
				}
				
			
			
			
			
			
			
			//
			
			/*ObjectMapper mapper = new ObjectMapper();
			corpusResponce = mapper.readValue(status, CorpusResponse.class);*/
	} catch (Exception e) {
		e.printStackTrace();
	}
		return ResponseEntity.ok().body("Success");
	}
	
	
/*	
	public String postAlertsCorpus(AlertsDTO alertsDTO) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		CorpusResponse corpusResponce = null;

		try {
		headers.add("username", corpusUserName);
		headers.add("apikey", corpusApiKey);
		HttpEntity<AlertsDTO> httpEntity = new HttpEntity<AlertsDTO>(alertsDTO, headers);
		String url = corpusAlertsMessages;
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		String status = response.getBody();
		ObjectMapper mapper = new ObjectMapper();
		corpusResponce = mapper.readValue(status, CorpusResponse.class);
		} catch (Exception e) {
		logger.error(e.getMessage());
		} finally {
		restTemplate = null;
		headers = null;
		}
		return corpusResponce.getResponseStatus().getStatusMessage();
		}*/

	@RequestMapping(value = "/generateBillIndividualForSingleCustomer/{districtuid}/{yearMonth}/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<String> generateBillIndividualForSingleCustomer(@PathVariable("districtuid") String districtuid,
			@PathVariable("yearMonth") String yearMonth,@PathVariable("customerId") String customerId) {
		int year = Integer.parseInt(yearMonth.substring(0, 4));
		int month = Integer.parseInt(yearMonth.substring(4, 6));
		try {
			invoiceGeneratorBusinessService.generateCafInvoicesForSingleCustomer(districtuid, month, year, yearMonth,customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	
	@RequestMapping(value = "/generateBillEnterpriseGovtForSingleCustomer/{yearMonth}/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<String> generateBillEnterpriseGovtForSingleCustomer(@PathVariable("yearMonth") String yearMonth
									,@PathVariable("customerId") String customerId) {
		int year = Integer.parseInt(yearMonth.substring(0, 4));
		int month = Integer.parseInt(yearMonth.substring(4, 6));
		try {
			invoiceGeneratorBusinessService.generateEntGovtCafInvoices(month, year, yearMonth, Integer.parseInt(customerId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}

	@RequestMapping(value = "/generateBillEnterprisePrivateForSingleCustomer/{yearMonth}/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<String> generateBillEnterprisePrivateForSingleCustomer(@PathVariable("yearMonth") String yearMonth
									,@PathVariable("customerId") String customerId) {
		int year = Integer.parseInt(yearMonth.substring(0, 4));
		int month = Integer.parseInt(yearMonth.substring(4, 6));
		try {
			invoiceGeneratorBusinessService.generateEntPrivateCafInvoices(month, year, yearMonth,Integer.parseInt(customerId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@RequestMapping(value = "/downloadBillPDF", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<byte[]> downloadBillPDF(@RequestParam(value = "billPdfPath", required = false) String filePath) {
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
		is = new FileInputStream(new File(filePath));
		baos = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[4096];
        while ((len = is.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, len);
        }
        System.out.println("Server size: " + baos.size());
        /*StreamUtils.copy(is, response.getOutputStream());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);*/
        is.close();
		}catch(Exception e) {
			LOGGER.error(e);
		}finally {
			if(is != null) {
				try {
					is.close();
				}catch(Exception e) {
					LOGGER.error(e);
				}
			}
		}
        return ResponseEntity.ok(baos.toByteArray());
	}

}
