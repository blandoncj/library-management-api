package com.library.library_management_api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.library_management_api.dto.CreateGenreDTO;
import com.library.library_management_api.dto.UpdateGenreDTO;
import com.library.library_management_api.exception.GenreAlreadyExistsException;
import com.library.library_management_api.exception.GenreNotFoundException;
import com.library.library_management_api.persistence.entity.GenreEntity;
import com.library.library_management_api.persistence.repository.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Set<GenreEntity> getAllGenres() {
        return new HashSet<>(genreRepository.findAll());
    }

    @Override
    public GenreEntity getGenreById(Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException());
    }

    @Override
    public GenreEntity getGenreByName(String genreName) {
        return genreRepository.findByName(genreName)
                .orElseThrow(() -> new GenreNotFoundException());
    }

    @Override
    public GenreEntity createGenre(CreateGenreDTO createGenreDTO) {
        genreRepository.findByName(createGenreDTO.name()).ifPresent(genre -> {
            throw new GenreAlreadyExistsException();
        });

        GenreEntity genre = new GenreEntity();
        genre.setName(createGenreDTO.name());
        genre.setEnabled(true);
        return genreRepository.save(genre);
    }

    @Override
    public GenreEntity updateGenre(Long genreId, UpdateGenreDTO updateGenreDTO) {
        GenreEntity genreEntity = genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException());

        genreRepository.findByName(updateGenreDTO.name()).ifPresent(genre -> {
            if (!genre.getId().equals(genreId)) {
                throw new GenreAlreadyExistsException();
            }
        });

        genreEntity.setName(updateGenreDTO.name());
        genreRepository.save(genreEntity);
        return genreEntity;
    }

    @Override
    public GenreEntity toggleGenreStatus(Long genreId) {
        GenreEntity genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException());

        genre.setEnabled(!genre.isEnabled());
        genreRepository.save(genre);
        return genre;
    }

}
