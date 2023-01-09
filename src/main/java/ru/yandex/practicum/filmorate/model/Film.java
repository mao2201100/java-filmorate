package ru.yandex.practicum.filmorate.model;

import java.time.Duration;
import java.time.LocalDate;
import lombok.Data;
import lombok.ToString;

import java.util.Random;

@Data
@ToString
public class Film {

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;

    public Film(String name, String description, LocalDate releaseDate, Long duration){
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

}
