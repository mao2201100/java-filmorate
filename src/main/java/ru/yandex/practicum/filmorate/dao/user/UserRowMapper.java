package ru.yandex.practicum.filmorate.dao.user;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        final User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setLogin(rs.getString("login"));
        user.setBirthday(LocalDate.parse(rs.getString("birthday")));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
