<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spotify Playlist</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="css/general.css" type="text/css">
        <link rel="stylesheet" href="css/login.css" type="text/css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="icon" href="/images/favicon.svg">
    </head>
    <body>
        <!-- Nav bar -->
        <nav class="navbar navbar-dark bg-dark">
            <div class=" container-fluid col-2">
                <a class="navbar-brand logo" href="login.html"><img src="images/logo.svg" alt="Spotify Playlist Logo"></a>
            </div>
        </nav>
        <!-- Main body -->
        <!-- Error Message -->
        <c:if test="${not empty requestScope}">
            <p class="message">${requestScope.message}</p>
        </c:if>
        <div class="container">
            <!-- Description Stuff -->
            <div class="row">
                <div class="col-sm-8 text-center">
                    <p class="spotify-font justify-text">
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                        Donec nunc enim, tristique sit amet volutpat non, sollicitudin eu elit. 
                        Nullam sollicitudin venenatis ipsum non sagittis. Proin at tincidunt massa. 
                        Aenean dignissim, lectus sit amet sodales cursus, eros sem mollis velit, quis porttitor ipsum elit at risus. 
                        Pellentesque ac euismod justo. In leo nibh, auctor id cursus eu, finibus rutrum quam. 
                        Vestibulum quis quam sit amet ligula molestie pellentesque. Nullam blandit ipsum at interdum porttitor. 
                        Proin scelerisque nisl eget ex laoreet bibendum. 
                        Donec condimentum tristique arcu id dictum. Nam sed tempus nunc.
                    </p>
                    <form action="login">
                        <button type="submit" class="green-button spotify-font">Login to Spotify</button>
                    </form>
                </div>
                <div class="col-sm-4">
                    <img src="images/login_img.svg" alt="Main Image">
                </div>
            </div>
        </div>
        <!-- Footer -->
        <footer id="footer" class="spotify-font">Copyright &copy; 2022</footer>
    </body>
</html>
