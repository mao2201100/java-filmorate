package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
public class FilmValidation {


    public void getFilms() {  //получение списка всех пользователей.
        log.info("Получен GET запрос.");
    }

    public Film create(Film film) {

        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Ошибка валидации: название не может быть пустым");
            throw new ValidationException("название не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.warn("Ошибка валидации: максимальная длина описания — 200 символов");
            throw new ValidationException("максимальная длина описания — 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Ошибка валидации: дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }

        if (film.getDuration() < 0) {
            log.warn("Ошибка валидации: продолжительность фильма должна быть положительной");
            throw new ValidationException("продолжительность фильма должна быть положительной");
        }
        return film;
    }

    public Film putFilm(Film film) {
        create(film);
        return film;
    }

    public Boolean containsFilm(long filmId, Map films){ //проверка существования фильма
        if (!films.containsKey(filmId)){
            log.warn("Ошибка валидации: фильм не найден.");
            throw new NotFoundException("Фильм не найден.");
        }
        return true;
    }

    public Boolean userBeadId(long userId){
        if(userId <= 0){
            log.warn("Ошибка валидации: Id пользователя не может быть отрицательным");
            throw new NotFoundException("Id пользователя не может быть отрицательным");
        }
        return true;
    }

}