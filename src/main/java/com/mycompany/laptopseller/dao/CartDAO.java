package com.mycompany.laptopseller.dao;

import com.mycompany.laptopseller.models.Cart;
import com.mycompany.laptopseller.models.CartItem;
import com.mycompany.laptopseller.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    
    public Cart getCartByUserId(int userId) {
        String sql = "SELECT * FROM cart WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setUserId(rs.getInt("user_id"));
                cart.setCreatedAt(rs.getTimestamp("created_at"));
                return cart;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int createCart(int userId) {
        String sql = "INSERT INTO cart (user_id) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public List<CartItem> getCartItems(int cartId) {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT ci.*, l.title, l.price, l.image_url " +
                     "FROM cart_items ci " +
                     "JOIN laptops l ON ci.laptop_id = l.laptop_id " +
                     "WHERE ci.cart_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            LaptopDAO laptopDAO = new LaptopDAO();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setLaptopId(rs.getInt("laptop_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setTotalPrice(rs.getDouble("total_price"));
                item.setLaptop(laptopDAO.getLaptopById(rs.getInt("laptop_id")));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public boolean addToCart(int cartId, int laptopId, int quantity, double totalPrice) {
        String checkSql = "SELECT * FROM cart_items WHERE cart_id = ? AND laptop_id = ?";
        String updateSql = "UPDATE cart_items SET quantity = quantity + ?, total_price = total_price + ? WHERE cart_id = ? AND laptop_id = ?";
        String insertSql = "INSERT INTO cart_items (cart_id, laptop_id, quantity, total_price) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, cartId);
            checkPs.setInt(2, laptopId);
            ResultSet rs = checkPs.executeQuery();
            
            if (rs.next()) {
                PreparedStatement updatePs = conn.prepareStatement(updateSql);
                updatePs.setInt(1, quantity);
                updatePs.setDouble(2, totalPrice);
                updatePs.setInt(3, cartId);
                updatePs.setInt(4, laptopId);
                return updatePs.executeUpdate() > 0;
            } else {
                PreparedStatement insertPs = conn.prepareStatement(insertSql);
                insertPs.setInt(1, cartId);
                insertPs.setInt(2, laptopId);
                insertPs.setInt(3, quantity);
                insertPs.setDouble(4, totalPrice);
                return insertPs.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateCartItem(int cartItemId, int quantity, double totalPrice) {
        String sql = "UPDATE cart_items SET quantity = ?, total_price = ? WHERE cart_item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setDouble(2, totalPrice);
            ps.setInt(3, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean removeCartItem(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean clearCart(int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            return ps.executeUpdate() >= 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
