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

        if(req.getAttribute("roomId") == null) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        int confirmedRoom = (int)req.getAttribute("roomId");

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
