package br.com.tqi.bootcamp.bookstore.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends APIBusinessException {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
