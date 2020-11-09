<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <ul class="list-unstyled">
        <c:forEach var="title" items="${titles}">
            <li class="media">
                <div class="media-body row d-flex align-items-center bg-secondary p-1">
                    <img class="mr-1 rounded border-dark" style="height: 30px; width: 30px" src="<c:url value="/image/${title.getTitleImg()}"/>" alt="">
                    <h5 class="mr-1 mt-0 mb-1">${title.getTitle()}</h5>
                    <a href="<c:url value="/title-manager?id=${title.getId()}"/>" class="mr-1 btn btn-outline-warning">Edit</a>
                    <a href="<c:url value="/chapter-manager?id=${title.getId()}"/>" class="mr-1 btn btn-outline-warning">Edit chapters</a>
                    <a href="<c:url value="/title-delete?id=${title.getId()}"/>" class="mr-1 btn btn-outline-danger">Delete</a>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>

<%@ include file="service/_footer.jsp"%>