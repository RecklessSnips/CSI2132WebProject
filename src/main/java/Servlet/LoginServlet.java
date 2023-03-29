package Servlet;

import Databases.PersonDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/login.jsp")
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        PersonDB personDB = null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        resp.sendRedirect("/CSI2132WebProject_war_exploded/customer");
        System.out.println("post");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
