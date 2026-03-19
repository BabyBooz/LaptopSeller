<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết Đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <div class="navbar">
        <div class="navbar-content">
            <div>
                <a href="${pageContext.request.contextPath}/home"><i class="fas fa-home"></i> Trang chủ</a>
                <a href="${pageContext.request.contextPath}/admin/laptops"><i class="fas fa-laptop"></i> Quản lý Laptop</a>
                <a href="${pageContext.request.contextPath}/admin/orders"><i class="fas fa-box"></i> Quản lý Đơn hàng</a>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <div class="container">
        <h1>Chi tiết Đơn hàng #${orderId}</h1>
        
        <table>
            <thead>
                <tr>
                    <th>Hình ảnh</th>
                    <th>Sản phẩm</th>
                    <th>Đơn giá</th>
                    <th>Số lượng</th>
                    <th>Thành tiền</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${orderItems}">
                    <tr>
                        <td>
                            <img src="${pageContext.request.contextPath}/images/${item.laptop.imageUrl}" 
                                 alt="${item.laptop.title}" style="width: 80px; height: 80px; object-fit: cover;"
                                 onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default.svg'; this.style.backgroundColor='#f0f0f0';">
                        </td>
                        <td>${item.laptop.title}</td>
                        <td><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VNĐ</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.totalPrice}" type="number" groupingUsed="true"/> VNĐ</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <div class="mt-20">
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn">Quay lại</a>
        </div>
    </div>
</body>
</html>
