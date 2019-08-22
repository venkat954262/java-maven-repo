package com.arbiva.apfgc.invoice.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.arbiva.apfgc.invoice.dto.AdjustmentDTO;
import com.arbiva.apfgc.invoice.dto.BillInfoDTO;
import com.arbiva.apfgc.invoice.dto.CafDetailsDTO;
import com.arbiva.apfgc.invoice.dto.CorpusUpdate;
import com.arbiva.apfgc.invoice.dto.CurrentChargesSummaryDTO;
import com.arbiva.apfgc.invoice.dto.DataUsageDTO;
import com.arbiva.apfgc.invoice.dto.EnterpriseBillInfoDTO;
import com.arbiva.apfgc.invoice.dto.InvCustIdDTO;
import com.arbiva.apfgc.invoice.dto.OnetimeChargesDTO;
import com.arbiva.apfgc.invoice.dto.OtherChargesDTO;
import com.arbiva.apfgc.invoice.dto.PackageDTO;
import com.arbiva.apfgc.invoice.dto.PaymentDTO;
import com.arbiva.apfgc.invoice.dto.RecurringDTO;
import com.arbiva.apfgc.invoice.dto.RevenueShareDTO;
import com.arbiva.apfgc.invoice.dto.SummaryDTO;
import com.arbiva.apfgc.invoice.dto.TaxDTO;
import com.arbiva.apfgc.invoice.dto.TelephoneUsageDTO;
import com.arbiva.apfgc.invoice.dto.UsageDTO;
import com.arbiva.apfgc.invoice.dto.ValueAddedServiceDTO;
import com.arbiva.apfgc.invoice.utils.ConvertingUtil;

/**
 * 
 * @author srinivasa
 *
 */
@Repository("commonDAO")
public class CommonDAO {

	private static final Logger LOGGER = Logger.getLogger(CommonDAO.class);

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private EntityManager em;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	private EntityManager getEntityManager() {
		return em;
	}

	/**
	 * 
	 * @param districtId
	 * @param month
	 * @param year
	 * @return
	 */
	public int invoiceAcctNumberCount(String districtId, int month, int year) {
		int count = 0;
		List<Object> obj = null;
		try {
			Query query = getEntityManager().createNativeQuery("SELECT count(1) from ( "
					+ " SELECT GROUP_CONCAT(ci.invno) invnos " + " FROM cafinv ci,customers cu,cafs cf "
					+ " WHERE ci.custdistuid = ? " + " AND ci.invyr = ? " + " AND ci.invmn = ? "
					+ " AND ci.acctcafno = cf.cafno " + " AND cf.custid = cu.custid "
					+ " AND cu.custtypelov ='INDIVIDUAL' " + " AND cf.status='6' " + " GROUP BY ci.pmntcustid) a ");
			query.setParameter(1, districtId);
			query.setParameter(2, year);
			query.setParameter(3, month);

			obj = query.getResultList();
			if (!obj.isEmpty()) {
				count = obj.get(0) == null ? 0 : Integer.parseInt(obj.get(0).toString());
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while counting the number cafinvoice records:" + e);
			e.printStackTrace();
		}
		return count;
	}

	public int getEntGovtInvoiceNumberCount(int month, int year) {
		int count = 0;
		List<Object> obj = null;
		try {
			Query query = getEntityManager()
					.createNativeQuery(" SELECT count(1) from ( SELECT GROUP_CONCAT(invno) invnos, pmntcustid "
							+ " FROM cafinv ci " + " WHERE exists (select 1 from cafs caf,entcustomers entcust  "
							+ "               where caf.custid = entcust.custid  "
							+ "               and entcust.enttypelov='GOVT' and caf.status='6' "
							+ "               and ci.acctcafno = caf.cafno)  "
							+ " AND invyr = ? AND invmn = ? GROUP BY pmntcustid ORDER BY pmntcustid ) a ");
			query.setParameter(1, year);
			query.setParameter(2, month);

			obj = query.getResultList();
			if (!obj.isEmpty()) {
				count = obj.get(0) == null ? 0 : Integer.parseInt(obj.get(0).toString());
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while counting the number cafinvoice records:" + e);
			e.printStackTrace();
		}
		return count;
	}

	public int getEntPrivateInvoiceNumberCount(int month, int year) {
		int count = 0;
		List<Object> obj = null;
		try {
			Query query = getEntityManager()
					.createNativeQuery(" SELECT count(1) from ( SELECT GROUP_CONCAT(invno) invnos, pmntcustid "
							+ " FROM cafinv ci " + " WHERE exists (select 1 from cafs caf,entcustomers entcust  "
							+ "               where caf.custid = entcust.custid  "
							+ "               and entcust.enttypelov='PRIVATE' and caf.status='6' "
							+ "               and ci.acctcafno = caf.cafno)  "
							+ " AND invyr = ? AND invmn = ? GROUP BY pmntcustid ORDER BY pmntcustid ) a ");
			query.setParameter(1, year);
			query.setParameter(2, month);

			obj = query.getResultList();
			if (!obj.isEmpty()) {
				count = obj.get(0) == null ? 0 : Integer.parseInt(obj.get(0).toString());
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while counting the number cafinvoice records:" + e);
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 
	 * @param districtId
	 * @param month
	 * @param year
	 * @param start
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InvCustIdDTO> getInvoiceNumbers(String districtId, int month, int year) {
		List<InvCustIdDTO> cafAcctNumbers = new ArrayList<>();
		List<Object[]> objList = new ArrayList<>();
		try {
			Query query = getEntityManager()
					.createNativeQuery("SELECT GROUP_CONCAT(ci.invno) invnos, ci.pmntcustid,cu.custtypelov"
							+ " FROM cafinv ci,customermst cu,cafs cf " + " WHERE ci.custdistuid = ? "
							+ " AND ci.invyr = ? " + " AND ci.invmn = ? " + " AND ci.acctcafno = cf.cafno "
							+ " AND cf.custid = cu.custid " + " AND cu.custtypelov ='INDIVIDUAL' "
							// +" AND cf.status='6' "
							+ " GROUP BY ci.pmntcustid " + " ORDER BY ci.pmntcustid ");
			query.setParameter(1, districtId);
			query.setParameter(2, year);
			query.setParameter(3, month);
			// query.setFirstResult(start);
			// query.setMaxResults(count);

			objList = query.getResultList();

			for (Object[] object : objList) {
				InvCustIdDTO invDTO = new InvCustIdDTO();
				invDTO.setInvno(object[0] == null ? "" : object[0].toString());
				invDTO.setPmntcustid(object[1] == null ? "" : object[1].toString());
				invDTO.setCustTypeLOV(object[2] == null ? "" : object[2].toString());
				cafAcctNumbers.add(invDTO);
			}
		} catch (Exception e) {
			LOGGER.error(String
					.format("Error occured while retrieving cafinvoice account number between start:%s and end:%s"), e);
			e.printStackTrace();
		}
		return cafAcctNumbers;
	}

	/**
	 * Invoice css
	 */
	
	/**
	 * 
	 * @param districtId
	 * @param month
	 * @param year
	 * @param start
	 * @param count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public InvCustIdDTO getInvoiceNumbersIndividualCSS(String customerID, int month, int year) {
		List<InvCustIdDTO> cafAcctNumbers = new ArrayList<>();
		List<Object[]> objList = new ArrayList<>();
		InvCustIdDTO invDTO = new InvCustIdDTO();
		try {
			LOGGER.info("CommonDAO::getInvoiceNumbersIndividualCSS::Fetching::START");
			String sb= "SELECT GROUP_CONCAT(ci.invno) invnos, ci.pmntcustid,cu.custtypelov"
					+ " FROM cafinv ci,customermst cu,cafs cf  WHERE "
					+ " ci.invyr = "+year+"  AND ci.invmn = "+month+"  AND ci.acctcafno = cf.cafno "
					+ " AND cf.custid = cu.custid AND cu.custid = "+customerID+" AND  cu.custtypelov ='INDIVIDUAL' "
					// +" AND cf.status='6' "
					+ " GROUP BY ci.pmntcustid ORDER BY ci.pmntcustid ";
			
			
			Query query = getEntityManager()
					.createNativeQuery("SELECT GROUP_CONCAT(ci.invno) invnos, ci.pmntcustid,cu.custtypelov"
							+ " FROM cafinv ci,customermst cu,cafs cf  WHERE "
							+ " ci.invyr = ?  AND ci.invmn = ?  AND ci.acctcafno = cf.cafno "
							+ " AND cf.custid = cu.custid AND cu.custid = ? AND  cu.custtypelov ='INDIVIDUAL' "
							// +" AND cf.status='6' "
							+ " GROUP BY ci.pmntcustid ORDER BY ci.pmntcustid ");
			
			query.setParameter(1, year);
			query.setParameter(2, month);
			query.setParameter(3, customerID);
			// query.setFirstResult(start);
			// query.setMaxResults(count);

			objList = query.getResultList();
			LOGGER.info("CommonDAO::getInvoiceNumbersIndividualCSS::Fetching::END");
			//Object obj=query.getSingleResult();
			LOGGER.info("CommonDAO::getInvoiceNumbersIndividualCSS::Converting To Object::START");
			for (Object[] object : objList) {
				
				invDTO.setInvno(object[0] == null ? "" : object[0].toString());
				invDTO.setPmntcustid(object[1] == null ? "" : object[1].toString());
				invDTO.setCustTypeLOV(object[2] == null ? "" : object[2].toString());
				cafAcctNumbers.add(invDTO);
			}
			LOGGER.info("CommonDAO::getInvoiceNumbersIndividualCSS::Converting To Object::END");
		} catch (Exception e) {
			LOGGER.error(String
					.format("Error occured while retrieving cafinvoice account number between start:%s and end:%s"), e);
			e.printStackTrace();
		}
		return invDTO;
	}

	
	@SuppressWarnings("unchecked")
	public List<InvCustIdDTO> getInvoiceNumbersEntGovt(int month, int year) {
		List<InvCustIdDTO> cafAcctNumbers = new ArrayList<>();
		List<Object[]> objList = new ArrayList<>();
		try {
			Query query = getEntityManager().createNativeQuery("SELECT GROUP_CONCAT(invno) invnos, pmntcustid  "
					+ " FROM cafinv ci, customermst entcust  " + " WHERE 1=1 " + " AND entcust.enttypelov='GOVT'  "
					+ "  AND entcust.custid = ci.pmntcustid"
					+ "  AND invyr = ? AND invmn = ? GROUP BY pmntcustid ORDER BY pmntcustid ");
			query.setParameter(1, year);
			query.setParameter(2, month);
			// query.setFirstResult(start);
			// query.setMaxResults(count);

			objList = query.getResultList();

			for (Object[] object : objList) {
				InvCustIdDTO invDTO = new InvCustIdDTO();
				invDTO.setInvno(object[0] == null ? "" : object[0].toString());
				invDTO.setPmntcustid(object[1] == null ? "" : object[1].toString());
				cafAcctNumbers.add(invDTO);
			}
		} catch (Exception e) {
			LOGGER.error(String
					.format("Error occured while retrieving cafinvoice account number between start:%s and end:%s"), e);
			e.printStackTrace();
		}
		return cafAcctNumbers;
	}

	@SuppressWarnings("unchecked")
	public List<InvCustIdDTO> getInvoiceNumbersEntPrivate(int month, int year) {
		List<InvCustIdDTO> cafAcctNumbers = new ArrayList<>();
		List<Object[]> objList = new ArrayList<>();
		try {
			/*
			 * Query query = getEntityManager().createNativeQuery(
			 * "SELECT GROUP_CONCAT(invno) invnos, pmntcustid " +
			 * " FROM cafinv ci " +
			 * " WHERE exists (select 1 from cafs caf,entcustomers entcust  " +
			 * "               where caf.custid = entcust.custid  " +
			 * "               and entcust.enttypelov='PRIVATE' and caf.status='6' "
			 * +"               and ci.acctcafno = caf.cafno)  " +
			 * " AND invyr = ? AND invmn = ? GROUP BY pmntcustid ORDER BY pmntcustid "
			 * );
			 */
			Query query = getEntityManager().createNativeQuery("SELECT GROUP_CONCAT(invno) invnos, pmntcustid  "
					+ " FROM cafinv ci, customermst entcust  " + " WHERE 1=1 " + " AND entcust.enttypelov='PRIVATE'  "
					+ "  AND entcust.custid = ci.pmntcustid"
					+ "  AND invyr = ? AND invmn = ? GROUP BY pmntcustid ORDER BY pmntcustid ");

			query.setParameter(1, year);
			query.setParameter(2, month);
			// query.setFirstResult(start);
			// query.setMaxResults(count);

			objList = query.getResultList();

			for (Object[] object : objList) {
				InvCustIdDTO invDTO = new InvCustIdDTO();
				invDTO.setInvno(object[0] == null ? "" : object[0].toString());
				invDTO.setPmntcustid(object[1] == null ? "" : object[1].toString());
				cafAcctNumbers.add(invDTO);
			}
		} catch (Exception e) {
			LOGGER.error(String
					.format("Error occured while retrieving cafinvoice account number between start:%s and end:%s"), e);
			e.printStackTrace();
		}
		return cafAcctNumbers;
	}

	/**
	 * 
	 * @param invNumber
	 * @return
	 */
	@Transactional
	public EnterpriseBillInfoDTO getConsolidatedInvoiceDetails(String invNumber) {
		EnterpriseBillInfoDTO entDto = null;
		BillInfoDTO dto = null;
		String individualInvNos[] = null;
		List<BillInfoDTO> individualBillInfoList = new ArrayList<>();
		try {
			LOGGER.info("START::getConsolidatedInvoiceDetails"+invNumber);
			entDto = new EnterpriseBillInfoDTO();

			
				
				// Fetch Invoice Information
				// (invno,invdate,invfdate,invtdate,invduedate,prevbal,prevpaid,custid,custtypelov,acctcafno,pmntcustid,invamt)
				entDto = setEntInvoiceInfo(entDto, invNumber);
				
				// Fetch Customer Address Information
				entDto = setEntInvoiceCustomerInfo(entDto);
				
				// Fetch Payment Information
				/*entDto = setEntInvoicePaymentInfo(entDto, entDto.getBillFromDate(), entDto.getBillToDate());
				
				List<PaymentDTO> payments=entDto.getPayments();
				if(payments.size() == 0){
					Calendar cal=Calendar.getInstance();
					int curyear = cal.get(Calendar.YEAR);
					int curmonth = cal.get(Calendar.MONTH);
					int fday = 1;
					cal.set(curyear, curmonth, fday);
					String invFDate=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
					String invTDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					LOGGER.info("Fetching current month payment details for custid:"+entDto.getPaymentCustomerId());
					entDto = setEntInvoicePaymentInfo(entDto, invFDate, invTDate);
					if(entDto.getPayments().size()>0)
						updateInvPaymentPdfDisFlag(entDto.getPaymentCustomerId(), invFDate, invTDate);
				}else{
					updateInvPaymentPdfDisFlag(entDto.getPaymentCustomerId(), entDto.getBillFromDate(), entDto.getBillToDate());
				}*/
				
				String invTDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				entDto = setEntInvoicePaymentInfo(entDto, entDto.getBillFromDate(), invTDate);
				List<PaymentDTO> payments=entDto.getPayments();
				if(payments.size() > 0){
					updateInvPaymentPdfDisFlag(entDto.getPaymentCustomerId(), entDto.getBillFromDate(), invTDate);
				}
				
				// Fetch Summary of Current Charges - All Accounts
				entDto = setEntAllAccountsCurrentChargesSummary(entDto, entDto.getInvMonth(), entDto.getInvYear());
				
				individualInvNos = invNumber.split(",");
				
				// Fetch Each Individual Invoice Information.
				
				LOGGER.info("List Of Invoices Numbers ....."+individualInvNos.length);
				for (String invno : individualInvNos) {
					dto = getIndividualInvoiceDetails(Long.valueOf(invno),entDto.getInvYear()+entDto.getInvMonth());
					
					if (dto != null) {
						/**Added New Condition for Checking Customer Sub type**/
						if(entDto.getCustomerType().equalsIgnoreCase("ENTERPRISE") && entDto.getCustomerSubType().equalsIgnoreCase("GOVT")) {
							
						} else {
							/**Added for Changing PDF bill as per revenue share**/
							Map<String, RevenueShareDTO> revenueShareDTO = getRevenueShareDetails(invno);
							dto.setRevenueShareDTO(revenueShareDTO);
							/****/
						}
						/****/
						individualBillInfoList.add(dto);
					}
				}
				
				LOGGER.info("List Of Invoices Size ....."+individualBillInfoList.size());
				entDto.setIndividualBillInfoList(individualBillInfoList);

		} catch (Exception ex) {
			LOGGER.error("ERROR::getConsolidatedInvoiceDetails" + ex);
		} finally {
			individualInvNos = null;
			individualBillInfoList = null;
		}

		return entDto;
	}
	@Transactional
	public BillInfoDTO getIndividualInvoiceDetails(long invNumber,String yearMonth) {
		BillInfoDTO dto = null;
		int month = 0;
		int year = 0;
		
		try {
			//LOGGER.info("START::getIndividualInvoiceDetails Invoice No :: " + invNumber);

			// int month =
			// Integer.valueOf(DateUtil.getCurrentMonth(InvoiceEngineConstants.MONTH_PATTERN_IN_VALUE))-1;
			// int year =
			// Integer.valueOf(DateUtil.getCurrentMonth(InvoiceEngineConstants.YEAR_PATTERN));

			// Fetch Invoice Information
			// (invno,invdate,invfdate,invtdate,invduedate,prevbal,prevpaid,custid,custtypelov,acctcafno,pmntcustid,invamt)
			dto = setInvoiceInfo(dto, invNumber,yearMonth);

			if (dto != null) {
				month = Integer.parseInt(dto.getInvMonth().trim());
				year = Integer.parseInt(dto.getInvYear().trim());

				// Fetch Customer Address Information
				dto = setInvoiceCustomerInfo(dto);

				// Fetch Payment Information
				/*dto = setInvoicePaymentInfo(dto, dto.getBillPeriodFrom(), dto.getBillPeriodTo());
				
				List<PaymentDTO> payments=dto.getPayments();
				if(payments.size() == 0){
					Calendar cal=Calendar.getInstance();
					int curyear = cal.get(Calendar.YEAR);
					int curmonth = cal.get(Calendar.MONTH);
					int fday = 1;
					cal.set(curyear, curmonth, fday);
					String invFDate=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
					String invTDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					LOGGER.info("Fetching current month payment details for cafno:"+dto.getAccountNumber());
					dto = setInvoicePaymentInfo(dto, invFDate, invTDate);
					if(dto.getPayments().size()>0)
						updateInvPaymentPdfDisFlag(dto.getCustomerId(), invFDate, invTDate);
				}else{
					updateInvPaymentPdfDisFlag(dto.getCustomerId(), dto.getBillPeriodFrom(), dto.getBillPeriodTo());
				}*/
				String invTDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				dto = setInvoicePaymentInfo(dto, dto.getBillPeriodFrom(), invTDate);
				List<PaymentDTO> payments=dto.getPayments();
				if(payments.size() > 0){
					updateInvPaymentPdfDisFlag(dto.getCustomerId(), dto.getBillPeriodFrom(), invTDate);
				}
				
				// Fetch Package Information
				dto = setInvoicePackageInfo(dto);

				dto = setInternetSummaryInfo(dto, month, year);

				// Fetch Additional Charges like Recurring
				// Charges/Adjustments/One time charges/Late Fees Information
				dto = setAdditionalChargesInfo(dto);

				// Fetch tax details
				dto = setTaxInfo(dto);

				// Fetch Usage Charges Information
				dto = setUsageChargesInfo(dto);

				// Fetch Recurring Charge details & Internet Usage
				dto = setRecurrChargeDtlsInfo(dto, month, year);

				// Fetch Internet Usage
				dto = setInternetUsageInfo(dto, month, year);

				// Fetch Usage Charges Of Phone
				dto = setUsageChargesSummaryInfo(dto, month, year);

				// Fetch Itemized Call Details
				dto = setItemizedCallInfo(dto, month, year);

				// Fetch Other Charges / Discounts & Adjustments
				dto = setOtherChargesInfo(dto);
			}

			//LOGGER.info("END::getIndividualInvoiceDetails");
		} catch (Exception ex) {
			LOGGER.error("ERROR::getIndividualInvoiceDetails Invoice No :: " + invNumber + "  \n " + ex);
		} finally {

		}
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @param invNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BillInfoDTO setInvoiceInfo(BillInfoDTO dto, long invNumber,String yearMonth) {
		String qry = null;
		Query query = null;
		Object[] objects = null;
		List<Object[]> oltObjList = null;
		StringBuffer sb = new StringBuffer();
		String year;
		String month;
		String whereClause="";
		try {
			LOGGER.info("START::setInvoiceInfo");
			if (yearMonth!=null && yearMonth!=""){
				year=yearMonth.substring(0, 4);
				month=yearMonth.substring(4);
				whereClause=" and cusi.invyr='"+year+"' and cusi.invmn='"+month+"'" ;
			
			}
			// ADDED cafinv.acctcafno=cafs.pmntcustcode FOR ENTERPRISE CHECK -
			// NEED TO CHECK
			/*
			 * qry =
			 * "SELECT cafinv.invno, cafinv.invdate, cafinv.invfdate, cafinv.invtdate, cafinv.invduedate, cafinv.prevbal, cafinv.prevpaid, "
			 * +
			 * " cafinv.pmntcustid as custid, cafs.custtypelov, cafinv.acctcafno, cafinv.pmntcustid, cafinv.invamt+cafinv.srvctax+cafinv.swatchtax+cafinv.kisantax+cafinv.enttax currentBillAmount, invmn, invyr "
			 * + " FROM cafinv cafinv, cafs cafs " +
			 * " WHERE cafinv.acctcafno=cafs.cafno AND cafinv.invno=?";
			 */

			LOGGER.info("CommonDAO::setInvoiceInfo::Fetching::START");
			qry = "SELECT cusi.custinvno, cusi.invdate, cusi.invfdate, cusi.invtdate, cusi.invduedate, cusi.prevbal, cusi.prevpaid, "
					+ " cusi.pmntcustid as custid, cafs.custtypelov, cafinv.acctcafno, cusi.pmntcustid, cafinv.invamt+cafinv.srvctax+cafinv.swatchtax+cafinv.kisantax+cafinv.enttax currentBillAmount, cusi.invmn, cusi.invyr "
					+ " FROM cafinv cafinv, cafs cafs,custinv cusi "
					+ " WHERE cafinv.acctcafno=cafs.cafno AND cafinv.invno=? and cusi.custinvno = cafinv.custinvno and cusi.invmn=cafinv.invmn and cusi.invyr=cafinv.invyr and cusi.pmntcustid=cafinv.pmntcustid and cusi.custdistuid=cafinv.custdistuid"
					+ whereClause;

			LOGGER.info("setInvoiceInfo Query " + qry + " invoice Number " + invNumber);
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, invNumber);
			oltObjList = (List<Object[]>) query.getResultList();
			LOGGER.info("CommonDAO::setInvoiceInfo::Fetching::END");
			LOGGER.info("CommonDAO::setInvoiceInfo::Converting to Object::START");
			if (!oltObjList.isEmpty()) {
				dto = new BillInfoDTO();
				objects = oltObjList.get(0);
				dto.setBillNumber(objects[0] == null ? "" : objects[0].toString());
				dto.setBillDate(objects[1] == null ? "" : objects[1].toString());
				dto.setBillPeriodFrom(objects[2] == null ? "" : objects[2].toString());
				dto.setBillPeriodTo(objects[3] == null ? "" : objects[3].toString());
				dto.setDueDate(objects[4] == null ? "" : objects[4].toString());
				dto.setPrevBalance(objects[5] == null ? new BigDecimal("0") : new BigDecimal(objects[5].toString()));
				dto.setLastPayment(objects[6] == null ? new BigDecimal("0") : new BigDecimal(objects[6].toString()));
				dto.setCustomerId(objects[7] == null ? "" : objects[7].toString());
				dto.setCustomerType(objects[8] == null ? "" : objects[8].toString());
				dto.setAccountNumber(objects[9] == null ? "" : objects[9].toString());
				dto.setParentCustCode(objects[10] == null ? "" : objects[10].toString());
				dto.setCurrentBillAmount(
						objects[11] == null ? new BigDecimal("0") : new BigDecimal(objects[11].toString()));
				dto.setInvMonth(objects[12] == null ? "" : objects[12].toString());
				dto.setInvYear(objects[13] == null ? "" : objects[13].toString());
			}
			LOGGER.info("CommonDAO::setInvoiceInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setInvoiceInfo" + ex);
		} finally {
			qry = null;
			query = null;
			objects = null;
			oltObjList = null;
			sb = null;
		}
		return dto;
	}

	/*
	 * @SuppressWarnings("unchecked") public EnterpriseBillInfoDTO
	 * setEntInvoiceInfo(EnterpriseBillInfoDTO dto, String invNumber) { String
	 * qry = null; Query query = null; Object[] objects = null; List<Object[]>
	 * oltObjList = null; try{ LOGGER.info("START::setEntInvoiceInfo"); //ADDED
	 * cafinv.acctcafno=cafs.pmntcustcode FOR ENTERPRISE CHECK - NEED TO CHECK
	 * qry =
	 * "SELECT concat(2,cafinv.pmntcustid), min(cafinv.invdate), min(cafinv.invfdate), min(cafinv.invtdate), min(cafinv.invduedate), "
	 * +
	 * " sum(cafinv.invamt+srvctax+swatchtax+kisantax+enttax), sum(cafinv.prevbal), sum(cafinv.prevpaid), cafinv.pmntcustid as custid, cafinv.pmntcustid, caf.custtypelov, min(invmn), min(invyr) "
	 * +" FROM cafinv cafinv, cafs caf " +" WHERE cafinv.acctcafno = caf.cafno "
	 * +" AND cafinv.invno in ("+invNumber+") " +
	 * " GROUP BY cafinv.pmntcustid, caf.custtypelov ";
	 * 
	 * LOGGER.info("setEntInvoiceInfo Query " + qry + " invoice Number " +
	 * invNumber); query = getEntityManager().createNativeQuery(qry);
	 * //query.setParameter("invNo", invNumber); oltObjList =
	 * (List<Object[]>)query.getResultList(); if(!oltObjList.isEmpty()){ objects
	 * = oltObjList.get(0); dto.setBillNumber(objects[0] == null ? "" :
	 * objects[0].toString()); dto.setBillDate(objects[1] == null ? "" :
	 * objects[1].toString()); dto.setBillFromDate(objects[2] == null ? "" :
	 * objects[2].toString()); dto.setBillToDate(objects[3] == null ? "" :
	 * objects[3].toString()); //dto.setBillPeriod(objects[2] == null ? "" :
	 * objects[2].toString() + " to " +objects[3] == null ? "" :
	 * objects[3].toString()); dto.setDueDate(objects[4] == null ? "" :
	 * objects[4].toString()); dto.setCurrentBillAmount(objects[5] == null ? new
	 * BigDecimal("0") : new BigDecimal(objects[5].toString()));
	 * dto.setPrevBalance(objects[6] == null ? new BigDecimal("0") : new
	 * BigDecimal(objects[6].toString())); dto.setLastPayment(objects[7] == null
	 * ? new BigDecimal("0") : new BigDecimal(objects[7].toString()));
	 * dto.setEnterpriseCustomerId(objects[8] == null ? "" :
	 * objects[8].toString()); dto.setPaymentCustomerId(objects[9] == null ? ""
	 * : objects[9].toString()); dto.setCustomerType(objects[10] == null ? "" :
	 * objects[10].toString()); dto.setInvMonth(objects[11] == null ? "" :
	 * objects[11].toString()); dto.setInvYear(objects[12] == null ? "" :
	 * objects[12].toString()); } } catch(Exception ex) {
	 * LOGGER.error("ERROR::setEntInvoiceInfo" + ex); } finally { qry = null;
	 * query = null; objects = null; oltObjList = null; } return dto; }
	 */

	@SuppressWarnings("unchecked")
	public EnterpriseBillInfoDTO setEntInvoiceInfo(EnterpriseBillInfoDTO dto, String invNumber) {
		String qry = null;
		Query query = null;
		Object[] objects = null;
		List<Object[]> oltObjList = null;
		try {
			LOGGER.info("START::setEntInvoiceInfo");
			// ADDED cafinv.acctcafno=cafs.pmntcustcode FOR ENTERPRISE CHECK -
			// NEED TO CHECK
			/*
			 * qry =
			 * "SELECT concat(2,cafinv.pmntcustid), min(cafinv.invdate), min(cafinv.invfdate), min(cafinv.invtdate), min(cafinv.invduedate), "
			 * +
			 * " sum(cafinv.invamt+srvctax+swatchtax+kisantax+enttax), sum(cafinv.prevbal), sum(cafinv.prevpaid), cafinv.pmntcustid as custid, cafinv.pmntcustid, caf.custtypelov, min(invmn), min(invyr) "
			 * +" FROM cafinv cafinv, cafs caf " +
			 * " WHERE cafinv.acctcafno = caf.cafno " +" AND cafinv.invno in ("
			 * +invNumber+") " +" GROUP BY cafinv.pmntcustid, caf.custtypelov ";
			 */

			/**Commented Old query slightly modified to get enttype love**/
			/*qry = "SELECT distinct custinv.custinvno, custinv.invdate, custinv.invfdate, custinv.invtdate, custinv.invduedate, "
					+ " custinv.invamt+custinv.srvctax+custinv.swatchtax+custinv.kisantax+custinv.enttax, custinv.prevbal, custinv.prevpaid, custinv.pmntcustid as custid, custinv.pmntcustid, caf.custtypelov, custinv.invmn, custinv.invyr"
					+ " FROM  cafs caf,custinv custinv," + " (SELECT distinct cafinv.custinvno,cafinv.custdistuid,"
					+ " cafinv.invmn, cafinv.invyr,cafinv.acctcafno" + " FROM cafinv cafinv, cafs caf "
					+ " WHERE cafinv.acctcafno = caf.cafno " + " AND cafinv.invno in (" + invNumber + ")) as cfinv "
					+ " WHERE cfinv.acctcafno = caf.cafno "
					+ " AND  cfinv.custinvno=custinv.custinvno and cfinv.custdistuid=custinv.custdistuid "
					+ " AND cfinv.invmn=custinv.invmn and cfinv.invyr = custinv.invyr";*/
			/****/
			qry = "SELECT distinct custinv.custinvno, custinv.invdate, custinv.invfdate, custinv.invtdate, custinv.invduedate, "
					+ " custinv.invamt+custinv.srvctax+custinv.swatchtax+custinv.kisantax+custinv.enttax, custinv.prevbal, custinv.prevpaid, custinv.pmntcustid as custid, custinv.pmntcustid, caf.custtypelov, custinv.invmn, custinv.invyr, cmst.enttypelov"
					+ " FROM  cafs caf,custinv custinv, customermst cmst," + " (SELECT distinct cafinv.custinvno,cafinv.custdistuid,"
					+ " cafinv.invmn, cafinv.invyr,cafinv.acctcafno" + " FROM cafinv cafinv, cafs caf "
					+ " WHERE cafinv.acctcafno = caf.cafno " + " AND cafinv.invno in (" + invNumber + ")) as cfinv "
					+ " WHERE cfinv.acctcafno = caf.cafno "
					+ " AND  cfinv.custinvno=custinv.custinvno and cfinv.custdistuid=custinv.custdistuid "
					+ " AND cfinv.invmn=custinv.invmn and cfinv.invyr = custinv.invyr"
					+ " AND caf.custid = cmst.custid";

			/*
			 * qry = "(SELECT distinct cafinv.custinvno,cafinv.custdistuid" +
			 * " cafinv.invmn, cafinv.invyr,cafinv.acctcafno" +
			 * " FROM cafinv cafinv, cafs caf " +
			 * " WHERE cafinv.acctcafno = caf.cafno " +" AND cafinv.invno in ("
			 * +invNumber+")) as cfinv ";
			 */

			LOGGER.info("setEntInvoiceInfo Query " + qry + " invoice Number " + invNumber);

			query = getEntityManager().createNativeQuery(qry);
			// query.setParameter("invNo", invNumber);
			oltObjList = (List<Object[]>) query.getResultList();
			if (!oltObjList.isEmpty()) {
				objects = oltObjList.get(0);
				dto.setBillNumber(objects[0] == null ? "" : objects[0].toString());
				dto.setBillDate(objects[1] == null ? "" : objects[1].toString());
				dto.setBillFromDate(objects[2] == null ? "" : objects[2].toString());
				dto.setBillToDate(objects[3] == null ? "" : objects[3].toString());
				// dto.setBillPeriod(objects[2] == null ? "" :
				// objects[2].toString() + " to " +objects[3] == null ? "" :
				// objects[3].toString());
				dto.setDueDate(objects[4] == null ? "" : objects[4].toString());
				dto.setCurrentBillAmount(
						objects[5] == null ? new BigDecimal("0") : new BigDecimal(objects[5].toString()));
				dto.setPrevBalance(objects[6] == null ? new BigDecimal("0") : new BigDecimal(objects[6].toString()));
				dto.setLastPayment(objects[7] == null ? new BigDecimal("0") : new BigDecimal(objects[7].toString()));
				dto.setEnterpriseCustomerId(objects[8] == null ? "" : objects[8].toString());
				dto.setPaymentCustomerId(objects[9] == null ? "" : objects[9].toString());
				dto.setCustomerType(objects[10] == null ? "" : objects[10].toString());
				dto.setInvMonth(objects[11] == null ? "" : objects[11].toString());
				dto.setInvYear(objects[12] == null ? "" : objects[12].toString());
				/**Added Customer Subtype love**/
				dto.setCustomerSubType(objects[13] == null ? "" : objects[13].toString());
				/****/
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR::setEntInvoiceInfo" + ex);
		} finally {
			qry = null;
			query = null;
			objects = null;
			oltObjList = null;
		}
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BillInfoDTO setInvoiceCustomerInfo(BillInfoDTO dto) {
		String qry = null;
		Query query = null;
		Object[] objects = null;
		List<Object[]> oltObjList = null;
		try {
			LOGGER.info("START::setInvoiceCustomerInfo");
			LOGGER.info("CommonDAO::setInvoiceCustomerInfo::Fetching::START");
			if (dto.getCustomerType().equalsIgnoreCase("INDIVIDUAL")) {
				qry = "SELECT CONCAT_WS(' ', CASE WHEN ifnull(customers.fname,'') = '' THEN null ELSE upper(customers.fname) END "
						+ " 			    , CASE WHEN ifnull(customers.mname,'') = '' THEN null ELSE upper(customers.mname) END  "
						+ "              , CASE WHEN ifnull(customers.lname,'') = '' THEN null ELSE upper(customers.lname) END) fullname "
						+ " ,CONCAT_WS(', ', CASE WHEN ifnull(caf.inst_addr1,'') = '' THEN null ELSE upper(customers.addr1) END "
						+ " 			  , CASE WHEN ifnull(caf.inst_addr2,'') = '' THEN null ELSE upper(customers.addr2) END  "
						+ "            , CASE WHEN ifnull(caf.inst_locality,'') = '' THEN null ELSE upper(customers.locality) END "
						+ "            , CASE WHEN ifnull(caf.inst_area,'') = '' THEN null ELSE upper(customers.area) END "
						+ "            , CASE WHEN ifnull(v.villagename,'') = '' THEN null ELSE upper(v.villagename) END "
						+ " 			  , CASE WHEN ifnull(m.mandalname,'') = '' THEN null ELSE upper(m.mandalname) END "
						+ " 			  , CASE WHEN ifnull(d.districtname,'') = '' THEN null ELSE upper(d.districtname) END "
						+ " 			  , CASE WHEN ifnull(caf.inst_state,'') = '' THEN null ELSE upper(customers.state) END "
						+ "            , CASE WHEN ifnull(caf.inst_pin,'') = '' THEN null ELSE upper(customers.pin) END) address, (select group_concat(concat(0,phoneno)) from cafsrvcphonenos where cafno = caf.cafno GROUP BY cafno) phoneno "
						+ " FROM cafs caf, customers customers, districts d, mandals m, villages v "
						+ " WHERE caf.custid=customers.custid  " + " AND caf.inst_district = d.districtuid  "
						+ " AND caf.inst_mandal = m.mandalslno  " + " AND caf.inst_district = m.districtuid  "
						+ " AND caf.inst_city_village = v.villageslno  " + " AND caf.inst_mandal = v.mandalslno  "
						+ " AND caf.inst_district = v.districtuid  " + " AND customers.custid=? "
						+ " AND caf.cafno=?  ";
				query = getEntityManager().createNativeQuery(qry);
				query.setParameter(1, dto.getCustomerId());
				query.setParameter(2, dto.getAccountNumber());
				LOGGER.info("START::setInvoiceCustomerInfo INDIVIDUAL : " + query);
			} else if (dto.getCustomerType().equalsIgnoreCase("ENTERPRISE")) {
				qry = "SELECT ifnull(upper(caf.cpeplace),'') fullname "
						+ "	,CONCAT_WS(', ', CASE WHEN ifnull(caf.inst_addr1,'') = '' THEN null ELSE upper(entcustomers.addr1) END "
						+ " , CASE WHEN ifnull(caf.inst_addr2,'') = '' THEN null ELSE upper(entcustomers.addr2) END "
						+ " , CASE WHEN ifnull(caf.inst_locality,'') = '' THEN null ELSE upper(entcustomers.locality) END "
						+ " , CASE WHEN ifnull(caf.inst_area,'') = '' THEN null ELSE upper(entcustomers.area) END "
						+ " , CASE WHEN ifnull(v.villagename,'') = '' THEN null ELSE upper(v.villagename) END  "
						+ " , CASE WHEN ifnull(m.mandalname,'') = '' THEN null ELSE upper(m.mandalname) END "
						+ " , CASE WHEN ifnull(d.districtname,'') = '' THEN null ELSE upper(d.districtname) END "
						+ " , CASE WHEN ifnull(caf.inst_state,'') = '' THEN null ELSE upper(entcustomers.state) END "
						+ " , CASE WHEN ifnull(caf.inst_pin,'') = '' THEN null ELSE upper(entcustomers.pin) END) address, (select group_concat(concat(0,phoneno)) from cafsrvcphonenos where cafno = caf.cafno GROUP BY cafno) phoneno "
						+ " FROM cafs caf, entcustomers entcustomers, districts d, mandals m, villages v "
						+ " WHERE caf.pmntcustcode=entcustomers.custid " + " AND caf.inst_district = d.districtuid "
						+ " AND caf.inst_mandal = m.mandalslno " + " AND caf.inst_district = m.districtuid "
						+ " AND caf.inst_city_village = v.villageslno " + " AND caf.inst_mandal = v.mandalslno "
						+ " AND caf.inst_district = v.districtuid " + " AND caf.cafno=?";
				query = getEntityManager().createNativeQuery(qry);
				query.setParameter(1, dto.getAccountNumber());
				LOGGER.info("START::setInvoiceCustomerInfo ENTERPRISE : " + query);
			}
			LOGGER.info("setInvoiceCustomerInfo Query " + qry + " Customer ID " + dto.getCustomerId());
			oltObjList = (List<Object[]>) query.getResultList();
			LOGGER.info("CommonDAO::setInvoiceCustomerInfo::Fetching::END");
			LOGGER.info("CommonDAO::setInvoiceCustomerInfo::Converting to Object::START");
			if (!oltObjList.isEmpty()) {
				objects = oltObjList.get(0);
				dto.setFullName(objects[0] == null ? "" : objects[0].toString());
				dto.setAddress(objects[1] == null ? "" : objects[1].toString());
				dto.setPhoneNumber(objects[2] == null ? "" : objects[2].toString());
			}
			LOGGER.info("CommonDAO::setInvoiceCustomerInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setInvoiceCustomerInfo" + ex);
		} finally {
			qry = null;
			query = null;
			objects = null;
			oltObjList = null;
		}
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @param invFDate
	 * @param invTDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BillInfoDTO setInvoicePaymentInfo(BillInfoDTO dto, String invFDate, String invTDate) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		List<PaymentDTO> paymentInfoList = null;
		try {
			LOGGER.info("START::setInvoicePaymentInfo");
			LOGGER.info("CommonDAO::setInvoicePaymentInfo::Fetching::START");
		//	qry = "SELECT DATE_FORMAT(pmntdate, '%Y-%m-%d'), pmntamt, pmntmodelov FROM payments WHERE status=1 and acctcafno=? AND pmntdate BETWEEN ? AND ?";
			qry = "SELECT DATE_FORMAT(pmntdate, '%Y-%m-%d'), pmntamt, pmntmodelov FROM payments WHERE status=1 and PDFDisFlag='N' and pmntcustid=? AND pmntdate BETWEEN ? AND ?";
			LOGGER.info("Payment Cust Id " + dto.getCustomerId()
					+ " Invoice From Date " + invFDate + " Invoice To Date " + invTDate);
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getCustomerId());
			query.setParameter(2, invFDate);
			query.setParameter(3, invTDate);
			LOGGER.info("Query for fetching payments: " + qry );
			paymentInfoList = new ArrayList<>();
			oltObjList = (List<Object[]>) query.getResultList();
			LOGGER.info("CommonDAO::setInvoicePaymentInfo::Fetching::END");
			LOGGER.info("CommonDAO::setInvoicePaymentInfo::Converting to Object::START");
			for (Object[] object : oltObjList) {
				PaymentDTO paymentDTO = new PaymentDTO();
				paymentDTO.setDate(object[0] == null ? "" : object[0].toString());
				paymentDTO.setAmount((object[1] == null ? new BigDecimal("0") : new BigDecimal(object[1].toString())));
				paymentDTO.setDescription(object[2] == null ? "" : object[2].toString());
				paymentInfoList.add(paymentDTO);
				paymentDTO = null;
			}
			dto.setPayments(paymentInfoList);
			LOGGER.info("CommonDAO::setInvoicePaymentInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setInvoicePaymentInfo" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
			paymentInfoList = null;
		}
		return dto;
	}
	
	public void updateInvPaymentPdfDisFlag(String custid, String invFDate, String invTDate) {

		try {
			LOGGER.info("Updating PDF Display flag in Payments table for custid:"+custid);
			LOGGER.info("CommonDAO::updateInvPaymentPdfDisFlag::Updating::START");
			String queryString = "UPDATE payments SET PDFDisFlag ='Y' WHERE status=1 AND pmntcustid="+Long.parseLong(custid)+" AND pmntdate BETWEEN '"+invFDate+"' AND '"+invTDate+"'";
			LOGGER.info("query for update:"+queryString);
			int count=getEntityManager().createNativeQuery(queryString).executeUpdate();
			LOGGER.info("CommonDAO::updateInvPaymentPdfDisFlag::Updating::END");
			if(count>0)
				LOGGER.info("Successfully Updated PDF Display flag in Payments table for custid:"+custid);
		} catch (Exception ex) {
			LOGGER.error("Exception occurred during updatePdfPathInCustInv(): " + ex);
			throw ex;
		}
	}
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BillInfoDTO setInvoicePackageInfo(BillInfoDTO dto) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		List<PackageDTO> packageInfoList = null;
		try {
			LOGGER.info("START::setInvoicePackageInfo");
			qry = "SELECT cp.prodcode p1, pd.prodname p2, DATE_FORMAT(cs.actdate,'%d-%m-%Y') "
					+ " FROM cafprods cp, products pd, cafsrvcs cs " + " WHERE cp.prodcode = pd.prodcode "
					+ " and cs.prodcode = cp.prodcode  " + " and cs.parentcafno = cp.parentcafno "
					+ " and cp.parentcafno=? " + " group by cp.prodcode, pd.prodname, cs.actdate, pd.prodtype "
					+ " order by cs.actdate, (case when pd.prodtype='B' THEN 1 when pd.prodtype='A' THEN 2 ELSE 3 END) ";
			LOGGER.info("setInvoicePackageInfo Query " + qry + " cafno " + dto.getAccountNumber());
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getAccountNumber());

			packageInfoList = new ArrayList<>();
			oltObjList = (List<Object[]>) query.getResultList();
			for (Object[] object : oltObjList) {
				PackageDTO packageDTO = new PackageDTO();
				packageDTO.setProdcode(object[0] == null ? "" : (object[0].toString()));
				packageDTO.setDescription(object[1] == null ? "" : object[1].toString());
				packageDTO.setDate(object[2] == null ? "" : object[2].toString());

				packageInfoList.add(packageDTO);
				packageDTO = null;
			}
			dto.setPackages(packageInfoList);
		} catch (Exception ex) {
			LOGGER.error("ERROR::setInvoicePackageInfo" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
			packageInfoList = null;
		}
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BillInfoDTO setAdditionalChargesInfo(BillInfoDTO dto) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		SummaryDTO summaryDTO = null;
		String chargetypeflag = "";
		String chargecodes = "";
		BigDecimal onetimeCharge = BigDecimal.ZERO;
		BigDecimal valueaddedCharge = BigDecimal.ZERO;
		try {
			LOGGER.info("START::setAdditionalChargesInfo");
			LOGGER.info("CommonDAO::setAdditionalChargesInfo::Fetching::START");
			qry = "SELECT chargecodes.chargetypeflag, sum(chargeamt),chargecodes.chargecode FROM cafinvdtls cafinvdtls, chargecodes chargecodes "
					+ " WHERE chargecodes.chargecode=cafinvdtls.chargecode  AND chargecodes.chargetypeflag in ('1','2','3','4','5','6','7','8','9') "
					+ " AND chargecodes.chargecode NOT IN ('LOCALUSAGE', 'STDUSAGE', 'ISDUSAGE') "
					+ " AND cafinvdtls.acctcafno=? AND custinvno=? GROUP BY chargecodes.chargetypeflag,chargecodes.chargecode";
			LOGGER.info("setAdditionalChargesInfo Query " + qry + " invno " + dto.getBillNumber());
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getAccountNumber());
			query.setParameter(2, dto.getBillNumber());
			oltObjList = (List<Object[]>) query.getResultList();
			LOGGER.info("CommonDAO::setAdditionalChargesInfo::Fetching::END");
			LOGGER.info("CommonDAO::setAdditionalChargesInfo::Converting to Object::START");
			summaryDTO = new SummaryDTO();
			for (Object[] object : oltObjList) {
				chargetypeflag = object[0] == null ? "" : object[0].toString();
				chargecodes = object[2] == null ? "" : object[2].toString();
				// if(chargetypeflag.equals("8"))
				// summaryDTO.setDiscountsOrAdjustments((object[1] == null ? new
				// BigDecimal("0") : new BigDecimal(object[1].toString())));
				summaryDTO.setChargeTypeFlag(chargetypeflag);
				if (chargecodes.equalsIgnoreCase("VOD")) {
					valueaddedCharge = valueaddedCharge
							.add((object[1] == null ? new BigDecimal("0") : new BigDecimal(object[1].toString())));
					summaryDTO.setValueAddedCharges(valueaddedCharge);
				}

				else if (chargetypeflag.equals("2") || chargetypeflag.equals("3") || chargetypeflag.equals("4")
						|| chargetypeflag.equals("5") || chargetypeflag.equals("6") || chargetypeflag.equals("7")
						|| chargetypeflag.equals("9")) {
					onetimeCharge = onetimeCharge
							.add((object[1] == null ? new BigDecimal("0") : new BigDecimal(object[1].toString())));
					summaryDTO.setOnetimeCharges(onetimeCharge);
				}
				// else if(chargetypeflag.equals("10"))
				// summaryDTO.setLateFee((object[1] == null ? new
				// BigDecimal("0") : new BigDecimal(object[1].toString())));
				else if (chargetypeflag.equals("1") || chargetypeflag.equals("8"))
					summaryDTO.setRecurringCharges(
							object[1] == null ? new BigDecimal("0") : new BigDecimal(object[1].toString()));
			}
			dto.setSummary(summaryDTO);
			LOGGER.info("CommonDAO::setAdditionalChargesInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setAdditionalChargesInfo" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	public BillInfoDTO setTaxInfo(BillInfoDTO dto) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		TaxDTO taxDTO = null;
		try {
			LOGGER.info("START::setTaxInfo");
			LOGGER.info("CommonDAO::setTaxInfo::Fetching::START");
			qry = " select srvctax, swatchtax, kisantax, enttax " + " from cafinv where custinvno = ? and acctcafno='"
					+ dto.getAccountNumber() + "'";

			/*
			 * if("INDIVIDUAL".equalsIgnoreCase(dto.getCustomerType())) { qry =
			 * " select srvctax, swatchtax, kisantax, enttax " +
			 * " from cafinv where custinvno = ? and acctcafno='"
			 * +dto.getAccountNumber()+"'"; } else{ qry =
			 * " select srvctax, swatchtax, kisantax, enttax " +
			 * " from custinv where custinvno = ?"; }
			 */

			LOGGER.info("setTaxInfo Query " + qry + " invno " + dto.getBillNumber());
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getBillNumber());
			oltObjList = (List<Object[]>) query.getResultList();
			LOGGER.info("CommonDAO::setTaxInfo::Fetching::END");
			LOGGER.info("CommonDAO::setTaxInfo::Converting to Object::START");
			taxDTO = new TaxDTO();
			for (Object[] object : oltObjList) {
				taxDTO.setServiceTax((object[0] == null ? new BigDecimal("0") : new BigDecimal(object[0].toString())));
				taxDTO.setSwatchTax((object[1] == null ? new BigDecimal("0") : new BigDecimal(object[1].toString())));
				taxDTO.setKisantax((object[2] == null ? new BigDecimal("0") : new BigDecimal(object[2].toString())));
				taxDTO.setEnttax((object[3] == null ? new BigDecimal("0") : new BigDecimal(object[3].toString())));
			}
			taxDTO.setAllTax(taxDTO.getSwatchTax().add(taxDTO.getKisantax()));
			dto.setTaxDTO(taxDTO);
			dto.getSummary().setTotalTax(taxDTO.getServiceTax()
					.add(taxDTO.getSwatchTax().add(taxDTO.getKisantax()).add(taxDTO.getEnttax())));
			LOGGER.info("CommonDAO::setTaxInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setTaxInfo" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
		}
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @param month
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BillInfoDTO setUsageChargesInfo(BillInfoDTO dto) {
		String qry = null;
		Query query = null;
		List<Object> oltObjList = new ArrayList<>();
		Object objects = null;
		try {
			LOGGER.info("START::setUsageChargesInfo");
			LOGGER.info("CommonDAO::setUsageChargesInfo::Fetching::START");
			qry = "SELECT sum(chargeamt) " + " FROM cafinvdtls " + " WHERE acctcafno = ? " + " AND custinvno = ? "
					+ " AND chargecode in ('LOCALUSAGE', 'STDUSAGE', 'ISDUSAGE')";
			LOGGER.info("setUsageChargesInfo Query " + qry + " cafno " + dto.getAccountNumber());
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getAccountNumber());
			query.setParameter(2, dto.getBillNumber());

			oltObjList = query.getResultList();
			LOGGER.info("CommonDAO::setUsageChargesInfo::Fetching::END");
			LOGGER.info("CommonDAO::setUsageChargesInfo::Converting to Object::START");
			if (oltObjList != null && !oltObjList.isEmpty()) {
				objects = oltObjList.get(0);
				dto.getSummary()
						.setUsageCharges(objects == null ? new BigDecimal("0") : new BigDecimal(objects.toString()));
			}
			LOGGER.info("CommonDAO::setUsageChargesInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setUsageChargesInfo" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
			objects = null;
		}
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @param month
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BillInfoDTO setRecurrChargeDtlsInfo(BillInfoDTO dto, int month, int year) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		List<RecurringDTO> recurringCharges = new ArrayList<RecurringDTO>();
		RecurringDTO recurringDTO = null;
		try {
			LOGGER.info("CommonDAO::setRecurrChargeDtlsInfo::Fetching::START");
			qry = "SELECT cafinvdtls.chargefdate, cafinvdtls.chargetdate, SUM(chargeamt), products.prodname, chargecodes.chargename, chargecodes.chargetypeflag "
					+ " FROM cafinvdtls cafinvdtls, chargecodes chargecodes, products products "
					+ " WHERE chargecodes.chargecode=cafinvdtls.chargecode "
					+ " AND chargecodes.chargetypeflag in ('1','8') "
					+ " AND chargecodes.chargecode NOT IN ('LOCALUSAGE', 'STDUSAGE', 'ISDUSAGE') "
					+ " AND products.prodcode = cafinvdtls.prodcode " + " AND cafinvdtls.acctcafno = ? "
					+ " AND cafinvdtls.custinvno = ? "
					+ " GROUP BY cafinvdtls.chargefdate,cafinvdtls.chargetdate, chargecodes.chargetypeflag, chargecodes.chargename, products.prodname ";
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getAccountNumber());
			query.setParameter(2, dto.getBillNumber());
			LOGGER.info("queryyyyyyyyyyyy...." + qry);
			oltObjList = query.getResultList();
			LOGGER.info("CommonDAO::setRecurrChargeDtlsInfo::Fetching::END");
			LOGGER.info("CommonDAO::setRecurrChargeDtlsInfo::Converting to Object::START");
			for (Object[] objects : oltObjList) {
				recurringDTO = new RecurringDTO();
				recurringDTO.setStartDate((objects[0] == null ? "" : objects[0].toString()));
				recurringDTO.setEndDate(objects[1] == null ? "" : objects[1].toString());
				recurringDTO
						.setCharges(objects[2] == null ? new BigDecimal("0") : new BigDecimal(objects[2].toString()));
				recurringDTO.setDescription((objects[3] == null ? "" : objects[3].toString()));
				recurringDTO.setChargeType((objects[4] == null ? "" : objects[4].toString()));
				recurringDTO.setChargeTypeFlag((objects[5] == null ? "" : objects[5].toString()));
				recurringCharges.add(recurringDTO);
				recurringDTO = null;
			}
			oltObjList = null;
			query = null;
			qry = null;
			dto.setRecurringCharges(recurringCharges);
			LOGGER.info("CommonDAO::setRecurrChargeDtlsInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setRecurrChargeDtlsAndInternetUsageInfo" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
		}
		return dto;
	}

	/*
	 * @SuppressWarnings("unchecked") public BillInfoDTO
	 * setInternetSummaryInfo(BillInfoDTO dto, int month, int year) { String qry
	 * = null; Query query = null; SummaryDTO summaryDTO = null; List<Object[]>
	 * oltObjList = null;
	 * 
	 * 
	 * BigDecimal internetCharge = BigDecimal.ZERO; try{ qry =
	 * "SELECT cafinvdtls.chargefdate, cafinvdtls.chargetdate, SUM(chargeamt), products.prodname, chargecodes.chargename "
	 * +
	 * " FROM cafinvdtls cafinvdtls, chargecodes chargecodes, products products "
	 * +" WHERE chargecodes.chargecode=cafinvdtls.chargecode " +
	 * " AND chargecodes.chargetypeflag in ('1','8') " +
	 * " AND chargecodes.chargecode NOT IN ('LOCALUSAGE', 'STDUSAGE', 'ISDUSAGE') "
	 * +" AND products.prodcode = cafinvdtls.prodcode " +
	 * " AND cafinvdtls.acctcafno = ? " +" AND cafinvdtls.custinvno = ? " +
	 * " AND products.prodtype = 'o' " +
	 * " GROUP BY cafinvdtls.chargefdate,cafinvdtls.chargetdate, chargecodes.chargetypeflag, chargecodes.chargename, products.prodname "
	 * ; query = getEntityManager().createNativeQuery(qry);
	 * query.setParameter(1, dto.getAccountNumber()); query.setParameter(2,
	 * dto.getBillNumber()); oltObjList = query.getResultList(); summaryDTO =
	 * new SummaryDTO(); LOGGER.info("In setInternetSummaryInfo query"+query);
	 * LOGGER.info(dto.getAccountNumber()+dto.getBillNumber()); for(Object[]
	 * objects : oltObjList) { internetCharge = internetCharge.add((objects[2]
	 * == null ? new BigDecimal("0") : new BigDecimal(objects[2].toString())));
	 * summaryDTO.setInternetusageCharges(internetCharge); } oltObjList = null;
	 * query = null; qry = null;
	 * LOGGER.info(dto.getAccountNumber()+"::::"+summaryDTO.
	 * getInternetusageCharges()+"Internet Usage Charges");
	 * dto.setSummary(summaryDTO); } catch(Exception ex) {
	 * LOGGER.error("ERROR::setRecurrChargeDtlsAndInternetUsageInfo" + ex); }
	 * finally { qry = null; query = null; oltObjList = null; } return dto; }
	 */

	@SuppressWarnings("unchecked")
	public BillInfoDTO setInternetSummaryInfo(BillInfoDTO dto, int month, int year) {
		String qry = null;
		Query query = null;
		SummaryDTO summaryDTO = null;
		List<Object[]> oltObjList = null;

		BigDecimal internetCharge = BigDecimal.ZERO;
		try {
			LOGGER.info("CommonDAO::setInternetSummaryInfo::Fetching::START");
			qry = "SELECT cafinvdtls.chargefdate, cafinvdtls.chargetdate, SUM(chargeamt), products.prodname, chargecodes.chargename "
					+ " FROM cafinvdtls cafinvdtls, chargecodes chargecodes, products products "
					+ " WHERE chargecodes.chargecode=cafinvdtls.chargecode "
					+ " AND chargecodes.chargetypeflag in ('1','8') "
					+ " AND chargecodes.chargecode NOT IN ('LOCALUSAGE', 'STDUSAGE', 'ISDUSAGE') "
					+ " AND products.prodcode = cafinvdtls.prodcode " + " AND cafinvdtls.acctcafno = ? "
					+ " AND cafinvdtls.custinvno = ? " + " AND products.prodtype = 'o' "
					+ " GROUP BY cafinvdtls.chargefdate,cafinvdtls.chargetdate, chargecodes.chargetypeflag, chargecodes.chargename, products.prodname ";
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getAccountNumber());
			query.setParameter(2, dto.getBillNumber());
			oltObjList = query.getResultList();
			summaryDTO = new SummaryDTO();
			LOGGER.info("CommonDAO::setInternetSummaryInfo::Fetching::END");
			LOGGER.info("In setInternetSummaryInfo query" + query);
			LOGGER.info(dto.getAccountNumber() + dto.getBillNumber());
			LOGGER.info("CommonDAO::setInternetSummaryInfo::Converting to Object::START");
			for (Object[] objects : oltObjList) {
				internetCharge = internetCharge
						.add((objects[2] == null ? new BigDecimal("0") : new BigDecimal(objects[2].toString())));
				// summaryDTO.setInternetusageCharges(new BigDecimal(0.00));
			}
			oltObjList = null;
			query = null;
			qry = null;
			LOGGER.info(
					dto.getAccountNumber() + "::::" + summaryDTO.getInternetusageCharges() + "Internet Usage Charges");
			summaryDTO.setInternetusageCharges(new BigDecimal(0.00));
			dto.setSummary(summaryDTO);
			LOGGER.info("CommonDAO::setInternetSummaryInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setRecurrChargeDtlsAndInternetUsageInfo" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	public BillInfoDTO setInternetUsageInfo(BillInfoDTO dto, int month, int year) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		List<DataUsageDTO> dataUsages = new ArrayList<DataUsageDTO>();
		DataUsageDTO dataUsageDTO = null;
		try {
			LOGGER.info("CommonDAO::setInternetUsageInfo::Fetching::START");
			qry = " SELECT srvccode, SUM(upldsize+dnldsize), SUM(sessiondur) FROM hsicumusage "
					+ " WHERE acctcafno = ? and usageyyyy = ? AND usagemm = ? " + " GROUP BY srvccode";
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getAccountNumber());
			query.setParameter(2, year);
			query.setParameter(3, month);

			oltObjList = query.getResultList();
			LOGGER.info("CommonDAO::setInternetUsageInfo::Fetching::END");
			LOGGER.info("CommonDAO::setInternetUsageInfo::Converting to Object::START");
			for (Object[] objects : oltObjList) {
				dataUsageDTO = new DataUsageDTO();
				dataUsageDTO.setDescr((objects[0] == null ? "" : objects[0].toString()));
				dataUsageDTO
						.setUnits(ConvertingUtil.formateBytesToGb(objects[1] == null ? "0" : objects[1].toString()));
				dataUsageDTO
						.setDuration(ConvertingUtil.formateSecToTime(objects[2] == null ? "0" : objects[2].toString()));
				dataUsages.add(dataUsageDTO);
				dataUsageDTO = null;
			}
			dto.setDataUsages(dataUsages);
			LOGGER.info("CommonDAO::setInternetUsageInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setRecurrChargeDtlsAndInternetUsageInfo" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
		}
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @param month
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BillInfoDTO setUsageChargesSummaryInfo(BillInfoDTO dto, int month, int year) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		List<TelephoneUsageDTO> telephoneUsages = new ArrayList<TelephoneUsageDTO>();
		TelephoneUsageDTO telephoneUsageDTO = null;
		try {
			LOGGER.info("START::setUsageChargesSummaryInfo");
			LOGGER.info("CommonDAO::setUsageChargesSummaryInfo::Fetching::START");
			qry = "SELECT CASE WHEN srvccategory = 2 THEN 'STD' WHEN srvccategory = 8 THEN 'LOCAL' WHEN srvccategory = 3 THEN 'ISD' END srvccategory, SUM(units), SUM(calldurn) ,SUM(charge) FROM phonecumusage WHERE acctcafno=? and usageyyyy=? AND usagemm=? GROUP BY srvccategory";
			LOGGER.info("setUsageChargesSummaryInfo Query " + qry + " BillInfoDto " + dto + " month " + month + " year "
					+ year);
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getAccountNumber());
			query.setParameter(2, year);
			query.setParameter(3, month);

			oltObjList = query.getResultList();
			LOGGER.info("CommonDAO::setUsageChargesSummaryInfo::Fetching::END");
			LOGGER.info("CommonDAO::setUsageChargesSummaryInfo::Converting to Object::START");
			for (Object[] object : oltObjList) {
				telephoneUsageDTO = new TelephoneUsageDTO();
				telephoneUsageDTO.setTypeOfUsage((object[0] == null ? "" : object[0].toString()));
				telephoneUsageDTO.setUnits((object[1] == null ? "0" : object[1].toString()));
				telephoneUsageDTO.setDuration((object[2] == null ? "0" : object[2].toString()));
				telephoneUsageDTO
						.setCharges((object[3] == null ? new BigDecimal("0") : new BigDecimal(object[3].toString())));

				telephoneUsages.add(telephoneUsageDTO);
				telephoneUsageDTO = null;
			}
			dto.setTelephoneUsages(telephoneUsages);
			LOGGER.info("CommonDAO::setUsageChargesSummaryInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setUsageChargesSummaryInfo:" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
			telephoneUsages = null;
		}
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @param month
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BillInfoDTO setItemizedCallInfo(BillInfoDTO dto, int month, int year) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		List<UsageDTO> offnetLocalSummary = new ArrayList<UsageDTO>();
		List<UsageDTO> offnetSTDLandlineSummary = new ArrayList<UsageDTO>();
		List<UsageDTO> offnetISDSummary = new ArrayList<UsageDTO>();
		List<UsageDTO> offnetSTDMobileSummary = new ArrayList<UsageDTO>();
		UsageDTO usageDTO = null;
		try {
			LOGGER.info("START::setItemizedCallInfo");
			qry = "SELECT date(starttime), starttime, cldpartyaddr, calldurn, billunits, callcharge, srvccategory,phoneType FROM phoneusage WHERE acctcafno=? and usageyyyy=? AND usagemm=? ORDER BY starttime";
			LOGGER.info(
					"setItemizedCallInfo Query " + qry + " BillInfoDto " + dto + " month " + month + " year " + year);
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getAccountNumber());
			query.setParameter(2, year);
			query.setParameter(3, month);

			oltObjList = query.getResultList();
			for (Object[] object : oltObjList) {
				usageDTO = new UsageDTO();
				usageDTO.setDate(object[0] == null ? "" : object[0].toString());
				usageDTO.setTime(object[1] == null ? "" : object[1].toString());
				usageDTO.setCalledNumber(object[2] == null ? "" : object[2].toString());
				usageDTO.setDuration(object[3] == null ? "" : object[3].toString());
				usageDTO.setUnits(object[4] == null ? "0" : object[4].toString());
				usageDTO.setCharges((object[5] == null ? new BigDecimal("0") : new BigDecimal(object[5].toString())));

				if ((object[6] == null ? "" : object[6].toString()).equalsIgnoreCase("8")) {
					offnetLocalSummary.add(usageDTO);
				} else if ((object[7] == null ? "" : object[7].toString()).equalsIgnoreCase("STDMOBILE")) {
					usageDTO.setCalledNumber(usageDTO.getCalledNumber().replace("+91", ""));
					offnetSTDMobileSummary.add(usageDTO);
				} else if ((object[6] == null ? "" : object[6].toString()).equalsIgnoreCase("3")) {
					offnetISDSummary.add(usageDTO);
				} else if ((object[7] == null ? "" : object[7].toString()).equalsIgnoreCase("STDLANDLINE")) {
					usageDTO.setCalledNumber(usageDTO.getCalledNumber().replace("+91", "0"));
					offnetSTDLandlineSummary.add(usageDTO);
				}

				usageDTO = null;
			}
			dto.setOffnetLocalSummary(offnetLocalSummary);
			dto.setOffnetSTDSummary(offnetSTDMobileSummary);
			dto.setOffnetISDSummary(offnetISDSummary);
			dto.setOffnetMobileSummary(offnetSTDLandlineSummary);
		} catch (Exception ex) {
			LOGGER.error("ERROR::setItemizedCallInfo:" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
			offnetLocalSummary = null;
			offnetSTDMobileSummary = null;
			offnetISDSummary = null;
			offnetSTDLandlineSummary = null;
		}
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BillInfoDTO setOtherChargesInfo(BillInfoDTO dto) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		List<OtherChargesDTO> vodOrMovieCharges = new ArrayList<OtherChargesDTO>();
		List<OnetimeChargesDTO> onetimeCharges = new ArrayList<OnetimeChargesDTO>();
		List<AdjustmentDTO> discOrAdjustemnts = new ArrayList<AdjustmentDTO>();
		OtherChargesDTO otherChargesDTO = null;
		OnetimeChargesDTO onetimeChargesDTO = null;
		ValueAddedServiceDTO valueAddedServiceDTO = null;
		AdjustmentDTO adjustmentDTO = null;
		String chargetypeflag = "";
		String chargeCode = "";
		List<ValueAddedServiceDTO> valueAddedServiceList = new ArrayList<ValueAddedServiceDTO>();
		try {
			LOGGER.info("START::setOtherChargesInfo");
			LOGGER.info("CommonDAO::setOtherChargesInfo::Fetching::START");
			/**Added 8 IN parameter for chargecodes.chargetypeflag for showing CPEEMI in Recurring Charges**/
			qry = "SELECT CASE WHEN ifnull(cafinvdtls.chargedesc,'') = '' THEN chargecodes.chargename ELSE cafinvdtls.chargedesc END chargename, cafinvdtls.chargeddate, chargecodes.chargetypeflag, sum(chargeamt) chargeamt, chargecodes.chargecode FROM cafinvdtls cafinvdtls, chargecodes chargecodes "
					+ " WHERE chargecodes.chargecode=cafinvdtls.chargecode AND cafinvdtls.acctcafno=? AND chargecodes.chargetypeflag in ('2','3','4','5','6','7','9','8') AND custinvno=? "
					+ " GROUP BY chargedesc, chargename, chargeddate, chargetypeflag, chargecode";
			/****/
			LOGGER.info("setOtherChargesInfo Query " + qry + " BillInfoDto " + dto);
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, dto.getAccountNumber());
			query.setParameter(2, dto.getBillNumber());

			oltObjList = query.getResultList();
			LOGGER.info("CommonDAO::setOtherChargesInfo::Fetching::END");
			LOGGER.info("CommonDAO::setOtherChargesInfo::Converting to Object::START");
			for (Object[] object : oltObjList) {
				chargetypeflag = object[2] == null ? "" : object[2].toString();
				chargeCode = object[4] == null ? "" : object[4].toString();
				if (chargeCode.equalsIgnoreCase("VOD")) {
					otherChargesDTO = new OtherChargesDTO();
					otherChargesDTO.setVodName(object[0] == null ? "" : object[0].toString());
					otherChargesDTO.setDate(object[1] == null ? "" : object[1].toString());
					otherChargesDTO.setCharges(
							(object[3] == null ? new BigDecimal("0") : new BigDecimal(object[3].toString())));
					vodOrMovieCharges.add(otherChargesDTO);
					otherChargesDTO = null;
				} else if (chargetypeflag.equals("2") || chargetypeflag.equals("3") || chargetypeflag.equals("4")
						|| chargetypeflag.equals("5") || chargetypeflag.equals("6") || chargetypeflag.equals("7")
						|| chargetypeflag.equals("9") || chargetypeflag.equals("8")) {/**Added 8 IN condition for showing CPEEMI in Recurring Charges**/
					onetimeChargesDTO = new OnetimeChargesDTO();
					onetimeChargesDTO.setDescription(object[0] == null ? "" : object[0].toString());
					onetimeChargesDTO.setDate(object[1] == null ? "" : object[1].toString());
					onetimeChargesDTO.setCharges(
							(object[3] == null ? new BigDecimal("0") : new BigDecimal(object[3].toString())));
					onetimeCharges.add(onetimeChargesDTO);
					otherChargesDTO = null;
				}

				/*
				 * else if(chargetypeflag.equals("2")) { valueAddedServiceDTO =
				 * new ValueAddedServiceDTO();
				 * valueAddedServiceDTO.setDescription(object[0] == null ? "" :
				 * object[0].toString()); valueAddedServiceDTO.setDate(object[1]
				 * == null ? "" : object[1].toString());
				 * valueAddedServiceDTO.setCharges((object[3] == null ? new
				 * BigDecimal("0") : new BigDecimal(object[3].toString())));
				 * valueAddedServiceList.add(valueAddedServiceDTO);
				 * valueAddedServiceDTO = null; }
				 */

				/*
				 * else if(chargetypeflag.equalsIgnoreCase("9")) { adjustmentDTO
				 * = new AdjustmentDTO(); adjustmentDTO.setDescription(object[0]
				 * == null ? "" : object[0].toString());
				 * adjustmentDTO.setDate(object[1] == null ? "" :
				 * object[1].toString()); adjustmentDTO.setCharges((object[3] ==
				 * null ? new BigDecimal("0") : new
				 * BigDecimal(object[3].toString())));
				 * discOrAdjustemnts.add(adjustmentDTO); adjustmentDTO = null; }
				 */
				dto.setVodOrMovieCharges(vodOrMovieCharges);
				dto.setOnetimeCharges(onetimeCharges);
				// dto.setValueaddedCharges(valueAddedServiceList);
				dto.setDiscOrAdjustemnts(discOrAdjustemnts);
			}
			if (oltObjList.isEmpty()) {
				dto.setVodOrMovieCharges(vodOrMovieCharges);
				dto.setOnetimeCharges(onetimeCharges);
				dto.setDiscOrAdjustemnts(discOrAdjustemnts);
			}
			LOGGER.info("CommonDAO::setOtherChargesInfo::Converting to Object::END");
		} catch (Exception ex) {
			LOGGER.error("ERROR::setOtherChargesInfo:" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
			vodOrMovieCharges = null;
			onetimeCharges = null;
			discOrAdjustemnts = null;
		}
		return dto;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public EnterpriseBillInfoDTO setEntInvoiceCustomerInfo(EnterpriseBillInfoDTO entDto) {
		//StringBuilder qry = new StringBuilder();
		String qry = null;
		Query query = null;
		Object[] objects = null;
		List<Object[]> oltObjList = null;
		try {
			LOGGER.info("START::setEntInvoiceCustomerInfo");
			if (entDto.getCustomerType().equalsIgnoreCase("INDIVIDUAL")) {
				qry = "SELECT CONCAT_WS(' ', CASE WHEN ifnull(customers.fname,'') = '' THEN null ELSE upper(customers.fname) END "
						+ " 			    , CASE WHEN ifnull(customers.mname,'') = '' THEN null ELSE upper(customers.mname) END  "
						+ "              , CASE WHEN ifnull(customers.lname,'') = '' THEN null ELSE upper(customers.lname) END) fullname "
						+ " ,CONCAT_WS(', ', CASE WHEN ifnull(customers.addr1,'') = '' THEN null ELSE upper(customers.addr1) END "
						+ " 			  , CASE WHEN ifnull(customers.addr2,'') = '' THEN null ELSE upper(customers.addr2) END  "
						+ "            , CASE WHEN ifnull(customers.locality,'') = '' THEN null ELSE upper(customers.locality) END "
						+ "            , CASE WHEN ifnull(customers.area,'') = '' THEN null ELSE upper(customers.area) END "
						+ "            , CASE WHEN ifnull(v.villagename,'') = '' THEN null ELSE upper(v.villagename) END "
						+ " 			  , CASE WHEN ifnull(m.mandalname,'') = '' THEN null ELSE upper(m.mandalname) END "
						+ " 			  , CASE WHEN ifnull(d.districtname,'') = '' THEN null ELSE upper(d.districtname) END "
						+ " 			  , CASE WHEN ifnull(customers.state,'') = '' THEN null ELSE upper(customers.state) END "
						+ "            , CASE WHEN ifnull(customers.pin,'') = '' THEN null ELSE upper(customers.pin) END) address, (select group_concat(concat(0,phoneno)) from cafsrvcphonenos where cafno = caf.cafno GROUP BY cafno) phoneno "
						+ " FROM cafs caf, customers customers, districts d, mandals m, villages v "
						+ " WHERE caf.custid=customers.custid  " + " AND customers.district = d.districtuid  "
						+ " AND customers.mandal = m.mandalslno  " + " AND customers.district = m.districtuid  "
						+ " AND customers.city_village = v.villageslno  " + " AND customers.mandal = v.mandalslno  "
						+ " AND customers.district = v.districtuid  " + " AND customers.custid=? ";
			} else if (entDto.getCustomerType().equalsIgnoreCase("ENTERPRISE")) {
				/*qry = "SELECT ifnull(UPPER(entcustomers.custname),'') fullname "
						+ " ,CONCAT_WS(', ', CASE WHEN ifnull(entcustomers.addr1,'') = '' THEN null ELSE upper(entcustomers.addr1) END "
						+ " , CASE WHEN ifnull(entcustomers.addr2,'') = '' THEN null ELSE upper(entcustomers.addr2) END "
						+ " , CASE WHEN ifnull(entcustomers.locality,'') = '' THEN null ELSE upper(entcustomers.locality) END "
						+ " , CASE WHEN ifnull(entcustomers.area,'') = '' THEN null ELSE upper(entcustomers.area) END "
						+ " , CASE WHEN ifnull(v.villagename,'') = '' THEN null ELSE upper(v.villagename) END "
						+ " , CASE WHEN ifnull(m.mandalname,'') = '' THEN null ELSE upper(m.mandalname) END "
						+ " , CASE WHEN ifnull(d.districtname,'') = '' THEN null ELSE upper(d.districtname) END "
						+ " , CASE WHEN ifnull(entcustomers.state,'') = '' THEN null ELSE upper(entcustomers.state) END "
						+ " , CASE WHEN ifnull(entcustomers.pin,'') = '' THEN null ELSE upper(entcustomers.pin) END) address, GROUP_CONCAT(concat(0,cafph.phoneno)) phonenos "
						+ " FROM cafs caf, customermst entcustomers, cafsrvcphonenos cafph, districts d, mandals m, villages v "
						+ " WHERE caf.pmntcustcode=entcustomers.custid " + " AND caf.cafno = cafph.cafno   "
						+ " AND entcustomers.district = d.districtuid " + " AND entcustomers.mandal = m.mandalslno "
						+ " AND entcustomers.district = m.districtuid " + " AND entcustomers.city_village = v.villageslno "
						+ " AND entcustomers.mandal = v.mandalslno " + " AND entcustomers.district = v.districtuid "
						+ " AND entcustomers.custid = ? " + " group by address, entcustomers.custid ";*/
				
				//24-08-2017 @PRIYA
				qry = "SELECT ifnull(UPPER(entcustomers.custname),'') fullname "
						+ " ,CONCAT_WS(', ', CASE WHEN ifnull(entcustomers.addr1,'') = '' THEN null ELSE upper(entcustomers.addr1) END "
						+ " , CASE WHEN ifnull(entcustomers.addr2,'') = '' THEN null ELSE upper(entcustomers.addr2) END "
						+ " , CASE WHEN ifnull(entcustomers.locality,'') = '' THEN null ELSE upper(entcustomers.locality) END "
						+ " , CASE WHEN ifnull(entcustomers.area,'') = '' THEN null ELSE upper(entcustomers.area) END "
						+ " , CASE WHEN ifnull(v.villagename,'') = '' THEN null ELSE upper(v.villagename) END "
						+ " , CASE WHEN ifnull(m.mandalname,'') = '' THEN null ELSE upper(m.mandalname) END "
						+ " , CASE WHEN ifnull(d.districtname,'') = '' THEN null ELSE upper(d.districtname) END "
						+ " , CASE WHEN ifnull(entcustomers.state,'') = '' THEN null ELSE upper(entcustomers.state) END "
						+ " , CASE WHEN ifnull(entcustomers.pin,'') = '' THEN null ELSE upper(entcustomers.pin) END) address, GROUP_CONCAT(concat(0,cafph.phoneno)) phonenos "
						+ " FROM customermst entcustomers, districts d, mandals m, villages v, cafs caf LEFT JOIN cafsrvcphonenos cafph ON cafph.cafno = caf.cafno "
						+ " WHERE caf.pmntcustcode=entcustomers.custid   "
						+ " AND entcustomers.district = d.districtuid AND entcustomers.mandal = m.mandalslno "
						+ " AND entcustomers.district = m.districtuid AND entcustomers.city_village = v.villageslno "
						+ " AND entcustomers.mandal = v.mandalslno AND entcustomers.district = v.districtuid "
						+ " AND entcustomers.custid = ? " + " group by address, entcustomers.custid ";
				
			}
			
			
			
			/*qry.append(" SELECT IFNULL(UPPER(entcustomers.custname),'') fullname ");
			qry.append(" ,CONCAT_WS(', ', CASE WHEN IFNULL(entcustomers.addr1,'') = '' THEN NULL ELSE UPPER(entcustomers.addr1) END ");
			qry.append(" , CASE WHEN IFNULL(entcustomers.addr2,'') = '' THEN NULL ELSE UPPER(entcustomers.addr2) END ");
			qry.append(" , CASE WHEN IFNULL(entcustomers.locality,'') = '' THEN NULL ELSE UPPER(entcustomers.locality) END ");
			qry.append(" , CASE WHEN IFNULL(entcustomers.area,'') = '' THEN NULL ELSE UPPER(entcustomers.area) END ");
			qry.append(" , CASE WHEN IFNULL(v.villagename,'') = '' THEN NULL ELSE UPPER(v.villagename) END ");
			qry.append(" , CASE WHEN IFNULL(m.mandalname,'') = '' THEN NULL ELSE UPPER(m.mandalname) END ");
			qry.append(" , CASE WHEN IFNULL(d.districtname,'') = '' THEN NULL ELSE UPPER(d.districtname) END ");
			qry.append(" , CASE WHEN IFNULL(entcustomers.state,'') = '' THEN NULL ELSE UPPER(entcustomers.state) END ");
			qry.append(" , CASE WHEN IFNULL(entcustomers.pin,'') = '' THEN NULL ELSE UPPER(entcustomers.pin) END) address");
			//qry.append(" ,IFNULL((SELECT GROUP_CONCAT(CONCAT(0,cafph.phoneno)) phonenos FROM cafs cf, cafsrvcphonenos cafph WHERE cafph.parentcafno=cf.cafno AND cf.pmntcustid=entcustomers.custid ),'') phonenos");
			qry.append(",IFNULL((SELECT GROUP_CONCAT(CONCAT(0,cafph.phoneno)) phonenos FROM cafs cf, cafsrvcphonenos cafph WHERE cafph.parentcafno=cf.cafno AND cf.pmntcustid=entcustomers.custid LIMIT 1),'NA') phonenos");
			qry.append(" FROM customermst entcustomers,  districts d, mandals m, villages v ");
			qry.append(" WHERE 1=1");
			qry.append(" AND entcustomers.district = d.districtuid   AND entcustomers.mandal = m.mandalslno ");
			qry.append(" AND entcustomers.district = m.districtuid   AND entcustomers.city_village = v.villageslno ");
			qry.append(" AND entcustomers.mandal = v.mandalslno   AND entcustomers.district = v.districtuid ");
			qry.append(" AND entcustomers.custid = ?");*/
			LOGGER.info("QUERY FOR : " + qry + " OBJE:" + entDto.getEnterpriseCustomerId());
			query = getEntityManager().createNativeQuery(qry.toString());
			query.setParameter(1, entDto.getEnterpriseCustomerId());
			oltObjList = (List<Object[]>) query.getResultList();
			if (!oltObjList.isEmpty()) {
				objects = oltObjList.get(0);
				// dto.setAccountNumber(objects[0] == null ? "" :
				// objects[0].toString());
				entDto.setFullName(objects[0] == null ? "" : objects[0].toString());
				entDto.setAddress(objects[1] == null ? "" : objects[1].toString());
				entDto.setAddress(entDto.getAddress().replaceAll(",,", ","));
				entDto.setPhoneNumber(objects[2] == null ? "" : objects[2].toString());
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR::setEntInvoiceCustomerInfo" + ex);
		} finally {
			qry = null;
			query = null;
			objects = null;
			oltObjList = null;
		}
		return entDto;
	}

	/**
	 * 
	 * @param dto
	 * @param invFDate
	 * @param invTDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public EnterpriseBillInfoDTO setEntInvoicePaymentInfo(EnterpriseBillInfoDTO entDto, String invFDate,
			String invTDate) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		List<PaymentDTO> paymentInfoList = null;
		try {
			LOGGER.info("START::setEntInvoicePaymentInfo");

			//qry = "SELECT DATE_FORMAT(pmntdate, '%Y-%m-%d'), pmntamt, pmntmodelov, acctcafno FROM payments WHERE status=1 and pmntcustid=? AND pmntdate BETWEEN ? AND ?";
			qry="SELECT DATE_FORMAT(pmntdate, '%Y-%m-%d'), pmntamt, pmntmodelov,acctcafno FROM payments WHERE status=1 and PDFDisFlag='N' and pmntcustid=? AND pmntdate BETWEEN ? AND ?";
			LOGGER.info("Payment Cust Id " + entDto.getPaymentCustomerId()
					+ " Invoice From Date " + invFDate + " Invoice To Date " + invTDate);
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, entDto.getPaymentCustomerId());
			query.setParameter(2, invFDate);
			query.setParameter(3, invTDate);
			LOGGER.info("Fetching payment data query:" + qry);
			paymentInfoList = new ArrayList<>();
			oltObjList = (List<Object[]>) query.getResultList();
			for (Object[] object : oltObjList) {
				PaymentDTO paymentDTO = new PaymentDTO();
				paymentDTO.setDate(object[0] == null ? "" : object[0].toString());
				paymentDTO.setAmount((object[1] == null ? new BigDecimal("0") : new BigDecimal(object[1].toString())));
				paymentDTO.setDescription(object[2] == null ? "" : object[2].toString());
				paymentDTO.setCafno(object[3] == null ? "" : object[3].toString());
				paymentInfoList.add(paymentDTO);
				paymentDTO = null;
			}
			entDto.setPayments(paymentInfoList);
		} catch (Exception ex) {
			LOGGER.error("ERROR::setEntInvoicePaymentInfo" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
			paymentInfoList = null;
		}
		return entDto;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public EnterpriseBillInfoDTO setEntAllAccountsCurrentChargesSummary(EnterpriseBillInfoDTO entDto, String month,
			String year) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		List<CurrentChargesSummaryDTO> allAccountCurrentCharges = new ArrayList<CurrentChargesSummaryDTO>();
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal totalTax = new BigDecimal(0);
		BigDecimal totalofTotal = new BigDecimal(0);
		BigDecimal serviceTax = new BigDecimal(0);
		BigDecimal otherTax = new BigDecimal(0);
		BigDecimal entTax = new BigDecimal(0);
		try {
			LOGGER.info("START::setEntAllAccountsCurrentChargesSummary"+entDto);
			if (entDto.getCustomerType().equalsIgnoreCase("INDIVIDUAL")) {
				qry = "SELECT cust.custid, cafs.cafno, cafs.cpeplace , cafinv.invamt, cafinv.srvctax+cafinv.swatchtax+cafinv.kisantax+cafinv.enttax as tax, cafinv.srvctax, cafinv.swatchtax+cafinv.kisantax, cafinv.enttax "
						+ " from customers cust, cafs cafs, cafinv cafinv " + " where cafs.pmntcustid=cust.custid  "
						+ " and cafs.cafno = cafinv.acctcafno " + " and cust.custid = ? "
						+ " and invmn = ? and invyr = ?";
			} else if (entDto.getCustomerType().equalsIgnoreCase("ENTERPRISE")) {
				qry = "SELECT entcust.custid, cafs.cafno, cafs.cpeplace , cafinv.invamt, cafinv.srvctax+cafinv.swatchtax+cafinv.kisantax+cafinv.enttax as tax, cafinv.srvctax, cafinv.swatchtax+cafinv.kisantax, cafinv.enttax "
						+ " from entcustomers entcust, cafs cafs, cafinv cafinv "
						+ " where cafs.pmntcustid=entcust.custid  " + " and cafs.cafno = cafinv.acctcafno "
						+ " and entcust.custid = ? " + " and invmn = ? and invyr = ?";
			}
			LOGGER.info("setEntAllAccountsCurrentChargesSummary Query " + qry + " parentcustcode "
					+ entDto.getPaymentCustomerId());
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, entDto.getPaymentCustomerId());
			query.setParameter(2, month);
			query.setParameter(3, year);
			oltObjList = (List<Object[]>) query.getResultList();
			for (Object[] object : oltObjList) {

				CurrentChargesSummaryDTO currentChargesSummaryDTO = new CurrentChargesSummaryDTO();
				currentChargesSummaryDTO.setCustomerId(object[0].toString());
				currentChargesSummaryDTO.setAccountNumber(object[1].toString());
				currentChargesSummaryDTO.setAccountName(object[2].toString());
				currentChargesSummaryDTO
						.setAmount(object[3] == null ? new BigDecimal("0") : new BigDecimal(object[3].toString()));
				currentChargesSummaryDTO
						.setTax(object[4] == null ? new BigDecimal("0") : new BigDecimal(object[4].toString()));
				allAccountCurrentCharges.add(currentChargesSummaryDTO);

				totalAmount = totalAmount.add(currentChargesSummaryDTO.getAmount());
				totalTax = totalTax.add(currentChargesSummaryDTO.getTax());
				totalofTotal = totalofTotal.add(currentChargesSummaryDTO.getTotalAmount());

				serviceTax = serviceTax
						.add(object[5] == null ? new BigDecimal("0") : new BigDecimal(object[5].toString()));
				otherTax = otherTax.add(object[6] == null ? new BigDecimal("0") : new BigDecimal(object[6].toString()));
				entTax = entTax.add(object[7] == null ? new BigDecimal("0") : new BigDecimal(object[7].toString()));

				currentChargesSummaryDTO = null;
			}
			entDto.setAllAccountCurrentCharges(allAccountCurrentCharges);
			entDto.setAllAccountCurrentChargesTotalAmount(totalAmount);
			entDto.setAllAccountCurrentChargesTotalTax(totalTax);
			entDto.setAllAccountCurrentChargesTotalofTotalAmount(totalofTotal);
			entDto.setServiceTax(serviceTax);
			entDto.setOtherTax(otherTax);
			entDto.setEntTax(entTax);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("ERROR::setEntAllAccountsCurrentChargesSummary" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
			allAccountCurrentCharges = null;
			totalAmount = null;
			totalTax = null;
			totalofTotal = null;
		}
		return entDto;
	}

	/**
	 * 
	 * @param districtId
	 * @return
	 */
	public int getInvoiceChargeCafsCount(String districtId, int yearMonth) {
		int count = 0;
		List<Object> obj = null;
		try {
			LOGGER.info("START::getInvoiceChargeCafsCount");

			String yearMon = String.valueOf(yearMonth);

			String year = yearMon.substring(0, 4);
			String month = yearMon.substring(4, 6);

			String queryString = "SELECT count(1)" + " FROM customermst mst, cafs cf "
					+ " WHERE mst.custid=cf.pmntcustid and "
					+ " cf.actdate is not null and mst.actdate is not null and mst.actdate <= (select min(cff.actdate) from cafs cff "
					+ " where cff.pmntcustid=mst.custid limit 1) AND IFNULL(mst.finalbilldtl,'') ='' AND IFNULL(cf.finalbilldtl,'')='' ANd "
					+ " ( (cf.status=6 and date(cf.actdate) <= date(last_day('" + year + "-" + month + "-01')))"
					+ " or (cf.status=8 and year(cf.actdate)='" + year + "'  and month(cf.actdate)='" + month
					+ "' ) ) and  inst_district=? " + " and cf.custtypelov='INDIVIDUAL'";

			// String queryString = "SELECT count(1) FROM customermst mst, cafs
			// cf WHERE mst.custid=cf.pmntcustid and cf.actdate is not null and
			// mst.actdate is not null and mst.actdate <= (select
			// min(cff.actdate) from cafs cff where cff.pmntcustid=mst.custid
			// limit 1) AND IFNULL(mst.finalbilldtl,'') ='' AND
			// IFNULL(cf.finalbilldtl,'')='' ANd cf.status IN (6,8) and
			// inst_district=? and date(cf.actdate) <=
			// date(last_day('"+year+"-"+month+"-01')) and
			// cf.custtypelov='INDIVIDUAL'";

			/*
			 * String queryString = "SELECT count(1)" +
			 * " FROM customermst mst, cafs cf " +
			 * " WHERE mst.custid=cf.pmntcustid and " +
			 * " cf.actdate is not null and mst.actdate is not null and mst.actdate <= (select min(cff.actdate) from cafs cff "
			 * +
			 * " where cff.pmntcustid=mst.custid limit 1) AND IFNULL(mst.finalbilldtl,'') ='' AND IFNULL(cf.finalbilldtl,'')='' ANd "
			 * + " ( (cf.status=6 and date(cf.actdate) <= date(last_day('"
			 * +year+"-"+month+"-01')))" +
			 * " or (cf.status=8 and year(cf.actdate)='"+year+
			 * "'  and month(cf.actdate)='"+month+"' ) ) and  inst_district=? "
			 * + " and cf.custtypelov='INDIVIDUAL'";
			 */

			// Query query = getEntityManager().createNativeQuery("select
			// count(1) FROM cafs WHERE status in(6,8) and inst_district=? and
			// year(actdate)=? and month(actdate)=? and
			// custtypelov='INDIVIDUAL'");
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter(1, districtId);
			// query.setParameter(2, year);
			// query.setParameter(3, month);

			obj = query.getResultList();
			if (!obj.isEmpty()) {
				count = obj.get(0) == null ? 0 : Integer.parseInt(obj.get(0).toString());
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR::getInvoiceChargeCafsCount" + ex);
			ex.printStackTrace();
		}
		return count;
	}

	public int getInvoiceEntCafsCount(String year, String month) {
		int count = 0;
		List<Object> obj = null;
		try {
			LOGGER.info("START::getInvoiceEntCafsCount");
			// Query query = getEntityManager().createNativeQuery("select
			// count(1) from cafs caf,entcustomers entcust where caf.custid =
			// entcust.custid and entcust.enttypelov='GOVT' and
			// caf.status='6'");

			String queryString = "SELECT count(1) FROM customermst mst, cafs cf,entcustomers entcust WHERE mst.custid=cf.pmntcustid "
					+ " and cf.pmntcustid = entcust.custid and cf.actdate is not null and mst.actdate is not null "
					+ " and mst.actdate <= (select min(cff.actdate) from cafs cff where cff.pmntcustid=mst.custid limit 1) "
					+ " AND IFNULL(mst.finalbilldtl,'') ='' AND IFNULL(cf.finalbilldtl,'')='' "
					+ " ANd cf.status IN (6,8) and " + " date(cf.actdate) <= date(last_day('" + year + "-" + month
					+ "-01'))" + " and entcust.enttypelov='GOVT'";
			// Query query = getEntityManager().createNativeQuery("select
			// count(1) from cafs caf,entcustomers entcust where
			// year(caf.actdate)='"+year+"' and month(caf.actdate)='"+month+"'
			// and caf.pmntcustid = entcust.custid and entcust.enttypelov='GOVT'
			// and caf.status in(6,8)");
			Query query = getEntityManager().createNativeQuery(queryString);
			obj = query.getResultList();
			if (!obj.isEmpty()) {
				count = obj.get(0) == null ? 0 : Integer.parseInt(obj.get(0).toString());
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR::getInvoiceEntCafsCount" + ex);
			ex.printStackTrace();
		}
		return count;
	}

	public int getInvoiceEntPrivateCafsCount(String year, String month) {
		int count = 0;
		List<Object> obj = null;
		try {
			LOGGER.info("START::getInvoiceEntPrivateCafsCount");

			String queryString = "SELECT count(1) FROM customermst mst, cafs cf,entcustomers entcust WHERE mst.custid=cf.pmntcustid "
					+ " and cf.pmntcustid = entcust.custid and cf.actdate is not null and mst.actdate is not null "
					+ " and mst.actdate <= (select min(cff.actdate) from cafs cff where cff.pmntcustid=mst.custid limit 1) "
					+ " AND IFNULL(mst.finalbilldtl,'') ='' AND IFNULL(cf.finalbilldtl,'')='' "
					+ " ANd cf.status IN (6,8) and " + " date(cf.actdate) <= date(last_day('" + year + "-" + month
					+ "-01'))" + " and entcust.enttypelov='PRIVATE'";

			Query query = getEntityManager().createNativeQuery(queryString);
			// Query query = getEntityManager().createNativeQuery("select
			// count(1) from cafs caf,entcustomers entcust where
			// year(caf.actdate)='"+year+"' and month(caf.actdate)='"+month+"'
			// and caf.pmntcustid = entcust.custid and
			// entcust.enttypelov='PRIVATE' and caf.status in(6,8)");

			obj = query.getResultList();
			if (!obj.isEmpty()) {
				count = obj.get(0) == null ? 0 : Integer.parseInt(obj.get(0).toString());
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR::getInvoiceEntPrivateCafsCount" + ex);
			ex.printStackTrace();
		}
		return count;
	}

	/**
	 * 
	 * @param districtId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getCafChargeDetails(String districtId, int yearMonth) {
		String qry = null;
		Query query = null;
		List<Object> oltObjList = new ArrayList<>();
		List<String> cafList = null;
		try {

			String yearMon = String.valueOf(yearMonth);
			String year = yearMon.substring(0, 4);
			String month = yearMon.substring(4, 6);

			LOGGER.info("START::getCafChargeDetails");
			LOGGER.info("getCafChargeDetails Query " + qry + " districtId " + districtId);
			// qry = "SELECT cafno FROM cafs WHERE status=6 and inst_district=?
			// and custtypelov='INDIVIDUAL' order by cafno";
			// qry = "SELECT pmntcustid FROM cafs WHERE status in(6,8) and
			// inst_district=? and year(actdate)=? and month(actdate)=? and
			// custtypelov='INDIVIDUAL' order by pmntcustid";
			qry = "SELECT distinct cf.pmntcustid" + " FROM customermst mst, cafs cf "
					+ " WHERE mst.custid=cf.custid and "
					+ " cf.actdate is not null and mst.actdate is not null and mst.actdate <= (select min(cff.actdate) from cafs cff "
					+ " where cff.custid=mst.custid limit 1) AND IFNULL(mst.finalbilldtl,'') ='' AND IFNULL(cf.finalbilldtl,'')='' ANd "
					+ " ( (cf.status=6 and date(cf.actdate) <= date(last_day('" + year + "-" + month + "-01')))"
					+ " or (cf.status=8 AND DATE_FORMAT(cf.actdate,'%Y%m') <= '" + year + "" + month
					+ "' ) ) and  cf.custdistuid=? " + " and cf.custtypelov='INDIVIDUAL'";
			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, districtId);
			// query.setParameter(2, year);
			// query.setParameter(3, month);

			// query.setFirstResult(start);
			// query.setMaxResults(count);

			oltObjList = query.getResultList();
			cafList = new ArrayList<String>();

			for (Object object : oltObjList) {
				cafList.add(object == null ? "" : object.toString());
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR::getCafChargeDetails" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
		}
		return cafList;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getNoBillCafChargeDetails() {
		String qry = null;
		Query query = null;
		List<Object> oltObjList = new ArrayList<>();
		List<String> noBillCustomers = null;
		try {
			LOGGER.info("START::getNoBillCafChargeDetails");
			qry = " SELECT customerId FROM nobillcustomers ";
			query = getEntityManager().createNativeQuery(qry);
			oltObjList = query.getResultList();
			noBillCustomers = new ArrayList<String>();

			for (Object object : oltObjList) {
				noBillCustomers.add(object == null ? "" : object.toString());
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR::getNoBillCafChargeDetails" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
		}
		return noBillCustomers;
	}
	
	@SuppressWarnings("unchecked")
	public List<CafDetailsDTO> getEntCafDetails(String year, String month) {
		String qry = null;
		Query query = null;
		List<BigInteger> oltObjList = null;
		List<CafDetailsDTO> cafList = null;
		try {
			LOGGER.info("START::getCafEntDetails");

			// qry = "select caf.cafno,caf.inst_district from cafs
			// caf,entcustomers entcust where caf.custid = entcust.custid and
			// entcust.enttypelov='GOVT' and caf.status='6'";
			// qry = "select caf.pmntcustid,caf.inst_district from cafs
			// caf,entcustomers entcust where year(caf.actdate)='"+year+"' and
			// month(caf.actdate)='"+month+"' and caf.pmntcustid =
			// entcust.custid and entcust.enttypelov='GOVT' and caf.status
			// in(6,8)";

			String queryString = "SELECT distinct cf.pmntcustid FROM customermst mst, cafs cf,entcustomers entcust WHERE mst.custid=cf.pmntcustid "
					+ " and cf.pmntcustid = entcust.custid and cf.actdate is not null and mst.actdate is not null "
					+ " and mst.actdate <= (select min(cff.actdate) from cafs cff where cff.pmntcustid=mst.custid limit 1) "
					+ " AND IFNULL(mst.finalbilldtl,'') ='' AND IFNULL(cf.finalbilldtl,'')='' AND "
					+ "( (cf.status=6 AND DATE(cf.actdate) <= DATE(LAST_DAY('" + year + "-" + month + "-01'))) "
					+ "OR (cf.status=8 AND DATE_FORMAT(cf.actdate,'%Y%m') <= '" + year + "" + month
					+ "')) AND cf.custdistuid='0' " + "and entcust.enttypelov='GOVT'";

			query = getEntityManager().createNativeQuery(queryString);

			// query.setFirstResult(start);
			// query.setMaxResults(count);

			oltObjList = query.getResultList();
			cafList = new ArrayList<CafDetailsDTO>();

			for (BigInteger object : oltObjList) {
				CafDetailsDTO cafDetailsDTOObj = new CafDetailsDTO();
				cafDetailsDTOObj.setCafNo(object == null ? "" : object.toString());
				// cafDetailsDTOObj.setDistrict(object[1].toString());
				/*
				 * cafDetailsDTOObj.setCafNo(object[0].toString());
				 * cafDetailsDTOObj.setDistrict(object[1].toString());
				 */
				cafList.add(cafDetailsDTOObj);

			}
		} catch (Exception ex) {
			LOGGER.error("ERROR::getCafChargeDetails" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
		}
		return cafList;
	}

	
	
	@SuppressWarnings("unchecked")
	public List<CorpusUpdate> getSubList(String customerID) {
		String qry = null;
		Query query = null;
		List<BigInteger> oltObjList = null;
		List<CorpusUpdate> subList = null;
		try {
			
			
			String queryString = "SELECT nwsubscode FROM cafstbs cs,cafs c WHERE custid='"+customerID+"' and c.cafno = cs.parentcafno";

			query = getEntityManager().createNativeQuery(queryString);

			// query.setFirstResult(start);
			// query.setMaxResults(count);

			oltObjList = query.getResultList();
			subList = new ArrayList<CorpusUpdate>();

			for (BigInteger object : oltObjList) {
				CorpusUpdate corpusUpdateObj = new CorpusUpdate();
				corpusUpdateObj.setSubscribercode(object == null ? "" : object.toString());
				// cafDetailsDTOObj.setDistrict(object[1].toString());
				subList.add(corpusUpdateObj);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR::getSubList" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
		}
		return subList;
	}

	
	@SuppressWarnings("unchecked")
	public List<CafDetailsDTO> getEntPrivateCafDetails(String year, String month) {
		String qry = null;
		Query query = null;
		List<BigInteger> oltObjList = null;
		List<CafDetailsDTO> cafList = null;
		try {
			LOGGER.info("START::getEntPrivateCafDetails");

			// qry = "select caf.cafno,caf.inst_district from cafs
			// caf,entcustomers entcust where caf.custid = entcust.custid and
			// entcust.enttypelov='PRIVATE' and caf.status='6'";
			/*
			 * qry =
			 * "select caf.pmntcustid,caf.inst_district from cafs caf,entcustomers entcust where year(caf.actdate)='"
			 * +year+"' and month(caf.actdate)='"+month+
			 * "' and caf.pmntcustid = entcust.custid and entcust.enttypelov='PRIVATE' and caf.status in(6,8)"
			 * ; query = getEntityManager().createNativeQuery(qry);
			 */

			/*
			 * String queryString =
			 * "SELECT distinct cf.pmntcustid FROM customermst mst, cafs cf,entcustomers entcust WHERE mst.custid=cf.pmntcustid "
			 * +
			 * " and cf.pmntcustid = entcust.custid and cf.actdate is not null and mst.actdate is not null "
			 * +
			 * " and mst.actdate <= (select min(cff.actdate) from cafs cff where cff.pmntcustid=mst.custid limit 1) "
			 * +
			 * " AND IFNULL(mst.finalbilldtl,'') ='' AND IFNULL(cf.finalbilldtl,'')='' "
			 * + " ANd cf.status IN (6,8) and " +
			 * " date(cf.actdate) <= date(last_day('"+year+"-"+month+"-01'))" +
			 * " and entcust.enttypelov='PRIVATE'";
			 */

			String queryString = "SELECT distinct cf.pmntcustid FROM customermst mst, cafs cf,entcustomers entcust WHERE mst.custid=cf.pmntcustid "
					+ " and cf.pmntcustid = entcust.custid and cf.actdate is not null and mst.actdate is not null "
					+ " and mst.actdate <= (select min(cff.actdate) from cafs cff where cff.pmntcustid=mst.custid limit 1) "
					+ " AND IFNULL(mst.finalbilldtl,'') ='' AND IFNULL(cf.finalbilldtl,'')='' AND "
					+ "( (cf.status=6 AND DATE(cf.actdate) <= DATE(LAST_DAY('" + year + "-" + month + "-01'))) "
					+ "OR (cf.status=8 AND DATE_FORMAT(cf.actdate,'%Y%m') <= '" + year + "" + month
					+ "') ) AND cf.custdistuid='0' " + "and entcust.enttypelov='PRIVATE'";

			query = getEntityManager().createNativeQuery(queryString);

			// query.setFirstResult(start);
			// query.setMaxResults(count);

			oltObjList = query.getResultList();
			cafList = new ArrayList<CafDetailsDTO>();

			for (BigInteger object : oltObjList) {
				CafDetailsDTO cafDetailsDTOObj = new CafDetailsDTO();
				cafDetailsDTOObj.setCafNo(object == null ? "" : object.toString());
				// cafDetailsDTOObj.setDistrict(object[1].toString());
				cafList.add(cafDetailsDTOObj);

			}
		} catch (Exception ex) {
			LOGGER.error("ERROR::getCafChargeDetails" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
		}
		return cafList;
	}

	/**
	 * 
	 * @param districtId
	 * @param cafNo
	 * @return
	 */
	@Transactional
	public String executeCAFChargeProcess(String districtId, Long cafNo, int yearMonth) {
		String output = null;
		SimpleJdbcCall generateSequenceSP = null;
		Map<String, Object> in = null;
		Map<String, Object> out = null;
		try {
			// LOGGER.info(String.format("executeCAFChargeProcess() Input
			// DistrictId:%s cafNo:%s yearMonth:%s", districtId, cafNo,
			// yearMonth));
			// yearMonth =
			// DateUtil.getCurrentMonth(InvoiceEngineConstants.YEAR_PATTERN) +
			// DateUtil.getCurrentMonth(InvoiceEngineConstants.MONTH_PATTERN_IN_VALUE_WITH_ZERO);
			generateSequenceSP = new SimpleJdbcCall(jdbcTemplate).withProcedureName("geninv")
					.withoutProcedureColumnMetaDataAccess()
					.useInParameterNames(
							// "p_districtuid", "p_acctcafno",
							// "p_yyyymm","p_taxzones","p_debuglevel").declareParameters(
							"p_pmntcustid", "p_yyyymm", "p_finalbillflag", "p_taxzones", "p_debuglevel")
					.declareParameters(
							/*
							 * new SqlParameter("p_districtuid",Types.INTEGER),
							 * new SqlParameter("p_acctcafno", Types.BIGINT),
							 */
							new SqlParameter("p_pmntcustid", Types.BIGINT), new SqlParameter("p_yyyymm", Types.INTEGER),
							new SqlParameter("p_finalbillflag", Types.VARCHAR),
							new SqlParameter("p_taxzones", Types.INTEGER),
							new SqlParameter("p_debuglevel", Types.INTEGER),
							new SqlOutParameter("p_result", Types.VARCHAR));

			in = new HashMap<String, Object>();
			/*
			 * in.put("p_districtuid", Integer.valueOf(districtId));
			 * in.put("p_acctcafno", cafNo);
			 */
			in.put("p_pmntcustid", cafNo);
			in.put("p_yyyymm", yearMonth);
			in.put("p_finalbillflag", "N");
			in.put("p_taxzones", 2);
			in.put("p_debuglevel", 0);
			/*
			 * if(cafNo==10000217) {
			 */
			out = generateSequenceSP.execute(in);
			/* } */
			if (out.get("p_result") != null)
				output = out.get("p_result").toString();

			LOGGER.info("executeCAFChargeProcess() Enterprise  Latest Output for cafNo: " + cafNo + ">>>" + output);
		} catch (Exception ex) {
			LOGGER.error("Exception occurred during executeCAFChargeProcess(): " + ex);
			throw ex;
		} finally {
			generateSequenceSP = null;
			in = null;
			out = null;
		}

		return output;
	}

	@Transactional
	public void updatePdfPathInCustInv(String year, String month, String custId, String fileLocation) {

		try {
			LOGGER.info("START::updatePdfPathInCustInv");

			String queryString = "UPDATE custinv SET invfilepath ='" + fileLocation + "' WHERE invmn = '" + month
					+ "' AND invyr = '" + year + "' AND pmntcustid = '" + custId + "'";

			getEntityManager().createNativeQuery(queryString).executeUpdate();
		} catch (Exception ex) {
			LOGGER.error("Exception occurred during updatePdfPathInCustInv(): " + ex);
			throw ex;
		}
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BillInfoDTO> getAllCustomerInvoiceSummaryForSMS(int year, int month) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;

		List<BillInfoDTO> billInfoDtoList = new ArrayList<>();

		try {
			LOGGER.info("START::getAllCustomerInvoiceSummaryForSMS");

			qry = "SELECT c.custname,c.addr1,c.email1,c.pocmob1,DATE_FORMAT(cv.invdate, '%d-%m-%Y') AS indate,DATE_FORMAT(cv.invfdate, '%d-%m-%Y') AS invfdate1,DATE_FORMAT(cv.invtdate, '%d-%m-%Y') AS invtdate2,DATE_FORMAT(cv.invduedate, '%d-%m-%Y') AS induedate,cv.invamt,cv.prevdueamt,c.custtypelov , "
					+ " CASE WHEN c.regbal+c.chargedbal>0 THEN c.regbal+c.chargedbal ELSE 0 END AS totalamt,cv.custinvno,cv.custdistuid,cv.invyr,cv.invmn,"
					+ "cv.pmntcustid,cv.smsflag,cv.invfilepath,cv.emailflag, cv.invamt + cv.srvctax + cv.swatchtax + cv.kisantax + cv.enttax AS currentmontamt"
					+ " FROM customermst c, custinv cv WHERE c.custid = cv.pmntcustid AND cv.invyr = ? AND cv.invmn = ?  AND ( cv.smsflag = 0 OR cv.emailflag = 0)";

			query = getEntityManager().createNativeQuery(qry);
			query.setParameter(1, year);
			query.setParameter(2, month);
			LOGGER.info("getAllCustomerInvoiceSummaryForSMS Query " + qry);
			// query.setParameter(3, custType);
			oltObjList = (List<Object[]>) query.getResultList();
			for (Object[] object : oltObjList) {

				BillInfoDTO billdto = new BillInfoDTO();

				billdto.setFullName(object[0].toString().toString());
				billdto.setAddress(object[1].toString());
				billdto.setEmail(object[2] == null ? null : object[2].toString());
				billdto.setPhoneNumber(object[3] == null ? null : object[3].toString());
				billdto.setBillDate(object[4].toString());
				billdto.setBillPeriodFrom(object[5].toString());
				billdto.setBillPeriodTo(object[6].toString());
				billdto.setDueDate(object[7].toString());
				/*
				 * billdto.setAmountPayable( object[8] == null ? new
				 * BigDecimal("0") : new BigDecimal(object[8].toString()));
				 */
				billdto.setPrevBalance(object[9] == null ? new BigDecimal("0") : new BigDecimal(object[9].toString()));
				billdto.setCustomerType(object[10].toString());

				billdto.setTotalBalance(object[11] == null ? "0" : object[11].toString());

				billdto.setBillNumber(object[12].toString());
				billdto.setCustomerId(object[16] == null ? null : object[16].toString());
				billdto.setInvYear(object[14].toString());
				billdto.setInvMonth(object[15].toString());
				billdto.setPmntCustId(object[16] == null ? new BigInteger("0") : new BigInteger(object[16].toString()));
				billdto.setSmsflag((Byte) object[17]);
				billdto.setFilePath(object[18] == null ? null : object[18].toString());
				billdto.setEmailflag((Byte) object[19]);

				billdto.setInvamtwithtax(
						object[20] == null ? new BigDecimal("0") : new BigDecimal(object[20].toString()));

				billInfoDtoList.add(billdto);
			}

		} catch (Exception ex) {
			LOGGER.error("ERROR::getAllCustomerInvoiceSummaryForSMS" + ex);
		}
		return billInfoDtoList;
	}

	@Transactional
	public int updateCustinvForSmsAndEmail(BillInfoDTO bdto) {
		String qry = null;
		Query query = null;
		int noOfRecordsUpdated = 0;
		try {
			LOGGER.info("START::updateCustinvForSmsAndEmail");
			qry = "UPDATE custinv SET smsflag=" + bdto.getSmsflag() + ",emailflag=" + bdto.getEmailflag()
					+ "  WHERE  invyr=" + bdto.getInvYear() + " AND invmn="
					+ bdto.getInvMonth() + " AND pmntcustid=" + bdto.getPmntCustId();
			LOGGER.info("updateCustinvForSmsAndEmail Query " + qry);
			query = getEntityManager().createNativeQuery(qry);
			noOfRecordsUpdated = query.executeUpdate();
		} catch (Exception ex) {
			LOGGER.error("ERROR::updateCustinvForSmsAndEmail" + ex);
		} finally {
			qry = null;
			query = null;
		}
		return noOfRecordsUpdated;
	}
	
	
	public int invoiceAcctNumberCount(String districtId, int month, int year, int customerId) {
		int count = 0;
		List<Object> obj = null;
		try {
			Query query = getEntityManager().createNativeQuery("SELECT count(1) from ( "
					+ " SELECT GROUP_CONCAT(ci.invno) invnos " + " FROM cafinv ci,customers cu,cafs cf "
					+ " WHERE ci.custdistuid = ?  AND ci.invyr = ? " + " AND ci.invmn = ? "
					+ " AND ci.acctcafno = cf.cafno  AND cf.custid = cu.custid "
					+ " AND cu.custtypelov ='INDIVIDUAL'  AND cf.status='6' AND cu.custid = ? GROUP BY ci.pmntcustid) a ");
			query.setParameter(1, districtId);
			query.setParameter(2, year);
			query.setParameter(3, month);
			query.setParameter(4, customerId);
			
			obj = query.getResultList();
			if (!obj.isEmpty()) {
				count = obj.get(0) == null ? 0 : Integer.parseInt(obj.get(0).toString());
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while counting the number cafinvoice records:" + e);
			e.printStackTrace();
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<InvCustIdDTO> getInvoiceNumbers(String districtId, int month, int year, int customerId) {
		List<InvCustIdDTO> cafAcctNumbers = new ArrayList<>();
		List<Object[]> objList = new ArrayList<>();
		try {
			Query query = getEntityManager()
					.createNativeQuery("SELECT GROUP_CONCAT(ci.invno) invnos, ci.pmntcustid,cu.custtypelov"
							+ " FROM cafinv ci,customermst cu,cafs cf " + " WHERE ci.custdistuid = ? "
							+ " AND ci.invyr = ? " + " AND ci.invmn = ? " + " AND ci.acctcafno = cf.cafno "
							+ " AND cf.custid = cu.custid " + " AND cu.custtypelov ='INDIVIDUAL' AND cu.custid = ? "
							// +" AND cf.status='6' "
							+ " GROUP BY ci.pmntcustid " + " ORDER BY ci.pmntcustid ");
			query.setParameter(1, districtId);
			query.setParameter(2, year);
			query.setParameter(3, month);
			query.setParameter(4, customerId);

			objList = query.getResultList();

			for (Object[] object : objList) {
				InvCustIdDTO invDTO = new InvCustIdDTO();
				invDTO.setInvno(object[0] == null ? "" : object[0].toString());
				invDTO.setPmntcustid(object[1] == null ? "" : object[1].toString());
				invDTO.setCustTypeLOV(object[2] == null ? "" : object[2].toString());
				cafAcctNumbers.add(invDTO);
			}
		} catch (Exception e) {
			LOGGER.error(String
					.format("Error occured while retrieving cafinvoice account number between start:%s and end:%s"), e);
			e.printStackTrace();
		}
		return cafAcctNumbers;
	}
	
	public int getEntGovtInvoiceNumberCount(int month, int year, int custId) {
		int count = 0;
		List<Object> obj = null;
		try {
			Query query = getEntityManager()
					.createNativeQuery(" SELECT count(1) from ( SELECT GROUP_CONCAT(invno) invnos, pmntcustid "
							+ " FROM cafinv ci " + " WHERE exists (select 1 from cafs caf,entcustomers entcust  "
							+ "               where caf.custid = entcust.custid  "
							+ "               and entcust.enttypelov='GOVT' and caf.status='6' "
							+ "               and ci.acctcafno = caf.cafno)  "
							+ " AND invyr = ? AND invmn = ? AND pmntcustid = ? GROUP BY pmntcustid ORDER BY pmntcustid ) a ");
			query.setParameter(1, year);
			query.setParameter(2, month);
			query.setParameter(3, custId);
			
			obj = query.getResultList();
			if (!obj.isEmpty()) {
				count = obj.get(0) == null ? 0 : Integer.parseInt(obj.get(0).toString());
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while counting the number cafinvoice records:" + e);
			e.printStackTrace();
		}
		return count;
	}

	public int getEntPrivateInvoiceNumberCount(int month, int year, int custId) {
		int count = 0;
		List<Object> obj = null;
		try {
			Query query = getEntityManager()
					.createNativeQuery(" SELECT count(1) from ( SELECT GROUP_CONCAT(invno) invnos, pmntcustid "
							+ " FROM cafinv ci " + " WHERE exists (select 1 from cafs caf,entcustomers entcust  "
							+ "               where caf.custid = entcust.custid  "
							+ "               and entcust.enttypelov='PRIVATE' and caf.status='6' "
							+ "               and ci.acctcafno = caf.cafno)  "
							+ " AND invyr = ? AND invmn = ? AND pmntcustid = ? GROUP BY pmntcustid ORDER BY pmntcustid ) a ");
			query.setParameter(1, year);
			query.setParameter(2, month);
			query.setParameter(3, custId);
			
			obj = query.getResultList();
			if (!obj.isEmpty()) {
				count = obj.get(0) == null ? 0 : Integer.parseInt(obj.get(0).toString());
			}
		} catch (Exception e) {
			LOGGER.error("Error occured while counting the number cafinvoice records:" + e);
			e.printStackTrace();
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<InvCustIdDTO> getInvoiceNumbersEntGovt(int month, int year, int custId) {
		List<InvCustIdDTO> cafAcctNumbers = new ArrayList<>();
		List<Object[]> objList = new ArrayList<>();
		try {
			Query query = getEntityManager().createNativeQuery("SELECT GROUP_CONCAT(invno) invnos, pmntcustid  "
					+ " FROM cafinv ci, customermst entcust  " + " WHERE 1=1 " + " AND entcust.enttypelov='GOVT'  "
					+ "  AND entcust.custid = ci.pmntcustid"
					+ "  AND invyr = ? AND invmn = ? AND pmntcustid = ? GROUP BY pmntcustid ORDER BY pmntcustid ");
			query.setParameter(1, year);
			query.setParameter(2, month);
			query.setParameter(3, custId);
			// query.setFirstResult(start);
			// query.setMaxResults(count);

			objList = query.getResultList();

			for (Object[] object : objList) {
				InvCustIdDTO invDTO = new InvCustIdDTO();
				invDTO.setInvno(object[0] == null ? "" : object[0].toString());
				invDTO.setPmntcustid(object[1] == null ? "" : object[1].toString());
				cafAcctNumbers.add(invDTO);
			}
		} catch (Exception e) {
			LOGGER.error(String
					.format("Error occured while retrieving cafinvoice account number between start:%s and end:%s"), e);
			e.printStackTrace();
		}
		return cafAcctNumbers;
	}

	@SuppressWarnings("unchecked")
	public List<InvCustIdDTO> getInvoiceNumbersEntPrivate(int month, int year, int custId) {
		List<InvCustIdDTO> cafAcctNumbers = new ArrayList<>();
		List<Object[]> objList = new ArrayList<>();
		try {
			Query query = getEntityManager().createNativeQuery("SELECT GROUP_CONCAT(invno) invnos, pmntcustid  "
					+ " FROM cafinv ci, customermst entcust  " + " WHERE 1=1 " + " AND entcust.enttypelov='PRIVATE'  "
					+ "  AND entcust.custid = ci.pmntcustid"
					+ "  AND invyr = ? AND invmn = ? AND pmntcustid = ? GROUP BY pmntcustid ORDER BY pmntcustid ");

			query.setParameter(1, year);
			query.setParameter(2, month);
			query.setParameter(3, custId);
			// query.setFirstResult(start);
			// query.setMaxResults(count);

			objList = query.getResultList();

			for (Object[] object : objList) {
				InvCustIdDTO invDTO = new InvCustIdDTO();
				invDTO.setInvno(object[0] == null ? "" : object[0].toString());
				invDTO.setPmntcustid(object[1] == null ? "" : object[1].toString());
				cafAcctNumbers.add(invDTO);
			}
		} catch (Exception e) {
			LOGGER.error(String
					.format("Error occured while retrieving cafinvoice account number between start:%s and end:%s"), e);
			e.printStackTrace();
		}
		return cafAcctNumbers;
	}
	
	/**Added for Changing PDF bill as per revenue share */
	@SuppressWarnings("unchecked")
	public Map<String,RevenueShareDTO> getRevenueShareDetails(String invnumber) {
		Map<String,RevenueShareDTO> revenueShareDTOs = new HashMap<String,RevenueShareDTO>();
		List<Object[]> objList = new ArrayList<>();
		try {
			LOGGER.info("CommonDAO::getRevenueShareDetails::Fetching::START");
			Query query = getEntityManager().createNativeQuery("select invno,cafno,chargecode,apsflshareamt,lmoshareamt,msoshareamt from invamtsharedtls where invno = ?");
			
			query.setParameter(1, invnumber);

			objList = query.getResultList();
			LOGGER.info("CommonDAO::getRevenueShareDetails::Fetching::END");
			LOGGER.info("CommonDAO::getRevenueShareDetails::Converting To Object::START");
			for (Object[] object : objList) {
				RevenueShareDTO revenueShareDTO = new RevenueShareDTO();
				String chargeCode = null;
				revenueShareDTO.setInvno(object[0] == null ? "" : object[0].toString());
				revenueShareDTO.setCafno(object[1] == null ? "" : object[1].toString());
				if(object[2] == null) {
					chargeCode="";
				}else {
					chargeCode = object[2].toString();
				}
				revenueShareDTO.setChargecode(chargeCode);
				revenueShareDTO.setApsflshare(object[3] == null ? new BigDecimal("0") : new BigDecimal(object[3].toString()));
				revenueShareDTO.setLmoshare(object[4] == null ? new BigDecimal("0") : new BigDecimal(object[4].toString()));
				revenueShareDTO.setMsoshare(object[5] == null ? new BigDecimal("0") : new BigDecimal(object[5].toString()));
				revenueShareDTOs.put(chargeCode, revenueShareDTO);
			}
			LOGGER.info("CommonDAO::getRevenueShareDetails::Converting To Object::END");
		} catch (Exception e) {
			LOGGER.error(String
					.format("Error occured while retrieving Revenue Share Details between start:%s and end:%s"), e);
			e.printStackTrace();
		}
		return revenueShareDTOs;
	}
	
	@Transactional
	public BillInfoDTO getIndividualInvoiceDetails_css(long invNumber, String yearMonth) {
		BillInfoDTO dto = null;
		int month = 0;
		int year = 0;
		try {

			// Fetch Invoice Information
			// (invno,invdate,invfdate,invtdate,invduedate,prevbal,prevpaid,custid,custtypelov,acctcafno,pmntcustid,invamt)
			LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setInvoiceInfo::START");
			dto = setInvoiceInfo(dto, invNumber,yearMonth);
			LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setInvoiceInfo::END");

			if (dto != null) {
				month = Integer.parseInt(dto.getInvMonth().trim());
				year = Integer.parseInt(dto.getInvYear().trim());

				// Fetch Customer Address Information
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setInvoiceCustomerInfo::START");
				dto = setInvoiceCustomerInfo(dto);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setInvoiceCustomerInfo::END");

				String invTDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setInvoicePaymentInfo::START");
				dto = setInvoicePaymentInfo(dto, dto.getBillPeriodFrom(), invTDate);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setInvoicePaymentInfo::END");
				List<PaymentDTO> payments=dto.getPayments();
				if(payments.size() > 0){
					LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::updateInvPaymentPdfDisFlag::START");
					updateInvPaymentPdfDisFlag(dto.getCustomerId(), dto.getBillPeriodFrom(), invTDate);
					LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::updateInvPaymentPdfDisFlag::END");
				}
				
				// Fetch Package Information
				//dto = setInvoicePackageInfo(dto);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setInternetSummaryInfo::START");
				dto = setInternetSummaryInfo(dto, month, year);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setInternetSummaryInfo::END");

				// Fetch Additional Charges like Recurring
				// Charges/Adjustments/One time charges/Late Fees Information
				
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setAdditionalChargesInfo::START");
				dto = setAdditionalChargesInfo(dto);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setAdditionalChargesInfo::END");

				// Fetch tax details
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setTaxInfo::START");
				dto = setTaxInfo(dto);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setTaxInfo::END");

				// Fetch Usage Charges Information
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setUsageChargesInfo::START");
				dto = setUsageChargesInfo(dto);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setUsageChargesInfo::END");

				// Fetch Recurring Charge details & Internet Usage
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setRecurrChargeDtlsInfo::START");
				dto = setRecurrChargeDtlsInfo(dto, month, year);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setRecurrChargeDtlsInfo::END");

				// Fetch Internet Usage
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setInternetUsageInfo::START");
				dto = setInternetUsageInfo(dto, month, year);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setInternetUsageInfo::END");

				// Fetch Usage Charges Of Phone
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setUsageChargesSummaryInfo::START");
				dto = setUsageChargesSummaryInfo(dto, month, year);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setUsageChargesSummaryInfo::END");

				// Fetch Itemized Call Details
				//dto = setItemizedCallInfo(dto, month, year);

				// Fetch Other Charges / Discounts & Adjustments
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setOtherChargesInfo::START");
				dto = setOtherChargesInfo(dto);
				LOGGER.info("CommonDAO::getIndividualInvoiceDetails_css::Calling::setOtherChargesInfo::END");
			}

			//LOGGER.info("END::getIndividualInvoiceDetails");
		} catch (Exception ex) {
			LOGGER.error("ERROR::getIndividualInvoiceDetails Invoice No :: " + invNumber + "  \n " + ex);
		} finally {

		}
		return dto;
	}
	
	@Transactional
	public BillInfoDTO getIndividualInvoiceDetails_css_Itemised(long invNumber,String detailedType, String yearMonth) {
		BillInfoDTO dto = null;
		int month = 0;
		int year = 0;
		try {

			// Fetch Invoice Information
			// (invno,invdate,invfdate,invtdate,invduedate,prevbal,prevpaid,custid,custtypelov,acctcafno,pmntcustid,invamt)
			dto = setInvoiceInfo(dto, invNumber,yearMonth);

			if (dto != null) {
				month = Integer.parseInt(dto.getInvMonth().trim());
				year = Integer.parseInt(dto.getInvYear().trim());

				// Fetch Customer Address Information
				dto = setInvoiceCustomerInfo(dto);

				// Fetch Itemized Call Details
				dto = setItemizedCallInfo_css(dto, month, year,detailedType);
			}

			//LOGGER.info("END::getIndividualInvoiceDetails");
		} catch (Exception ex) {
			LOGGER.error("ERROR::getIndividualInvoiceDetails Invoice No :: " + invNumber + "  \n " + ex);
		} finally {

		}
		return dto;
	}
	
	@SuppressWarnings("unchecked")
	public BillInfoDTO setItemizedCallInfo_css(BillInfoDTO dto, int month, int year, String detailedType) {
		String qry = null;
		Query query = null;
		List<Object[]> oltObjList = null;
		List<UsageDTO> offnetLocalSummary = new ArrayList<UsageDTO>();
		List<UsageDTO> offnetSTDLandlineSummary = new ArrayList<UsageDTO>();
		List<UsageDTO> offnetISDSummary = new ArrayList<UsageDTO>();
		List<UsageDTO> offnetSTDMobileSummary = new ArrayList<UsageDTO>();
		UsageDTO usageDTO = null;
		try {
			LOGGER.info("START::setItemizedCallInfo");
			if(detailedType.equalsIgnoreCase("LOCAL") || detailedType.equalsIgnoreCase("ISD")) {
				qry = "SELECT date(starttime), starttime, cldpartyaddr, calldurn, billunits, callcharge, srvccategory,phoneType FROM phoneusage WHERE acctcafno=? and usageyyyy=? AND usagemm=? AND srvccategory=? ORDER BY starttime";
				query = getEntityManager().createNativeQuery(qry);
				int srvccategory = 0;
				if(detailedType.equalsIgnoreCase("LOCAL")) {
					srvccategory = 8;
				}else {
					srvccategory = 3;
				}
				query.setParameter(4, srvccategory);
				
			}else {
				qry = "SELECT date(starttime), starttime, cldpartyaddr, calldurn, billunits, callcharge, srvccategory,phoneType FROM phoneusage WHERE acctcafno=? and usageyyyy=? AND usagemm=? AND phoneType=? ORDER BY starttime";
				query = getEntityManager().createNativeQuery(qry);
				String phonetype = "";
				if(detailedType.equalsIgnoreCase("STD")) {
					phonetype = "STDMOBILE";
				}else {
					phonetype = "STDLANDLINE";
				}
				query.setParameter(4, phonetype);
			}
			
			LOGGER.info(
					"setItemizedCallInfo Query " + qry + " BillInfoDto " + dto + " month " + month + " year " + year);
			
			query.setParameter(1, dto.getAccountNumber());
			query.setParameter(2, year);
			query.setParameter(3, month);

			oltObjList = query.getResultList();
			for (Object[] object : oltObjList) {
				usageDTO = new UsageDTO();
				usageDTO.setDate(object[0] == null ? "" : object[0].toString());
				usageDTO.setTime(object[1] == null ? "" : object[1].toString());
				usageDTO.setCalledNumber(object[2] == null ? "" : object[2].toString());
				usageDTO.setDuration(object[3] == null ? "" : object[3].toString());
				usageDTO.setUnits(object[4] == null ? "0" : object[4].toString());
				usageDTO.setCharges((object[5] == null ? new BigDecimal("0") : new BigDecimal(object[5].toString())));

				if ((object[6] == null ? "" : object[6].toString()).equalsIgnoreCase("8")) {
					offnetLocalSummary.add(usageDTO);
				} else if ((object[7] == null ? "" : object[7].toString()).equalsIgnoreCase("STDMOBILE")) {
					usageDTO.setCalledNumber(usageDTO.getCalledNumber().replace("+91", ""));
					offnetSTDMobileSummary.add(usageDTO);
				} else if ((object[6] == null ? "" : object[6].toString()).equalsIgnoreCase("3")) {
					offnetISDSummary.add(usageDTO);
				} else if ((object[7] == null ? "" : object[7].toString()).equalsIgnoreCase("STDLANDLINE")) {
					usageDTO.setCalledNumber(usageDTO.getCalledNumber().replace("+91", "0"));
					offnetSTDLandlineSummary.add(usageDTO);
				}

				usageDTO = null;
			}
			dto.setOffnetLocalSummary(offnetLocalSummary);
			dto.setOffnetSTDSummary(offnetSTDMobileSummary);
			dto.setOffnetISDSummary(offnetISDSummary);
			dto.setOffnetMobileSummary(offnetSTDLandlineSummary);
		} catch (Exception ex) {
			LOGGER.error("ERROR::setItemizedCallInfo:" + ex);
		} finally {
			qry = null;
			query = null;
			oltObjList = null;
			offnetLocalSummary = null;
			offnetSTDMobileSummary = null;
			offnetISDSummary = null;
			offnetSTDLandlineSummary = null;
		}
		return dto;
	}
	
}
