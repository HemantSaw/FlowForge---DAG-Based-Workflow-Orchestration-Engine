package com.hemant.workflow_orchestrator.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hemant.workflow_orchestrator.services.CycleDetectedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            CycleDetectedException.class
    )
    public ResponseEntity<String>
    handleCycleException(
            CycleDetectedException ex
    ) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}