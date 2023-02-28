DROP ALL OBJECTS DELETE FILES;
CREATE TABLE "users" (
                         "id" long auto_increment PRIMARY KEY,
                         "email" text,
                         "login" text,
                         "name" text,
                         "birthday" Date
);

CREATE TABLE "films" (
                         "id" long auto_increment PRIMARY KEY,
                         "description" text,
                         "releaseDate" Date,
                         "duration" Long,
                         "MPA_id" Long,
                         "name" text
);

CREATE TABLE "friend_ship" (
                               "id" long auto_increment PRIMARY KEY,
                               "user_owner_id" long,
                               "friend_id" long,
                               "FriendshipStatus" text
);

CREATE TABLE "film_likes" (
                              "id" long auto_increment PRIMARY KEY,
                              "film_id" long,
                              "user_id" long
);

CREATE TABLE "MPA" (
                       "id" long auto_increment PRIMARY KEY,
                       "name" text
);

CREATE TABLE "genre" (
                         "id" long auto_increment PRIMARY KEY,
                         "name" text
);

ALTER TABLE "film_likes" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("id");

ALTER TABLE "film_likes" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "friend_ship" ADD FOREIGN KEY ("user_owner_id") REFERENCES "users" ("id");

ALTER TABLE "friend_ship" ADD FOREIGN KEY ("friend_id") REFERENCES "users" ("id");

ALTER TABLE "films" ADD FOREIGN KEY ("MPA_id") REFERENCES "MPA" ("id");

CREATE TABLE "genre_films" (
                               "genre_id" long,
                               "films_id" long,
                               PRIMARY KEY ("genre_id", "films_id")
);

ALTER TABLE "genre_films" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("id");

ALTER TABLE "genre_films" ADD FOREIGN KEY ("films_id") REFERENCES "films" ("id");
