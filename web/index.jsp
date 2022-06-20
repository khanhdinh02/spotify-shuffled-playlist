<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spotity / Suffled Playlist</title>
    </head>
    <body>
        <form action="getPlaylist" method='post'>
            <input type="url" name="url" value="${sessionScope.url}" placeholder="Enter playlist's url">
            <button type="submit">Login to Spotify</button>
        </form>
        <c:set var="status" value="${requestScope.status}" />
        <c:if test="${status != null}">
            <c:choose>
                <c:when test="${status == 0}">
                    <!-- Confirm -->
                    <form action="createPlaylist">
                        <button type="submit">Create Playlist</button>
                    </form>
                </c:when>
                <c:when test="${status == 1}">
                    <!-- Create playlist success -->
                </c:when>
                <c:when test="${status == -1}">
                    <!-- Error -->
                </c:when>
            </c:choose>
        </c:if>
    </body>
</html>
