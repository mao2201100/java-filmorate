package ru.yandex.practicum.filmorate.service.film.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.films.likes.FilmLikesStorage;
import ru.yandex.practicum.filmorate.model.film.FilmLikes;

import java.util.List;

@Service
public class FilmLikesServiceImpl implements FilmLikesService {
    private FilmLikesStorage storage;

    @Override
    public List<FilmLikes> readAll() {
        return storage.readAll();
    }

    @Override
    public void create(FilmLikes likes) {
        storage.create(likes);
    }

    @Override
    public void update(FilmLikes likes) {
        storage.update(likes);
    }

    @Override
    public void delete(long filmId, long userId) {
        storage.delete(filmId, userId);
    }

    @Override
    public List<FilmLikes> findByFilmId(long filmId) {
        return storage.findByFilmId(filmId);
    }

    @Override
    public FilmLikes findById(long id) {
        return storage.findById(id);
    }


    @Autowired
    public void setStorage(FilmLikesStorage storage) {
        this.storage = storage;
    }

}
