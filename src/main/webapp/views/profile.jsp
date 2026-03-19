<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thông tin cá nhân</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <div class="navbar">
        <div class="navbar-content">
            <div>
                <a href="${pageContext.request.contextPath}/home"><i class="fas fa-home"></i> Trang chủ</a>
                <% if ("admin".equals(((com.mycompany.laptopseller.models.User)request.getAttribute("user")).getRole())) { %>
                    <a href="${pageContext.request.contextPath}/admin/laptops"><i class="fas fa-laptop"></i> Quản lý Laptop</a>
                    <a href="${pageContext.request.contextPath}/admin/orders"><i class="fas fa-box"></i> Quản lý Đơn hàng</a>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/cart"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a>
                    <a href="${pageContext.request.contextPath}/my-orders"><i class="fas fa-receipt"></i> Đơn hàng của tôi</a>
                <% } %>
            </div>
            <div>
                <span><i class="fas fa-user"></i> Xin chào, ${user.fullName}</span>
                <a href="${pageContext.request.contextPath}/profile"><i class="fas fa-user-circle"></i> Thông tin cá nhân</a>
                <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <div class="container">
        <div class="card" style="max-width: 600px; margin: 50px auto;">
            <h2>Thông tin cá nhân</h2>
            
            <% if (request.getAttribute("success") != null) { %>
                <div class="alert alert-success">
                    <%= request.getAttribute("success") %>
                </div>
            <% } %>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <form method="post" action="${pageContext.request.contextPath}/profile">
                <div class="form-group">
                    <label>Mã số:</label>
                    <input type="text" name="rollNumber" value="${user.rollNumber}" required>
                </div>
                
                <div class="form-group">
                    <label>Họ và tên:</label>
                    <input type="text" name="fullName" value="${user.fullName}" required>
                </div>
                
                <div class="form-group">
                    <label>Tên đăng nhập:</label>
                    <input type="text" value="${user.username}" disabled>
                </div>
                
                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="email" value="${user.email}" required>
                </div>
                
                <div class="form-group">
                    <label>Số điện thoại:</label>
                    <input type="text" name="phone" value="${user.phone}" required>
                </div>
                
                <div class="form-group">
                    <label>Địa chỉ:</label>
                    <textarea name="address" required>${user.address}</textarea>
                </div>
                
                <div class="form-group">
                    <label>Mật khẩu mới (để trống nếu không đổi):</label>
                    <input type="password" name="newPassword" placeholder="Nhập mật khẩu mới nếu muốn đổi">
                </div>
                
                <button type="submit" class="btn btn-primary">Cập nhật thông tin</button>
            </form>
        </div>
    </div>
</body>
</html>
