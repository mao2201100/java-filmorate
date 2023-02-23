package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;

public interface UserService {

    User findUserById(Long id);
    User findUserByLogin(String login);
    User create(User user); // добавить пользователя
    Collection<User> getUsers(); // показать всех пользователей
    User update(User user); // изменить пользователя
   // User findById(long user); // найти юзера по id
    void addFriend(long userId, long friendId); //добавление в друзья.
    void deleteFriend(long userId, long friendsId ); // удаление из друзей
    Collection<User> getFriendsList(long userId); //возвращаем список пользователей, являющихся его друзьями.

    Collection<User> fetchUsersByIds(Collection<Long> ids);
    Collection<User> friendsCommonOtherId(long userId, long otherId); //список друзей, общих с другим пользователем.
}
