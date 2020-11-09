<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <div class="row">
        <div class="col-3">
            <img class="rounded" style="height: 200px; width: 200px" src="<c:url value="/image/${title.getTitleImg()}"/>" alt="">
        </div>
        <div class="col">
            <h3>${title.getTitle()}</h3>
            <label for="author">Author:</label>
            <p id="author">${title.getAuthor().getUser().getNickname()}</p>
            <label for="description">Description</label>
            <p id="description" class="description">${title.getDescription()}</p>
        </div>
    </div>
    <c:if test="${not empty user}">
        <c:if test="${showAdd}">
            <a href="<c:url value="/add-to-list?id=${title.getId()}"/>" class="btn btn-outline-primary">Add title</a>
        </c:if>
    </c:if>
    <div class="container-fluid">
        <h4>Chapters:</h4>
        <ul class="list-unstyled">
            <c:forEach var="chapter" items="${chapters}">
                <li class="media">
                    <div class="media-body">
                        <span><a href="<c:url value="/chapter?title=${title.getId()}&chapter_num=${chapter.getNumber()}"/>">${chapter.getChapterTitle()}</a></span>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<%@ include file="service/_footer.jsp"%>