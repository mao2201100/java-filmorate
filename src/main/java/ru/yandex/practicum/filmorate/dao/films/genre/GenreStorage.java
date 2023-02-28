package ru.yandex.practicum.filmorate.dao.films.genre;

import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;

import java.util.List;

public interface GenreStorage {

    List<Genre> readByFilmId(Long filmId);
    void create(Genre genre);

    void create(GenreFilm genreFilm);

    Genre readById(Long id);

    List<Genre> readAll();

    GenreFilm readById(Long genreId, Long filmId);

    void delete(GenreFilm genreFilm);

    void deleteGenreFilmByFilm(Long filmId);
}
