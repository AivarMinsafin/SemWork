<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <form action="<c:url value="/title-manager"/>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">Title name:</label>
            <input type="text" name="title" value="${title.getTitle()}" id="title" placeholder="Enter title name">
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <textarea name="description" id="description" cols="50" rows="15" maxlength="250">${title.getDescription()}</textarea>
            <p class="text-secondary">Max: 250 characters</p>
        </div>
        <div class="form-group">
            <label for="file">Title image:</label>
            <input type="file" name="file" id="file" accept="image/*">
        </div>
        <input type="text" value="${title.getId()}" name="id" id="id" hidden>
        <input type="submit" value="Update title">
    </form>
    <a href="<c:url value="/add-chapter?id=${title.getId()}"/>" class="btn btn-primary">Add chapter</a>
</div>

<%@ include file="service/_footer.jsp"%>