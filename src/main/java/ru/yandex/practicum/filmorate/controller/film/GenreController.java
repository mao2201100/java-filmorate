package ru.yandex.practicum.filmorate.controller.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.service.film.genre.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private GenreService service;


    @GetMapping("/{id}")
    public Genre readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @GetMapping
    public List<Genre> readAll() {
        return service.readAll();
    }

    @Autowired
    public void setService(GenreService service) {
        this.service = service;
    }
}
