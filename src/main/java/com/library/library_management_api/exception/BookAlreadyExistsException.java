package com.library.library_management_api.exception;

public class BookAlreadyExistsException extends RuntimeException {

    public BookAlreadyExistsException() {
        super("Book already exists");
    }

}
