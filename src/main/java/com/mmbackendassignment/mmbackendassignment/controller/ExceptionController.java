package com.mmbackendassignment.mmbackendassignment.controller;

import com.mmbackendassignment.mmbackendassignment.exception.*;
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

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> exception(BadRequestException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BindingResultException.class)
    public ResponseEntity<Object> exception(BindingResultException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = ContractWithOwnException.class)
    public ResponseEntity<Object> exception(ContractWithOwnException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = NullValueNotAllowedException.class)
    public ResponseEntity<Object> exception(NullValueNotAllowedException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = UserIsDisabledException.class)
    public ResponseEntity<Object> exception(UserIsDisabledException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = CantDeleteWithDependencyException.class)
    public ResponseEntity<Object> exception(CantDeleteWithDependencyException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.IM_USED);
    }

    @ExceptionHandler(value = RecordAlreadyExistException.class)
    public ResponseEntity<Object> exception(RecordAlreadyExistException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.IM_USED);
    }

    @ExceptionHandler(value = EntityNotFromJwtUserException.class)
    public ResponseEntity<Object> exception(EntityNotFromJwtUserException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = FileExtensionNotSupportedException.class)
    public ResponseEntity<Object> exception(FileExtensionNotSupportedException exception) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}

