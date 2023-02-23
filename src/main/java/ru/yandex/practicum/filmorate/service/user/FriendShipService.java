package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.user.FriendShip;

import java.util.Collection;
import java.util.List;

public interface FriendShipService {
    List<FriendShip> readAll();

    Collection<Long> getFriendsList(long userId);

    FriendShip readById(Long id); // найти юзера по id

    void deleteFriends(long userId, long friendId);

    void create(FriendShip friendShip);

    void update(FriendShip friendShip); // изменить пользователя
}