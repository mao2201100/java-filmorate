package ru.yandex.practicum.filmorate.dao.films.mpa;

import ru.yandex.practicum.filmorate.model.film.MPA;

import java.util.List;

public interface MpaStorage {
    void create(MPA mpa);

    List<MPA> readAll();
    MPA readById(Long id);

    void delete(Long id);

}
