<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="Entities.Room" %>
<%@ page import="Entities.Hotel" %>
<%@ page import="Entities.HotelChain" %>
<!DOCTYPE html>
<html>
<head>
	<title>Hotel Rental Confirmation</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="/css/confirm.css">
</head>
<body>
<%
	Hotel hotel = (Hotel) request.getAttribute("hotel");
	Room room = (Room) request.getAttribute("room");
	HotelChain hotelChain = (HotelChain) request.getAttribute("hotelChain");
%>
	<div class="container">
		<h1>Hotel Rental Confirmation</h1>
		<table>
            <tr>
				<th>Address:</th>
				<td><%=hotel.getAddress().toString()%></td>
			</tr>
			<tr>
				<th>Room Capacity:</th>
				<td><%=room.getRoomCapacity()%></td>
			</tr>
			<tr>
				<th>Area:</th>
				<td><%=hotel.getArea()%></td>
			</tr>
			<tr>
				<th>Hotel Chain:</th>
				<td><%=hotelChain.getChainName() + " "%></td>
			</tr>
			<tr>
				<th>Rating:</th>
				<td><%=hotel.getRating()+" stars"%></td>
			</tr>
			<tr>
				<th>Price:</th>
				<td><%=room.getPricePerNight() + "$ per night"%></td>
			</tr>
		</table>
		 <div class="signup-form">
            <form action="/confirm" method="post">
              <%--@declare id="credit-card-num"--%><%--@declare id="exp-date"--%><%--@declare id="cvv"--%><h1>Payment</h1>
              <% if (request.getAttribute("failedLogin") != null && (boolean)request.getAttribute("failedLogin")) {%>
              <div class="error">
                <p>Either the username and/or password are invalid.</p>
              </div>
              <% } %>
              <label for="credit-card-num">Credit Card</label>
              <input type="text" name="credit-card-num">
              <label for="exp-date">Expiry Date</label>
              <input type="text" name="exp-date">
              <label for="CVV">CVV</label>
              <input type="text" name="CVV">
            </form>
            <button class="btn" button type="submit" >Confirm</button>
        </div>
		<p>Thank you for choosing our hotel. We look forward to your stay.</p>
		<div>
			<button class="btn" onclick="location.href='/'">Back</button>

		</div>
	</div>
</body>
</html>
