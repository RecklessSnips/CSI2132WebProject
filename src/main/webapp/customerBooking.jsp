<%--
  Created by IntelliJ IDEA.
  User: ahsoka
  Date: 3/30/23
  Time: 10:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Customer Booking Information</title>
  <style>
    body {
      font-family: Arial, sans-serif;
    }
    table {
      border-collapse: collapse;
      width: 100%;
    }
    th,
    td {
      text-align: left;
      padding: 8px;
      border-bottom: 1px solid #ddd;
    }
    th {
      background-color: #4caf50;
      color: white;
    }
    input[type="text"] {
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-sizing: border-box;
      margin-top: 8px;
      margin-bottom: 8px;
      width: 100%;
    }
    .container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 5vh;
      position: relative;
    }

    #backButton {
      background-color: #4caf50;
      border: none;
      color: white;
      padding: 8px 16px;
      text-align: center;
      text-decoration: none;
      display: inline-block;
      font-size: 16px;
      border-radius: 4px;
      cursor: pointer;
      bottom: 0;
      left: 50%;
      transform: translateX(-50%);
      z-index: 1;
      transition: background-color 0.3s ease;
    }

    #backButton:hover {
      background-color: #3e8e41;
    }
  </style>
</head>
<body>
<h1>All Customer's Booking</h1>
<input type="text" id="search" placeholder="Search..." />
<table id="bookings">
  <thead>
  <tr>
    <th>Name</th>
    <th>Date</th>
    <th>Time</th>
    <th>Service</th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <td>Jane Doe</td>
    <td>2023-04-01</td>
    <td>10:00 AM</td>
    <td>Massage</td>
  </tr>
  <tr>
    <td>John Smith</td>
    <td>2023-04-02</td>
    <td>2:00 PM</td>
    <td>Haircut</td>
  </tr>
  <tr>
    <td>Sarah Johnson</td>
    <td>2023-04-03</td>
    <td>11:00 AM</td>
    <td>Manicure</td>
  </tr>
  <tr>
    <td>Mark Brown</td>
    <td>2023-04-05</td>
    <td>3:30 PM</td>
    <td>Facial</td>
  </tr>
  <tr>
    <td>Amy Lee</td>
    <td>2023-04-07</td>
    <td>9:00 AM</td>
    <td>Massage</td>
  </tr>
  <tr>
    <td>Bob Johnson</td>
    <td>2023-04-08</td>
    <td>1:30 PM</td>
    <td>Haircut</td>
  </tr>
  <tr>
    <td>Emily Brown</td>
    <td>2023-04-09</td>
    <td>4:00 PM</td>
    <td>Pedicure</td>
  </tr>
  <tr>
    <td>James Smith</td>
    <td>2023-04-11</td>
    <td>10:30 AM</td>
    <td>Facial</td>
  </tr>
  <tr>
    <td>Michael Kim</td>
    <td>2023-04-12</td>
    <td>2:00 PM</td>
    <td>Haircut</td>
  </tr>
  <tr>
    <td>Amanda Davis</td>
    <td>2023-04-15</td>
    <td>10:30 AM</td>
    <td>Massage</td>
  </tr>
  <tr>
    <td>Jason Lee</td>
    <td>2023-04-17</td>
    <td>1:00 PM</td>
    <td>Manicure</td>
  </tr>
  <tr>
    <td>Jennifer Chen</td>
    <td>2023-04-19</td>
    <td>3:30 PM</td>
    <td>Haircut</td>
  </tr>
  <tr>
    <td>David Wang</td>
    <td>2023-04-21</td>
    <td>11:00 AM</td>
    <td>Pedicure</td>
  </tr>
  <tr>
    <td>Samantha Rodriguez</td>
    <td>2023-04-23</td>
    <td>9:00 AM</td>
    <td>Massage</td>
  </tr>
  <tr>
    <td>Alexander Martinez</td>
    <td>2023-04-25</td>
    <td>2:30 PM</td>
    <td>Facial</td>
  </tr>
  <tr>
    <td>Nicole Thompson</td>
    <td>2023-04-27</td>
    <td>4:00 PM</td>
    <td>Manicure</td>
  </tr>
  <tr>
    <td>William Davis</td>
    <td>2023-04-29</td>
    <td>11:30 AM</td>
    <td>Haircut</td>
  </tr>
  <tr>
    <td>Maria Hernandez</td>
    <td>2023-05-01</td>
    <td>3:00 PM</td>
    <td>Facial</td>
  </tr>
  </tbody>
</table>

<script>
  const searchInput = document.getElementById("search");
  const bookingsTable = document.getElementById("bookings");

  searchInput.addEventListener("input", () => {
    const searchText = searchInput.value.toLowerCase();
    const rows = bookingsTable.getElementsByTagName("tr");

    for (let i = 0; i < rows.length; i++) {
      let shouldDisplayRow = false;

      for (let j = 0; j < rows[i].cells.length; j++) {
        const cell = rows[i].cells[j];
        if (cell.textContent.toLowerCase().indexOf(searchText) > -1) {
          shouldDisplayRow = true;
          break;
        }
      }

      if (shouldDisplayRow) {
        rows[i].style.display = "";
      } else {
        rows[i].style.display = "none";
      }
    }
  });
  backButton.addEventListener("click", () => {
    history.back();
  });
</script>

<button id="backButton" class="container">back</button>
</body>
</html>
