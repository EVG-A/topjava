package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealAbstractServiceTest;

@ActiveProfiles(profiles = Profiles.JPA)
public class MealServiceJpaTest extends MealAbstractServiceTest {
}
