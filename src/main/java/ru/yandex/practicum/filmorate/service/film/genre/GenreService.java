package ru.yandex.practicum.filmorate.service.film.genre;

import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;

import java.util.List;

public interface GenreService {

    Genre readById(Long id);

    List<Genre> readAll();
    List<Genre> readByFilmId(Long filmId);
    void create(Genre genre, GenreFilm genreFilm);
    void update(Genre genre, GenreFilm genreFilm);
    void delete(GenreFilm genreFilm);

    void deleteGenreFilmByFilm(Long filmId);
}
