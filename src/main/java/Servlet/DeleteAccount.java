package Servlet;

import Databases.AccountAccessor;
import Databases.Database;
import Databases.HotelAccessor;
import Databases.RoomAccessor;
import Entities.HotelType;
import Entities.RoomDisplay;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/deleteAccount")
public class DeleteAccount extends HttpServlet {

    Database db;
    AccountAccessor accountAcc;
    RoomAccessor roomAccessor;
    HotelAccessor hotelAccessor;
    @Override
    public void init() throws ServletException {
        db = new Database();
        db.connect();
        accountAcc = new AccountAccessor(db);
        hotelAccessor = new HotelAccessor(db);
        roomAccessor =  new RoomAccessor(db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            try {
                if (req.getSession().getAttribute("accountId") != null) {
                    int accountId = (int) req.getSession().getAttribute("personId");
                    accountAcc.deleteAccount(accountId);
                }
            } catch (Exception e) {
            }

            // Invalidate the session if exists
            HttpSession session = req.getSession(false);
            System.out.println("User disconnected : " + session.getAttribute("username"));
            if (session != null) {
                session.invalidate();
            }

            resp.sendRedirect("/");
        }
        catch (Exception e) {}

    }

    @Override
    public void destroy() {
        System.out.println("Destroyed");
        db.disconnect();
        super.destroy();
    }
}
