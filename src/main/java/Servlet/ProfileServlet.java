package Servlet;

import Databases.BookingAccessor;
import Entities.BookingDisplay;
import Utilities.Pair;
import Databases.AccountAccessor;
import Databases.Database;
import Entities.Account;
import Entities.Person;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {

    Database db;
    AccountAccessor accountAcc;
    BookingAccessor bookingAcc;

    @Override
    public void init() throws ServletException {
        db = new Database();
        db.connect();
        accountAcc = new AccountAccessor(db);
        bookingAcc = new BookingAccessor(db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Pair<Account, Person> accPair = null;
        ArrayList<BookingDisplay> bookings = null;

        Object accountIdAttributeObj = req.getSession().getAttribute("accountId");
        if(accountIdAttributeObj != null) {
            int accountId = (int)accountIdAttributeObj;
            if(accountId != 0) {
                accPair = accountAcc.getAccountPersonPair(accountId);
                bookings = bookingAcc.getBookingOfPerson(accountId);
            }
        }

        req.setAttribute("accountPersonPair", accPair);
        req.setAttribute("bookings", bookings);
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("RemoveButton") != null) {
            String[] buttonParams = req.getParameter("RemoveButton").split("-");
            try {
                int bookingId = Integer.parseInt(buttonParams[buttonParams.length - 1]);
                if(req.getSession().getAttribute("personId") != null) {
                    int personId = (int)req.getSession().getAttribute("personId");
                    bookingAcc.deleteBookingIfBelongsToPersonId(personId, bookingId);
                }
            }
            catch (Exception e) {}
        }

        // Refresh the page
        doGet(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("Destroyed");
        db.disconnect();
        super.destroy();
    }
}
