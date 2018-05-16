package controller;

import model.Users;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterController", urlPatterns = "/register")
public class RegisterController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("btn_register") != null) {
            String firstname = request.getParameter("txt_firstname");
            String lastname = request.getParameter("txt_lastname");
            String username = request.getParameter("txt_username");
            String password = request.getParameter("txt_password");

            Users user = new Users();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setUsername(username);
            user.setPassword(password);

            UserService registerdao = new UserService();
            String registerValidate = registerdao.authorizeRegister(user);

            if (registerValidate.equals("SUCCESS REGISTER")) {
                request.setAttribute("RegiseterSuccessMsg", registerValidate);
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("RegisterErrorMsg", registerValidate);
                RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
                rd.include(request, response);
            }
        }
    }
}