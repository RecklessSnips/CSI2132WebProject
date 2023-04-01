package Servlet;

import Databases.*;
import Entities.Person;
import Entities.Room;
import Utilities.Address;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/employee")
public class EmployeeServlet extends HttpServlet {

    Database db;
    AccountAccessor accountAccessor;
    BookingAccessor bookingAccessor;
    HotelAccessor hotelAccessor;
    RoomAccessor roomAccessor;

    @Override
    public void init() throws ServletException {
        db = new Database();
        db.connect();
        accountAccessor = new AccountAccessor(db);
        bookingAccessor = new BookingAccessor(db);
        hotelAccessor = new HotelAccessor(db);
        roomAccessor = new RoomAccessor(db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("hotelChains", hotelAccessor.getHotelChains());
        req.getRequestDispatcher("/employeeBooking.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("enter-name") != null) {
            System.out.println("enter-name");
            String firstName = req.getParameter("first-name");
            String lastName = req.getParameter("last-name");

            ArrayList<Person> persons = accountAccessor.getListOfPersonWithNames(firstName, lastName);
            if(persons == null) { persons = new ArrayList<>(); }

            req.setAttribute("profiles", persons);
        }
        if(req.getParameter("select-address") != null) {
            System.out.println("select-address");

            int personId = Integer.parseInt(req.getParameter("select-address"));
            req.setAttribute("bookings", bookingAccessor.getBookingOfPerson(personId));
        }
        if(req.getParameter("select-booking") != null) {
            System.out.println("select-booking");

            int bookingId = Integer.parseInt(req.getParameter("select-booking"));
            bookingAccessor.transferBookingToRenting(bookingId);
        }

        if(req.getParameter("create-profile") != null) {
            System.out.println("create-profile");

            String firstName = req.getParameter("first-name");
            String lastName = req.getParameter("last-name");
            String address = req.getParameter("address");

            // Address should have proper fields!!!
            Person person = new Person(firstName, lastName, new Address(0, "street", null, "Ottawa", "ON", "Canada"));
            int personId = accountAccessor.createNewPerson(person);


            Date checkInDate = Date.valueOf(req.getParameter("check-in"));
            Date checkOutDate = Date.valueOf(req.getParameter("check-out"));

            int roomNumber = Integer.parseInt(req.getParameter("room-number"));
            int hotelChain = Integer.parseInt(req.getParameter("hotel-chain"));

            req.getSession().setAttribute("employee-set-person-id", personId);
            req.getSession().setAttribute("employee-set-check-in", checkInDate);
            req.getSession().setAttribute("employee-set-check-out", checkOutDate);
            req.getSession().setAttribute("employee-set-room-number", roomNumber);

            req.setAttribute("hotels", hotelAccessor.getHotelsFromChain(hotelChain));
        }
        if(req.getParameter("select-hotel") != null) {
            System.out.println("select-hotel");

            int hotelId = Integer.parseInt(req.getParameter("select-hotel"));
            int personId = (int)req.getSession().getAttribute("employee-set-person-id");
            Date checkInDate = (Date)req.getSession().getAttribute("employee-set-check-in");
            Date checkOutDate = (Date)req.getSession().getAttribute("employee-set-check-out");
            int roomNumber = (int)req.getSession().getAttribute("employee-set-room-number");

            Room room = roomAccessor.getRoomFromHotelAndNumber(roomNumber, hotelId);

            bookingAccessor.createNewBooking(room.getRoomId(), personId, checkInDate, checkOutDate);
        }

        req.setAttribute("hotelChains", hotelAccessor.getHotelChains());
        req.getRequestDispatcher("/employeeBooking.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        db.disconnect();
        super.destroy();
    }
}
