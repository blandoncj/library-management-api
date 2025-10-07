package com.library.library_management_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_management_api.dto.CreateLoanDTO;
import com.library.library_management_api.persistence.entity.LoanEntity;
import com.library.library_management_api.service.LoanServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
@Validated
public class LoanController {

    @Autowired
    private LoanServiceImpl loanService;

    @GetMapping
    public ResponseEntity<List<LoanEntity>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanEntity> getLoanById(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanEntity>> getLoansByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getLoansByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<LoanEntity> createLoan(@Valid @RequestBody CreateLoanDTO createLoanDTO) {
        return ResponseEntity.ok(loanService.createLoan(createLoanDTO));
    }

    @PutMapping("/{loanId}/return")
    public ResponseEntity<LoanEntity> returnLoan(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.returnLoan(loanId));
    }

}
