package Servlet;

import Databases.AccountDB;
import Databases.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/person")
public class PersonServlet extends HttpServlet {

    Database db;
    AccountDB accountDB;

    @Override
    public void init() throws ServletException {
        db = new Database();
        accountDB = new AccountDB(db);
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
    }

    @Override
    public void destroy() {

        db.disconnect();
        super.destroy();
    }
}
