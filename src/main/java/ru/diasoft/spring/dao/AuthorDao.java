package ru.diasoft.spring.dao;

import ru.diasoft.spring.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Long createAuthor(String name);

    Long getOrCreateAuthorIdByAuthorName(String name);

    Optional<Author> getAuthorById(Long id);

    List<Author> getAllAuthors();

    void updateAuthor(Author author);

    void deleteAuthor(Long id);
}
