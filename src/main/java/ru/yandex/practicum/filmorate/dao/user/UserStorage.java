package ru.yandex.practicum.filmorate.dao.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {

    User findUserByLogin(String login) ;

    List<User> readAll();

    User readById(Long userId); // найти юзера по id

    void create(User user);

    void update(User user); // изменить пользователя
    void addFriend(long userId, long friendId); //добавление в друзья.
    void deleteFriend(long userId, long friendsId ); // удаление из друзей
    List<User> friendsCommonOtherId(long userId, long otherId); //список друзей, общих с другим пользователем.

    Collection<User> fetchUsersByIds(Collection<Long> ids);
}
