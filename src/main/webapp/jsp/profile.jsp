<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="service/_header.jsp"%>

<div class="container">
    <div class="row">
        <img src="" alt="user-img" class="user-img">
        <strong>${user.getNickname()}</strong>
        <div class="update-profile">
            <a href="<c:url value="/update-profile"/>" class="btn">Edit profile</a>
        </div>
    </div>
    <div class="row">
        <div class="col" style="background-color: lightsalmon;">
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
    </div>
    <div class="author">
        <c:if test="${isAuthor}">
            <a href="<c:url value="/create-title"/>" class="btn btn-primary">Create new title</a>
            <a href="<c:url value="/title-manager"/>" class="btn btn-primary">Title manager</a>
        </c:if>
        <c:if test="${not isAuthor}">
            <a href="<c:url value="/new-author"/>" class="btn btn-primary">Become an author</a>
        </c:if>
    </div>
</div>

<%@ include file="service/_footer.jsp"%>