package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserStorage service;
    public UserController(@Qualifier("InMemoryUserStorage") UserStorage userStorage) {
        this.service = userStorage;
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
        service.putFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}") // удаление из друзей
    public void delFriends(@PathVariable Long id, @PathVariable Long friendId){
        service.delFriend(id,friendId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        return Map.of(
                "error", "Что-то пошло не так",
                "errorMessage", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final NotFoundException e) {
        return Map.of(
                "error", "Что-то пошло не так",
                "errorMessage", e.getMessage()
        );
    }
}
