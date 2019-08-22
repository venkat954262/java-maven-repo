package com.arbiva.apfgc.invoice.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.arbiva.apfgc.invoice.dto.ErrorMessageDTO;
import com.arbiva.apfgc.invoice.exception.InvoiceEngineException;
import com.arbiva.apfgc.invoice.utils.InvoiceEngineErrorCode.InvoiceEngineErrorCodes;

/**
 * {@link InvoiceEngineExceptionalHandler} is a handle to handle the exceptions and
 * provide proper error response with status.
 * 
 * @author srinivasa
 *
 */
public class InvoiceEngineExceptionalHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessageDTO> parseError(HttpServletRequest request, Exception exception) {
		if (exception instanceof InvoiceEngineException) {
			return ResponseEntity.badRequest().body(((InvoiceEngineException) exception).getErrorMessageDTO());
		}
		return ResponseEntity.badRequest().body(new ErrorMessageDTO(InvoiceEngineErrorCodes.GAE001));
	}

}
