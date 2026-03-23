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
import java.io.InputStream;
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
    
    private static final String UPLOAD_DIR = "images";
    
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
    
    // Upload ảnh vào cả src và target
    private String uploadImage(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("image");
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }
        
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        
        // Kiểm tra định dạng file
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex).toLowerCase();
        }
        
        if (!fileExtension.matches("\\.(jpg|jpeg|png|gif|webp)")) {
            return null;
        }
        
        // Tạo tên file unique
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // Lấy đường dẫn thực tế (target)
        String realPath = getServletContext().getRealPath("/" + UPLOAD_DIR);
        
        System.out.println("=== UPLOAD DEBUG ===");
        System.out.println("Real Path: " + realPath);
        
        // Tự động tìm đường dẫn src
        String projectPath = realPath;
        if (realPath.contains("target")) {
            // Tìm vị trí của "target" và thay thế
            int targetIndex = realPath.indexOf("target");
            String basePath = realPath.substring(0, targetIndex);
            projectPath = basePath + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + UPLOAD_DIR;
        }
        
        System.out.println("Project Path: " + projectPath);
        
        // Tạo thư mục src/main/webapp/images nếu chưa tồn tại
        File srcUploadDir = new File(projectPath);
        if (!srcUploadDir.exists()) {
            boolean created = srcUploadDir.mkdirs();
            System.out.println("Created SRC directory: " + projectPath + " - Success: " + created);
        }
        
        // Lưu file vào src/main/webapp/images
        String srcFilePath = projectPath + File.separator + uniqueFileName;
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(srcFilePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File saved to SRC: " + srcFilePath);
        }
        
        // Copy sang target/LaptopSeller/images để hiển thị ngay
        File targetUploadDir = new File(realPath);
        if (!targetUploadDir.exists()) {
            boolean created = targetUploadDir.mkdirs();
            System.out.println("Created TARGET directory: " + realPath + " - Success: " + created);
        }
        
        String targetFilePath = realPath + File.separator + uniqueFileName;
        Files.copy(Paths.get(srcFilePath), Paths.get(targetFilePath), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File copied to TARGET: " + targetFilePath);
        
        return uniqueFileName;
    }
}
