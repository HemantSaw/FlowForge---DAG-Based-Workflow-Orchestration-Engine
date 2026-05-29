package com.hemant.workflow_orchestrator.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CycleDetectedException
        extends RuntimeException {

    public CycleDetectedException(
            String message
    ) {
        super(message);
    }
}

