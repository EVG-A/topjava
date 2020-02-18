package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(authUserId());
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Collection<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealTo> getAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getFiltered");
        Collection<Meal> mealsFilteredByDate = service.getAll(authUserId(), startDate, endDate, LocalTime.MIN, LocalTime.MAX);
        return MealsUtil.getFilteredTos(mealsFilteredByDate,MealsUtil.DEFAULT_CALORIES_PER_DAY,startTime,endTime);
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        //assureIdConsistent(meal, id);
        service.update(meal, id, authUserId());
    }
}