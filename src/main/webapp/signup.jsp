<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Sign Up</title>
  <link rel="stylesheet" type="text/css" href="signup.css">
</head>
<body>
  <div class="signup-form">
    <h2>Sign Up</h2>
    <form action="profile" onsubmit="return validateForm()">
      <div class="form-group">
        <label for="first-name">First Name</label>
        <input type="text" id="first-name" name="first-name" required>
      </div>
      <div class="form-group">
        <label for="last-name">Last Name</label>
        <input type="text" id="last-name" name="last-name" required>
      </div>
      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" id="username" name="username" required>
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>
      </div>
      <div class="form-group">
        <label for="phone-number">Phone Number</label>
        <input type="tel" id="phone-number" name="phone-number" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" required>
        <small>Format: 123-456-7890</small>
      </div>
      <div class="form-group">
        <label for="ssn">SSN</label>
        <input type="text" id="ssn" name="ssn" pattern="[0-9]{3}-[0-9]{2}-[0-9]{4}" required>
        <small>Format: 123-45-6789</small>
      </div>
      <div class="form-group">
        <label for="address">Address</label>
        <input type="text" id="address" name="address" required>
      </div>
      <button type="submit">Sign Up</button>
<%--      <button type="submit" onclick="location.href='search.jsp'">Sign Up</button>--%>
    </form>
    <button onclick="location.href='index.jsp'">Already have an account?</button>
<%--    <button onclick="location.href='index.jsp'">Already have an account?</button>--%>
  </div>
  <script type="text/javascript">
    function validateForm() {
      var firstName = document.forms["signup-form"]["first-name"].value;
      var lastName = document.forms["signup-form"]["last-name"].value;
      var username = document.forms["signup-form"]["username"].value;
      var password = document.forms["signup-form"]["password"].value;
      var email = document.forms["signup-form"]["email"].value;
      var phoneNumber = document.forms["signup-form"]["phone-number"].value;
      var ssn = document.forms["signup-form"]["ssn"].value;
      var address = document.forms["signup-form"]["address"].value;

      if (firstName.value() == "" || lastName.value() == "" || username.value() == "" || password.value() == "" || email.value() == "" || phoneNumber.value() == "" || ssn.value() == "" || address.value() == "") {
        alert("Please fill out all fields.");
        return false;
      }
      if (firstName.indexOf(' ') >= 0 || lastName.indexOf(' ') >= 0 || username.indexOf(' ') >= 0 || password.indexOf(' ') >= 0 || email.indexOf(' ') >= 0 || phoneNumber.indexOf(' ') >= 0 || ssn.indexOf(' ') >= 0 || address.indexOf(' ') >= 0) {
        alert("Fields cannot contain spaces.");
        return false;
      }
      return true;
    }
  </script>
</body>

</html>
