package com.arbiva.apfgc.invoice.utils;

/**
 * {@link InventoryMgmtErrorCodes} holds the application specific error codes.
 * 
 * @author srinivasa
 *
 */
public class InvoiceEngineErrorCode {
	
	public enum InvoiceEngineErrorCodes {

		GAE001(400, "Internal Server Error"), 
		GAE002(400, "Invalid empty parameter"), 
		GAE003(400,	"Invalid Information"), 
		INV001(400, "Invalid Invoice Information");

		private final int code;
		private final String description;

		private InvoiceEngineErrorCodes(int code, String description) {
			this.code = code;
			this.description = description;
		}

		public int getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}

}
