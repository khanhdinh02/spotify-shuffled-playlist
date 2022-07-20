package chinriku.spotifyshuffledplaylist.servlets;

import chinriku.spotifyshuffledplaylist.dtos.WeightedPlaylist;
import chinriku.spotifyshuffledplaylist.utils.Authorization;
import chinriku.spotifyshuffledplaylist.utils.CookieHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.NotFoundException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;

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
        String url = request.getParameter("url").trim();
        String id = url.substring(url.lastIndexOf('/') + 1);
        Cookie[] cookies = request.getCookies();
        String accessToken = CookieHelper.getCookiesValue(cookies, "accessToken");

        // Save url to session
        HttpSession session = request.getSession();
        session.setAttribute("url", url);

        // get playlist
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(accessToken).build();
        GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(id).build();
        try {
            Playlist playlist = getPlaylistRequest.execute();
            WeightedPlaylist weightedPlaylist = new WeightedPlaylist(playlist);
            request.setAttribute("playlist", weightedPlaylist);
            request.setAttribute("status", 0);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
