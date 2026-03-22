package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.dao.UserOrderDAO;
import com.mycompany.laptopseller.models.Order;
import com.mycompany.laptopseller.models.OrderItem;
import com.mycompany.laptopseller.models.User;
import com.mycompany.laptopseller.utils.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/my-orders")
public class MyOrdersServlet extends HttpServlet {
    
    // Hiển thị lịch sử đơn hàng của user
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AuthUtil.getAuthenticatedUser(request);
        UserOrderDAO orderDAO = new UserOrderDAO();
        String action = request.getParameter("action");
        
        if ("view".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("id"));
            List<OrderItem> orderItems = orderDAO.getOrderItems(orderId);
            
            request.setAttribute("orderItems", orderItems);
            request.setAttribute("orderId", orderId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/views/user/order-detail.jsp").forward(request, response);
        } else {
            List<Order> orders = orderDAO.getOrdersByUserId(user.getUserId());
            request.setAttribute("user", user);
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/views/user/my-orders.jsp").forward(request, response);
        }
    }
}
