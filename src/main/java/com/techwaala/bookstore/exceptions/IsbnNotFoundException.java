package com.techwaala.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IsbnNotFoundException extends RuntimeException {

    public IsbnNotFoundException(String isbn) {
        super("Isbn with " + isbn + " not found");
    }
}
