package Servlet;

import Utilities.Pair;
import Databases.AccountAccessor;
import Databases.Database;
import Entities.Account;
import Entities.Person;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {

    Database db;
    AccountAccessor accountAcc;

    @Override
    public void init() throws ServletException {
        db = new Database();
        db.connect();
        accountAcc = new AccountAccessor(db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Pair<Account, Person> accPair = null;

        Object accountIdAttributeObj = req.getSession().getAttribute("accountId");
        System.out.println(accountIdAttributeObj);
        if(accountIdAttributeObj != null) {
            int accountId = (int)accountIdAttributeObj;
            if(accountId != 0) {
                accPair = accountAcc.getAccountPersonPair(accountId);
            }
        }

        if(accPair != null) {
            System.out.println("Snn/Sin is " + accPair.getFirst().getSsnSin());
        }
        else
        {
            System.out.println("Could not load profile");
        }

        req.setAttribute("accountPersonPair", accPair);
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    @Override
    public void destroy() {
        System.out.println("Destroyed");
        db.disconnect();
        super.destroy();
    }
}
