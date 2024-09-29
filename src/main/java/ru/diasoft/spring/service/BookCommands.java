package ru.diasoft.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.spring.model.Book;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BookService service;

    @ShellMethod(value = "Add book", key = "add")
    public String addBook(@ShellOption String title, @ShellOption String genre, @ShellOption String author) {
        service.createBook(title, genre, author);
        return "Book added";
    }

    @ShellMethod(value = "List all books", key = "get-all")
    public List<Book> listBooks() {
        return service.getAllBooks();
    }

    @ShellMethod(value = "Get book by id", key = "get-by-id")
    public Book getBookById(@ShellOption Long id) {
        return service.getBookById(id).orElse(null);
    }

    @ShellMethod(value = "Update book", key = "update")
    public String updateBook(@ShellOption("--id") Long id,
                           @ShellOption("--title") String title,
                           @ShellOption("--authorId") Long authorId,
                           @ShellOption("--genreId") Long genreId) {
        Book book = Book.builder()
                .id(id)
                .title(title)
                .authorId(authorId)
                .genreId(genreId)
                .build();

        service.updateBook(book);
        return "Book updated successfully";
    }

    @ShellMethod(value = "Delete book", key = "delete")
    public String deleteBookById(@ShellOption Long id) {
        service.deleteBook(id);
        return "Book delete successfully";
    }
}
