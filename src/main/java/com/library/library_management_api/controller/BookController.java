package com.library.library_management_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_management_api.dto.CreateBookDTO;
import com.library.library_management_api.dto.StockDTO;
import com.library.library_management_api.dto.UpdateBookDTO;
import com.library.library_management_api.persistence.entity.BookEntity;
import com.library.library_management_api.service.BookServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping
    public ResponseEntity<List<BookEntity>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @PostMapping
    public ResponseEntity<BookEntity> createBook(@Valid @RequestBody CreateBookDTO createBookDTO) {
        return ResponseEntity.ok(bookService.createBook(createBookDTO));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookEntity> updateBook(@Valid @PathVariable Long bookId,
            @RequestBody UpdateBookDTO updateBookDTO) {
        return ResponseEntity.ok(bookService.updateBook(bookId, updateBookDTO));
    }

    @PatchMapping("/{bookId}/status")
    public ResponseEntity<BookEntity> toggleBookStatus(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.toggleBookStatus(bookId));
    }

    @PatchMapping("/{bookId}/subtract-stock")
    public ResponseEntity<BookEntity> subtractStock(@PathVariable Long bookId, @Valid @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(bookService.subtractStock(bookId, stockDTO.quantity()));
    }

    @PatchMapping("/{bookId}/add-stock")
    public ResponseEntity<BookEntity> addStock(@PathVariable Long bookId, @Valid @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(bookService.addStock(bookId, stockDTO.quantity()));
    }

}
