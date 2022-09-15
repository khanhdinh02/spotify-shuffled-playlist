<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spotity / Suffled Playlist</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="css/general.css" type="text/css">
        <link rel="stylesheet" href="css/index.css" type="text/css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
    </head>
    <body>
        <header>

            <nav class="navbar navbar-dark bg-dark">
                <div class="container-fluid">
                    <a class="navbar-brand" href="index.html"><img src="images/logo.svg" alt="Spotify Playlist Logo" style="width:200px;"></a>
                    <div class="d-flex">
                        <img src="${sessionScope.user.images[0].url}" style="width:40px;">
                        <span class="spotify-font" style="color: white">${sessionScope.user.name}</span>
                        <p><a href="${sessionScope.user.url}" target="_blank">Profile</a></p>
                        <p><a href="logout">Logout</a></p>
                    </div>
                </div>
            </nav>
        </header>
        <section>
            <form action="getPlaylist" method="post">
                <input type="url" name="url" value="${sessionScope.url}" placeholder="Enter playlist's url" required>
                <button type="submit">Submit</button>
            </form>
            <c:set var="status" value="${requestScope.status}" />
            <c:if test="${not empty status}">
                <c:choose>
                    <c:when test="${status == 0}">
                        <!-- Confirm -->
                        <form action="createPlaylist" method="post">
                            <button type="submit">Create Playlist</button>
                        </form>
                    </c:when>
                    <c:when test="${status == 1}">
                        <!-- Create playlist success -->
                    </c:when>
                    <c:when test="${status == -1}">
                        <!-- Error -->
                        ${requestScope.message}
                    </c:when>
                </c:choose>
            </c:if>
        </section>
        <footer id="footer" class="spotify-font">Copyright &copy; 2022</footer>
    </body>
</html>
