package com.library.library_management_api.exception;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException() {
        super("Genre not found");
    }

}
