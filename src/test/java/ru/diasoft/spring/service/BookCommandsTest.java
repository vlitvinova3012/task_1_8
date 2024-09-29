package ru.diasoft.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.diasoft.spring.model.Book;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCommandsTest {
    @Mock
    private BookService service;
    @InjectMocks
    private BookCommands bookCommands;

    @Test
    void testAddBook() {
        String result = bookCommands.addBook("Test title", "Test genre", "Test author");
        verify(service, times(1)).createBook(anyString(), anyString(), anyString());
        assertEquals("Book added", result);
    }

    @Test
    void testListBooks() {
        bookCommands.listBooks();
        verify(service, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById() {
        Book book = Book.builder().id(1L).build();
        when(service.getBookById(anyLong())).thenReturn(Optional.of(book));
        Book result = bookCommands.getBookById(1L);
        assertEquals(book, result);
    }

    @Test
    void testUpdateBook() {
        String response = bookCommands.updateBook(1L, "Test title", 1L, 1L);
        verify(service, times(1)).updateBook(any());
        assertEquals("Book updated successfully", response);
    }
    @Test
    void testDeleteBookById(){
        String response = bookCommands.deleteBookById(1L);
        verify(service, times(1)).deleteBook(any());
        assertEquals("Book delete successfully", response);
    }
}