package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.dao.UserDAO;
import com.mycompany.laptopseller.models.User;
import com.mycompany.laptopseller.utils.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AuthUtil.getAuthenticatedUser(request);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User currentUser = AuthUtil.getAuthenticatedUser(request);
        
        String rollNumber = request.getParameter("rollNumber");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String newPassword = request.getParameter("newPassword");
        
        currentUser.setRollNumber(rollNumber);
        currentUser.setFullName(fullName);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);
        
        // Update password if provided
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            currentUser.setPassword(newPassword);
        }
        
        UserDAO userDAO = new UserDAO();
        if (userDAO.updateUser(currentUser)) {
            // Update session with new user data
            AuthUtil.setAuthUser(request, currentUser);
            request.setAttribute("success", "Cập nhật thông tin thành công");
        } else {
            request.setAttribute("error", "Cập nhật thông tin thất bại");
        }
        
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
    }
}
