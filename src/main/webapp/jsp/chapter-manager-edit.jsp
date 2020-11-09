<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <form action="<c:url value="/chapter-manager"/>" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">Chapter name:</label>
            <input type="text" name="title" id="title" value="${chapter.getChapterTitle()}" placeholder="Enter name">
        </div>
        <div class="form-group">
            <label for="file">Select all pictures in right order:</label>
            <input type="file" name="file" id="file" multiple accept="image/*">
        </div>
        <input type="text" value="${title.getId()}" name="id" id="id" hidden>
        <input type="text" value="${chapter.getId()}" name="chapter_id" id="chapter_id" hidden>
        <input type="submit" value="Update chapter">
    </form>
</div>

<%@ include file="service/_footer.jsp"%>