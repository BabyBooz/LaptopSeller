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

@WebServlet("/admin/laptops")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AdminLaptopServlet extends HttpServlet {
    
    // Hiển thị danh sách laptop
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AuthUtil.getAuthenticatedUser(request);
        LaptopDAO laptopDAO = new LaptopDAO();
        BrandDAO brandDAO = new BrandDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        
        List<Laptop> laptops = laptopDAO.getAllLaptopsForAdmin();
        List<Brand> brands = brandDAO.getAllBrands();
        List<Category> categories = categoryDAO.getAllCategories();
        
        request.setAttribute("user", user);
        request.setAttribute("laptops", laptops);
        request.setAttribute("brands", brands);
        request.setAttribute("categories", categories);
        
        request.getRequestDispatcher("/views/admin/laptops.jsp").forward(request, response);
    }
    
    // Thêm/sửa/xóa laptop
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        LaptopDAO laptopDAO = new LaptopDAO();
        
        if ("add".equals(action)) {
            Laptop laptop = new Laptop();
            laptop.setTitle(request.getParameter("title"));
            laptop.setPrice(Double.parseDouble(request.getParameter("price")));
            laptop.setDescription(request.getParameter("description"));
            laptop.setBrandId(Integer.parseInt(request.getParameter("brandId")));
            laptop.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
            laptop.setStatus("1".equals(request.getParameter("status")));
            
            String imageUrl = uploadImage(request);
            laptop.setImageUrl(imageUrl != null ? imageUrl : "default.jpg");
            laptopDAO.addLaptop(laptop);
            
        } else if ("edit".equals(action)) {
            Laptop laptop = new Laptop();
            laptop.setLaptopId(Integer.parseInt(request.getParameter("laptopId")));
            laptop.setTitle(request.getParameter("title"));
            laptop.setPrice(Double.parseDouble(request.getParameter("price")));
            laptop.setDescription(request.getParameter("description"));
            laptop.setBrandId(Integer.parseInt(request.getParameter("brandId")));
            laptop.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
            laptop.setStatus("1".equals(request.getParameter("status")));
            
            String imageUrl = uploadImage(request);
            if (imageUrl != null) {
                laptop.setImageUrl(imageUrl);
            } else {
                laptop.setImageUrl(request.getParameter("currentImage"));
            }
            
            laptopDAO.updateLaptop(laptop);
            
        } else if ("delete".equals(action)) {
            int laptopId = Integer.parseInt(request.getParameter("laptopId"));
            laptopDAO.deleteLaptop(laptopId);
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/laptops");
    }
    
    // Upload ảnh và trả về tên file
    private String uploadImage(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("image");
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }
        
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExtension;
        
        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        Path filePath = Paths.get(uploadPath, newFileName);
        Files.copy(filePart.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        return newFileName;
    }
}
