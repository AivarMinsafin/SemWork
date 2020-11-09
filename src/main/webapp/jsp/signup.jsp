<%--
  Created by IntelliJ IDEA.
  User: aivar
  Date: 13.10.2020
  Time: 23:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
<c:if test = "${not empty message}">
    <h3>${message}</h3>
</c:if>
<form action="<c:url value="/signup"/>" method="post">
    <div class="form-group">
        <label for="login">Login:</label>
        <input type="text" name="login" id="login" placeholder="Login">
    </div>
    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" placeholder="Email">
    </div>
    <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" placeholder="Password">
    <input type="password" name="conf_password" placeholder="Confirm password">
    </div>
    <div class="form-check">
        <input type="checkbox" id="agr" name="agreement">
        <label for="agr">Consent to the processing of personal data</label>
    </div>
        <input type="submit" value="Sign up">
    </form>
</div>

<%@ include file="service/_footer.jsp"%>