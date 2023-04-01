<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Utilities.*, Entities.*, java.util.ArrayList, java.sql.Date"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Employee Booking Service</title>
        <link rel="stylesheet" type="text/css" href="css/employee.css">
    </head>
  <body>
    <header>
        <h1>Employee Booking Service</h1>
        <div class="login-buttons">
            <a href="profile"><button>Profile</button></a>
        </div>
    </header>
        <div class="container">
            <div class="renting-method-container">
                <h1>Convert Booking to Renting</h1>

                <h3>1. Enter Information</h3>
                <form method="post">
                <div class="form-group">
                    <label for="first-name">First Name</label>
                    <input type="text" name="first-name">
                </div>
                <div class="form-group">
                    <label for="last-name">Last Name</label>
                    <input type="text" name="last-name">
                </div>
                <button type="submit" name="enter-name">Enter Names</button>
                </form>

                <%
                ArrayList<Person> persons = (ArrayList<Person>)request.getAttribute("profiles");
                %>

                <h3>2. Select Profile</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        if(persons != null){
                            for(int i = 0; i < persons.size(); i++) {
                        %>
                                <tr>
                                    <td><%=persons.get(i).getAddress().toString()%></td>
                                    <td><form method="post"><button type="submit" value="<%=persons.get(i).getPersonId()%>" name="select-address">Select</button></form></td>
                                </tr>
                        <%
                            }
                        }
                        %>
                    </tbody>
                </table>

                <h3>3. Select Booking</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Hotel Chain Name</th>
                            <th>Address</th>
                            <th>Check-in Date</th>
                            <th>Check-out Date</th>
                            <th>Room Size</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                    ArrayList<BookingDisplay> bookings = (ArrayList<BookingDisplay>)request.getAttribute("bookings");
                    if(bookings != null) {
                        for(int i = 0; i < bookings.size(); i++) {%>
                        <tr>
                            <td><%=bookings.get(i).chainName%></td>
                            <td><%=bookings.get(i).address.toString()%></td>
                            <td><%=bookings.get(i).startDate.toString()%></td>
                            <td><%=bookings.get(i).endDate.toString()%></td>
                            <td><%=bookings.get(i).roomCapacity%></td>
                            <td><form method="post">
                                <button type="submit" value="<%=bookings.get(i).bookingId%>" name="select-booking">Rent</button>
                            </form></td>
                        </tr>
                        <%}
                    }%>
                    </tbody>
                </table>

            </div>
            <div class="renting-method-container">
                <h1>Create Renting and Profile</h1>

                <h3>1. Create Profile and Enter Information</h3>
                <form method="post">
                    <div class="form-group">
                        <label for="first-name">First Name</label>
                        <input type="text" name="first-name">
                    </div>
                    <div class="form-group">
                        <label for="last-name">Last Name</label>
                        <input type="text" name="last-name">
                    </div>
                    <div class="form-group">
                        <label for="address">Address</label>
                        <input type="text" name="address">
                    </div>
                    <div class="form-group">
                        <label for="check-in">Check-In</label>
                        <input type="date" id="check-in" name="check-in" value="">
                    </div>
                    <div class="form-group">
                        <label for="check-out">Check-Out</label>
                        <input type="date" id="check-out" name="check-out" value="">
                    </div>
                    <div class="form-group">
                        <label for="room-number">Room Number</label>
                        <input type="number" name="room-number">
                    </div>

                    <div class="filter-item">
                        <label for="hotel-chain">Hotel Chain</label>
                        <select name="hotel-chain">
                            <%
                            ArrayList<HotelChain> hotelChains = (ArrayList<HotelChain>)request.getAttribute("hotelChains");
                            if(hotelChains != null) {
                                for(int i = 0; i < hotelChains.size(); i++) {%>
                                    <option value=<%=hotelChains.get(i).getChainId()%>><%=hotelChains.get(i).getChainName()%></option>
                                <%}
                            }%>
                        </select>
                    </div>
                    <button type="submit" name="create-profile">Create Profile</button>
                </form>

                <h3>2. Select Hotel Address</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        ArrayList<Hotel> hotels = (ArrayList<Hotel>)request.getAttribute("hotels");
                        if(hotels != null) {
                        for(int i = 0; i < hotels.size(); i++) {
                        Hotel hotel = hotels.get(i);
                        %>
                        <tr>
                            <td><%=hotel.getAddress().toString()%></td>
                            <td><form method="post"><button class="select-button" type="submit" value="<%=hotel.getHotelId()%>" name="select-hotel">Select</button></form></td>
                        </tr>
                        <%}}%>
                    </tbody>
                </table>


            </div>
        </div>
    </body>
</html>
