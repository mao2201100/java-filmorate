package ru.yandex.practicum.filmorate.dao.films.likes;

import ru.yandex.practicum.filmorate.model.film.FilmLikes;

import java.util.List;

public interface FilmLikesStorage {
    List<FilmLikes> readAll();

    FilmLikes findById(long id);

    List<FilmLikes> findByFilmId(long filmId);
    void create(FilmLikes likes);

    void update(FilmLikes likes);

    void delete(long filmId, long userId);
}
