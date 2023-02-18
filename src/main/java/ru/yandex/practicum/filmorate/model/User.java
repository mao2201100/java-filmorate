package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class User {

    private long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>(); // хранит id пользователей добавленных в друзья
    private FriendShipStatus friendShipStatus = new FriendShipStatus();

    public User (String email, String login, String name, LocalDate birthday){
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void addFriends(long id) {
        friends.add(id);
    }

    public void removeFriends(long id){
        friends.remove(id);
    }

    public Set getFriends(long userId){
        return friends;
    }
}
