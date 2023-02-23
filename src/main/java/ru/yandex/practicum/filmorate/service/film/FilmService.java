package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.Collection;

public interface FilmService {

    Collection<Film> getFilms(); // показать все фильмы
    Film create(Film film); // добавить фильм
    Film update(Film film); // изменить фильм
    Film findById(long filmId); // найти фильм по id
    Film addLike(long filmId, long userId ); // добавление лайка
    Film removeLike(long filmId, long userId); //пользователь удаляет лайк Collection<Film> popularFilms(long count); //возвращает список из первых count фильмов по количеству лайков.
    Collection<Film> popularFilms(Long count); //возвращает список из первых count фильмов по количеству лайков.// Если значение параметра count не задано, верните первые
}
