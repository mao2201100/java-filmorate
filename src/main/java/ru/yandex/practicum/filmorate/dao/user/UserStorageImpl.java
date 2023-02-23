package ru.yandex.practicum.filmorate.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.user.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserStorageImpl implements UserStorage {

    private JdbcTemplate template;

    @Override
    public User findUserByLogin(String login) {
        return template.queryForObject("SELECT * FROM \"users\" WHERE \"login\" = ? ", new UserRowMapper()
                , new Object[]{login});}

    @Override
    public List<User> readAll() {
        return template.query("SELECT * FROM \"users\"", new UserRowMapper());
    }

    @Override
    public User readById(Long userId) {
        try {
            return template.queryForObject("SELECT * FROM \"users\" WHERE \"id\" = ? ", new UserRowMapper()
                    , new Object[]{userId});
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void create(User user) {
        template.update("INSERT INTO \"users\" (\"email\",\"login\",\"name\",\"birthday\") VALUES (?,?,?,?)"
                , user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
    }

    @Override
    public void update(User user) {
        template.update("UPDATE \"users\" SET \"email\"=?, \"login\"=?,\"name\"=?,\"birthday\"=? where \"id\" = ?"
                , new Object[]{user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId()});
    }

    @Override
    public void addFriend(long userId, long friendId) {

    }

    @Override
    public void deleteFriend(long userId, long friendsId) {

    }

    @Override
    public List<User> friendsCommonOtherId(long userId, long otherId) {
        return null;
    }

    @Override
    public Collection<User> fetchUsersByIds(Collection<Long> ids) {
        String sql =  "SELECT * FROM \"users\" WHERE \"id\" in (%s) ";

        final var e = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                for(int i=0; i < ids.size(); i++) {
                    ps.setLong(i+1, ((ArrayList<Long>) ids).get(i));
                }
            }
        };
        return template.query(String.format(sql, ids.stream().map(x-> "?").collect(Collectors.joining(","))),e, new UserRowMapper());
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

}
