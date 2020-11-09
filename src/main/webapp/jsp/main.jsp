<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <ul class="list-unstyled">
        <c:forEach var="title" items="${titles}">
            <li class="media">
                <img style="width: 40px; height: 40px"  src="<c:url value="/image/${title.getTitleImg()}"/>" class="mr-3 rounded" alt="">
                <div class="media-body">
                    <h5 class="mt-0 mb-1"><a href="<c:url value="/title?id=${title.getId()}"/>">${title.getTitle()}</a></h5>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>

<%@ include file="service/_footer.jsp"%>