package ru.yandex.practicum.filmorate.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.user.FriendShip;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FriendShipStorageImpl implements FriendShipStorage {
    private JdbcTemplate template;

    @Override
    public List<FriendShip> readAll() {
        return template.query("SELECT * FROM \"friend_ship\"", new FrindShipRowMapper());
    }

    @Override
    public FriendShip readById(Long id) {
        return template.queryForObject("SELECT * FROM \"friend_ship\" WHERE \"id\" = ? ",
                new FrindShipRowMapper(), new Object[]{id});
    }

    @Override
    public void create(FriendShip friendShip) {
        template.update("INSERT INTO \"friend_ship\" (\"user_owner_id\",\"friend_id\",\"FriendshipStatus\") VALUES (?,?,?)"
                , new Object[]{friendShip.getUserOwnerId(), friendShip.getFriendId(), friendShip.getFriendShipStatus()
                        });

    }

    @Override
    public void update(FriendShip friendShip) {
        template.update("UPDATE \"friend_ship\" SET \"user_owner_id\"=?, \"friend_id\"=?,\"FriendshipStatus\"=? where \"id\" = ?"
                , new Object[]{friendShip.getUserOwnerId(), friendShip.getFriendId(), friendShip.getFriendShipStatus(), friendShip.getId()});
    }

    @Override
    public void deleteFriends(long userId, long friendId){
        template.update("DELETE FROM \"friend_ship\"  where \"user_owner_id\"=? AND \"friend_id\"=?"
                , new Object[]{userId, friendId});

    }

    @Override
    public Collection<Long> getFriendsList(long userId) {
        return template.query("SELECT * FROM \"friend_ship\" WHERE \"user_owner_id\"=? ", new FrindShipRowMapper(),
                 new Object[]{userId}).stream().map(FriendShip::getFriendId).collect(Collectors.toList());
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

}
