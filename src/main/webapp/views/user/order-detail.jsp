<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <div class="navbar">
        <div class="navbar-content">
            <div>
                <a href="${pageContext.request.contextPath}/home"><i class="fas fa-home"></i> Trang chủ</a>
                <a href="${pageContext.request.contextPath}/cart"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a>
                <a href="${pageContext.request.contextPath}/my-orders"><i class="fas fa-receipt"></i> Đơn hàng của tôi</a>
            </div>
            <div>
                <span><i class="fas fa-user"></i> Xin chào, ${user.fullName}</span>
                <a href="${pageContext.request.contextPath}/profile"><i class="fas fa-user-circle"></i> Thông tin cá nhân</a>
                <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <div class="container">
        <h1>Chi tiết đơn hàng #${orderId}</h1>
        
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
                <c:set var="total" value="0" />
                <c:forEach var="item" items="${orderItems}">
                    <tr>
                        <td>
                            <img src="${pageContext.request.contextPath}/images/${item.laptop.imageUrl}" 
                                 alt="${item.laptop.title}" 
                                 style="width: 80px; height: 80px; object-fit: cover; border-radius: 4px;"
                                 onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default.jpg';">
                        </td>
                        <td>
                            <strong>${item.laptop.title}</strong><br>
                            <small>${item.laptop.brandName} - ${item.laptop.categoryName}</small>
                        </td>
                        <td><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VNĐ</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.totalPrice}" type="number" groupingUsed="true"/> VNĐ</td>
                    </tr>
                    <c:set var="total" value="${total + item.totalPrice}" />
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="4" style="text-align: right; font-weight: bold;">Tổng cộng:</td>
                    <td style="font-weight: bold; color: #e74c3c; font-size: 18px;">
                        <fmt:formatNumber value="${total}" type="number" groupingUsed="true"/> VNĐ
                    </td>
                </tr>
            </tfoot>
        </table>
        
        <div class="mt-20">
            <a href="${pageContext.request.contextPath}/my-orders" class="btn">Quay lại</a>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">Tiếp tục mua sắm</a>
        </div>
    </div>
</body>
</html>
