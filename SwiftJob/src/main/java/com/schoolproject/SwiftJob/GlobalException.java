/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.schoolproject.SwiftJob;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.Map;

/**
 *
 * @author motho
 */
@RestControllerAdvice
public class GlobalException 
{
    
    // Handles jobs that cannot be found.
    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(JobNotFoundException ex) 
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    // Handles broken repair rules.
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, String>> handleBusinessError(BusinessRuleException ex) 
    {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    // Handles invalid user input.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) 
    {

        String message = "Please check the information you entered.";

        if (!ex.getBindingResult().getFieldErrors().isEmpty()) {
            message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        }
        return ResponseEntity.badRequest().body(Map.of("error", message));
    }

    // Handles invalid JSON or an invalid status value.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(HttpMessageNotReadableException ex) 
    {
        return ResponseEntity.badRequest().body(Map.of( "error","Invalid request data. Check your JSON and status."));
    }
}
