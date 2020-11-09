<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <c:if test="${not empty errors}">
        <c:forEach var="error" items="${errors}">
            <p class="text-warning">${error}</p>
        </c:forEach>
    </c:if>
    <form method="post" action="<c:url value="/update-profile"/>">
    <div class="form-group">
        <label for="login">Nickname:</label>
        <input type="text" name="nickname" value="${user.getNickname()}" id="login" placeholder="Nickname">
    </div>
    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" name="email" value="${user.getEmail()}" id="email" placeholder="Email">
    </div>
    <div class="form-group">
        <label for="password">New password:</label>
        <input type="password" name="password" id="password" placeholder="New password">
        <input type="password" name="conf_password" placeholder="Confirm password">
    </div>
    <input type="submit" value="Update">
    </form>
</div>

<%@ include file="service/_footer.jsp"%>
