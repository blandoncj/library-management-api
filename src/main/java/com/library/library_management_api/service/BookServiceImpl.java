package com.library.library_management_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.library_management_api.dto.CreateBookDTO;
import com.library.library_management_api.dto.UpdateBookDTO;
import com.library.library_management_api.exception.BookAlreadyExistsException;
import com.library.library_management_api.exception.BookNotFoundException;
import com.library.library_management_api.exception.GenreNotFoundException;
import com.library.library_management_api.exception.InsufficientStockException;
import com.library.library_management_api.persistence.entity.BookEntity;
import com.library.library_management_api.persistence.entity.GenreEntity;
import com.library.library_management_api.persistence.repository.BookRepository;
import com.library.library_management_api.persistence.repository.GenreRepository;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public BookEntity getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException());
    }

    @Override
    public BookEntity getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException());
    }

    @Override
    public BookEntity createBook(CreateBookDTO createBookDTO) {
        bookRepository.findByIsbn(createBookDTO.isbn()).ifPresent(book -> {
            throw new BookAlreadyExistsException();
        });

        GenreEntity genre = genreRepository.findById(createBookDTO.genreId())
                .orElseThrow(() -> new GenreNotFoundException());

        BookEntity book = BookEntity.builder()
                .isbn(createBookDTO.isbn())
                .title(createBookDTO.title())
                .author(createBookDTO.author())
                .publicationYear(createBookDTO.publicationYear())
                .genre(genre)
                .stock(createBookDTO.stock())
                .isEnabled(true)
                .build();

        return bookRepository.save(book);
    }

    @Override
    public BookEntity updateBook(Long bookId, UpdateBookDTO updateBookDTO) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException());

        GenreEntity genre = genreRepository.findById(updateBookDTO.genreId())
                .orElseThrow(() -> new GenreNotFoundException());

        book.setTitle(updateBookDTO.title());
        book.setAuthor(updateBookDTO.author());
        book.setPublicationYear(updateBookDTO.publicationYear());
        book.setStock(updateBookDTO.stock());
        book.setGenre(genre);

        return bookRepository.save(book);
    }

    @Override
    public BookEntity toggleBookStatus(Long bookId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException());

        book.setEnabled(!book.isEnabled());
        return bookRepository.save(book);
    }

    @Override
    public BookEntity subtractStock(Long bookId, Integer quantity) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException());

        if (book.getStock() < quantity) {
            throw new InsufficientStockException();
        }

        book.setStock(book.getStock() - quantity);
        return bookRepository.save(book);
    }

    @Override
    public BookEntity addStock(Long bookId, Integer quantity) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException());

        book.setStock(book.getStock() + quantity);
        return bookRepository.save(book);
    }

}
