package chinriku.spotifyshuffledplaylist.servlets;

import chinriku.spotifyshuffledplaylist.daos.PlaylistDAO;
import chinriku.spotifyshuffledplaylist.utils.Authorization;
import chinriku.spotifyshuffledplaylist.utils.CookieHelper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.NotFoundException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

public class GetPlaylistServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String urlString = request.getParameter("url");
        Cookie[] cookies = request.getCookies();
        String accessToken = CookieHelper.getCookiesValue(cookies, "accessToken");

        // Save url to session
        HttpSession session = request.getSession();
        session.setAttribute("url", urlString);

        try {
            // Parse playlist's id
            URL url = new URL(urlString);
            String path = url.getPath();
            String id = path.substring(path.lastIndexOf('/') + 1);

            // Get playlist
            Playlist weightedPlaylist = PlaylistDAO.getPlaylist(accessToken, id);

            // Get tracks
            List<PlaylistTrack> trackList = PlaylistDAO.getPlaylistTracks(accessToken, id);

            session.setAttribute("playlist", weightedPlaylist);
            session.setAttribute("trackList", trackList);
            request.setAttribute("status", 0);
            request.getRequestDispatcher("./").forward(request, response);
        } catch (MalformedURLException e) {
            request.setAttribute("status", -1);
            request.setAttribute("message", "Invalid URL");
            request.getRequestDispatcher("./").forward(request, response);
        } catch (NotFoundException e) {
            // Playlist not found
            request.setAttribute("message", "Playlist not found");
            request.setAttribute("status", -1);
            request.getRequestDispatcher("./").forward(request, response);
        } catch (UnauthorizedException e) {
            // Refresh access token, then retry
            boolean hasRefreshed = Boolean.valueOf(String.valueOf(request.getAttribute("hasRefreshed")));
            if (!hasRefreshed) {
                try {
                    AuthorizationCodeCredentials credentials = Authorization.refreshAccessToken(CookieHelper.getCookiesValue(cookies, "refreshToken"));
                    accessToken = credentials.getAccessToken();

                    // Save new access token
                    Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
                    accessTokenCookie.setMaxAge(-1);
                    response.addCookie(accessTokenCookie);

                    // Call the servlet again
                    request.setAttribute("hasRefreshed", true);
                    request.getRequestDispatcher("getPlaylist").forward(request, response);
                } catch (IOException | ParseException | SpotifyWebApiException e1) {
                    e1.printStackTrace();
                    response.sendRedirect("logout");
                }
            } else {
                response.sendRedirect("logout");
            }
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            // Unknown error
            e.printStackTrace();
            request.setAttribute("status", -1);
            request.setAttribute("message", "Something went wrong. Please try again later.");
            request.getRequestDispatcher("./").forward(request, response);
        }
    }
}
