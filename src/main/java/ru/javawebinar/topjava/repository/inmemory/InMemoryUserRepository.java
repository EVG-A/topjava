package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<String, User> repository = new ConcurrentHashMap<>();

    {
        UsersUtil.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        for (Map.Entry<String, User> entry : repository.entrySet()) {
            if (entry.getValue().getId() == id) {
                repository.remove(entry.getKey());
                return true;
            }
        }
        return false;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        repository.put(user.getEmail(), user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        for (Map.Entry<String, User> entry : repository.entrySet()) {
            if (entry.getValue().getId() == id) return entry.getValue();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> users = new ArrayList<>(repository.values());
        users.sort(Comparator.comparing(User::getEmail));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.get(email);
    }
}
