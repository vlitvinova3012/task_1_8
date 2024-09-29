package ru.diasoft.spring.dao;

import ru.diasoft.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void createBook(String title, String genre, String author);

    Optional<Book> getBookById(Long id);

    List<Book> getAllBooks();

    void updateBook(Book book);

    void deleteBook(Long id);
}
