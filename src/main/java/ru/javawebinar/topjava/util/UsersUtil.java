package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
           new User(1,"Tom","tom@mail.ru","123", Role.ROLE_USER),
            new User(2,"Max","max@mail.ru","qwe", Role.ROLE_USER)
            );
}
