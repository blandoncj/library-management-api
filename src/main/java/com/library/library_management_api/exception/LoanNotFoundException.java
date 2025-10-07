package com.library.library_management_api.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException() {
        super("Loan not found");
    }

}
