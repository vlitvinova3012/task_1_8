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
import ru.diasoft.spring.model.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreDaoImplTest {
    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;
    @InjectMocks
    private GenreDaoImpl genreDao;
    private Genre resultGenre = Genre.builder().id(11L).name("Test genre").build();


    @Test
    void testGetOrCreateGenreIdByGenreNameWhenGenreIsPresent() {
        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), (RowMapper<Object>) any())).thenReturn(List.of(resultGenre));

        Long result = genreDao.getOrCreateGenreIdByGenreName("name");

        assertEquals(11L, result);
    }

    @Test
    void testGetOrCreateGenreIdByGenreNameWhenGenreIsNew() {
        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), (RowMapper<Object>) any())).thenReturn(Collections.emptyList()).thenReturn(List.of(resultGenre));

        Long result = genreDao.getOrCreateGenreIdByGenreName("name");

        assertEquals(11L, result);
    }

    @Test
    void testGetGenreById() {
        when(jdbcTemplate.query(anyString(), any(SqlParameterSource.class), (RowMapper<Object>) any())).thenReturn(List.of(resultGenre));

        Optional<Genre> result = genreDao.getGenreById(11L);

        assertTrue(result.isPresent());
    }

    @Test
    void testGetAllGenres() {
        when(jdbcTemplate.query(anyString(), (RowMapper<Object>) any())).thenReturn(List.of(resultGenre));

        List<Genre> result = genreDao.getAllGenres();

        assertFalse(result.isEmpty());
    }

    @Test
    void testUpdateGenre() {
        genreDao.updateGenre(resultGenre);
        verify(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));
    }

    @Test
    void testDeleteGenre() {
        genreDao.deleteGenre(1L);
        verify(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class));
    }

}