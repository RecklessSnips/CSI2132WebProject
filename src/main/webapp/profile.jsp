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
        boolean allowedToEdit = false;
        Pair<Account, Person> accPair = (Pair<Account, Person>)request.getAttribute("accountPersonPair");
    %>
    <div class="title">
        User Profile
    </div>
    <%if(accPair != null) {

        String[] split = accPair.getFirst().getSsnSin().split("-");
        String censoredSsnSin =
        "*".repeat(split[0].length()) + "-" +
        "*".repeat(split[1].length()) + "-" +
        split[2];

    %>
	<div class="container">
		<h2>Personal Infomation</h2>
        <table>
            <thead>
            </thead>
            <tbody>
                <tr>
                    <th>First Name</th>
                    <th><%=accPair.getSecond().getFirstName()%></th>
                    <%if(allowedToEdit) {%>
                        <th><button class="edit-personal-info-button">Edit</button></th>
                    <%}%>
                </tr>
                <tr>
                    <th>Last Name</th>
                    <th><%=accPair.getSecond().getLastName()%></th>
                    <%if(allowedToEdit) {%>
                        <th><button class="edit-personal-info-button">Edit</button></th>
                    <%}%>
                </tr>
                <tr>
                    <th>Address</th>
                    <th><%=accPair.getSecond().getAddress().toString()%></th>
                    <%if(allowedToEdit) {%>
                        <th><button class="edit-personal-info-button">Edit</button></th>
                    <%}%>
                </tr>
                <tr>
                    <th>SSN-SIN</th>
                    <th><%=censoredSsnSin%></th>
                    <%if(allowedToEdit) {%>
                        <th><button class="edit-personal-info-button">Edit</button></th>
                    <%}%>
                </tr>
            </tbody>
            
        </table>
        <th><button class="delete-profile-button">Delete Account (NOT IMPLEMENTED YET)</button></th>
        <form action="/logout" method="post">
            <th><button class="delete-profile-button" onclick="location.href='/search.jsp'">Log out</button></th>
        </form>
		<h2>Hotel Bookings</h2>
		<table>
			<thead>
				<tr>
					<th>Hotel Chain Name</th>
					<th>Address</th>
					<th>Check-in Date</th>
					<th>Check-out Date</th>
					<th>Number of Guests</th>
					<th>Room Size</th>
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
        <button class="back-button" onclick="location.href='/'">Back</button>
	</div>
	<%}%>
</body>
</html>
