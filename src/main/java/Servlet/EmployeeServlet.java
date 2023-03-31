package Servlet;

import Databases.AccountAccessor;
import Databases.Database;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = "/employee")
public class EmployeeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Not sure if we should get anything

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String username = req.getParameter("accountId");
//        String password = req.getParameter("password");
//
//        if(accountAcc.checkIfLogInIsValid(username, password)) {
//            HttpSession session = req.getSession();
//            session.setAttribute("username", username);
//            session.setAttribute("accountId", accountAcc.getAccountIdFromUsername(username));
//            session.setMaxInactiveInterval(30*60);
//
//            Cookie usernameCookie = new Cookie("username", username);
//            resp.addCookie(usernameCookie);
//
//            String encodedURL = resp.encodeRedirectURL("/");
//
//            req.setAttribute("failedLogin", false);
//            req.getSession().setAttribute("isLoggedIn", true);
//            resp.sendRedirect(encodedURL);
//        }
//        else
//        {
//            req.setAttribute("failedLogin", true);
//            req.getSession().setAttribute("isLoggedIn", false);
//            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
//            rd.forward(req, resp);
//        }
        req.getSession().getAttribute("accountId");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
