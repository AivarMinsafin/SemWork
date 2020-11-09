<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <h3>${chapter.getChapterTitle()}</h3>
    <div class="container-fluid">
        <c:forEach var="page" items="${pages}">
            <img style="width: 100%" src="<c:url value="/image/${page.getImagePath()}"/>" alt="">
        </c:forEach>
    </div>
    <div class="row justify-content-around">
        <a href="<c:url value="/chapter?title=${title.getId()}&chapter_num=${chapter.getNumber() - 1}"/>" class="btn btn-outline-primary">Previous</a>
        <a href="<c:url value="/title?id=${title.getId()}"/>" class="btn btn-outline-primary">To the title</a>
        <a href="<c:url value="/chapter?title=${title.getId()}&chapter_num=${chapter.getNumber() + 1}"/>" class="btn btn-outline-primary">Next</a>
    </div>
</div>

<%@ include file="service/_footer.jsp"%>