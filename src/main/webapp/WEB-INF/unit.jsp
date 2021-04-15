<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New unit</title>
</head>

<body>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    <c:forEach items="${units}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
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
    <h4>
        <label for="add">Add new unit => </label>
        <button id="add" type="submit">ADD</button>
    </h4>
</form>
</body>
</html>
