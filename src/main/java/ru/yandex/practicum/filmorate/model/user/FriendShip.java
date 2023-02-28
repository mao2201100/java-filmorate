package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;

@Data
public class FriendShip {
    private Long id;
    private Long userOwnerId;
    private Long friendId;
    private String friendShipStatus;
}
