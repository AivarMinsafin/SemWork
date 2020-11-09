<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comics</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/css/main.css"/>">
</head>
<body>

<header>
    <div class="container-fluid" style="background-color: lightgreen;">
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light" style="background-color: lightgreen;">
                <a class="navbar-brand" href="#">Navbar</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="<c:url value="/main"/>">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <c:if test="${empty user}">
                            <li class="nav-item">
                                <a class="nav-link btn btn-sm align-middle btn-outline-info" href="<c:url value="/auth"/>">Sign in</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link btn btn-sm align-middle btn-outline-info" href="<c:url value="/signup"/>">Sign up</a>
                            </li>
                        </c:if>
                        <c:if test="${not empty user}">
                            <li class="nav-item">
                                <a class="nav-link btn btn-sm align-middle btn-outline-info" href="<c:url value="/profile"/>">Profile</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link btn btn-sm align-middle btn-outline-info" href="<c:url value="/lout"/>">Sign out</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </nav>
        </div>
    </div>
</header>