package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealTestData {
    public static final int MEAL_ID = 100002;

    public static final LocalDate START_DATE = LocalDate.of(2020, Month.JANUARY, 30);

    public static final LocalDate END_DATE = LocalDate.of(2020, Month.JANUARY, 30);

    public static final Meal USER_MEAL = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);

    public static final List<Meal> USER_MEALS = Arrays.asList(
            new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
            new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));

    public static final List<Meal> FILTERED_USER_MEALS = Arrays.asList(
            new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));

    public static void assertMatch(Meal actual, Meal expected) {
        if (!actual.equals(expected)
                || !actual.getDate().equals(expected.getDate())
                || !actual.getDescription().equals(expected.getDescription())
                || actual.getCalories() != expected.getCalories()) throw new NotFoundException("");
    }

    public static void assertMatch(List<Meal> actual, List<Meal> expected) {
        for (int i = 0; i < actual.size(); i++) {
            assertMatch(actual.get(i), expected.get(i));
        }
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(USER_MEAL.getId(), USER_MEAL.getDateTime(), USER_MEAL.getDescription(), USER_MEAL.getCalories());
        updated.setCalories(555);
        return updated;
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "New meal", 1000);
    }
}
