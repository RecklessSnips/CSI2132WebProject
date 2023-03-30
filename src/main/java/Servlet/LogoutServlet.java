package Servlet;

import Databases.AccountAccessor;
import Databases.Database;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Not sure if we should get anything

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("JSESSIONID")) {
                    System.out.println("Disconnected JSESSIONID = " + cookie.getValue());
                    break;
                }
            }
        }
        // Invalidate the session if exists
        HttpSession session = req.getSession(false);
        System.out.println("User disconnected : " + session.getAttribute("username"));
        if(session != null){
            session.invalidate();
        }
        resp.sendRedirect("index.jsp");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
