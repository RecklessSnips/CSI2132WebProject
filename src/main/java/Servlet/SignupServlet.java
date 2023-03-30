package Servlet;

import Databases.AccountAccessor;
import Databases.Database;
import Entities.Account;
import Entities.AccountCredential;
import Entities.AccountType;
import Entities.Person;
import Utilities.Address;
import Utilities.Pair;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        req.getRequestDispatcher("/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phoneNumber = req.getParameter("phone-number");
        String ssnSin = req.getParameter("ssn");
        String addressDD = req.getParameter("address");
        // Address should come in different pieces

        StringBuilder errorMessage = new StringBuilder("Could not create account: \n");
        boolean signupValid = true;

        if (username == null || username.isEmpty() || username.length() < 5){
            // Failed: empty username or too short
            errorMessage.append("Empty username or username too short.\n");
            signupValid = false;
        }
        else if(accountAcc.getAccountIdFromUsername(username) != 0) {
            // Failed: username already exists
            errorMessage.append("Username already taken.\n");
            signupValid = false;
        }

        if(firstName == null || firstName.isEmpty() || firstName.length() < 2) {
            // Failed: empty/names too short
            errorMessage.append("Empty first name or first name too short.\n");
            signupValid = false;
        }

        if(lastName == null || lastName.isEmpty() || lastName.length() < 2) {
            // Failed: empty/names too short
            errorMessage.append("Empty last name or last name too short.\n");
            signupValid = false;
        }

        if(password == null || password.isEmpty() || passwordValidation(password)) {
            // Failed: invalid password
            errorMessage.append("Invalid password. It should have at least 6 characters, contain letters, numbers and special symbols.\n");
            signupValid = false;
        }

        if(email == null || email.isEmpty() || emailValidation(email)) {
            // Failed: invalid email
            errorMessage.append("Invalid email.\n");
            signupValid = false;
        }

        if(ssnSin == null || ssnSin.isEmpty() || ssnSinValidation(ssnSin)) {
            // Failed: invalid ssn
            errorMessage.append("The SSN/SIN should have this format: XXX-XXX-XXX.\n");
            signupValid = false;
        }

        if(phoneNumber == null || phoneNumber.isEmpty()) {
            // Failed: invalid phone number
            errorMessage.append("Empty phone number field.\n");
            signupValid = false;
        }
        errorMessage.append("Fix the form and try again.");

        req.setAttribute("failedSignup", !signupValid);
        if(!signupValid) {
            req.setAttribute("errorMessage", errorMessage.toString());
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/signup.jsp");
            rd.forward(req, resp);
        }
        else {
            Address address = new Address();
            Person person = new Person(firstName, lastName, address);
            Account account = new Account(username, ssnSin, AccountType.Client);
            AccountCredential accountCredential = new AccountCredential(username, password);

            int accountId = accountAcc.createNewAccount(account, person, accountCredential);

            if(accountId == 0) {
                // Failed, SQL failed somehow, display error
                req.setAttribute("failedSignup", true);
                req.setAttribute("errorMessage", "Account creation failed due to some issue with the server. Contact support.");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/signup.jsp");
                rd.forward(req, resp);
            }
            else {

                // Login using the signup thing.
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                session.setAttribute("accountId", accountId);
                session.setMaxInactiveInterval(30*60);

                Cookie usernameCookie = new Cookie("username", username);
                resp.addCookie(usernameCookie);

                String encodedURL = resp.encodeRedirectURL("/");
                req.getSession().setAttribute("isLoggedIn", true);
                resp.sendRedirect(encodedURL);
                req.getRequestDispatcher("/").forward(req, resp);
            }
        }
    }

    @Override
    public void destroy() {
        System.out.println("Destroyed");
        db.disconnect();
        super.destroy();
    }

    public static boolean passwordValidation(String password)
    {
        if(password.length() < 6) {
            return false;
        }
        Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasLetter = letter.matcher(password);
        Matcher hasDigit = digit.matcher(password);
        Matcher hasSpecial = special.matcher(password);

        return hasLetter.find() && hasDigit.find() && hasSpecial.find();
    }

    public static boolean ssnSinValidation (String ssnSin) {
        Pattern ssnCheck = Pattern.compile("[0-9]{3}-[0-9]{3}-[0-9]{3}");
        return ssnSin.length() == 11 && ssnCheck.matcher(ssnSin).find();
    }

    public static boolean emailValidation (String email) {
        Pattern emailCheck = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        return email.length() >= 5 && emailCheck.matcher(email).find();
    }
}
