package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealAbstractServiceTest;

@ActiveProfiles(profiles = "datajpa")
public class MealServiceDataJpaTest extends MealAbstractServiceTest {

//    @Test
//    @Transactional
//    public void getWitUser() throws Exception {
//        Meal actual = service.getWithUser(ADMIN_MEAL_ID2, ADMIN_ID);
//        MEAL_MATCHER2.assertMatch(actual, ADMIN_MEAL2);
//    }
}