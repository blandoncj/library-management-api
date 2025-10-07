package com.library.library_management_api.persistence.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    @JsonIgnore
    private GenreEntity genre;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<LoanEntity> loans;

}
