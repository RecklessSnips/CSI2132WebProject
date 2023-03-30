package Servlet;

import Databases.AccountAccessor;
import Databases.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    Database db;
    AccountAccessor accountAcc;

    @Override
    public void init() throws ServletException {
        db = new Database();
        accountAcc = new AccountAccessor(db);
        System.out.println("Created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req, resp);
        System.out.println("hi");
        accountAcc.checkIfLogInIsValid("username", "password");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        System.out.println("post");
        accountAcc.checkIfLogInIsValid("username", "password");
        resp.sendRedirect("/CSI2132/customer/loginn.html");
    }

    @Override
    public void destroy() {
        System.out.println("Destroyed");
        db.disconnect();
        super.destroy();
    }
}
