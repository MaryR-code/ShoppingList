<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New article</title>
</head>

<body>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>units</th>
    </tr>
    <c:forEach items="${articles}" var="a">
        <tr>
            <td>${a.id}</td>
            <td>${a.name}</td>
            <td>${a.units}</td>
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
        <label for="units">units</label>
        <select id="units" name="unitId">
            <c:forEach items="${units}" var="u">
                <option value="${u.id}">(${u.name})</option>
            </c:forEach>
        </select>
    </p>
    <p>
        <button type="submit">ADD</button>
    </p>
</form>
</body>
</html>
