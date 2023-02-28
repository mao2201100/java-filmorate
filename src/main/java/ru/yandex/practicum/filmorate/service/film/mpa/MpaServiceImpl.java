package ru.yandex.practicum.filmorate.service.film.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.films.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.MPA;

import java.util.List;

@Service
public class MpaServiceImpl implements MpaService {
    private MpaStorage storage;

    @Override
    public List<MPA> readAll() {
        return storage.readAll();
    }

    @Override
    public MPA readById(Long id) {
        MPA mpa = storage.readById(id);
        if (mpa == null) {
            throw new NotFoundException("Не найден ресурс");
        }
        return mpa;
    }

    @Override
    public void create(MPA mpa) {
        mpa.setName(MPAUtils.fetchMPAName(mpa.getId()));
        var created = storage.readById(mpa.getId());
        if (created == null) {
            storage.create(mpa);
        }
    }

    @Override
    public void update(Long oldMpa, MPA mpa) {
        mpa.setName(MPAUtils.fetchMPAName(mpa.getId()));
        storage.create(mpa);
    }

    @Autowired
    public void setStorage(MpaStorage storage) {
        this.storage = storage;
    }

}
