package com.library.library_management_api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.library_management_api.dto.CreateLoanDTO;
import com.library.library_management_api.exception.BookNotFoundException;
import com.library.library_management_api.exception.InsufficientStockException;
import com.library.library_management_api.exception.LoanNotFoundException;
import com.library.library_management_api.exception.UserNotFoundException;
import com.library.library_management_api.persistence.entity.BookEntity;
import com.library.library_management_api.persistence.entity.LoanEntity;
import com.library.library_management_api.persistence.entity.UserEntity;
import com.library.library_management_api.persistence.repository.BookRepository;
import com.library.library_management_api.persistence.repository.LoanRepository;
import com.library.library_management_api.persistence.repository.UserRepository;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<LoanEntity> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<LoanEntity> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    @Override
    public LoanEntity getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException());
    }

    @Override
    public LoanEntity createLoan(CreateLoanDTO createLoanDTO) {
        BookEntity book = bookRepository.findById(createLoanDTO.bookId())
                .orElseThrow(() -> new BookNotFoundException());

        UserEntity user = userRepository.findById(createLoanDTO.userId())
                .orElseThrow(() -> new UserNotFoundException());

        if (book.getStock() < createLoanDTO.quantity()) {
            throw new InsufficientStockException();
        }

        bookRepository.subtractStock(book.getId(), createLoanDTO.quantity());

        LoanEntity loan = LoanEntity.builder()
                .book(book)
                .user(user)
                .quantity(createLoanDTO.quantity())
                .loanDate(LocalDate.now())
                .dueDate(LocalDate.now().plusMonths(1))
                .returnDate(null)
                .isReturned(false)
                .build();

        return loanRepository.save(loan);
    }

    @Override
    public LoanEntity returnLoan(Long loanId) {
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException());

        loan.setReturnDate(LocalDate.now());
        loan.setIsReturned(true);
        bookRepository.addStock(loan.getBook().getId(), loan.getQuantity());

        return loanRepository.save(loan);
    }

}
