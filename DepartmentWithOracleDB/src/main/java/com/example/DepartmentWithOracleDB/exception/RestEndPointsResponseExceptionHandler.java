package com.example.DepartmentWithOracleDB.exception;

import com.example.DepartmentWithOracleDB.data.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestEndPointsResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DepartmentException.class)
    public ResponseEntity<ErrorMessage> departmentNotFoundException(DepartmentException exception,
                                                                    WebRequest webRequest) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
