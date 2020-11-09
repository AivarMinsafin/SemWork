<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <form action="<c:url value="/create-title"/>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="titleName">Title name:</label>
            <input type="text" name="titleName" id="titleName" placeholder="Enter title name" required>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <textarea name="description" id="description" cols="50" rows="15" maxlength="250"></textarea>
            <p class="text-secondary">Max: 250 characters</p>
        </div>
        <div class="form-group">
            <label for="file">Title image:</label>
            <input type="file" name="file" id="file" accept="image/*">
        </div>
        <input type="submit" value="Create title">
    </form>
</div>

<%@ include file="service/_footer.jsp"%>
