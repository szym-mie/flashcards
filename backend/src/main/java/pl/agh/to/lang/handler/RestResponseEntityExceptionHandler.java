package pl.agh.to.lang.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.agh.to.lang.exception.ResourceAlreadyExistsException;

import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleIOException(IOException exception, WebRequest request) {
        String body = "IO exception occurred";
        return this.handleExceptionInternal(
                exception,
                body,
                HttpHeaders.EMPTY,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<String> handleNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    protected ResponseEntity<String> handleResourceAlreadyExists(ResourceAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
