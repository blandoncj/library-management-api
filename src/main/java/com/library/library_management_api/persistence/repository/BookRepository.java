package com.library.library_management_api.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.library_management_api.persistence.entity.BookEntity;

import jakarta.transaction.Transactional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByIsbn(String isbn);

    @Modifying
    @Transactional
    @Query("UPDATE BookEntity b SET b.stock = b.stock - ?2 WHERE b.id = ?1")
    void subtractStock(Long bookId, Integer quantity);

    @Modifying
    @Transactional
    @Query("UPDATE BookEntity b SET b.stock = b.stock + ?2 WHERE b.id = ?1")
    void addStock(Long bookId, Integer quantity);

}