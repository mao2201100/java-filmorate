package ru.yandex.practicum.filmorate.servise;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilmService { //отвечает за операции с фильмами

    public Film addLike(long filmId, long userId, Map films) { // добавление лайка
        Film film;
        film = (Film) films.get(filmId);
        film.addLike(userId);
        return film;
    }

    public Film removeLike(long filmId, long userId, Map films) { // удаление лайка
        Film film;
        film = (Film) films.get(filmId);
        film.removeLike(userId);
        return film;
    }

    public Map<Long, Film> popularFilms(long count, Map<Long, Film> films) { // топ популярных фильмов
        final Map<Long, Film> popularFilm = new HashMap<>();
        films.values().stream().sorted((o1, o2) -> {
            return o2.getLikes().size() -o1.getLikes().size();
        }).limit(count).collect(Collectors.toList()).stream().forEach(x-> popularFilm.put(x.getId(), x));
        return popularFilm;
        }
    }


    // 10 наиболее популярных фильмов по количеству лайков
    // Пусть пока каждый пользователь может поставитьлайк фильму только один раз


