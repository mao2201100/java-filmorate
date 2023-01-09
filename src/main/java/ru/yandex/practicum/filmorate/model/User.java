package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import lombok.Data;


@Data
public class User {

    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    public User (String email, String login, String name, LocalDate birthday){
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
