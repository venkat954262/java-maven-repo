package com.arbiva.apfgc.invoice.dto;

import com.arbiva.apfgc.invoice.utils.InvoiceEngineErrorCode.InvoiceEngineErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * {@link ErrorMessageDTO} is a DTO to holds the application specific error
 * information understand by user.
 * 
 * @author srinivasa
 *
 */
@JsonInclude(Include.NON_NULL)
public class ErrorMessageDTO {

	private int code;

	private String message;

	private String description;

	private String localizedMessage;

	public ErrorMessageDTO() {
	}

	public ErrorMessageDTO(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ErrorMessageDTO(InvoiceEngineErrorCodes errorCodes) {
		this.code = errorCodes.getCode();
		this.message = errorCodes.getDescription();

	}

	public ErrorMessageDTO(InvoiceEngineErrorCodes errorCodes,
			String description) {
		this.code = errorCodes.getCode();
		this.message = errorCodes.getDescription();
		this.description = description;

	}

	public ErrorMessageDTO(InvoiceEngineErrorCodes errorCodes,
			String description, Throwable throwable) {
		this.code = errorCodes.getCode();
		this.message = errorCodes.getDescription();
		this.description = description;
		this.localizedMessage = throwable.getLocalizedMessage();

	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the localizedMessage
	 */
	public String getLocalizedMessage() {
		return localizedMessage;
	}

	/**
	 * @param localizedMessage
	 *            the localizedMessage to set
	 */
	public void setLocalizedMessage(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime
				* result
				+ ((localizedMessage == null) ? 0 : localizedMessage.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorMessageDTO other = (ErrorMessageDTO) obj;
		if (code != other.code)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (localizedMessage == null) {
			if (other.localizedMessage != null)
				return false;
		} else if (!localizedMessage.equals(other.localizedMessage))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ErrorMessageDTO [code=" + code + ", message=" + message
				+ ", description=" + description + ", throwable="
				+ localizedMessage + "]";
	}

}
