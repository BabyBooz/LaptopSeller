package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.dao.LaptopDAO;
import com.mycompany.laptopseller.models.Laptop;
import com.mycompany.laptopseller.models.User;
import com.mycompany.laptopseller.utils.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AuthUtil.getAuthenticatedUser(request);
        LaptopDAO laptopDAO = new LaptopDAO();
        List<Laptop> laptops = laptopDAO.getAllLaptops();
        
        request.setAttribute("user", user);
        request.setAttribute("laptops", laptops);
        request.getRequestDispatcher("/views/home.jsp").forward(request, response);
    }
}
