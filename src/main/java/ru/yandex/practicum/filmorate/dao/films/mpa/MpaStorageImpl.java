package ru.yandex.practicum.filmorate.dao.films.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.film.MPA;

import java.util.List;

@Repository
public class MpaStorageImpl implements MpaStorage {
    private JdbcTemplate template;


    @Override
    public void create(MPA mpa) {
        template.update("INSERT INTO \"MPA\"(\"id\", \"name\") VALUES (?, ?)",
                mpa.getId(), mpa.getName());
    }

    @Override
    public List<MPA> readAll() {
        try {
            return template.query("SELECT * FROM  \"MPA\"", new MpaRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public MPA readById(Long id) {
        try {
            return template.queryForObject("SELECT * FROM  \"MPA\" where \"id\"=?", new MpaRowMapper(), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        template.update("DELETE FROM \"MPA\" where \"id\" = ?", id);
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

}
