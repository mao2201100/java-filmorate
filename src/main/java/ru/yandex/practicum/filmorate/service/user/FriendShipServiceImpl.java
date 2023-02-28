package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.user.FriendShipStorage;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.FriendShip;

import java.util.Collection;
import java.util.List;

@Service
public class FriendShipServiceImpl implements FriendShipService {
    private FriendShipStorage storage;
    private UserService userService;

    @Override
    public List<FriendShip> readAll() {
        return storage.readAll();
    }

    @Override
    public Collection<Long> getFriendsList(long userId) {
        return storage.getFriendsList(userId);
    }

    @Override
    public FriendShip readById(Long id) {
        return storage.readById(id);
    }

    @Override
    public void deleteFriends(long userId, long friendId) {
        storage.deleteFriends(userId, friendId);
    }

    @Override
    public void create(FriendShip friendShip) {
        final var us1 = userService.findUserById(friendShip.getFriendId());
        final var us2 = userService.findUserById(friendShip.getUserOwnerId());
        if (us1 == null || us2 == null) {
            throw new NotFoundException("Пользователь не найден.");
        }
        storage.create(friendShip);
    }



    @Override
    public void update(FriendShip friendShip) {
        storage.update(friendShip);
    }

    @Autowired
    public void setStorage(FriendShipStorage storage) {
        this.storage = storage;
    }

    @Autowired
    public void setUserService(@Lazy UserService userService) {
        this.userService = userService;
    }
}
