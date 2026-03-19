<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="card" style="max-width: 500px; margin: 50px auto;">
            <h2 class="text-center">Đăng ký tài khoản</h2>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <form method="post" action="${pageContext.request.contextPath}/register">
                <div class="form-group">
                    <label>Mã số:</label>
                    <input type="text" name="rollNumber" required>
                </div>
                
                <div class="form-group">
                    <label>Họ và tên:</label>
                    <input type="text" name="fullName" required>
                </div>
                
                <div class="form-group">
                    <label>Tên đăng nhập:</label>
                    <input type="text" name="username" required>
                </div>
                
                <div class="form-group">
                    <label>Mật khẩu:</label>
                    <input type="password" name="password" required>
                </div>
                
                <div class="form-group">
                    <label>Email:</label>
                    <input type="email" name="email" required>
                </div>
                
                <div class="form-group">
                    <label>Số điện thoại:</label>
                    <input type="text" name="phone" required>
                </div>
                
                <div class="form-group">
                    <label>Địa chỉ:</label>
                    <textarea name="address" required></textarea>
                </div>
                
                <button type="submit" class="btn btn-primary" style="width: 100%;">Đăng ký</button>
            </form>
            
            <p class="text-center mt-20">
                Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            </p>
        </div>
    </div>
</body>
</html>
