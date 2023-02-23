package ru.yandex.practicum.filmorate.dao.user;

import ru.yandex.practicum.filmorate.model.user.FriendShip;

import java.util.Collection;
import java.util.List;

public interface FriendShipStorage {

    List<FriendShip> readAll();

    FriendShip readById(Long id);

    void create(FriendShip friendShip);

    void update(FriendShip friendShip);

    void deleteFriends(long userId, long friendId);

    Collection<Long> getFriendsList(long userId);


}


