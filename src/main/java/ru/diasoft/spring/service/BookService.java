package ru.diasoft.spring.service;

import ru.diasoft.spring.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void createBook(String title, String genre, String author);

    Optional<Book> getBookById(Long id);

    List<Book> getAllBooks();

    void updateBook(Book book);

    void deleteBook(Long id);
}
