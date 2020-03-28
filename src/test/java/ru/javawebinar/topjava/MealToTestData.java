package ru.javawebinar.topjava;

import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

public class MealToTestData {
    public static TestMatcher<MealTo> MEAL_TO_MATCHER = TestMatcher.usingFieldsComparator(MealTo.class);

    public static final MealTo MEAL_TO1 = new MealTo(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories(), false);
    public static final MealTo MEAL_TO2 = new MealTo(MEAL2.getId(), MEAL2.getDateTime(), MEAL2.getDescription(), MEAL2.getCalories(), false);
    public static final MealTo MEAL_TO3 = new MealTo(MEAL3.getId(), MEAL3.getDateTime(), MEAL3.getDescription(), MEAL3.getCalories(), false);
    public static final MealTo MEAL_TO4 = new MealTo(MEAL4.getId(), MEAL4.getDateTime(), MEAL4.getDescription(), MEAL4.getCalories(), true);
    public static final MealTo MEAL_TO5 = new MealTo(MEAL5.getId(), MEAL5.getDateTime(), MEAL5.getDescription(), MEAL5.getCalories(), true);
    public static final MealTo MEAL_TO6 = new MealTo(MEAL6.getId(), MEAL6.getDateTime(), MEAL6.getDescription(), MEAL6.getCalories(), true);
    public static final MealTo MEAL_TO7 = new MealTo(MEAL7.getId(), MEAL7.getDateTime(), MEAL7.getDescription(), MEAL7.getCalories(), true);

    public static final List<MealTo> MEAL_TOS = List.of(MEAL_TO7, MEAL_TO6, MEAL_TO5, MEAL_TO4, MEAL_TO3, MEAL_TO2, MEAL_TO1);
}
