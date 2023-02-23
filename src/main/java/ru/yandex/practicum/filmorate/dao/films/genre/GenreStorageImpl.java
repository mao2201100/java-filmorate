package ru.yandex.practicum.filmorate.dao.films.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;

import java.util.List;

@Repository
public class GenreStorageImpl implements GenreStorage {
    private JdbcTemplate template;

    @Override
    public List<Genre> readByFilmId(Long filmId) {
        return template.query("SELECT * FROM \"genre\" where \"id\" in (SELECT \"genre_id\" FROM \"genre_films\" where \"films_id\" = ?)", new GenreRowMapper(), filmId);
    }

    @Override
    public void create(Genre genre) {
        template.update("INSERT INTO \"genre\" (\"id\", \"name\") values(?, ?)",
                genre.getId(), genre.getName());
    }

    @Override
    public void create(GenreFilm genreFilm) {
        template.update("INSERT INTO \"genre_films\" (\"genre_id\", \"films_id\") values(?, ?)",
                genreFilm.getGenreId(), genreFilm.getFilmId());
    }

    @Override
    public Genre readById(Long id) {
        try {
            return template.queryForObject("SELECT * FROM \"genre\" where \"id\" = ?", new GenreRowMapper(), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Genre> readAll() {
        try {
            return template.query("SELECT * FROM \"genre\"", new GenreRowMapper());
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public GenreFilm readById(Long genreId, Long filmId) {
        try {
            return template.queryForObject("SELECT * FROM \"genre_films\" where \"genre_id\" = ? and \"films_id\" = ?", new GenreFilmRowMapper(), genreId, filmId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(GenreFilm genreFilm) {
        template.update("DELETE FROM \"genre_films\" where \"genre_id\" =? and \"films_id\" = ?",
                genreFilm.getGenreId(), genreFilm.getFilmId());
    }

    @Override
    public void deleteGenreFilmByFilm(Long filmId) {
        template.update("DELETE FROM \"genre_films\" where \"films_id\" = ?", filmId);
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

}
