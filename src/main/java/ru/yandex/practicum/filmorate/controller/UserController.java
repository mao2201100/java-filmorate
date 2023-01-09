package ru.yandex.practicum.filmorate.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service = new UserService();

    @GetMapping
    public Collection<User> readAll() {  //получение списка всех пользователей.
        return service.getUsers();
    }

    @PostMapping
    public User create(@Validated @RequestBody User user) {
        return service.create(user);
    }

    @PutMapping
    public User update(@Validated @RequestBody User user) {
        return service.update(user);
    }
}
