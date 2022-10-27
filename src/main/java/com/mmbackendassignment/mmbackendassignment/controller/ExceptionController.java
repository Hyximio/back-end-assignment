package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.exceptions.BadRequestException;
import com.mmbackendassignment.mmbackendassignment.exceptions.RecordNotFoundException;
import com.mmbackendassignment.mmbackendassignment.exceptions.RoleNotFoundException;
import com.mmbackendassignment.mmbackendassignment.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Object> exception(RecordNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<Object> exception(UsernameNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RoleNotFoundException.class)
    public ResponseEntity<Object> exception(RoleNotFoundException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> exception(BadRequestException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

    }

}

