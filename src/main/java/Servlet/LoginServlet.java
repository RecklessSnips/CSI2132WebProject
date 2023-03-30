package Servlet;

import Databases.AccountAccessor;
import Databases.Database;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    Database db;
    AccountAccessor accountAcc;

    @Override
    public void init() throws ServletException {
        db = new Database();
        db.connect();
        accountAcc = new AccountAccessor(db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // This is how to provide data with the request:
        //Object data = "Some data, can be a String or a Javabean";
        //req.setAttribute("data", data);
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // To try out:
        //"matkirk"
        //"joebiden.26"

        // get request parameters for userID and password
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(accountAcc.checkIfLogInIsValid(username, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            session.setAttribute("accountId", accountAcc.getAccountIdFromUsername(username));
            session.setMaxInactiveInterval(30*60);

            Cookie usernameCookie = new Cookie("username", username);
            resp.addCookie(usernameCookie);

            String encodedURL = resp.encodeRedirectURL("/");

            req.getSession().setAttribute("failedLogin", false);
            resp.sendRedirect(encodedURL);
        }
        else
        {
            req.getSession().setAttribute("failedLogin", true);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        System.out.println("Destroyed");
        db.disconnect();
        super.destroy();
    }
}
