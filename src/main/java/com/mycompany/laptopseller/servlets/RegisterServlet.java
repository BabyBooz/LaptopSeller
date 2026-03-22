package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.dao.UserDAO;
import com.mycompany.laptopseller.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    // Hiển thị trang đăng ký
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/register.jsp").forward(request, response);
    }
    
    // Xử lý đăng ký tài khoản mới
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        
        UserDAO userDAO = new UserDAO();
        
        if (userDAO.usernameExists(username)) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
            return;
        }
        
        // Auto-generate roll number: CUST + timestamp
        String rollNumber = "CUST" + System.currentTimeMillis();
        
        User user = new User();
        user.setRollNumber(rollNumber);
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRole("customer");
        
        if (userDAO.register(user)) {
            request.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Đăng ký thất bại");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
        }
    }
}
