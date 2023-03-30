<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Utilities.*, Entities.*" %>
<!DOCTYPE html>
<html>
<head>
	<title>User Profile</title>
	<link rel="stylesheet" type="text/css" href="profile.css">
</head>
<body>
    <%
        Pair<Account, Person> accPair = (Pair<Account, Person>)request.getAttribute("accountPersonPair");
        String firstName = accPair.getSecond().getFirstName();
    %>
    <div class="title">
        User Profile
    </div>
	<div class="container">
		<h2>Personal Infomation</h2>
        <table>
            <thead>

            </thead>
            <tbody>
                <tr>
                    <th>First Name</th>
                    <th><%=firstName%></th>
                    <th><button class="edit-personal-info-button">Edit</button></th>
                </tr>
                <tr>
                    <th>Last Name</th>
                    <th>Smith</th>
                    <th><button class="edit-personal-info-button">Edit</button></th>
                </tr>
                <tr>
                    <th>Address</th>
                    <th>1161 Indiana Avenue, Wahiawa, HI, USA</th>
                    <th><button class="edit-personal-info-button">Edit</button></th>
                </tr>
                <tr>
                    <th>SSN/SIN</th>
                    <th>***-***-234</th>
                    <th><button class="edit-personal-info-button">Edit</button></th>
                </tr>
            </tbody>
            
        </table>
        <th><button class="delete-profile-button">Delete Profile</button></th>
		<h2>Hotel Bookings</h2>
		<table>
			<thead>
				<tr>
					<th>Hotel Name</th>
					<th>Check-in Date</th>
					<th>Check-out Date</th>
					<th>Number of Guests</th>
					<th>Room Type</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>The Ritz Carlton</td>
					<td>2023-04-01</td>
					<td>2023-04-05</td>
					<td>2</td>
					<td>Deluxe King Room</td>
                    <td><button class="remove-button">Remove</button></td>
				</tr>
				<tr>
					<td>The Four Seasons</td>
					<td>2023-05-15</td>
					<td>2023-05-20</td>
					<td>3</td>
					<td>Grand Suite</td>
                    <td><button class="remove-button">Remove</button></td>
				</tr>
				<tr>
					<td>The W Hotel</td>
					<td>2023-06-10</td>
					<td>2023-06-15</td>
					<td>2</td>
					<td>Spectacular Room</td>
                    <td><button class="remove-button">Remove</button></td>
				</tr>
			</tbody>
            
		</table>
        <button class="back-button" onclick="location.href='index.jsp'">Back</button>
	</div>
</body>
</html>
