package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;

import java.time.LocalDate;


@Data
public class User {

    private long id;
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

    public User() {

    }

}
