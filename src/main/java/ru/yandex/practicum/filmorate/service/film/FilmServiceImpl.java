package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.films.FilmStorage;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.FilmLikes;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;
import ru.yandex.practicum.filmorate.service.film.genre.GenreService;
import ru.yandex.practicum.filmorate.service.film.likes.FilmLikesService;
import ru.yandex.practicum.filmorate.service.film.mpa.MpaService;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService { //отвечает за операции с фильмами

    private static Long ID_SEQ = 1L;
    private static Long LIKE_ID_SEQ = 1L;

    private FilmLikesService likesService;
    private FilmStorage storage;
    private MpaService mpaService;
    private GenreService genreService;
    private FilmValidation validation;


    public FilmServiceImpl(GenreService genreService, MpaService mpaService, FilmLikesService likesService, FilmStorage storage, FilmValidation filmValidation) {
        this.mpaService = mpaService;
        this.likesService = likesService;
        this.storage = storage;
        this.validation = filmValidation;
        this.genreService = genreService;
    }

    public Map<Long, Film> films() { // возвращает мапу с фильмами
        final Map<Long, Film> films = new HashMap<>();
        getFilms().stream().forEach(x -> films.put(x.getId(), x));
        return films;
    }

    @Override
    public Collection<Film> getFilms() { // показать все фильмы
        log.info("Получен GET запрос.");
        var result = storage.readAll();
        result.forEach(x-> {
            x.setMpa(mpaService.readById(x.getMpaId()));
            x.setGenres(genreService.readByFilmId(x.getId()));
        });
        return result;
    }

    @Override
    public Film create(Film film) { // добавить фильм
        validation.create(film);
        film.setId(ID_SEQ);
        film.setMpaId(film.getMpa().getId());
        if (film.getMpa() != null) {
            film.setMpaId(film.getMpa().getId());
        }
        storage.create(film);
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            film.getGenres().forEach(x-> {
                var genreFilm= new GenreFilm();
                genreFilm.setFilmId(film.getId());
                genreFilm.setGenreId(x.getId());
                genreService.create(x, genreFilm);
            });
        }
        ID_SEQ +=1;
        log.info("Добавлен новый фильм");
        return film;
    }

    @Override
    public Film update(Film film) { // изменить фильм
        validation.putFilm(film);
        containsFilm(film.getId(), films());
        if (film.getMpa() != null) {
            film.setMpaId(film.getMpa().getId());
        }
        genreService.deleteGenreFilmByFilm(film.getId());
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            Map<Long, Genre> buff = new HashMap<>(film.getGenres().size());
            film.getGenres().forEach(x-> {
                if (buff.get(x.getId()) == null) {
                    var genreFilm = new GenreFilm();
                    genreFilm.setFilmId(film.getId());
                    genreFilm.setGenreId(x.getId());
                    genreService.create(x, genreFilm);
                    buff.put(x.getId(), x);
                }
            });
        }
        storage.update(film);
        log.info("Изменен фильм id:" + film.getId());
        return findById(film.getId());
    }

    @Override
    public Film findById(long filmId) { // найти фильм по id
        log.info("Получен GET запрос.");
        containsFilm(filmId, films());
        var film = storage.findById(filmId);
        film.setMpa(mpaService.readById(film.getMpaId()));
        film.setGenres(genreService.readByFilmId(film.getId()));
        return film;
    }

    @Override
    public Film addLike(long filmId, long userId) // добавление лайка
    {
        containsFilm(filmId, films());
        validation.userBeadId(userId);
        FilmLikes likes = new FilmLikes();
        likes.setId(LIKE_ID_SEQ);
        likes.setFilmId(filmId);
        likes.setUserId(userId);
        likesService.create(likes);
        ID_SEQ +=1;
        return films().get(filmId);
    }

    @Override
    public Film removeLike(long filmId, long userId) { //пользователь удаляет лайк
        validation.userBeadId(userId);
        containsFilm(filmId, films());
        likesService.delete(filmId, userId);
        return findById(filmId);
    }

    @Override
    public Collection<Film> popularFilms(Long count) //возвращает список из первых count фильмов по количеству лайков.
    {
        final Map<Long, Film> popularFilm = new HashMap<>();
        final Map<Long, List<FilmLikes>> likesByFilm = new HashMap<>();
        if (count != null) {
            films().values().forEach(x-> {
                likesByFilm.put(x.getId(), likesService.findByFilmId(x.getId()));
            });
            films().values().stream().sorted((o1, o2) -> {
                return fetchSize(likesByFilm.get(o2.getId())) - fetchSize(likesByFilm.get(o1.getId()));
            }).limit(count).collect(Collectors.toList()).stream().forEach(x -> popularFilm.put(x.getId(), x));
            return popularFilm.values();
        } else {
            count = Long.valueOf(10);
            films().values().stream().sorted((o1, o2) -> {
                return fetchSize(likesByFilm.get(o2.getId()))  - fetchSize(likesByFilm.get(o1.getId()));
            }).limit(count).collect(Collectors.toList()).stream().forEach(x -> popularFilm.put(x.getId(), x));
            return popularFilm.values();
        }
    }

    public int fetchSize(List list) {
        return list != null ? list.size() : 0;
    }

    public void containsFilm(long filmId, Map films){ //проверка существования фильма
        if (!films.containsKey(filmId)){
            log.warn("Ошибка : фильм не найден.");
            throw new NotFoundException("Фильм не найден.");
        }
    }

}
