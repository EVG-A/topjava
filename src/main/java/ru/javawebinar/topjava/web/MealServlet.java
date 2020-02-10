package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealToDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class MealServlet extends HttpServlet {

    private MealToDao dao;
    private static String LIST_MealTo = "/meals.jsp";
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private volatile AtomicInteger counter = new AtomicInteger(7);

    public MealServlet() {
        dao = new MealToDao();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String dateTimeString = req.getParameter("dateTime");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
        int calories = Integer.parseInt(req.getParameter("calories"));

        Meal meal = new Meal(dateTime, req.getParameter("description"), calories);

        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            meal.setId(Integer.parseInt(id));
            dao.updateMeal(meal);
        } else dao.addMeal(meal);

        RequestDispatcher view = req.getRequestDispatcher(LIST_MealTo);
        req.setAttribute("meals", dao.getAllMealsTo());
        view.forward(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward = "";
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            forward = LIST_MealTo;
            request.setAttribute("meals", dao.getAllMealsTo());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = dao.getMealById(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMealsTo")) {
            forward = LIST_MealTo;
            request.setAttribute("meals", dao.getAllMealsTo());
        } else if (action.equalsIgnoreCase("insert")) {
            forward = INSERT_OR_EDIT;
            request.setAttribute("meals", dao.getAllMealsTo());
        } else if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteMealById(id);
            response.sendRedirect("meals");
            return;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
}
