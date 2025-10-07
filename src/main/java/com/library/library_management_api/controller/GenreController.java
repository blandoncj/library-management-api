package com.library.library_management_api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_management_api.dto.CreateGenreDTO;
import com.library.library_management_api.dto.UpdateGenreDTO;
import com.library.library_management_api.persistence.entity.GenreEntity;
import com.library.library_management_api.service.GenreServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private GenreServiceImpl genreService;

    @GetMapping
    public ResponseEntity<Set<GenreEntity>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<GenreEntity> getGenreById(@PathVariable Long genreId) {
        return ResponseEntity.ok(genreService.getGenreById(genreId));
    }

    @PostMapping
    public ResponseEntity<GenreEntity> createGenre(@Valid @RequestBody CreateGenreDTO createGenreDTO) {
        return ResponseEntity.ok(genreService.createGenre(createGenreDTO));
    }

    @PatchMapping("/{genreId}/name")
    public ResponseEntity<GenreEntity> updateGenre(@Valid @PathVariable Long genreId,
            @RequestBody UpdateGenreDTO updateGenreDTO) {
        return ResponseEntity.ok(genreService.updateGenre(genreId, updateGenreDTO));
    }

    @PatchMapping("/{genreId}/status")
    public ResponseEntity<GenreEntity> toggleGenreStatus(@PathVariable Long genreId) {
        return ResponseEntity.ok(genreService.toggleGenreStatus(genreId));
    }

}
