package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.dao.CategoryDAO;
import com.mycompany.laptopseller.dao.LaptopDAO;
import com.mycompany.laptopseller.models.Category;
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
    
    // Hiển thị trang chủ với danh sách laptop và filter theo category
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AuthUtil.getAuthenticatedUser(request);
        LaptopDAO laptopDAO = new LaptopDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        
        String categoryParam = request.getParameter("category");
        Integer categoryId = null;
        if (categoryParam != null && !categoryParam.isEmpty() && !"all".equals(categoryParam)) {
            categoryId = Integer.parseInt(categoryParam);
        }
        
        List<Laptop> laptops;
        if (categoryId != null) {
            laptops = laptopDAO.getLaptopsByCategory(categoryId);
        } else {
            laptops = laptopDAO.getAllLaptops();
        }
        
        List<Category> categories = categoryDAO.getAllCategories();
        
        request.setAttribute("user", user);
        request.setAttribute("laptops", laptops);
        request.setAttribute("categories", categories);
        request.setAttribute("selectedCategory", categoryParam != null ? categoryParam : "all");
        
        request.getRequestDispatcher("/views/home.jsp").forward(request, response);
    }
}
