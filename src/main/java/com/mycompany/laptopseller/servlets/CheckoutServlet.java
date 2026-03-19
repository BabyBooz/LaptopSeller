package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.dao.CartDAO;
import com.mycompany.laptopseller.dao.OrderDAO;
import com.mycompany.laptopseller.models.Cart;
import com.mycompany.laptopseller.models.CartItem;
import com.mycompany.laptopseller.models.User;
import com.mycompany.laptopseller.utils.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AuthUtil.getAuthenticatedUser(request);
        CartDAO cartDAO = new CartDAO();
        OrderDAO orderDAO = new OrderDAO();
        
        Cart cart = cartDAO.getCartByUserId(user.getUserId());
        List<CartItem> cartItems = cartDAO.getCartItems(cart.getCartId());
        
        if (cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        
        double totalPrice = cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
        
        if (orderDAO.createOrder(user.getUserId(), cartItems, totalPrice)) {
            cartDAO.clearCart(cart.getCartId());
            request.setAttribute("success", "Đặt hàng thành công!");
            request.getRequestDispatcher("/views/checkout-success.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Đặt hàng thất bại");
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}
