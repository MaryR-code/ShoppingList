<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
        <label for="name">Name</label>
        <input id="name" name="name">
    </p>
    <p>
        <label for="quantity">Quantity</label>
        <input id="quantity" name="quantity">
    </p>
    <p>
        <label for="units">Units</label>
        <input id="units" name="units">
    </p>
    <p>
        <button type="submit">Add</button>
    </p>
</form>
</body>
</html>
