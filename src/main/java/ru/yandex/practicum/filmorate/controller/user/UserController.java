package ru.yandex.practicum.filmorate.controller.user;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.service.user.UserServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    public UserController(UserServiceImpl service) {
        this.service = service;
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
    public User create(@RequestBody User user) {
        return service.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return service.update(user);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Long id){ // получить юзера по id
        return service.findUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}") // добавление в друзья.
    public void putFriend(@PathVariable("id") Long id, @PathVariable("friendId") Long friendId){
        service.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}") // удаление из друзей
    public void deleteFriends(@PathVariable Long id, @PathVariable Long friendId){
        service.deleteFriend(id, friendId);
    }


}
