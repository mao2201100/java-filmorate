package ru.yandex.practicum.filmorate.servise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private InMemoryUserStorage inMemoryUserStorage;
    private UserValidation validation;


    @Autowired
    public void setInMemoryUserStorage(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @Autowired
    public void setValidation(UserValidation validation) {
        this.validation = validation;
    }

    @Override
    public User create(User user) {
        validation.create(user);
        User newUser = inMemoryUserStorage.create(user);
        log.info("Добавлен новый пользователь id: " + newUser.getId());
        return newUser;
    }

    @Override
    public Collection<User> getUsers() { // показать всех пользователей
        log.info("Получен GET запрос.");
        return inMemoryUserStorage.getUsers();
    }

    public Map<Long, User> users() { // возвращает мапу со списком пользователей
        final Map<Long, User> users = new HashMap<>();
        getUsers().stream().forEach(x -> users.put(x.getId(), x));
        return users;
    }

    @Override
    public User update(User user) { // изменить пользователя
        validation.create(user);
        searchUser(user, users());
        inMemoryUserStorage.update(user);
        log.info("Изменен пользователь id:" + user.getId() + " логин: " + user.getLogin());
        return user;
    }

    @Override
    public User findById(long userId) { // найти юзера по id
        log.info("Получен GET запрос.");
        validation.containsUser(userId, users());
        return users().get(userId);
    }

    @Override
    public void addFriend(long userId, long friendId) { //добавление в друзья.
        if (validation.containsUserAndFriend(userId, friendId, users())) {
            Map<Long, User> map = new HashMap<>();
            map = users();
            map.get(userId).addFriends(friendId);
            update(map.get(userId));
            map.get(friendId).addFriends(userId);
            update(map.get(friendId));
        }
    }

    @Override
    public void delFriend(long userId, long friendId) { // удаление из друзей
        if (validation.containsUserAndFriend(userId, friendId, users()) && validation.containsFriendsUser(userId, friendId, users())) {
            Map<Long, User> map = new HashMap<>();
            map = users();
            map.get(userId).removeFriends(friendId);
            update(map.get(userId));
            map.get(friendId).removeFriends(userId);
        }
    }

    @Override
    public Collection<User> getFriendsList(long userId) { //возвращаем список пользователей, являющихся его друзьями.
        validation.friendsUser(userId, users());
        Set<Long> set = new HashSet<>();
        set = users().get(userId).getFriends();
        Iterator<Long> id = set.iterator();
        Map<Long, User> friendsUser = new HashMap<>();
        while (id.hasNext()) {
            long i = id.next();
            friendsUser.put(i, users().get(i));
        }
        return friendsUser.values();
    }

    @Override
    public Collection<User> friendsCommonOtherId(long userId, long otherId) { //список друзей, общих с другим пользователем.
        User user1 = users().get(userId);
        User user2 = users().get(otherId);
        ArrayList<Long> use1FriendsList = new ArrayList<>();
        ArrayList<Long> use2FriendsList = new ArrayList<>();
        ArrayList<Long> mutualFriendsList = new ArrayList<>();
        use1FriendsList.addAll(user1.getFriends());
        use2FriendsList.addAll(user2.getFriends());

        for (int i = 0; i < use1FriendsList.size(); i++) {
            if (use2FriendsList.contains(use1FriendsList.get(i))) {
                mutualFriendsList.add(use1FriendsList.get(i));
            }
        }
        Iterator<Long> idUsers = mutualFriendsList.iterator();
        Map<Long, User> mutualFriendsUsers = new HashMap<>();
        while (idUsers.hasNext()) {
            long i = idUsers.next();
            mutualFriendsUsers.put(i, (User) users().get(i));
        }

        return mutualFriendsUsers.values();
    }

    public void searchUser(User user, Map users) {
        if (users.get(user.getId()) == null) {
            log.warn("Ошибка валидации: пользователь не найден.");
            throw new NotFoundException("Пользователь не найден.");
        }
    }
}
