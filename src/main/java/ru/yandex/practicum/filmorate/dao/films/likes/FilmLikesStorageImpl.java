package ru.yandex.practicum.filmorate.dao.films.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.FilmLikes;

import java.util.List;

@Repository
public class FilmLikesStorageImpl implements FilmLikesStorage {
    private JdbcTemplate template;

    @Override
    public List<FilmLikes> readAll() {
        try {
            return template.query("SELECT * from \"film_likes\"", new FilmLikesRowMapper());
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void create(FilmLikes filmLikes) {
        template.update("INSERT INTO \"film_likes\"(\"id\", \"film_id\", \"user_id\") VALUES (?, ?, ?)",
                filmLikes.getId(), filmLikes.getFilmId(), filmLikes.getUserId());
    }

    @Override
    public void update(FilmLikes filmLikes) {
        template.update("UPDATE \"film_likes\" SET \"film_id\" =?, \"user_id\" = ? where \"id\"=?",
                filmLikes.getFilmId(), filmLikes.getUserId(), filmLikes.getId());
    }

    @Override
    public void delete(long filmId, long userId) {
        template.update("DELETE FROM \"film_likes\" where \"film_id\"=? and \"user_id\"=?", filmId, userId);
    }

    @Override
    public FilmLikes findById(long id) {
        final var filmLikes = template.query("SELECT * from \"film_likes\" where \"id\"=?", new FilmLikesRowMapper(), id);

            return filmLikes.isEmpty() ? null : filmLikes.get(0);
    }

    @Override
    public List<FilmLikes> findByFilmId(long filmId) {
        try {
            return template.query("SELECT * from \"film_likes\" where \"film_id\"=?", new FilmLikesRowMapper(), filmId);
        } catch (Exception e) {
            return List.of();
        }
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

}
