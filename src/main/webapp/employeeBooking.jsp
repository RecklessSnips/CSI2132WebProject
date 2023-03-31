<%--
  Created by IntelliJ IDEA.
  User: ahsoka
  Date: 3/30/23
  Time: 10:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee Booking</title>
    <h1>Booking for this Customer</h1>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }

        .container {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            padding: 20px;
            max-width: 400px;
            margin: 50px auto;
        }

        h1,
        h2 {
            text-align: center;
            margin-bottom: 30px;
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        input[type="text"],
        input[type="password"],
        select {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
            width: 100%;
            margin-bottom: 20px;
            box-sizing: border-box;
        }

        button {
            background-color: #0b6ed1;
            color: black;
            padding: 10px 20px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            width: 100%;
            margin: 10px 0;
        }

        button:hover {
            background-color: #0961b9;
        }

        .error {
            background-color: #f44336;
            color: white;
            padding: 10px;
            margin-bottom: 20px;
        }

        /* New styles for layout */
        .container-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            grid-gap: 20px;
            max-width: 800px;
            margin: 50px auto;
        }

        .container-grid div {
            padding: 20px;
        }

        .container-grid .hotel-info {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }

        .container-grid .customer-info {
            background-color: #f2f2f2;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }
    </style>
</head>
<body>
<!-- First Section -->
<div style="position: absolute; top: 0; right: 0">
    <button class="container">Check</button>
</div>

<!-- Second Section -->
<div class="container">
    <h2>Select Hotel Chain and Hotel</h2>
    <label for="hotelChain">Hotel Chain:</label>
    <select id="hotelChain" name="hotelChain">
        <option value="Hilton">Hilton</option>
        <option value="Marriott">Marriott</option>
        <option value="Sheraton">Sheraton</option>
    </select>
    <br />
    <label for="hotel">Hotel:</label>
    <select id="hotel" name="hotel">
        <option value="Hilton Hotel">Hilton Hotel</option>
        <option value="Marriott Hotel">Marriott Hotel</option>
        <option value="Sheraton Hotel">Sheraton Hotel</option>
    </select>
    <br />
    <form>
        <label for="roomNumber">Room Number:</label>
        <input type="text" id="roomNumber" name="roomNumber" />
    </form>
</div>

<!-- Third Section -->
<div class="container">
    <h2>Customer Information</h2>
    <form>
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" />
        <br />
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" />
        <br />
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" />
    </form>
    <button class="container">Confirm</button>
</div>
</body>
</html>
