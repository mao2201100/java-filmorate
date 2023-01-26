package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class InMemoryFilmStorage {

    private final Map<Long, Film> films = new HashMap<>(); // хранит фильмы
    private int sequenceId = 1;


    public Collection<Film> getFilms() {//получение списка всех пользователей.
        return films.values();
    }

    public Film create(Film film) {
        film.setId(sequenceId);
        films.put(film.getId(), film);
        sequenceId += 1;
        return film;
    }


    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

}
