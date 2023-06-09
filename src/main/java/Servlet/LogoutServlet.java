package Servlet;

import Databases.AccountAccessor;
import Databases.Database;
import Databases.HotelAccessor;
import Databases.RoomAccessor;
import Entities.HotelType;
import Entities.RoomDisplay;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    RoomAccessor roomAccessor;
    HotelAccessor hotelAccessor;
    Database db;
    @Override
    public void init() throws ServletException {
        db = new Database();
        db.connect();
        hotelAccessor = new HotelAccessor(db);
        roomAccessor =  new RoomAccessor(db);
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Not sure if we should get anything

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {

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
        super.destroy();
    }
}
