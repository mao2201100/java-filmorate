package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.validation.FilmValidation;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmorateApplicationTests {
    private LocalDate birthday = LocalDate.of(1946, 8, 20);
    private LocalDate releaseDate = LocalDate.of(1967,3,25);
    String description = "Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать " +
            "господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время " +
            "«своего отсутствия», стал кандидатом Коломбани.";
    User userFileLogin = new User("mail@mail.ru", "dolore ullamco", "Nick Name", birthday);
    User userFileEmail = new User("mailmail.ru", "dolore", "Nick Name", birthday);
    User userFileBirthday = new User("mail@mail.ru", "dolore", "Nick Name", LocalDate.now().plusDays(1));
    UserValidation userValidation = new UserValidation();
    FilmValidation filmValidation = new FilmValidation();

    Film film = new Film("nisi eiusmod", "adipisicing",releaseDate, 100L);
    Film filmFileName = new Film("", "adipisicing",releaseDate, 100L);
    Film filmFileDescription = new Film("nisi eiusmod", description,releaseDate, 100L);
    Film filmFileRelise = new Film("nisi eiusmod", "adipisicing",
            LocalDate.of(1895, 12, 27), 100L);
    Film filmFileDuration = new Film("nisi eiusmod", "adipisicing",releaseDate, -1L);




    private ValidationException exceptionMassageUser(User user) {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        userValidation.create(user);
                    }
                });
        return exception;
    }

    private ValidationException exceptionMassageFilm(Film film) {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                new Executable() {
                    @Override
                    public void execute() {
                        filmValidation.create(film);
                    }
                });
        return exception;
    }


    @Test
    public void createUser() {

        assertEquals("электронная почта не может быть пустой и должна содержать символ @",
                exceptionMassageUser(userFileEmail).getMessage());

        assertEquals("логин не может быть пустым и содержать пробелы ",
                exceptionMassageUser(userFileLogin).getMessage());

        assertEquals("дата рождения не может быть в будущем",
                exceptionMassageUser(userFileBirthday).getMessage());


    }

    @Test
    public void createFilm(){
        assertEquals("название не может быть пустым",
                exceptionMassageFilm(filmFileName).getMessage());

        assertEquals("максимальная длина описания — 200 символов",
                exceptionMassageFilm(filmFileDescription).getMessage());

        assertEquals("дата релиза — не раньше 28 декабря 1895 года",
                exceptionMassageFilm(filmFileRelise).getMessage());

        assertEquals("продолжительность фильма должна быть положительной",
                exceptionMassageFilm(filmFileDuration).getMessage());

    }

}
