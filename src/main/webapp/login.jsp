<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="css/general.css" type="text/css">
        <link rel="stylesheet" href="css/login.css" type="text/css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
    </head>
    <body>
        <!-- Nav bar -->
        <nav class="navbar navbar-dark bg-dark">
            <div class=" container-fluid col-2">
                <a class="navbar-brand" href="login.html"><img src="images/logo.svg" alt="Spotify Playlist Logo" style="width:200px;"></a>
            </div>
        </nav>
        <!-- Main body -->
        <div class="container">
            <!-- Error Message -->
            <c:if test="${not empty requestScope}">
                <div class="row">
                    <div class="col-sm-12 text-center">
                        <p class="message">${requestScope.message}</p>
                    </div>
                </div>
            </c:if>
            <!-- Description Stuff -->
            <div class="row">
                <div class="col-sm-8 text-center">
                    <p class="spotify-font justify-text">Insert description stuff here</p>
                    <form action="login">
                        <button type="submit" class="green-button spotify-font">Login to Spotify</button>
                    </form>
                </div>
                <div class="col-sm-4">
                    <p>Image WIP</p>
                </div>
            </div>
        </div>
        <!-- Footer -->
        <footer id="footer" class="spotify-font">Copyright &copy; 2022</footer>
    </body>
</html>
