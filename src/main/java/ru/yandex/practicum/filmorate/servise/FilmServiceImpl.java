package ru.yandex.practicum.filmorate.servise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService { //отвечает за операции с фильмами

    private InMemoryFilmStorage inMemoryFilmStorage;
    private FilmValidation validation;


    public FilmServiceImpl(InMemoryFilmStorage inMemoryFilmStorage, FilmValidation filmValidation) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.validation = filmValidation;
    }

    public Map<Long, Film> films() { // возвращает мапу с фильмами
        final Map<Long, Film> films = new HashMap<>();
        getFilms().stream().forEach(x -> films.put(x.getId(), x));
        return films;
    }

    @Override
    public Collection<Film> getFilms() // показать все фильмы
    {
        log.info("Получен GET запрос.");
        return inMemoryFilmStorage.getFilms();
    }

    @Override
    public Film create(Film film) // добавить фильм
    {
        validation.create(film);
        Film newFilm = inMemoryFilmStorage.create(film);
        log.info("Добавлен новый фильм");
        return newFilm;
    }

    @Override
    public Film update(Film film) // изменить фильм
    {
        validation.putFilm(film);
        containsFilm(film.getId(), films());
        inMemoryFilmStorage.update(film);
        log.info("Изменен фильм id:" + film.getId());
        return film;
    }

    @Override
    public Film findById(long filmId) // найти фильм по id
    {
        log.info("Получен GET запрос.");
        containsFilm(filmId, films());
        return films().get(filmId);
    }

    @Override
    public Film addLike(long filmId, long userId) // добавление лайка
    {
        containsFilm(filmId, films());
        validation.userBeadId(userId);
        Map<Long, Film> map = new HashMap<>();
        map = films();
        map.get(filmId).addLike(userId);
        inMemoryFilmStorage.update(map.get(filmId));
        return films().get(filmId);
    }

    @Override
    public Film removeLike(long filmId, long userId) //пользователь удаляет лайк
    {
        validation.userBeadId(userId);
        containsFilm(filmId, films());
        Film film = films().get(filmId);
        film.removeLike(userId);
        inMemoryFilmStorage.update(film);
        return film;
    }

    @Override
    public Collection<Film> popularFilms(Long count) //возвращает список из первых count фильмов по количеству лайков.
    {
        if (count != null) {
            final Map<Long, Film> popularFilm = new HashMap<>();
            films().values().stream().sorted((o1, o2) -> {
                return o2.getLikes().size() - o1.getLikes().size();
            }).limit(count).collect(Collectors.toList()).stream().forEach(x -> popularFilm.put(x.getId(), x));
            return popularFilm.values();
        } else {
            count = Long.valueOf(10);
            final Map<Long, Film> popularFilm = new HashMap<>();
            films().values().stream().sorted((o1, o2) -> {
                return o2.getLikes().size() - o1.getLikes().size();
            }).limit(count).collect(Collectors.toList()).stream().forEach(x -> popularFilm.put(x.getId(), x));
            return popularFilm.values();
        }
    }

    public void containsFilm(long filmId, Map films){ //проверка существования фильма
        if (!films.containsKey(filmId)){
            log.warn("Ошибка : фильм не найден.");
            throw new NotFoundException("Фильм не найден.");
        }
    }
}
