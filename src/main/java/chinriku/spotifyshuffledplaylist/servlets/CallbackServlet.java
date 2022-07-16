package chinriku.spotifyshuffledplaylist.servlets;

import chinriku.spotifyshuffledplaylist.utils.Authorization;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String savedState = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("state")) {
                savedState = c.getValue();
            }
        }

        // Return login page if auth code or state is invalid
        if (code == null || code.isEmpty() || reqState == null || !reqState.equals(savedState)) {
            response.sendRedirect("login.jsp");
            return;
        }

        AuthorizationCodeCredentials codeCredentials = Authorization.getAccessRefreshToken(code);
        // Store access token and refresh token to cookie
        if (codeCredentials != null) {
            Cookie accessTokenCookie = new Cookie("accessToken", codeCredentials.getAccessToken());
            accessTokenCookie.setMaxAge(-1);
            response.addCookie(accessTokenCookie);
            Cookie refreshTokenCookie = new Cookie("refreshToken", codeCredentials.getRefreshToken());
            refreshTokenCookie.setMaxAge(-1);
            response.addCookie(refreshTokenCookie);
            response.sendRedirect("./");
        } else {
            response.sendRedirect("login.jsp");
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
