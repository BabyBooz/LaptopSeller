package com.mycompany.laptopseller.filters;

import com.mycompany.laptopseller.utils.AuthUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = uri.substring(contextPath.length());
        
        boolean isPublicResource = path.equals("/") || path.equals("/login") || 
                                   path.equals("/register") || path.startsWith("/css/") ||
                                   path.startsWith("/images/");
        
        if (isPublicResource) {
            chain.doFilter(request, response);
            return;
        }
        
        if (!AuthUtil.isAuthenticated(httpRequest)) {
            httpResponse.sendRedirect(contextPath + "/login");
            return;
        }
        
        boolean isAdminPath = path.startsWith("/admin");
        if (isAdminPath && !AuthUtil.isAdmin(httpRequest)) {
            httpResponse.sendRedirect(contextPath + "/home");
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}
}
