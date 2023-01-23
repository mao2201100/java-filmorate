package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Long duration;
    Set<Long> likes = new HashSet<>(); // хранит id пользователей поставивших лайк

    public Film(String name, String description, LocalDate releaseDate, Long duration){
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public void addLike(long id){
        likes.add(id);
    }

    public void removeLike(long id){
        likes.remove(id);
    }

    public Set getLikes(){
        return likes;
    }

}
