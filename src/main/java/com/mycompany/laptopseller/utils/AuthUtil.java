package com.mycompany.laptopseller.utils;

import com.mycompany.laptopseller.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {
    private static final String USER_SESSION_KEY = "loggedInUser";
    
    public static void setAuthUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute(USER_SESSION_KEY, user);
    }
    
    public static void removeAuthUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(USER_SESSION_KEY);
            session.invalidate();
        }
    }
    
    public static User getAuthenticatedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute(USER_SESSION_KEY);
        }
        return null;
    }
    
    public static boolean isAuthenticated(HttpServletRequest request) {
        return getAuthenticatedUser(request) != null;
    }
    
    public static boolean isAdmin(HttpServletRequest request) {
        User user = getAuthenticatedUser(request);
        return user != null && "admin".equals(user.getRole());
    }
    
    public static boolean isCustomer(HttpServletRequest request) {
        User user = getAuthenticatedUser(request);
        return user != null && "customer".equals(user.getRole());
    }
}
