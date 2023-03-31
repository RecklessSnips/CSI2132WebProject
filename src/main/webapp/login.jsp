<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="login.css">
  </head>
  <body>
    <%
    //allow access only if session exists
    /*if(session.getAttribute("username") == null){
    	response.sendRedirect("login.html");
    }
    String username = null;
    String sessionID = null;
    Cookie[] cookies = request.getCookies();
    if(cookies !=null){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("username")) username = cookie.getValue();
        }
    }*/
    %>

    <div class="container">
    <form action="/login" method="post">
      <h1>Login</h1>
      <% if (request.getAttribute("failedLogin") != null && (boolean)request.getAttribute("failedLogin")) {%>
      <div class="error">
        <p>Either the username and/or password are invalid.</p>
      </div>
      <% } %>
      <label for="username">Username</label>
      <input type="text" id="username" name="username">
      <label for="password">Password</label>
      <input type="password" id="password" name="password">
      <button type="submit" class="login-button">Login</button>
    </form>
    <button class="back-button" onclick="location.href='/'">Back to Search</button>
  </div>
  </body>
</html>
