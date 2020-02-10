package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealToDao {
    private static final Logger log = getLogger(MealToDao.class);
    private List<Meal> meals;
    private int caloriesPerDay;
    private volatile AtomicInteger counter = new AtomicInteger(0);

    public MealToDao() {
        meals = MealsUtil.getStaticMealList();
        meals.forEach(meal -> meal.setId(counter.incrementAndGet()));
        caloriesPerDay = 2000;
    }

    public List<MealTo> getAllMealsTo() {

        return MealsUtil.filteredByStreams(meals, caloriesPerDay);
    }

    public void addMeal(Meal meal) {
        meal.setId(counter.incrementAndGet());
        meals.add(meal);
        log.info("added new meal");
    }

    public void updateMeal(Meal meal) {
        deleteMealById(meal.getId());
        addMeal(meal);
    }

    public Meal getMealById(int id) {
        for (Meal meal : meals) {
            if (meal.getId() == id) return meal;
        }
        return null;
    }

    public void deleteMealById(int id) {
        Meal currentMeal = getMealById(id);

        if (currentMeal != null) {
            meals.remove(currentMeal);
            log.info("deleted meal with id=" + id);
        } else log.info("meal with id=" + id + " not found");
    }

}
