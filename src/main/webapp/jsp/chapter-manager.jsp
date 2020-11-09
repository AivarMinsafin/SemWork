<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <h4>Chapters:</h4>
    <ul class="list-unstyled">
        <c:forEach var="chapter" items="${chapters}">
            <li class="media mb-2">
                <div class="media-body row d-flex align-items-center">
                    <span class="mr-1"><a href="<c:url value="/chapter-manager?id=${title.getId()}&chapter=${chapter.getId()}"/>">${chapter.getChapterTitle()}</a></span>
                    <span><a class="btn btn-outline-danger" href="<c:url value="/chapter-delete?id=${chapter.getId()}"/>">Delete</a></span>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>

<%@ include file="service/_footer.jsp"%>