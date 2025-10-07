package com.library.library_management_api.service;

import java.util.List;

import com.library.library_management_api.dto.CreateBookDTO;
import com.library.library_management_api.dto.UpdateBookDTO;
import com.library.library_management_api.persistence.entity.BookEntity;

public interface BookService {

    List<BookEntity> getAllBooks();

    BookEntity getBookById(Long bookId);

    BookEntity getBookByIsbn(String isbn);

    BookEntity createBook(CreateBookDTO createBookDTO);

    BookEntity updateBook(Long bookId, UpdateBookDTO updateBookDTO);

    BookEntity toggleBookStatus(Long bookId);

    BookEntity subtractStock(Long bookId, Integer quantity);

    BookEntity addStock(Long bookId, Integer quantity);

}
