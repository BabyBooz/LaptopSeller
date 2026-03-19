package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.dao.AdminOrderDAO;
import com.mycompany.laptopseller.models.Order;
import com.mycompany.laptopseller.models.OrderItem;
import com.mycompany.laptopseller.models.User;
import com.mycompany.laptopseller.utils.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AuthUtil.getAuthenticatedUser(request);
        AdminOrderDAO orderDAO = new AdminOrderDAO();
        
        String action = request.getParameter("action");
        
        if ("view".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("id"));
            List<OrderItem> orderItems = orderDAO.getOrderItems(orderId);
            request.setAttribute("orderItems", orderItems);
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/views/admin/order-detail.jsp").forward(request, response);
        } else {
            List<Order> orders = orderDAO.getAllOrders();
            request.setAttribute("user", user);
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/views/admin/orders.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        
        AdminOrderDAO orderDAO = new AdminOrderDAO();
        orderDAO.updateOrderStatus(orderId, status);
        
        response.sendRedirect(request.getContextPath() + "/admin/orders");
    }
}
