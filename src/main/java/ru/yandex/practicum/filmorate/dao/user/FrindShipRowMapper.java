package ru.yandex.practicum.filmorate.dao.user;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.user.FriendShip;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FrindShipRowMapper implements  RowMapper<FriendShip>{

        @Override
        public FriendShip mapRow(ResultSet rs, int rowNum) throws SQLException {
            final FriendShip friendShip = new FriendShip();
            friendShip.setId(rs.getLong("id"));
            friendShip.setUserOwnerId(rs.getLong("user_owner_id"));
            friendShip.setFriendId(rs.getLong("friend_id"));
            friendShip.setFriendShipStatus(rs.getString("FriendShipStatus"));
            return friendShip;
        }
}
