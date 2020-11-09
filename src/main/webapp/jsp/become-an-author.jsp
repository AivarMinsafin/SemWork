<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <form action="<c:url value="/new-author"/>" method="post">
        <div class="form-group">
            <label for="firstName">First name</label>
            <input type="text" name="firstName" id="firstName" placeholder="Enter first name">
        </div>
        <div class="form-group">
            <label for="lastName">Last Name</label>
            <input type="text" name="lastName" id="lastName" placeholder="Enter last name">
        </div>
        <input type="submit" value="Become an author">
    </form>
</div>

<%@ include file="service/_footer.jsp"%>
