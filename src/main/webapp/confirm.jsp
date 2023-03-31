<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
	<title>Hotel Rental Confirmation</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style>

	</style>
</head>
<body>
<%
	String username = "";
	session = request.getSession(false);
	if (session != null) {
		username = (String)session.getAttribute("username");
	}

	Hotel hotel = request.getAttribute("hotel");
	Room hotel = request.getAttribute("room");

%>
	<div class="container">
		<h1>Hotel Rental Confirmation</h1>
		<table>
            <tr>
				<th>Address:</th>
				<td>123 Database Street</td>
			</tr>
			<tr>
				<th>Room Capacity:</th>
				<td>2 adults, 1 child</td>
			</tr>
			<tr>
				<th>Area:</th>
				<td>Downtown</td>
			</tr>
			<tr>
				<th>Hotel Chain:</th>
				<td>Marriott</td>
			</tr>
			<tr>
				<th>Category:</th>
				<td>4 stars</td>
			</tr>
			<tr>
				<th>Price:</th>
				<td>$150 per night</td>
			</tr>
		</table>
		 <div class="signup-form">
        		<form action="/confirm" method="post">
                      <h1>Payment</h1>
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

        		<p>Thank you for choosing our hotel. We look forward to your stay.</p>
        		<div>
        			<button class="btn" onclick="location.href='search.jsp'">Back</button>
        			<button class="btn" onclick="location.href='search.jsp'">Confirm</button>
        			<button type="submit" class="btn" onclick="location.href='search.jsp'">Confirm</button>
        		</div>
        		</form>
        		</div>
		<p><% System.out.println(username + ", "); %>Thank you for choosing our hotel. We look forward to your stay.</p>
		<div>
			<button class="btn" onclick="location.href='search.jsp'">Back</button>
			<button class="btn" onclick="location.href='search.jsp'">Confirm</button>
		</div>
	</div>
</body>
</html>
