package Servlet;

import Databases.AccountDB;
import Databases.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet(urlPatterns = "/login")   //在 web.xml root节点 添加 metadata-complete="false" 才能生效
public class PersonServlet extends HttpServlet {

    Database db;
    AccountDB accountDB;

    public PersonServlet() {
        super();
        System.out.println("person servlet initialized");
    }

    @Override
    public void init() throws ServletException {
        db = new Database();
        accountDB = new AccountDB(db);
        System.out.println("Created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String contextPath = req.getContextPath();
        if (contextPath == null) {
            resp.sendRedirect("/index.jsp");
            return;
        }
        //webapp 下所有的文件都可以访问直接访问，但是要带上 contextPath
        resp.sendRedirect(contextPath + "/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("mock login success!!");  //假装登录成功了

        final String contextPath = Objects.requireNonNullElse(req.getContextPath(), ""); //Objects.requireNonNullElse 是jdk9以后的方法

        resp.sendRedirect(contextPath + "./customer/login.html");
    }

    @Override
    public void destroy() {
        db.disconnect();
        super.destroy();
    }
}
