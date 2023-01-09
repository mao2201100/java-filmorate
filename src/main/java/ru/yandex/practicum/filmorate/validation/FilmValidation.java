package ru.yandex.practicum.filmorate.validation;

import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;

public class FilmValidation {
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(FilmController.class);

    public void getFilms(){  //получение списка всех пользователей.
        log.info("Получен GET запрос.");
    }

    public Film create(Film film){

        if(film.getName() == null || film.getName().isBlank()) {
            log.warn("Ошибка валидации: название не может быть пустым");
            throw new ValidationException("название не может быть пустым");
        }
        if(film.getDescription().length() > 200 ) {
            log.warn("Ошибка валидации: максимальная длина описания — 200 символов");
            throw new ValidationException("максимальная длина описания — 200 символов");
        }

        if(film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            log.warn("Ошибка валидации: дата релиза — не раньше 28 декабря 1895 года");
            throw  new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }

        if(film.getDuration() < 0){
            log.warn("Ошибка валидации: продолжительность фильма должна быть положительной");
            throw  new ValidationException("продолжительность фильма должна быть положительной");
        }
        return film;
    }

    @PutMapping
    public Film putFilm(Film film){
        create(film);
        return film;
    }

}
