<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Добавить еду</title>
</head>
<body>
<form method="POST" action='meals' name="frmAddMeal">

    <input hidden="true" type="text" readonly="readonly" name="id"
           value="${meal.id}"/> <br/>

    Дата/Время <br/>
    <input
            type="datetime-local" name="dateTime"
            <fmt:parseDate value="${meal.dateTime}" var="parsedEmpDate" pattern="yyyy-MM-dd'T'HH:mm"/>
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ parsedEmpDate }" var="resultDate"/>
            value="<c:out value="${resultDate}"/>"/> <br/>
    Описание <br/>
    <input
            type="text" name="description"
            value="<c:out value="${meal.description}" />"/> <br/>
    Калории <br/>
    <input
            type="number" name="calories"
            value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="submit" value="Сохранить"/>
</form>
</body>
</html>