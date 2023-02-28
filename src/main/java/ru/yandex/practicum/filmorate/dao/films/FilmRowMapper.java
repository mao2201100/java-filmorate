package ru.yandex.practicum.filmorate.dao.films;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(LocalDate.parse(rs.getString("releaseDate")));
        film.setDuration(rs.getLong("duration"));
        film.setName(rs.getString("name"));
        film.setMpaId(rs.getLong("MPA_id"));
        return film;
    }

}