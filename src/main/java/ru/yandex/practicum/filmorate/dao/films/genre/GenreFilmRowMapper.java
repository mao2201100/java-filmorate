package ru.yandex.practicum.filmorate.dao.films.genre;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreFilmRowMapper implements RowMapper<GenreFilm> {
    @Override
    public GenreFilm mapRow(ResultSet rs, int rowNum) throws SQLException {
        GenreFilm genreFilm = new GenreFilm();
        genreFilm.setGenreId(rs.getLong("genre_id"));
        genreFilm.setFilmId(rs.getLong("film_id"));
        return genreFilm;
    }
}
