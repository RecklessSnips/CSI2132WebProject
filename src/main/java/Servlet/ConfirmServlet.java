package Servlet;

import Databases.AccountAccessor;
import Databases.BookingAccessor;
import Databases.Database;
import Entities.Account;
import Entities.Booking;
import Entities.Person;
import Utilities.Pair;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet(urlPatterns = "/confirm")
public class ConfirmServlet extends HttpServlet {

    BookingAccessor bookingAcc;
    AccountAccessor accountAccessor;
    Database db;
    @Override
    public void init() throws ServletException {
        db = new Database();
        db.connect();
        bookingAcc = new BookingAccessor(db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Not sure if we should get anything

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pair<Account, Person> accPair = null;
        Object accountIdAttributeObj = req.getSession().getAttribute("accountId");
        System.out.println(accountIdAttributeObj);
        if(accountIdAttributeObj != null) {
            int accountId = (int)accountIdAttributeObj;
            if(accountId != 0) {
                accPair = accountAccessor.getAccountPersonPair(accountId);
            }
        }

//        Come back to this with room id ready in session
//        booking = new Booking (roomId, accPair.getSecond().getPersonId(),startDate,endDate)
//
//        bookingAcc.createNewBooking()


    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
