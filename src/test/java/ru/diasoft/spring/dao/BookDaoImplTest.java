package ru.diasoft.spring.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import ru.diasoft.spring.model.Book;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookDaoImplTest {
    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;
    @InjectMocks
    private BookDaoImpl bookDao;

    @Test
    void testDeleteBook() {
        bookDao.deleteBook(1L);
        verify(jdbcTemplate, times(1)).update(anyString(), any(SqlParameterSource.class));
    }

    @Test
    void testUpdateBook() {
        Book book = Book.builder()
                .id(1L)
                .title("Test title")
                .genreId(1L)
                .authorId(1L)
                .build();
        bookDao.updateBook(book);
        verify(jdbcTemplate, times(1)).update(anyString(), any(SqlParameterSource.class));
    }

    @Test
    void testGetAllBooks() {
        bookDao.getAllBooks();
        verify(jdbcTemplate, times(1)).query(anyString(), (RowMapper<Object>) any());
    }

    @Test
    void testGetBookById() {
        bookDao.getBookById(1L);
        verify(jdbcTemplate, times(1)).query(anyString(), any(SqlParameterSource.class), (RowMapper<Object>) any());
    }

    @Test
    void testCreateBook() {
        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), (RowMapper<Object>) any())).thenReturn(Collections.emptyList());
        doAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(2);
            keyHolder.getKeyList().add(Collections.singletonMap("id", 3L));
            return null;
        }).when(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class));

        assertDoesNotThrow(() -> bookDao.createBook("Test title", "Test genre", "Test author"));
        verify(jdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));
    }


}