package ru.diasoft.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.diasoft.spring.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    @Override
    public void createBook(String title, String genre, String author) {
        if (title == null || title.isEmpty()) {
            return;
        }
        Long genreId = genreDao.getOrCreateGenreIdByGenreName(genre);
        Long authorId = authorDao.getOrCreateAuthorIdByAuthorName(author);

        if (genreId == null || authorId == null) {
            return;
        }

        String insertBookSql = "INSERT INTO Book (title, genreId, authorId) VALUES (:title, :genreId, :authorId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("genreId", genreId)
                .addValue("authorId", authorId);

        jdbcTemplate.update(insertBookSql, params);
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        String sql = "SELECT * FROM Book WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        List<Book> books = jdbcTemplate.query(sql, param, new BookRowMapper());
        return books.stream().findFirst();
    }

    @Override
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM Book";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    @Override
    public void updateBook(Book book) {
        String sql = "UPDATE Book SET title = :title, authorId = :authorId, genreId = :genreId WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle())
                .addValue("authorId", book.getAuthorId())
                .addValue("genreId", book.getGenreId());
        jdbcTemplate.update(sql, param);
    }

    @Override
    public void deleteBook(Long id) {
        String sql = "DELETE FROM Book WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, param);
    }

    private static final class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("authorId"))
                    .genreId(rs.getLong("genreId"))
                    .build();
        }
    }
}
