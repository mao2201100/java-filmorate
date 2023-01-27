package ru.yandex.practicum.filmorate.storage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private int sequenceId = 1;

    public Collection<User> getUsers() {
        return users.values();
    }

    public User create(User user) {  // создание пользователя
        if (user.getName() == null || user.getName() == "") {
            user.setName(user.getLogin());
            user.setId(sequenceId);
            users.put(user.getId(), user);
        } else {
            user.setId(sequenceId);
            users.put(user.getId(), user);
        }
        sequenceId += 1;
        log.info("Добавлен пользователь id: " + user.getId() + " логин: " + user.getLogin());
        return user;
    }

    public User update(User user) {  // изменение пользователя
        users.put(user.getId(), user);
        return user;
    }
}
