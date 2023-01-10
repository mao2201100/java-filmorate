package ru.yandex.practicum.filmorate.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
@Slf4j
public class UserValidation {
  //  private final static Logger log = LoggerFactory.getLogger(UserValidation.class);

    public void getUser() {
        log.info("Получен GET запрос.");
    }


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

    public User put(User user) {
        create(user);
        return user;
    }
}
