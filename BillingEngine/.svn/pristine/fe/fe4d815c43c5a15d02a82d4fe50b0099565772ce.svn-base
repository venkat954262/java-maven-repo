package com.arbiva.apfgc.invoice.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class SMSThread extends Thread{
	private static final Logger logger = Logger.getLogger(SMSThread.class);
	RestTemplate restTemplate;
	IpAddressValues ipAddressValues;
	List<String> mobiles=new ArrayList<String>();
	String msg;
	
	public SMSThread(IpAddressValues ipAddressValues,RestTemplate restTemplate,List<String> mobiles,String msg)
	{
		this.ipAddressValues=ipAddressValues;
		this.restTemplate=restTemplate;
		this.mobiles.addAll(mobiles);
		this.msg=msg;
	}
	
	public void run()
	{
		logger.info("in run");
		sendSMS(mobiles,msg);
	}
	
	public void sendSMS(List<String> mobiles,String msg)
    {
    	for(String mobile:mobiles)
    	{
    		logger.info(" in thread class"+mobile);
    		HttpEntity<String> httpEntity = ApsflHelper.getHttpEntity(ipAddressValues.getComUserName(), ipAddressValues.getComPwd());
    		String url = ipAddressValues.getComURL() + "sendTTSMS?mobileNo="+mobile+"&msg="+msg;
    		logger.info(" sendSMS "+url);
    		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
			response.getBody();
    	}
    }
}
