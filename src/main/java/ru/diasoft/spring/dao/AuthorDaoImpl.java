package ru.diasoft.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Long createAuthor(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        String insertAuthorSql = "INSERT INTO Author (name) VALUES (:name)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name);

        jdbcTemplate.update(insertAuthorSql, params);

        return getOrCreateAuthorIdByAuthorName(name);
    }

    @Override
    public Long getOrCreateAuthorIdByAuthorName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        String sql = "SELECT * FROM Author WHERE name = :name";
        SqlParameterSource param = new MapSqlParameterSource("name", name);
        List<Author> authors = jdbcTemplate.query(sql, param, new AuthorDaoImpl.AuthorRowMapper());
        Optional<Author> authorOpt = authors.stream().findFirst();
        if (authorOpt.isPresent()) {
            return authorOpt.get().getId();
        } else {
            return createAuthor(name);
        }
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        String sql = "SELECT * FROM Author WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        List<Author> authors = jdbcTemplate.query(sql, param, new AuthorDaoImpl.AuthorRowMapper());
        return authors.stream().findFirst();
    }

    @Override
    public List<Author> getAllAuthors() {
        String sql = "SELECT * FROM Author";
        return jdbcTemplate.query(sql, new AuthorDaoImpl.AuthorRowMapper());
    }

    @Override
    public void updateAuthor(Author author) {
        String sql = "UPDATE Author SET name = :name WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("name", author.getName());
        jdbcTemplate.update(sql, param);
    }

    @Override
    public void deleteAuthor(Long id) {
        String sql = "DELETE FROM Author WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, param);
    }

    private static final class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }
}
