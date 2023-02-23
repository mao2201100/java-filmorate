package ru.yandex.practicum.filmorate.dao.films;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;

@Repository
public class FilmStorageImpl implements FilmStorage {
    private JdbcTemplate template;

    @Override
    public List<Film> readAll() {
        return template.query("SELECT * from \"films\"", new FilmRowMapper());
    }

    @Override
    public void create(Film film) {
        template.update(
                "INSERT INTO \"films\" (\"id\", \"description\", \"releaseDate\", \"duration\", \"MPA_id\", \"name\") VALUES (?, ?, ?, ?, ?, ?)",
                film.getId(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpaId(), film.getName());
    }

    @Override
    public void update(Film film) {
        template.update("UPDATE \"films\" SET \"releaseDate\" = ?,  \"description\" = ?, \"duration\" = ?,  \"MPA_id\" = ?, \"name\" = ? where \"id\" = ?",
                film.getReleaseDate(), film.getDescription(), film.getDuration(), film.getMpaId(), film.getName(), film.getId());
    }

    @Override
    public Film findById(long filmId) {
        try {
            return template.queryForObject("SELECT * from \"films\" where \"id\"=?", new FilmRowMapper(), filmId);
        } catch (Exception e) {
            return null;
        }
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

}
