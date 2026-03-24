package com.mycompany.laptopseller.dao;

import com.mycompany.laptopseller.models.Laptop;
import com.mycompany.laptopseller.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaptopDAO {
    
    public List<Laptop> getAllLaptops() {
        List<Laptop> laptops = new ArrayList<>();
        String sql = "SELECT l.*, b.name as brand_name, c.name as category_name " +
                     "FROM laptops l " +
                     "LEFT JOIN brands b ON l.brand_id = b.brand_id " +
                     "LEFT JOIN categories c ON l.category_id = c.category_id " +
                     "WHERE l.status = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                laptops.add(extractLaptop(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laptops;
    }
    
    public List<Laptop> getAllLaptopsForAdmin() {
        List<Laptop> laptops = new ArrayList<>();
        String sql = "SELECT l.*, b.name as brand_name, c.name as category_name " +
                     "FROM laptops l " +
                     "LEFT JOIN brands b ON l.brand_id = b.brand_id " +
                     "LEFT JOIN categories c ON l.category_id = c.category_id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                laptops.add(extractLaptop(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laptops;
    }
    
    public Laptop getLaptopById(int laptopId) {
        String sql = "SELECT l.*, b.name as brand_name, c.name as category_name " +
                     "FROM laptops l " +
                     "LEFT JOIN brands b ON l.brand_id = b.brand_id " +
                     "LEFT JOIN categories c ON l.category_id = c.category_id " +
                     "WHERE l.laptop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, laptopId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractLaptop(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addLaptop(Laptop laptop) {
        String sql = "INSERT INTO laptops (title, price, description, brand_id, category_id, status, image_url, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, laptop.getTitle());
            ps.setDouble(2, laptop.getPrice());
            ps.setString(3, laptop.getDescription());
            ps.setInt(4, laptop.getBrandId());
            ps.setInt(5, laptop.getCategoryId());
            ps.setBoolean(6, laptop.isStatus());
            ps.setString(7, laptop.getImageUrl());
            ps.setInt(8, laptop.getQuantity());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateLaptop(Laptop laptop) {
        String sql = "UPDATE laptops SET title = ?, price = ?, description = ?, brand_id = ?, category_id = ?, status = ?, image_url = ?, quantity = ? WHERE laptop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, laptop.getTitle());
            ps.setDouble(2, laptop.getPrice());
            ps.setString(3, laptop.getDescription());
            ps.setInt(4, laptop.getBrandId());
            ps.setInt(5, laptop.getCategoryId());
            ps.setBoolean(6, laptop.isStatus());
            ps.setString(7, laptop.getImageUrl());
            ps.setInt(8, laptop.getQuantity());
            ps.setInt(9, laptop.getLaptopId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteLaptop(int laptopId) {
        String sql = "DELETE FROM laptops WHERE laptop_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, laptopId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private Laptop extractLaptop(ResultSet rs) throws SQLException {
        Laptop laptop = new Laptop();
        laptop.setLaptopId(rs.getInt("laptop_id"));
        laptop.setTitle(rs.getString("title"));
        laptop.setPrice(rs.getDouble("price"));
        laptop.setDescription(rs.getString("description"));
        laptop.setBrandId(rs.getInt("brand_id"));
        laptop.setCategoryId(rs.getInt("category_id"));
        laptop.setStatus(rs.getBoolean("status"));
        laptop.setImageUrl(rs.getString("image_url"));
        laptop.setBrandName(rs.getString("brand_name"));
        laptop.setCategoryName(rs.getString("category_name"));
        laptop.setQuantity(rs.getInt("quantity"));
        return laptop;
    }

    public List<Laptop> getLaptopsByCategory(int categoryId) {
        List<Laptop> laptops = new ArrayList<>();
        String sql = "SELECT l.*, b.name as brand_name, c.name as category_name " +
                     "FROM laptops l " +
                     "LEFT JOIN brands b ON l.brand_id = b.brand_id " +
                     "LEFT JOIN categories c ON l.category_id = c.category_id " +
                     "WHERE l.status = 1 AND l.category_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                laptops.add(extractLaptop(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laptops;
    }
    
    // Giảm số lượng sản phẩm khi mua hàng
    public boolean decreaseQuantity(int laptopId, int quantity) {
        String sql = "UPDATE laptops SET quantity = quantity - ? WHERE laptop_id = ? AND quantity >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, laptopId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}