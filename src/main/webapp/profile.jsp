<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>User Profile</title>
	<style>
		body {
            margin: 0;
			font-family: Arial, sans-serif;
			background-color: #f2f2f2;
		}
		.container {
			margin: 0 auto;
			max-width: 800px;
			padding: 20px;
		}
		.title {
			text-align: center;
			padding: 20px;
            background-color: #0b6ed1;
            font-family: Arial, sans-serif;
            color: white;
            font-size: 32px;
		}
        
		table {
			width: 100%;
			border-collapse: collapse;
			margin-top: 20px;
		}
		th, td {
			padding: 8px;
			text-align: left;
			border-bottom: 1px solid #ddd;
		}
		th {
			background-color: #f2f2f2;
		}
		tr:hover {
			background-color: #f5f5f5;
		}
        .remove-button {
			background-color: #0b6ed1;
			color: white;
			border: none;
			padding: 5px 10px;
			border-radius: 3px;
			cursor: pointer;
		}
        .back-button {
			background-color: #0b6ed1;
			color: white;
			border: none;
			padding: 5px 10px;
			border-radius: 3px;
			cursor: pointer;
			position: absolute;
			bottom: 20px;
			left: 20px;
		}
        .edit-personal-info-button{
            background-color: #0b6ed1;
			color: white;
			border: none;
			padding: 5px 10px;
			border-radius: 3px;
			cursor: pointer;
        }
        .delete-profile-button{
            background-color: #c33434;
			color: white;
			border: none;
			padding: 5px 10px;
			border-radius: 3px;
			cursor: pointer;
            margin: 1rem 0;
        }
	</style>
</head>
<body>
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
                    <th>Will</th>
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
        <button class="back-button" onclick="location.href='search.jsp'">Back</button>
	</div>
</body>
</html>
