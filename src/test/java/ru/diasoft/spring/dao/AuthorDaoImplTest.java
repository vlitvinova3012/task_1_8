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
import ru.diasoft.spring.model.Author;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorDaoImplTest {
    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;
    @InjectMocks
    private AuthorDaoImpl authorDao;
    private Author resultAuthor = Author.builder().id(11L).name("Test author").build();


    @Test
    void testGetOrCreateAuthorIdByAuthorNameWhenAutorIsPresent() {
        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), (RowMapper<Object>) any())).thenReturn(List.of(resultAuthor));

        Long result = authorDao.getOrCreateAuthorIdByAuthorName("name");

        assertEquals(11L, result);
    }

    @Test
    void testGetOrCreateAuthorIdByAuthorNameWhenAutorIsNew() {
        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), (RowMapper<Object>) any())).thenReturn(Collections.emptyList()).thenReturn(List.of(resultAuthor));

        Long result = authorDao.getOrCreateAuthorIdByAuthorName("name");

        assertEquals(11L, result);
    }

    @Test
    void testGetAuthorById() {
        when(jdbcTemplate.query(anyString(), any(SqlParameterSource.class), (RowMapper<Object>) any())).thenReturn(List.of(resultAuthor));

        Optional<Author> result = authorDao.getAuthorById(11L);

        assertTrue(result.isPresent());
    }

    @Test
    void testGetAllAuthors() {
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(List.of(resultAuthor));

        List<Author> result = authorDao.getAllAuthors();

        assertFalse(result.isEmpty());
    }

    @Test
    void testUpdateAuthor() {
        authorDao.updateAuthor(resultAuthor);
        verify(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));
    }

    @Test
    void testDeleteAuthor() {
        authorDao.deleteAuthor(1L);
        verify(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));
    }

}