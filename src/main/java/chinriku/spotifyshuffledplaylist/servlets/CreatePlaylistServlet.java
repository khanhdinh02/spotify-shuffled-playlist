package chinriku.spotifyshuffledplaylist.servlets;

import chinriku.spotifyshuffledplaylist.daos.PlaylistDAO;
import chinriku.spotifyshuffledplaylist.dtos.UserInfo;
import chinriku.spotifyshuffledplaylist.utils.Authorization;
import chinriku.spotifyshuffledplaylist.utils.CookieHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;

public class CreatePlaylistServlet extends HttpServlet {

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
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        List<PlaylistTrack> trackList = (List<PlaylistTrack>) session.getAttribute("trackList");
        UserInfo user = (UserInfo) session.getAttribute("user");
        String accessToken = CookieHelper.getCookiesValue(cookies, "accessToken");

        try {
            if (trackList != null) {
                // Create new playlist
                Playlist newPlaylist = PlaylistDAO.createPlaylist(accessToken, user.getId());

                // Shuffle and get uri list
                Collections.shuffle(trackList);
                ArrayList<String> trackUris = new ArrayList<>(trackList.size());
                trackList.forEach(track -> {
                    IPlaylistItem item = track.getTrack();
                    if (item != null) {
                        trackUris.add(item.getUri());
                    }
                });

                // Add items
                PlaylistDAO.addTracks(accessToken, newPlaylist.getId(), trackUris.toArray(new String[0]));

                int failedItems = trackList.size() - trackUris.size();
                if (failedItems > 0) {
                    request.setAttribute("message", "Fail to retrieve " + failedItems + " track(s)/episode(s)");
                }
                request.setAttribute("status", 1);
                request.setAttribute("newPlaylists", newPlaylist);
                request.getRequestDispatcher("./").forward(request, response);
            } else {
                request.setAttribute("status", -1);
                request.setAttribute("message", "Please get playlist again");
                request.getRequestDispatcher("./").forward(request, response);
            }
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
