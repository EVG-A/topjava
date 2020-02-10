<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach var="mealTo" items="${requestScope.meals}">
        <tr style="color:${mealTo.excess ? 'red' : 'green'}">
            <fmt:parseDate value="${mealTo.dateTime}" var="parsedEmpDate" pattern="yyyy-MM-dd'T'HH:mm"/>
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ parsedEmpDate }" var="resultDate"/>
            <th><c:out value="${resultDate}"/></th>
            <th>${mealTo.description}</th>
            <th>${mealTo.calories}</th>
            <td><a href="meals?action=edit&id=<c:out value="${mealTo.id}"/>">Изменить</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${mealTo.id}"/>">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=insert">Добавить</a></p>
</body>
</html>