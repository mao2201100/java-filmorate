package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage {

    private final Map<Long, Film> films = new HashMap<>(); // хранит фильмы
    private static Long sequenceId = 1L;


    public Collection<Film> getFilms() {//получение списка всех пользователей.
        return films.values();
    }

    public Film create(Film film) {
        film.setId(sequenceId);
        films.put(film.getId(), film);
        sequenceId += 1;
        log.info("Добавлен фильм id: " + film.getId());
        return film;
    }


    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

}
