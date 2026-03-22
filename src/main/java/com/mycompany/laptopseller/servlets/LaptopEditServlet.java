package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.dao.BrandDAO;
import com.mycompany.laptopseller.dao.CategoryDAO;
import com.mycompany.laptopseller.dao.LaptopDAO;
import com.mycompany.laptopseller.models.Brand;
import com.mycompany.laptopseller.models.Category;
import com.mycompany.laptopseller.models.Laptop;
import com.mycompany.laptopseller.models.User;
import com.mycompany.laptopseller.utils.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/laptop-edit")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class LaptopEditServlet extends HttpServlet {
    
    // Hiển thị trang chỉnh sửa laptop
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AuthUtil.getAuthenticatedUser(request);
        int laptopId = Integer.parseInt(request.getParameter("id"));
        
        LaptopDAO laptopDAO = new LaptopDAO();
        BrandDAO brandDAO = new BrandDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        
        Laptop laptop = laptopDAO.getLaptopById(laptopId);
        List<Brand> brands = brandDAO.getAllBrands();
        List<Category> categories = categoryDAO.getAllCategories();
        
        request.setAttribute("user", user);
        request.setAttribute("laptop", laptop);
        request.setAttribute("brands", brands);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/views/admin/laptop-edit.jsp").forward(request, response);
    }
}
