package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.utils.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    // Xử lý đăng xuất
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthUtil.removeAuthUser(request);
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
