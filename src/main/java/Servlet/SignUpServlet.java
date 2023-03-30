package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(urlPatterns = "/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
        req.getRequestDispatcher("/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration<String> enumeration = req.getParameterNames();
        while (enumeration.hasMoreElements()){
            String param = enumeration.nextElement();
            System.out.printf("%s %s\n", param, req.getParameter(param));
        }
        System.out.println("Method: " + req.getMethod());
        System.out.println("URL: " + req.getQueryString());
        req.getRequestDispatcher("/signup.jsp").forward(req, resp);
    }
}
