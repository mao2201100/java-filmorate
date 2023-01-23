package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.servise.FilmService;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Long, Film> films = new HashMap<>(); // хранит фильмы
    private int sequenceId = 1;
    private FilmValidation validation = new FilmValidation();
    private FilmService filmService = new FilmService();


    public Collection<Film> getFilms(){//получение списка всех пользователей.
        validation.getFilms();
        return films.values();
    }

    public Film findById(long filmId){ // получение фильма по id
        validation.containsFilm(filmId, films);
        return films.get(filmId);
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
        validation.containsFilm(film.getId(), films);
        films.put(film.getId(), film);
        log.info("Изменен фильм id:" +film.getId());
        return film;
    }

    public Film addLike(long filmId, long userId ){ // добавление лайка
        validation.containsFilm(filmId, films);
        validation.userBeadId(userId);
        update(filmService.addLike(filmId, userId, films));
        return films.get(filmId);
    }

    public Film removeLike(long filmId, long userId){ //пользователь удаляет лайк
        validation.userBeadId(userId);
        validation.containsFilm(filmId, films);
        update(filmService.removeLike(filmId, userId, films));
        return films.get(filmId);
    }

    public Collection<Film> popularFilms(long count){
        return filmService.popularFilms(count, films).values();

    }
}
