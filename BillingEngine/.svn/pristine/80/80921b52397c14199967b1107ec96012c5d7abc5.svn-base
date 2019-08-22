package com.arbiva.apfgc.invoice.utils;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.arbiva.apfgc.invoice.businessservice.InvoiceGeneratorBusinessService;


/**
 * 
 * @author srinivasa
 *
 */
public class CAFChargeCalcEngineFuture implements Callable<Boolean> {
	
	private static final Logger LOGGER = Logger.getLogger(CAFChargeCalcEngineFuture.class);
	
	private InvoiceGeneratorBusinessService invoiceGeneratorBusinessService;
	
	private long cafNo;
	
	private String districtId;
	
	private int yearMonth;
		
	public CAFChargeCalcEngineFuture() {
	}
	
	public CAFChargeCalcEngineFuture(InvoiceGeneratorBusinessService invoiceGeneratorBusinessService,
			String districtId, Long cafNo,  int yearMonth) {
		this.invoiceGeneratorBusinessService = invoiceGeneratorBusinessService;
		this.districtId = districtId;
		this.cafNo = cafNo;
		this.yearMonth = yearMonth;
	}

	@Override
	public Boolean call() throws Exception {
		String output = null;
		try {
			output = invoiceGeneratorBusinessService.executeCAFChargeProcess(districtId, cafNo, yearMonth);
			LOGGER.info("Output: " + output);
			LOGGER.debug(String.format("CAF Charge Calculation Process for DistrictID:%s and cafNo:%s", districtId, cafNo));
		} catch (Exception e) {
			LOGGER.error(
					String.format("Error occured while executing CAF Charge Calculation Process for an DistrictID:%s on cafNo:%s",
							districtId, cafNo));
		}
		return true;
	}

}
