package ru.yandex.practicum.filmorate.service.film.mpa;

import java.util.Map;

public class MPAUtils {
    private static final Map<Long, String> mapName = Map.of(1L, "G", 2L, "PG",3L, "PG-13", 4L, "R", 5L, "NC-17");
    public static String fetchMPAName(Long id) {
        var result = mapName.get(id);
        if (result == null) {
            return "PG";
        }
        return result;
    }
}
