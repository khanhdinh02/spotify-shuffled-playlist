package chinriku.spotifysuffledplaylist.servlets;

import chinriku.spotifysuffledplaylist.utils.Authorization;
import java.io.IOException;
import java.net.URI;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

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
        String state = Authorization.generateState();
        // Save state to cookie
        Cookie stateCookie = new Cookie("state", state);
        stateCookie.setMaxAge(-1);
        response.addCookie(stateCookie);
        
        URI uri = Authorization.getCodeURI(state);
        response.sendRedirect(uri.toString());
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
