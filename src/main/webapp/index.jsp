<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spotity / Suffled Playlist</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <link rel="stylesheet" href="css/general.css" type="text/css">
        <link rel="stylesheet" href="css/index.css" type="text/css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="icon" href="/images/favicon.svg">
    </head>
    <body>
        <header>
            <nav class="navbar fixed-top">
                <div class="container-fluid">
                    <a class="navbar-brand logo" href="index.html"><img src="images/logo.svg" alt="Spotify Playlist Logo"></a>
                    <div class="dropdown">
                        <a class="btn btn-dark dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                            <img class="profile_img" src="${sessionScope.user.images[0].url}">
                            <span class="spotify-font profile_name">${sessionScope.user.name}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark dropdown-menu-end" aria-labelledby="dropdownMenuLink">
                            <li><a class="dropdown-item" href="${sessionScope.user.url}" target="_blank">Profile</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="logout">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </nav>                            
        </header>

        <section class="playlist">
            <form class="playlist-search" action="getPlaylist" method="post">
                <input class="playlist-search-box" type="url" name="url" value="${sessionScope.url}" placeholder="Enter playlist's url" size="70" required>
                <button class="green-button spotify-font" type="submit">Submit</button>
            </form>
            <c:set var="status" value="${requestScope.status}" />
            <c:if test="${not empty status}">
                <c:choose>
                    <c:when test="${status == 0}">
                        <!-- Playlist Valid -->
                        <!-- Playlist Information -->
                        <div class="playlist-info">
                            <p class="spotify-font playlist-info-title">PLAYLIST DETAILS</p>
                            <p class="spotify-font playlist-info-title">${sessionScope.playlist.name}</p>
                            <p class="spotify-font playlist-info-title">${sessionScope.playlist.owner.displayName} • ${sessionScope.trackList.size()} songs</p>
                            <p class="spotify-font playlist-info-title">${sessionScope.playlist.description}</p>
                            <img src="${sessionScope.playlist.images[0].url}" style="width: 200px;"/>
                            <!-- Create Playlist on Logined Spotify account -->
                            <form class="playlist-create" action="createPlaylist" method="post">
                                <button class="green-button spotify-font" type="submit">Create Playlist</button>
                            </form>
                        </div>                            
                        <!-- Show all tracks in this playlist -->
                        <c:if test="${empty sessionScope.trackList}">
                            <p class="spotify-font" style="color: white;">This playlist does not have any track</p>
                        </c:if>
                        <c:if test="${not empty sessionScope.trackList}">
                            <table class="table table-dark table-hover table-borderless spotify-font">
                                <thead>
                                    <tr class="head-tracklist">
                                        <th>#</th>
                                        <th>TITLE</th>
                                        <th>ALBUM</th>
                                        <th>ARTISTS</th>
                                        <th>LENGTH</th>
                                    </tr>
                                </thead>
                                <c:forEach var="track" items="${sessionScope.trackList}" varStatus="counter">
                                    <c:set var="duration" value="${track.track.durationMs}"/>
                                    <c:set var="totalsec" value="${(duration - duration mod 1000)/1000}"/>
                                    <fmt:parseNumber var = "min" integerOnly = "true" type = "number" value = "${(totalsec - totalsec mod 60)/60}" />
                                    <fmt:parseNumber var = "sec" integerOnly = "true" type = "number" value = "${totalsec mod 60}" />
                                    <tbody>
                                        <tr>
                                            <th>${counter.count}</th>
                                            <td>${track.track.name}</td>
                                            <td>${track.track.album.name}</td>
                                            <td>
                                                <c:forEach var="artist" items="${track.track.artists}" varStatus="artistcounter">
                                                    <c:if test="${artistcounter.count == 1}">${artist.name}</c:if>
                                                    <c:if test="${artistcounter.count != 1}">, ${artist.name}</c:if>
                                                </c:forEach>                                                
                                            </td>
                                            <td>
                                                <c:if test="${sec < 10}">${min}:0${sec}</c:if>
                                                <c:if test="${sec >= 10}">${min}:${sec}</c:if>
                                                </td>
                                            </tr>
                                        </tbody>
                                </c:forEach>
                            </table>
                        </c:if>
                    </c:when>
                    <c:when test="${status == 1}">
                        <!-- Create playlist success -->
                        <p style="color: white;">Playlist Created: ${requestScope.newPlaylists.name}</p>
                    </c:when>
                    <c:when test="${status == -1}">
                        <!-- Error -->
                        <p class="message">${requestScope.message}</p>
                    </c:when>
                </c:choose>
            </c:if>
        </section>

        <footer id="footer" class="spotify-font">This project is an external app, not from Spotify</footer>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</html>
