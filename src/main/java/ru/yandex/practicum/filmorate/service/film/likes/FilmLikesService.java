package ru.yandex.practicum.filmorate.service.film.likes;

import ru.yandex.practicum.filmorate.model.film.FilmLikes;

import java.util.List;

public interface FilmLikesService {
    List<FilmLikes> readAll();

    FilmLikes findById(long id);

    void create(FilmLikes likes);

    void update(FilmLikes likes);

    void delete(long filmId, long userId);

    List<FilmLikes> findByFilmId(long filmId);
}
