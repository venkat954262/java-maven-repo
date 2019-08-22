package com.arbiva.apfgc.invoice.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("IpAddressValues")
public class IpAddressValues {
	
		
	@Value("${COM-URL}")
	private String comURL;

	@Value("${COM-USERNAME}")
	private String comUserName;

	@Value("${COM-PWD}")
	private String comPwd;
	
	@Value("${UMS-URL}")
	private String umsURL;
	
	@Value("${UMS-USERNAME}")
	private String umsUserName;
	
	@Value("${UMS-PWD}")
	private String umsPwd;
	
	

	@Value("${CORPUS-CALL-SAVE}")
	private String corpusCallSave;
	
	@Value("${CORPUS-CALL-UPDATE}")
	private String corpusCallUpdate;

	@Value("${CORPUS-USERNAME}")
	private String corpusUserName;

	@Value("${CORPUS-API-KEY}")
	private String corpusApiKey;

	@Value("${CORPUS-CALL-TO-GET-CHANNELS}")
	private String corpusCallToGetChannels;

	@Value("${CORPUS-ALERTS-MESSAGES}")
	private String corpusAlertsMessages;
	
	public String getCorpusCreatePartner() {
		return corpusCreatePartner;
	}

	public void setCorpusCreatePartner(String corpusCreatePartner) {
		this.corpusCreatePartner = corpusCreatePartner;
	}

	@Value("${CORPUS-CREATE-PARTNER}")
	private String corpusCreatePartner;
	

	public String getUmsURL() {
		return umsURL;
	}

	public void setUmsURL(String umsURL) {
		this.umsURL = umsURL;
	}

	public String getUmsUserName() {
		return umsUserName;
	}

	public void setUmsUserName(String umsUserName) {
		this.umsUserName = umsUserName;
	}

	public String getUmsPwd() {
		return umsPwd;
	}

	public void setUmsPwd(String umsPwd) {
		this.umsPwd = umsPwd;
	}

	public String getComURL() {
		return comURL;
	}

	public void setComURL(String comURL) {
		this.comURL = comURL;
	}

	public String getComUserName() {
		return comUserName;
	}

	public void setComUserName(String comUserName) {
		this.comUserName = comUserName;
	}

	public String getComPwd() {
		return comPwd;
	}

	public void setComPwd(String comPwd) {
		this.comPwd = comPwd;
	}

	public String getCorpusCallSave() {
		return corpusCallSave;
	}

	public void setCorpusCallSave(String corpusCallSave) {
		this.corpusCallSave = corpusCallSave;
	}

	public String getCorpusCallUpdate() {
		return corpusCallUpdate;
	}

	public void setCorpusCallUpdate(String corpusCallUpdate) {
		this.corpusCallUpdate = corpusCallUpdate;
	}

	public String getCorpusUserName() {
		return corpusUserName;
	}

	public void setCorpusUserName(String corpusUserName) {
		this.corpusUserName = corpusUserName;
	}

	public String getCorpusApiKey() {
		return corpusApiKey;
	}

	public void setCorpusApiKey(String corpusApiKey) {
		this.corpusApiKey = corpusApiKey;
	}

	public String getCorpusCallToGetChannels() {
		return corpusCallToGetChannels;
	}

	public void setCorpusCallToGetChannels(String corpusCallToGetChannels) {
		this.corpusCallToGetChannels = corpusCallToGetChannels;
	}

	public String getCorpusAlertsMessages() {
		return corpusAlertsMessages;
	}

	public void setCorpusAlertsMessages(String corpusAlertsMessages) {
		this.corpusAlertsMessages = corpusAlertsMessages;
	}

	
	
}
