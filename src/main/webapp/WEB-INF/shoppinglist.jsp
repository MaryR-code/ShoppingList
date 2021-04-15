<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ShoppingList</title>
</head>

<body>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Qnty</th>
        <th>units</th>
        <th>done</th>
    </tr>
    <c:forEach items="${items}" var="i">
        <tr>
            <td>${i.id}</td>
            <td>${i.name}</td>
            <td>${i.quantity}</td>
            <td>${i.units}</td>
            <td>${i.done}</td>
        </tr>
    </c:forEach>
</table>

<form method="post">
    <p>
        <label for="id">ID</label>
        <input id="id" name="id">
    </p>
    <p>
        <label for="article">Article</label>
        <select id="article" name="articleId">
            <c:forEach items="${articles}" var="a">
                <option value="${a.id}">${a.name} (${a.units})</option>
            </c:forEach>
        </select>
    </p>
    <p>
        <label for="quantity">Quantity</label>
        <input id="quantity" name="quantity">
    </p>
    <h4>
        <label for="add">Add new item => </label>
        <button id="add" type="submit">ADD</button>
    </h4>
</form>
</body>
</html>
