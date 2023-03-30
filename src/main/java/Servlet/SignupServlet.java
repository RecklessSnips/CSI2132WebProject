package Servlet;

import Databases.AccountAccessor;
import Databases.Database;
import Entities.Account;
import Entities.AccountCredential;
import Entities.AccountType;
import Entities.Person;
import Utilities.Address;
import Utilities.Pair;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(urlPatterns = "/signup")
public class SignupServlet extends HttpServlet {
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
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phone-number");
        String SSN = req.getParameter("ssn");
        String address = req.getParameter("address");

        AccountCredential accountCredential = new AccountCredential(0, username, password);
        Account account = new Account(0, username, SSN, AccountType.Client);
        Address parsedAddress = Address.parseSQLAddress(address);
        Person person = new Person(firstName, lastName, parsedAddress);


        req.setAttribute("message", "Account created successfully.");
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("Destroyed");
        db.disconnect();
        super.destroy();
    }

}
