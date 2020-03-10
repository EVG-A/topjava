package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealAbstractServiceTest;

@ActiveProfiles(profiles = Profiles.JDBC)
public class MealServiceJdbcTest extends MealAbstractServiceTest {
}
