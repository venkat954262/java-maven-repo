package com.arbiva.apfgc.invoice.exception;

import com.arbiva.apfgc.invoice.dto.ErrorMessageDTO;

/**
 * {@link InvoiceEngineException} is an application specific exception to hold and
 * provide the proper error message object to end users.
 * 
 * @author srinivasa
 *
 */
public class InvoiceEngineException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ErrorMessageDTO errorMessageDTO;

	public InvoiceEngineException() {
		super();
	}

	public InvoiceEngineException(String message) {
		super(message);
	}

	public InvoiceEngineException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvoiceEngineException(ErrorMessageDTO messageDTO) {
		super();
		this.setErrorMessageDTO(messageDTO);
	}

	/**
	 * @return the ErrorMessageDTO
	 */
	public ErrorMessageDTO getErrorMessageDTO() {
		return errorMessageDTO;
	}

	/**
	 * @param errorMessageDTO
	 *            the errorMessageDTO to set
	 */
	public void setErrorMessageDTO(ErrorMessageDTO errorMessageDTO) {
		this.errorMessageDTO = errorMessageDTO;
	}

}
