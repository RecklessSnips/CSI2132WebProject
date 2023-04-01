package Servlet;

import Databases.*;
import Entities.*;
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
    RoomAccessor roomAccessor;
    HotelAccessor hotelAccessor;

    Database db;
    @Override
    public void init() throws ServletException {
        db = new Database();
        db.connect();
        bookingAcc = new BookingAccessor(db);
        roomAccessor = new RoomAccessor(db);
        hotelAccessor = new HotelAccessor(db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Room room = null;
        Hotel hotel = null;
        HotelChain hotelChain = null;

        if(req.getSession().getAttribute("confirmedRoomId") == null) {
            System.out.println("RoomID is null");
            resp.sendRedirect("/");
            return;
        }

        int confirmedRoom = (int)req.getSession().getAttribute("confirmedRoomId");

        room = roomAccessor.getRoomFromId(confirmedRoom);
        hotel = hotelAccessor.getHotelFromId(room.getHotelId());
        hotelChain = hotelAccessor.getHotelChainFromId(hotel.getChainId());

        // Not sure if we should get anything
        req.setAttribute("room", room);
        req.setAttribute("hotel", hotel);
        req.setAttribute("hotelChain", hotelChain);
        req.getRequestDispatcher("/confirm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Creating booking");

        int roomId = (int)req.getSession().getAttribute("confirmedRoomId");
        int personId = (int)req.getSession().getAttribute("personId");
        Date startDate = (Date)req.getSession().getAttribute("confirmedStartDate");
        Date endDate = (Date)req.getSession().getAttribute("confirmedEndDate");
        bookingAcc.createNewBooking(roomId, personId, startDate, endDate);

        resp.sendRedirect("/profile");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
