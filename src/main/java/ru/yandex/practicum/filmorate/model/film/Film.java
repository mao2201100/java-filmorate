package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Film {

    private Long id;
    private String description;

    private LocalDate releaseDate;
    private Long duration;
    private String name;
    private Long mpaId;
    private MPA mpa;
    private List<Genre> genres;
    public Film(String name, String description, LocalDate releaseDate, Long duration){
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film() {

    }

}
