package ru.diasoft.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Long createGenre(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        String insertGenreSql = "INSERT INTO Genre (name) VALUES (:name)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name);

        jdbcTemplate.update(insertGenreSql, params);

        return getOrCreateGenreIdByGenreName(name);
    }

    @Override
    public Long getOrCreateGenreIdByGenreName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        String sql = "SELECT * FROM Genre WHERE name = :name";
        SqlParameterSource param = new MapSqlParameterSource("name", name);
        List<Genre> genres = jdbcTemplate.query(sql, param, new GenreDaoImpl.GenreRowMapper());
        Optional<Genre> genreOpt = genres.stream().findFirst();
        if (genreOpt.isPresent()) {
            return genreOpt.get().getId();
        } else {
            return createGenre(name);
        }
    }

    @Override
    public Optional<Genre> getGenreById(Long id) {
        String sql = "SELECT * FROM Genre WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        List<Genre> genres = jdbcTemplate.query(sql, param, new GenreDaoImpl.GenreRowMapper());
        return genres.stream().findFirst();
    }

    @Override
    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM Genre";
        return jdbcTemplate.query(sql, new GenreDaoImpl.GenreRowMapper());
    }

    @Override
    public void updateGenre(Genre genre) {
        String sql = "UPDATE Genre SET name = :name WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("name", genre.getName());
        jdbcTemplate.update(sql, param);
    }

    @Override
    public void deleteGenre(Long id) {
        String sql = "DELETE FROM Genre WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, param);
    }

    private static final class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Genre.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }
}
