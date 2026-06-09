package com.pms.patient_service.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleValidationExceptions(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {

        log.warn("Email already exists exception: " + e.getMessage());
        Map<String , String> errors = new HashMap<>();
        errors.put("message",  "Email Already Exists");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException e){
        log.warn("Patient not found exception: " + e.getMessage());
        Map<String , String> errors = new HashMap<>();
        errors.put("message",  "Patient Not Found");
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}
