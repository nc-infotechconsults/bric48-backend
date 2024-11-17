package it.infotechconsults.bric48.backend.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.infotechconsults.bric48.backend.configuration.error.ErrorDetail;
import jakarta.persistence.EntityExistsException;

@ControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ EntityExistsException.class })
    public ResponseEntity<Object> handleEntityExistsException(EntityExistsException ex, WebRequest request) {
        return new ResponseEntity<Object>(ErrorDetail.builder()
                .message(ex.getMessage())
                .build(), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(ErrorDetail.builder()
                .message(ex.getMessage())
                .type(ex.getClass().getName())
                .build(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
