<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Utilities.*, Entities.*, java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
	<title>User Profile</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>
    <%
        boolean allowedToEdit = false;
        Pair<Account, Person> accPair = (Pair<Account, Person>)request.getAttribute("accountPersonPair");
        ArrayList<BookingDisplay> bookings = (ArrayList<BookingDisplay>)request.getAttribute("bookings");
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
        <div class="form_button_row">
        <form action="/deleteAccount" method="post">
            <th><button class="delete-profile-button">Delete Account</button></th>
           </form>
        <form action="/logout" method="post">
            <th><button class="delete-profile-button" onclick="location.href='/search.jsp'">Log out</button></th>
        </form>
        <%if(accPair != null && accPair.getFirst().getType().getValue() > 0){%>
        <form action="/employee" method="post">
            <th><button class="delete-profile-button" onclick="location.href='/employeeBooking.jsp'">Employee Booking</button></th>
        </form>
        <%}%>
        </div>
		<h2>Hotel Bookings</h2>
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
			    <%if(bookings != null) {
			    for(int i = 0; i < bookings.size(); i++) {%>
				<tr>
					<td><%=bookings.get(i).chainName%></td>
					<td><%=bookings.get(i).address.toString()%></td>
					<td><%=bookings.get(i).startDate.toString()%></td>
					<td><%=bookings.get(i).endDate.toString()%></td>
					<td><%=bookings.get(i).roomCapacity%></td>
					<td><form action="/profile" method="post">
                        <button class="remove-button" type="submit" value=<%="delete-"+bookings.get(i).bookingId%> name="RemoveButton">Remove</button>
                    </form></td>
				</tr>
				<%}
				}%>
			</tbody>
            
		</table>
        <button class="back-button" onclick="location.href='/'">Back</button>
	</div>
	<%}%>
</body>
</html>
