package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FilmService {

    private static Map<Integer, Film> films = new HashMap<>();
    private static int sequenceId = 1;
    private static FilmValidation validation = new FilmValidation();
    private final static Logger log = LoggerFactory.getLogger(FilmService.class);


    public Collection<Film> getFilms(){  //получение списка всех пользователей.
        validation.getFilms();
        return films.values();
    }


    public Film create(Film film){
        validation.create(film);
        film.setId(sequenceId);
        films.put(film.getId(), film);
        sequenceId += 1;
        log.info("Добавлен новый фильм");
        return film;
    }


    public Film update(Film film){
        validation.putFilm(film);
        if (films.get(film.getId()) == null){
            log.warn("Ошибка валидации: фильм не найден.");
            throw new ValidationException("Фильм не найден.");
        }
        films.put(film.getId(), film);
        log.info("Изменен фильм id:" +film.getId());
        return film;
    }


}
