package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.user.FriendShip;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.dao.user.FriendShipStorage;
import ru.yandex.practicum.filmorate.dao.user.UserStorageImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmoRateApplicationTestsDAO {
    private final UserStorageImpl userStorage;
    private final FriendShipStorage friendShipStorage;
    @Test
    public void testReadAllUsers() {

        List<User> users = userStorage.readAll();

        assertFalse(users.isEmpty());
    }

    @Test
    public void testCreateById(){
        User user = new User();
        user.setId(3);
        user.setEmail("rrr");
        user.setLogin("qqq");
        user.setBirthday(LocalDate.now());
        userStorage.create(user);
        assertEquals(user.getId(), userStorage.readById(3L).getId());
    }

    @Test
    public void testCreateFriendShip(){
        FriendShip friendShip = new FriendShip();
        friendShip.setId(1L);
        friendShip.setFriendId(2L);
        friendShip.setUserOwnerId(2L);
        friendShipStorage.create(friendShip);
        assertEquals(friendShip.getId(), friendShipStorage.readById(1L).getId());
    }


}