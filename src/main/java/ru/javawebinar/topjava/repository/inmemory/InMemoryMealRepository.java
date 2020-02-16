package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (get(id, userId) == null) return false;
        else {
            repository.remove(id);
            return true;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal currentMeal = repository.get(id);
        if (currentMeal == null || currentMeal.getUserId() != userId) return null;
        else return currentMeal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
       return repository.values().stream()
               .filter(meal -> meal.getUserId() == userId)
               .sorted(Comparator.comparing(Meal::getDateTime).reversed())
               .collect(Collectors.toList());
    }
}

