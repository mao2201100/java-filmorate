package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;

@Data
public class FilmLikes {
    private Long id;
    private Long filmId;
    private Long userId;
}
