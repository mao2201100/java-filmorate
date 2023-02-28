package ru.yandex.practicum.filmorate.service.film.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.films.genre.GenreStorage;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.GenreFilm;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private GenreStorage storage;

    @Override
    public Genre readById(Long id) {
        var genre = storage.readById(id);
        if (genre == null) {
            throw new NotFoundException("Не найден ресурс");
        }
        return genre;
    }

    @Override
    public List<Genre> readAll() {
        return storage.readAll();
    }

    @Override
    public List<Genre> readByFilmId(Long filmId) {
        return storage.readByFilmId(filmId);
    }

    @Override
    public void create(Genre genre, GenreFilm genreFilm) {
        var created = storage.readById(genreFilm.getGenreId(), genreFilm.getFilmId());
        if (created == null) {
            storage.create(genreFilm);
        }
    }

    @Override
    public void update(Genre genre, GenreFilm genreFilm) {
        storage.create(genreFilm);
    }

    @Override
    public void delete(GenreFilm genreFilm) {
        storage.delete(genreFilm);
    }

    @Override
    public void deleteGenreFilmByFilm(Long filmId) {
        storage.deleteGenreFilmByFilm(filmId);
    }

    @Autowired
    public void setStorage(GenreStorage storage) {
        this.storage = storage;
    }

}
