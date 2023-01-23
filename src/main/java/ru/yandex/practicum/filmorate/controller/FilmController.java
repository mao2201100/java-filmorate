package ru.yandex.practicum.filmorate.controller;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmStorage service;

    @Autowired
    public FilmController() {
        this.service = new InMemoryFilmStorage();
    }

    @GetMapping
    public Collection<Film> readAll() {  //получение списка всех фильмов.
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
        if(count != null){
            return service.popularFilms(Long.valueOf(count));
        }else {
            return service.popularFilms(10);
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        return Map.of(
                "error", "Что-то пошло не так",
                "errorMessage", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final NotFoundException e) {
        return Map.of(
                "error", "Что-то пошло не так",
                "errorMessage", e.getMessage()
        );
    }
    }
