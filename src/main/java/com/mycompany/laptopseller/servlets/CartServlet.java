package com.mycompany.laptopseller.servlets;

import com.mycompany.laptopseller.dao.CartDAO;
import com.mycompany.laptopseller.dao.LaptopDAO;
import com.mycompany.laptopseller.models.Cart;
import com.mycompany.laptopseller.models.CartItem;
import com.mycompany.laptopseller.models.Laptop;
import com.mycompany.laptopseller.models.User;
import com.mycompany.laptopseller.utils.AuthUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    
    // Hiển thị giỏ hàng
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = AuthUtil.getAuthenticatedUser(request);
        CartDAO cartDAO = new CartDAO();
        
        Cart cart = cartDAO.getCartByUserId(user.getUserId());
        if (cart == null) {
            int cartId = cartDAO.createCart(user.getUserId());
            cart = new Cart();
            cart.setCartId(cartId);
            cart.setUserId(user.getUserId());
        }
        
        List<CartItem> cartItems = cartDAO.getCartItems(cart.getCartId());
        double totalPrice = cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
        
        request.setAttribute("user", user);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalPrice", totalPrice);
        request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
    }
    
    // Thêm/cập nhật/xóa sản phẩm trong giỏ
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        User user = AuthUtil.getAuthenticatedUser(request);
        CartDAO cartDAO = new CartDAO();
        
        Cart cart = cartDAO.getCartByUserId(user.getUserId());
        if (cart == null) {
            int cartId = cartDAO.createCart(user.getUserId());
            cart = new Cart();
            cart.setCartId(cartId);
        }
        
        if ("add".equals(action)) {
            int laptopId = Integer.parseInt(request.getParameter("laptopId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            LaptopDAO laptopDAO = new LaptopDAO();
            Laptop laptop = laptopDAO.getLaptopById(laptopId);
            double totalPrice = laptop.getPrice() * quantity;
            
            cartDAO.addToCart(cart.getCartId(), laptopId, quantity, totalPrice);
            response.sendRedirect(request.getContextPath() + "/cart");
            
        } else if ("update".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int laptopId = Integer.parseInt(request.getParameter("laptopId"));
            
            LaptopDAO laptopDAO = new LaptopDAO();
            Laptop laptop = laptopDAO.getLaptopById(laptopId);
            double totalPrice = laptop.getPrice() * quantity;
            
            cartDAO.updateCartItem(cartItemId, quantity, totalPrice);
            response.sendRedirect(request.getContextPath() + "/cart");
            
        } else if ("remove".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            cartDAO.removeCartItem(cartItemId);
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}
