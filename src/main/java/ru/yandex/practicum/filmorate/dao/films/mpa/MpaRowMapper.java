package ru.yandex.practicum.filmorate.dao.films.mpa;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.film.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MpaRowMapper implements RowMapper<MPA> {

    @Override
    public MPA mapRow(ResultSet rs, int rowNum) throws SQLException {
        MPA mpa = new MPA();
        mpa.setId(rs.getLong("id"));
        mpa.setName(rs.getString("name"));
        return mpa;
    }
}
