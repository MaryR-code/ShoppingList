<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping List</title>
</head>

<body>
    <p>
        <a href="<c:url value="/list"/>">Open Shopping List</a>
    </p>
    <p>
        <a href="<c:url value="/article"/>">Add new Article</a>
    </p>
    <p>
        <a href="<c:url value="/unit"/>">Add new Unit</a>
    </p>
</body>
</html>