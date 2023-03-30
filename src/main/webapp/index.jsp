<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="index.css">
  </head>
  <body>
    <div class="container">
    <form action="search.jsp" method="post">
      <h1>Login</h1>
      <div class="error">
        <p>Error message goes here</p>
      </div>
      <label for="username">Username</label>
      <input type="text" id="username" name="username">
      <label for="password">Password</label>
      <input type="password" id="password" name="password">
      <button type="submit" class="login-button">Login</button>
      
    </form>
    <button class="back-button" onclick="location.href='signup.jsp'">Sign Up</button>
  </div>
  </body>
</html>
