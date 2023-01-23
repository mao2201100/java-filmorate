package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    public Collection<User> getUsers(); // показать всех пользователей
    public User create(User user); // добавить пользователя
    public User update(User user); // изменить пользователя
    public User findById(long user); // найти юзера по id
    public void putFriend(long userId, long friendId); //добавление в друзья.
    public void delFriend(long userId, long friendsId ); // удаление из друзей
    public Collection<User> getFriendsList(long userId); //возвращаем список пользователей, являющихся его друзьями.
    public Collection<User> friendsCommonOtherId(long userId, long otherId); //список друзей, общих с другим пользователем.

}
