package ru.yandex.practicum.filmorate.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final static Logger log = LoggerFactory.getLogger(UserService.class);
    private static final Map<Integer, User> users = new HashMap<>();
    private static int sequenceId = 1;
    private static UserValidation validation = new UserValidation();

    public Collection<User> getUsers() {
        validation.getUser();
        return users.values();
    }

    public User create(User user) {
        validation.create(user);
        if (user.getName() == null) {
            user.setName(user.getLogin());
            user.setId(sequenceId);
            users.put(user.getId(), user);
            sequenceId += 1;
            return user;
        }
        user.setId(sequenceId);
        users.put(user.getId(), user);
        sequenceId += 1;
        log.info("Добавлен пользователь id: " + user.getId() + " логин: " + user.getLogin());
        return user;
    }

    public User update(User user) {
        validation.put(user);
        if (users.get(user.getId()) == null) {
            log.warn("Ошибка валидации: пользователь не найден.");
            throw new ValidationException("Пользователь не найден.");
        }
        users.put(user.getId(), user);
        log.info("Изменен пользователь id:" + user.getId() + " логин: " + user.getLogin());
        return user;
    }
}
