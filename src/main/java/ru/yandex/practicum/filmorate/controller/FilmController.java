package ru.yandex.practicum.filmorate.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.servise.FilmServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmServiceImpl service;
//@Autowired
    public FilmController(FilmServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public Collection<Film> readAll() { //получение списка всех фильмов.
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


    @GetMapping("/{id}")
    public Film findById(@PathVariable int id) {
        return service.findById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable long id, @PathVariable long userId){
        return service.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable long id, @PathVariable long userId){
        return service.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> popularFilms(@RequestParam (required = false) Long count){
            return service.popularFilms(count);
    }


    }
