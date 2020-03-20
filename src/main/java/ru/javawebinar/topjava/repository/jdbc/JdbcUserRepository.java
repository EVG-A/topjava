package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(@NotNull User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());

        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0
                || jdbcTemplate.update("DELETE FROM user_roles where user_id=?", user.getId()) == 0) {
            return null;
        }
        List<String> roles = user.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        batchInsertRoles(user.getId(), roles);
        return user;
    }

    public void batchInsertRoles(int id, List<String> roles) {
        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) values (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, id);
                ps.setString(2, roles.get(i));
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("" +
                "SELECT users.id,\n" +
                "       users.name,\n" +
                "       users.email,\n" +
                "       users.password,\n" +
                "       users.registered,\n" +
                "       users.enabled,\n" +
                "       users.calories_per_day,\n" +
                "       user_roles.role as roles\n" +
                "FROM users\n" +
                "         left join user_roles on users.id = user_roles.user_id\n" +
                "WHERE id=?", ROW_MAPPER, id);
        return getSingletUser(users);
    }

    @Override
    public User getByEmail(@Email String email) {
        List<User> users = jdbcTemplate.query("" +
                "SELECT users.id,\n" +
                "       users.name,\n" +
                "       users.email,\n" +
                "       users.password,\n" +
                "       users.registered,\n" +
                "       users.enabled,\n" +
                "       users.calories_per_day,\n" +
                "       user_roles.role as roles\n" +
                "FROM users\n" +
                "         left join user_roles on users.id = user_roles.user_id\n" +
                "WHERE email=?", ROW_MAPPER, email);

        return getSingletUser(users);
    }

    public User getSingletUser(List<User> users) {
        if (users.size() == 0) {
            return null;
        }
        Set<Role> roles = new HashSet<>();
        users.forEach(user -> roles.addAll(user.getRoles()));
        User user = users.get(0);
        user.setRoles(roles);
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT users.id,\n" +
                "       users.name,\n" +
                "       users.email,\n" +
                "       users.password,\n" +
                "       users.registered,\n" +
                "       users.enabled,\n" +
                "       users.calories_per_day,\n" +
                "       user_roles.role as roles\n" +
                "FROM users\n" +
                "         left join user_roles on users.id = user_roles.user_id\n" +
                "ORDER BY users.name, users.email", ROW_MAPPER);
        Map<User, Set<Role>> map = new HashMap<>();
        users.forEach(user -> map.merge(user, user.getRoles(), this::mergeRoles));

        return users.stream()
                .peek(user -> user.setRoles(map.get(user)))
                .distinct()
                .collect(Collectors.toList());
    }

    public Set<Role> mergeRoles(Set<Role> set1, Set<Role> set2) {
        set1.addAll(set2);
        return set1;
    }
}
