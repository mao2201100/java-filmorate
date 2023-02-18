package ru.yandex.practicum.filmorate.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.servise.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping
    public Collection<User> readAll() {  //получение списка всех пользователей.
        return service.getUsers();
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriendsList(@PathVariable Long id){ // возвращаем список пользователя по его id
        return service.getFriendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getFriendsOtherId(@PathVariable Long id, @PathVariable Long otherId){
        return service.friendsCommonOtherId(id, otherId);
    }

    @PostMapping
    public User create(@Validated @RequestBody User user) {
        return service.create(user);
    }

    @PutMapping
    public User update(@Validated @RequestBody User user) {
        return service.update(user);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable long id){ // получить юзера по id
        return service.findById(id);
    }

    @PutMapping("/{id}/friends/{friendId}") // добавление в друзья.
    public void putFriend(@PathVariable("id") Long id, @PathVariable("friendId") Long friendId){
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}") // удаление из друзей
    public void delFriends(@PathVariable Long id, @PathVariable Long friendId){
        service.delFriend(id,friendId);
    }


}
