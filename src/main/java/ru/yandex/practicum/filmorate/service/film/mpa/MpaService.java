package ru.yandex.practicum.filmorate.service.film.mpa;

import ru.yandex.practicum.filmorate.model.film.MPA;

import java.util.List;

public interface MpaService {

    List<MPA> readAll();

    MPA readById(Long id);

    void create(MPA mpa);

    void update(Long filmId, MPA mpa);
}
