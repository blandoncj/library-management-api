package com.library.library_management_api.exception;

public class GenreAlreadyExistsException extends RuntimeException {

    public GenreAlreadyExistsException() {
        super("Genre already exists");
    }

}
