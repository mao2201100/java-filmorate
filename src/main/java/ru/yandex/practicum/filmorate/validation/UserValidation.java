package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@Component
public class UserValidation {

    public void getUser() {
        log.info("Получен GET запрос.");
    }

@ExceptionHandler
    public void create(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Валидация не пройдена: электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.warn("Валидация не пройдена: логин не может быть пустым и содержать пробелы ");
            throw new ValidationException("логин не может быть пустым и содержать пробелы ");
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Валидация не пройдена: дата рождения не может быть в будущем");
            throw new ValidationException("дата рождения не может быть в будущем");
        }

    }

    public User searchUser(User user, Map users) {
        if (users.get(user.getId()) == null) {
            log.warn("Ошибка валидации: пользователь не найден.");
            throw new NotFoundException("Пользователь не найден.");
        }
        return user;
    }

    public Boolean containsUser(long userId, Map users){ //проверка существования пользователя
        if (users.get(userId) == null || !users.containsKey(userId)){
            log.warn("Ошибка валидации: пользователь не найден.");
            throw new NotFoundException("Пользователь не найден.");
        }
        return true;
    }

    public Boolean containsUserAndFriend(long userId, long frendId, Map users){ // проверка существования id пользователя и его друга
        if (users.containsKey(userId) && users.containsKey(frendId)) {
            return true;
        } else if (!users.containsKey(userId)){
            log.warn("пользователя с id: " + userId + "не существует");
            throw new NotFoundException("пользователя с id: " + userId + "не существует");
        }else if (!users.containsKey(frendId)){
            log.warn("пользователя с id: " + frendId + "не существует");
            throw new NotFoundException("пользователя с id: " + frendId + "не существует");
        }
        return false;
    }

    public Boolean containsFriendsUser(long userId, long friendId, Map users){ // проверка друзей пользователя
        User user1 = (User)users.get(userId);
        User user2 = (User)users.get(friendId);
        if(!user1.getFriends(friendId).isEmpty() && !user2.getFriends(userId).isEmpty()){
            return true;
        }
        log.warn("пользователя с id: " + userId + " и id " + friendId + " не друзья");
        throw new ValidationException("пользователя с id: " + userId + " и id " + friendId + " не друзья");
    }

    public Boolean friendsUser(long userId, Map users){ // проверка наличия друзей у пользователя
        User user = (User)users.get(userId);
        if (user.getFriends().isEmpty()){
            log.warn("у пользователя с id: " + userId + " нет друзей");
            throw new ValidationException("у пользователя с id: " + userId + " нет друзей");
        }
        return true;
    }
}
