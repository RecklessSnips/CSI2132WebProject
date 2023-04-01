package Servlet;

import Databases.AccountAccessor;
import Databases.Database;
import Databases.HotelAccessor;
import Databases.RoomAccessor;
import Entities.Hotel;
import Entities.HotelType;
import Entities.Room;
import Entities.RoomDisplay;
import Utilities.Pair;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;

@WebServlet(urlPatterns = "")
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

        ArrayList<RoomDisplay> rooms = roomAccessor.getRoomsWithConditions(
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                HotelType.Default,
                "",
                0,
                0,
                0,
                0,
                Date.valueOf("1970-1-1"),
                Date.valueOf("1970-1-1"));
        if(rooms == null) {
            rooms = new ArrayList<>();
        }

        req.setAttribute("rooms", rooms);
        req.setAttribute("hotelChains", hotelAccessor.getHotelChains());
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getParameter("BookButton") != null) {
            System.out.println(req.getParameter("BookButton"));
            String[] buttonParams = req.getParameter("BookButton").split("-");
            try {
                int roomId = Integer.parseInt(buttonParams[buttonParams.length - 1]);
                req.getSession().setAttribute("confirmedRoomId", roomId);
                resp.sendRedirect("/confirm");
            }
            catch (Exception e) {}
            return;
        }
        if(req.getParameter("ApplyFilters") != null) {
            try {

                boolean categoryEnabled = true;
                boolean chainEnabled = true;
                boolean roomCapacityEnabled = true;
                boolean priceMinEnabled = true;
                boolean priceMaxEnabled = true;
                boolean dateEnabled = true;

                Date checkInDate = Date.valueOf("1970-1-1");
                Date checkOutDate = Date.valueOf("1970-1-1");

                try {
                    checkInDate = Date.valueOf(req.getParameter("check-in"));
                    checkOutDate = Date.valueOf(req.getParameter("check-out"));
                } catch (Exception e) {
                    dateEnabled = false;
                }

                HotelType hotelType = HotelType.Default;
                try {
                    int hotelTypeInt = Integer.parseInt(req.getParameter("category"));
                    if(hotelTypeInt == 0) {
                        categoryEnabled = false;
                    }
                    else {
                        hotelType = HotelType.getEnum(hotelTypeInt);
                    }
                } catch (Exception e) {
                    categoryEnabled = false;
                }

                int roomCapacity = 0;
                try {
                    roomCapacity = Integer.parseInt(req.getParameter("room-size"));
                    if(roomCapacity == 0) {
                        roomCapacityEnabled = false;
                    }
                } catch (Exception e) {
                    roomCapacityEnabled = false;
                }

                int hotelChain = 0;
                try {
                    hotelChain = Integer.parseInt(req.getParameter("hotel-chain"));
                    if(hotelChain == 0) {
                        chainEnabled = false;
                    }
                } catch (Exception e) {
                    chainEnabled = false;
                }

                double priceMin = 0.0;
                try {priceMin = Double.parseDouble(req.getParameter("price-min"));} catch (Exception e) {
                    priceMinEnabled = false;
                }

                double priceMax = 0.0;
                try {priceMax = Double.parseDouble(req.getParameter("price-max"));} catch (Exception e) {
                    priceMaxEnabled = false;
                }

                String area = req.getParameter("area");
                boolean areaEnabled = area != null && !area.equals("");


                ArrayList<RoomDisplay> rooms = roomAccessor.getRoomsWithConditions(
                        categoryEnabled,
                        areaEnabled,
                        roomCapacityEnabled,
                        chainEnabled,
                        priceMinEnabled,
                        priceMaxEnabled,
                        dateEnabled,
                        HotelType.Default,
                        area,
                        roomCapacity,
                        hotelChain,
                        priceMin,
                        priceMax,
                        checkInDate,
                        checkOutDate);
                if(rooms == null) {
                    rooms = new ArrayList<>();
                }

                if(dateEnabled) {
                    req.getSession().setAttribute("confirmedStartDate", checkInDate);
                    req.getSession().setAttribute("confirmedEndDate", checkOutDate);
                }
                if(chainEnabled) {
                    req.setAttribute("chain", hotelChain);
                }
                if(areaEnabled) {
                    req.setAttribute("area", area);
                }
                if(categoryEnabled) {
                    req.setAttribute("category", hotelType.getValue());
                }
                if(roomCapacityEnabled) {
                    req.setAttribute("capacity", roomCapacity);
                }
                if(priceMinEnabled) {
                    req.setAttribute("minPrice", (int)priceMin);
                }
                if(priceMaxEnabled) {
                    req.setAttribute("maxPrice", (int)priceMax);
                }

                req.setAttribute("rooms", rooms);
                req.setAttribute("hotelChains", hotelAccessor.getHotelChains());
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        db.disconnect();
        super.destroy();
    }
}
