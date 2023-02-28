package ru.yandex.practicum.filmorate.controller.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.MPA;
import ru.yandex.practicum.filmorate.service.film.mpa.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MPAController {
    private final MpaService mpaService;

    public MPAController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/{id}")
    public MPA readById(@PathVariable Long id) {
        var mpa = mpaService.readById(id);
        if (mpa.getId() == 1) {
            mpa.setName("G");
        }
        return mpa;
    }

    @GetMapping("")
    public List<MPA> readAll() {
        return mpaService.readAll();
    }
    
}
