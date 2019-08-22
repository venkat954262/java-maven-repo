package com.arbiva.apfgc.invoice.utils;

import org.apache.commons.net.util.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class ApsflHelper {

	public static HttpEntity<String> getHttpEntity(String username, String pwd) {
		String plainCreds = username + ":" + pwd;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		return new HttpEntity<String>(headers);
	}

	public static <DTO> HttpEntity<DTO> getHttpEntity(String username, String pwd, DTO dto) {

		String plainCreds = username + ":" + pwd;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		return new HttpEntity<DTO>(dto, headers);
	}

}
