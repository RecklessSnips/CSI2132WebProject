package Servlet;

import Databases.AccountAccessor;
import Databases.Database;
import Databases.HotelAccessor;
import Databases.RoomAccessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(urlPatterns = "/")
public class IndexServlet extends HttpServlet {

    Database db;
    RoomAccessor roomAccessor;
    HotelAccessor hotelAccessor;

    @Override
    public void init() throws ServletException {
        db = new Database();
        db.connect();
        roomAccessor = new RoomAccessor(db);
        hotelAccessor = new HotelAccessor(db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String checkInDate = req.getParameter("check-in");
            String checkOutDate = req.getParameter("check-out");
            String category = req.getParameter("category");
            int roomCapacity = 0;
            boolean roomCapacityEnabled = false;
            try {
                roomCapacity = Integer.parseInt(req.getParameter("room-capacity"));
            }
            catch(Exception e) {
                roomCapacityEnabled = false;
            }
            String area = req.getParameter("area");
            String hotelChain = req.getParameter("hotel-chain");
            String location = req.getParameter("location");
            String priceMin = req.getParameter("price-min");
            String priceMax = req.getParameter("price-max");


            boolean categoryEnabled = !category.equals("");
            boolean areaEnabled = !area.equals("");
            boolean chainEnabled = !hotelChain.equals("");
            boolean priceEnabled = !priceMax.equals("") || !priceMin.equals("");
            boolean timeEnabled = !checkOutDate.equals("") || !checkInDate.equals("");

//            roomAccessor.getRoomsWithConditions(categoryEnabled,
//                    areaEnabled,
//                    roomCapacityEnabled,
//                    false,
//                    priceEnabled,
//                    timeEnabled,
//                    category,
//                    area,
//                    roomCapacity,
//                    1, );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
