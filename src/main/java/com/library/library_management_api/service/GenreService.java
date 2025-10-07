package com.library.library_management_api.service;

import java.util.Set;

import com.library.library_management_api.dto.CreateGenreDTO;
import com.library.library_management_api.dto.UpdateGenreDTO;
import com.library.library_management_api.persistence.entity.GenreEntity;

public interface GenreService {

    Set<GenreEntity> getAllGenres();

    GenreEntity getGenreById(Long genreId);

    GenreEntity getGenreByName(String genreName);

    GenreEntity createGenre(CreateGenreDTO createGenreDTO);

    GenreEntity updateGenre(Long genreId, UpdateGenreDTO updateGenreDTO);

    GenreEntity toggleGenreStatus(Long genreId);

}
