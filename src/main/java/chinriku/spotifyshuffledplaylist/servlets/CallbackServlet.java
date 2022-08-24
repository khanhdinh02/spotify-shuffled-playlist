package chinriku.spotifyshuffledplaylist.servlets;

import chinriku.spotifyshuffledplaylist.daos.UserInfoDAO;
import chinriku.spotifyshuffledplaylist.dtos.UserInfo;
import chinriku.spotifyshuffledplaylist.utils.Authorization;
import chinriku.spotifyshuffledplaylist.utils.CookieHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;

public class CallbackServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String reqState = request.getParameter("state");
        Cookie[] cookies = request.getCookies();

        // Get state stored in cookie
        String savedState = CookieHelper.getCookiesValue(cookies, "state");

        // Return login page if auth code or state is invalid
        if (code == null || code.isEmpty() || reqState == null || !reqState.equals(savedState)) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            AuthorizationCodeCredentials codeCredentials = Authorization.getAccessRefreshToken(code);
            // Store access token and refresh token to cookie
            String accessToken = codeCredentials.getAccessToken();
            Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
            accessTokenCookie.setMaxAge(-1);
            response.addCookie(accessTokenCookie);
            Cookie refreshTokenCookie = new Cookie("refreshToken", codeCredentials.getRefreshToken());
            refreshTokenCookie.setMaxAge(-1);
            response.addCookie(refreshTokenCookie);

            // Save user to session
            UserInfo user = UserInfoDAO.getCurrentUserInfo(accessToken);
            request.getSession().setAttribute("user", user);

            response.sendRedirect("./");
        } catch (IOException | ParseException | SpotifyWebApiException e) {
            e.printStackTrace();
            request.getSession().invalidate();
            request.setAttribute("message", "Something went wrong. Please login again");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
