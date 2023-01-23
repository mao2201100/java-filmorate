package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    public Collection<Film> getFilms(); // показать все фильмы
    public Film create(Film film); // добавить фильм
    public Film update(Film film); // изменить фильм
    public Film findById(long filmId); // найти фильм по id
    public Film addLike(long filmId, long userId ); // добавление лайка
    public Film removeLike(long filmId, long userId); //пользователь удаляет лайк
    public Collection<Film> popularFilms(long count); //возвращает список из первых count фильмов по количеству лайков.
    // Если значение параметра count не задано, верните первые 10

}
