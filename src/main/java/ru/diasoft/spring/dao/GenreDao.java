package ru.diasoft.spring.dao;

import ru.diasoft.spring.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Long createGenre(String name);
    Long getOrCreateGenreIdByGenreName(String name);
    Optional<Genre> getGenreById(Long id);
    List<Genre> getAllGenres();
    void updateGenre(Genre genre);
    void deleteGenre(Long id);
}
