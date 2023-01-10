package ru.yandex.practicum.filmorate.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService service;

    public FilmController() {
        this.service = new FilmService();
    }

    @GetMapping
    public Collection<Film> readAll() {  //получение списка всех пользователей.
        return service.getFilms();
    }

    @PostMapping
    public Film create(@Validated @RequestBody Film film) {
        return service.create(film);
    }

    @PutMapping
    public Film update(@Validated @RequestBody Film film) {
        return service.update(film);
    }
}
