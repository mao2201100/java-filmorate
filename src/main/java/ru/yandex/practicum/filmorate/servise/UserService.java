package ru.yandex.practicum.filmorate.servise;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Data
@Slf4j
public class UserService { // будет отвечать за такие операции с пользователями, как добавление в друзья,
    // удаление из друзей, вывод списка общих друзей. Пока пользователям не надо одобрять заявки в друзья
    // — добавляем сразу. То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
//    private InMemoryUserStorage inMemoryUserStorage;


    //добавление в друзья

    // удаление из друзей
    public Collection<User> getFriendsUser(long userId, Map users) { // показать друзей пользователя по его id
        User user = (User) users.get(userId);
        Iterator<Long> id = user.getFriends().iterator();
        Map<Long, User> friendsUser = new HashMap<>();
        while (id.hasNext()) {
            long i = id.next();
            friendsUser.put(i, (User) users.get(i));
        }
        return friendsUser.values();
    }

    public Collection<User> friendsCommonOtherId(long userId, long otherId, Map users) {// список друзей, общих с другим пользователем.
        User user1 = (User) users.get(userId);
        User user2 = (User) users.get(otherId);
        ArrayList<Long> use1FriendsList = new ArrayList<>();
        ArrayList<Long> use2FriendsList = new ArrayList<>();
        ArrayList<Long> mutualFriendsList = new ArrayList<>();
        use1FriendsList.addAll(user1.getFriends());
        use2FriendsList.addAll(user2.getFriends());

        for (int i = 0; i < use1FriendsList.size(); i++) {
            if (use2FriendsList.contains(use1FriendsList.get(i))) {
                mutualFriendsList.add(use1FriendsList.get(i));
            }
        }
        Iterator<Long> idUsers = mutualFriendsList.iterator();
        Map<Long, User> mutualFriendsUsers = new HashMap<>();
        while (idUsers.hasNext()) {
            long i = idUsers.next();
            mutualFriendsUsers.put(i, (User) users.get(i));
        }

        return mutualFriendsUsers.values();
    }
}
