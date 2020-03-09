package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Profile({"hsqldb"})
public class JdbcHsqlMealRepository extends AbstractJdbcMealRepository {

    public JdbcHsqlMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public String getDateTimeString(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime).toString();
    }
}
