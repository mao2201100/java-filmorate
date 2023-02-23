package ru.yandex.practicum.filmorate.dao.films.likes;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.film.FilmLikes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmLikesRowMapper implements RowMapper<FilmLikes>{

        @Override
        public FilmLikes mapRow(ResultSet rs, int rowNum) throws SQLException {
            final FilmLikes filmLikes = new FilmLikes();
            filmLikes.setId(rs.getLong("id"));
            filmLikes.setFilmId(rs.getLong("film_id"));
            filmLikes.setUserId(rs.getLong("user_id"));
            return filmLikes;
        }
}
