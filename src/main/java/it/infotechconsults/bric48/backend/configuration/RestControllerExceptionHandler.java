package it.infotechconsults.bric48.backend.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.infotechconsults.bric48.backend.configuration.error.ErrorDetail;
import it.infotechconsults.bric48.backend.exception.ResourceAlreadyExists;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ResourceAlreadyExists.class })
    public ResponseEntity<Object> handleEntityExistsException(ResourceAlreadyExists ex, WebRequest request) {
        return new ResponseEntity<Object>(ErrorDetail.builder()
                .type(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build(), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        log.error("Exception returned: ", ex);
        return new ResponseEntity<Object>(ErrorDetail.builder()
                .message(ex.getMessage())
                .type(ex.getClass().getSimpleName())
                .build(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
