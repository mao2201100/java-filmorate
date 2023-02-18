package ru.yandex.practicum.filmorate.model;

public class MotionPictureAssociation {
    private enum MPA{
        G,      //— у фильма нет возрастных ограничений,
        PG,     //— детям рекомендуется смотреть фильм с родителями,
        PG_thirteen,  //— детям до 13 лет просмотр не желателен,
        R,      //— лицам до 17 лет просматривать фильм можно только в присутствии взрослого,
        NC_seventeen   //— лицам до 18 лет просмотр запрещён.
    }
}
