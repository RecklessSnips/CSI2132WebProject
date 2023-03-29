package Servlet;

import Databases.AccountDB;
import Databases.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebServlet(urlPatterns = "/loginn")
public class PersonServlet extends HttpServlet {

    Database db;
    AccountDB accountDB;

    @Override
    public void init() throws ServletException {
        db = new Database();
        accountDB = new AccountDB(db);
        System.out.println("Created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req, resp);
        System.out.println("hi");

        accountDB.checkIfLogInIsValid("username", "password");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        System.out.println("post");
        accountDB.checkIfLogInIsValid("username", "password");
        resp.sendRedirect("/CSI2132WebProject_war_exploded/customer/loginn.html");
    }

    @Override
    public void destroy() {
        System.out.println("Destroyed");
        db.disconnect();
        super.destroy();
    }
}
