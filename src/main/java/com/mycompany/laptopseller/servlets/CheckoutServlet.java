package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.dao.CartDAO;
import com.mycompany.laptopseller.dao.LaptopDAO;
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
    
    // Xử lý thanh toán và tạo đơn hàng
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AuthUtil.getAuthenticatedUser(request);
        CartDAO cartDAO = new CartDAO();
        OrderDAO orderDAO = new OrderDAO();
        LaptopDAO laptopDAO = new LaptopDAO();
        
        Cart cart = cartDAO.getCartByUserId(user.getUserId());
        List<CartItem> cartItems = cartDAO.getCartItems(cart.getCartId());
        
        if (cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        
        double totalPrice = cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
        
        // Tạo đơn hàng và trừ số lượng sản phẩm
        if (orderDAO.createOrder(user.getUserId(), cartItems, totalPrice)) {
            // Trừ số lượng từng sản phẩm
            for (CartItem item : cartItems) {
                laptopDAO.decreaseQuantity(item.getLaptopId(), item.getQuantity());
            }
            
            cartDAO.clearCart(cart.getCartId());
            request.setAttribute("success", "Đặt hàng thành công!");
            request.getRequestDispatcher("/views/checkout-success.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Đặt hàng thất bại");
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}
