package ru.yandex.practicum.filmorate.storage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.servise.UserService;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service("InMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private  final Map<Long, User> users = new HashMap<>();
    private int sequenceId = 1;
    private UserValidation validation = new UserValidation();
    private UserService userService = new UserService();

    public Collection<User> getUsers() {
        validation.getUser();
        return users.values();
    }
    public User findById(long userId){ // получение пользователя по id
        validation.containsUser(userId, users);
        return users.get(userId);
    }

    public User create(User user) {  // создание пользователя
        validation.create(user);
        if (user.getName() == null || user.getName() == "") {
            user.setName(user.getLogin());
            user.setId(sequenceId);
            users.put(user.getId(), user);
        }else {
            user.setId(sequenceId);
            users.put(user.getId(), user);

        }
        sequenceId += 1;

        log.info("Добавлен пользователь id: " + user.getId() + " логин: " + user.getLogin());
        return user;
    }

    public User update(User user) {  // изменение пользователя
        validation.create(user);
        validation.searchUser(user, users);
        users.put(user.getId(), user);
        log.info("Изменен пользователь id:" + user.getId() + " логин: " + user.getLogin());
        return user;
    }

    public void putFriend(long userId, long friendId){ // добавление в друзья
        if (validation.containsUserAndFriend(userId, friendId, users)){
            users.get(userId).addFriends(friendId);
            users.get(friendId).addFriends(userId);
        }
    }

    public void delFriend(long userId, long friendId) { // удаляем друга
        if(validation.containsUserAndFriend(userId, friendId, users) && validation.containsFriendsUser(userId, friendId, users)){
            users.get(userId).removeFriends(friendId);
            users.get(friendId).removeFriends(userId);
        }
    }

    public Collection<User> getFriendsList(long userId){ //возвращаем список пользователей, являющихся его друзьями по его id
       validation.friendsUser(userId, users);
        return userService.getFriendsUser(userId, users);
    }

    public Collection<User> friendsCommonOtherId(long userId, long otherId){ //список друзей, общих с другим пользователем.
        validation.containsUserAndFriend(userId, otherId, users); //проверяем что такие пользователи существуют
        return userService.friendsCommonOtherId(userId, otherId, users);

    }
}
