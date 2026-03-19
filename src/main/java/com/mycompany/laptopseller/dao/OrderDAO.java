package com.mycompany.laptopseller.dao;

import com.mycompany.laptopseller.models.Order;
import com.mycompany.laptopseller.models.CartItem;
import com.mycompany.laptopseller.utils.DatabaseConnection;
import java.sql.*;
import java.util.List;

public class OrderDAO {
    
    public boolean createOrder(int userId, List<CartItem> cartItems, double totalPrice) {
        String orderSql = "INSERT INTO orders (user_id, total_price, status) VALUES (?, ?, 'wait')";
        String orderItemSql = "INSERT INTO order_items (order_id, laptop_id, quantity, price, total_price) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            PreparedStatement orderPs = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderPs.setInt(1, userId);
            orderPs.setDouble(2, totalPrice);
            orderPs.executeUpdate();
            
            ResultSet rs = orderPs.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                
                PreparedStatement itemPs = conn.prepareStatement(orderItemSql);
                for (CartItem item : cartItems) {
                    itemPs.setInt(1, orderId);
                    itemPs.setInt(2, item.getLaptopId());
                    itemPs.setInt(3, item.getQuantity());
                    itemPs.setDouble(4, item.getLaptop().getPrice());
                    itemPs.setDouble(5, item.getTotalPrice());
                    itemPs.addBatch();
                }
                itemPs.executeBatch();
                
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
