package com.library.library_management_api.service;

import java.util.List;

import com.library.library_management_api.dto.CreateLoanDTO;
import com.library.library_management_api.persistence.entity.LoanEntity;

public interface LoanService {
   
    List<LoanEntity> getAllLoans();

    List<LoanEntity> getLoansByUserId(Long userId);

    LoanEntity getLoanById(Long loanId);

    LoanEntity createLoan(CreateLoanDTO createLoanDTO);

    LoanEntity returnLoan(Long loanId);

}
