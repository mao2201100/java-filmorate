package ru.yandex.practicum.filmorate.dao.films;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;

public interface FilmStorage {

    List<Film> readAll();

    void create(Film film);

    void update(Film film);

    Film findById(long filmId);

}
