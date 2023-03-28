package Databases;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    private DatabaseAccessor db;

    public void init() {

        db = new DatabaseAccessor();
        if(!db.connect("HotelManager", "5433", "postgres", "admin")) {
            System.out.println("Failed to connect to database!");
        }

        db.printHotelChains();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1> HELLO WORLD!!! </h1>");
        out.println("</body></html>");
    }

    public void destroy() {
        db.disconnect();
    }
}