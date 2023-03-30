<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Hotel Rental Confirmation</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f2f2f2;
		}
		.container {
			margin: 50px auto;
			padding: 20px;
			background-color: #fff;
			box-shadow: 0px 0px 10px #888;
			max-width: 600px;
			text-align: center;
		}
		h1 {
			font-size: 36px;
			margin-bottom: 30px;
		}
		table {
			margin: 0 auto;
			border-collapse: collapse;
			width: 100%;
			max-width: 500px;
			text-align: left;
		}
		th, td {
			padding: 10px;
			border: 1px solid #ddd;
		}
		th {
			background-color: #f2f2f2;
			font-weight: bold;
		}
		.btn {
			display: inline-block;
			margin: 10px;
			padding: 10px 20px;
			background-color: #007bff;
			color: #fff;
			border: none;
			border-radius: 5px;
			cursor: pointer;
			transition: background-color 0.3s ease;
		}
		.btn:hover {
			background-color: #0062cc;
		}
	</style>
</head>
<body>
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
		<p>Thank you for choosing our hotel. We look forward to your stay.</p>
		<div>
			<button class="btn" onclick="location.href='search.jsp'">Back</button>
			<button class="btn" onclick="location.href='search.jsp'">Confirm</button>
		</div>
	</div>
</body>
</html>
