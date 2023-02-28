package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.FriendShip;
import ru.yandex.practicum.filmorate.model.user.FriendShipStatus;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private FriendShipService friendShipService;
    private UserValidation validation;
    private UserStorage storage;


    @Override
    public User findUserById(Long id) {
        User user = storage.readById(id);
        searchUser(user);
        return user;
    }

    @Override
    public User findUserByLogin(String login) {
        return storage.findUserByLogin(login);
    }

    @Override
    public User create(User user) {
        validation.create(user);
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        storage.create(user);
        return findUserByLogin(user.getLogin());
    }

    @Override
    public Collection<User> getUsers() { // показать всех пользователей
        log.info("Получен GET запрос.");
        return storage.readAll();
    }

    @Override
    public User update(User user) { // изменить пользователя
        searchUser(user);
        storage.update(user);
        log.info("Изменен пользователь id:" + user.getId() + " логин: " + user.getLogin());
        return user;
    }

    @Override
    public void addFriend(long userId, long friendId) { //добавление в друзья.
        FriendShip friendShip = new FriendShip();
        friendShip.setUserOwnerId(userId);
        friendShip.setFriendId(friendId);
        friendShip.setFriendShipStatus(FriendShipStatus.NOT_CONFIRMED.name());
        friendShipService.create(friendShip);
    }

    @Override
    public void deleteFriend(long userId, long friendId) { // удаление из друзей
        friendShipService.deleteFriends(userId, friendId);
    }

    @Override
    public Collection<User> getFriendsList(long userId) { //возвращаем список пользователей, являющихся его друзьями.
        final Collection<Long> ids = friendShipService.getFriendsList(userId);
        return fetchUsersByIds(ids);
    }

    @Override
    public Collection<User> fetchUsersByIds(Collection<Long> ids) {
        if (!ids.isEmpty()) {
            return storage.fetchUsersByIds(ids);
        } else {
            return List.of();
        }
    }

    @Override
    public Collection<User> friendsCommonOtherId(long userId, long otherId) { //список друзей, общих с другим пользователем.
        ArrayList<Long> use1FriendsList = new ArrayList<>(getFriendsList(userId).stream().map(x -> x.getId()).collect(Collectors.toList()));
        ArrayList<Long> use2FriendsList = new ArrayList<>(getFriendsList(otherId).stream().map(x -> x.getId()).collect(Collectors.toList()));

        ArrayList<Long> mutualFriendsList = new ArrayList<>();

        for (int i = 0; i < use1FriendsList.size(); i++) {
            if (use2FriendsList.contains(use1FriendsList.get(i))) {
                mutualFriendsList.add(use1FriendsList.get(i));
            }
        }
        return fetchUsersByIds(mutualFriendsList);
    }

    public void searchUser(User user) {
        if (user == null || storage.readById(user.getId()) == null) {
            log.warn("Ошибка валидации: пользователь не найден.");
            throw new NotFoundException("Пользователь не найден.");
        }
    }

    @Autowired
    public void setFriendShipService(FriendShipService friendShipService) {
        this.friendShipService = friendShipService;
    }

    @Autowired
    public void setValidation(UserValidation validation) {
        this.validation = validation;
    }

    @Autowired
    public void setStorage(UserStorage storage) {
        this.storage = storage;
    }


}
