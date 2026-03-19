<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng</title>
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
        <h1>Giỏ hàng của bạn</h1>
        
        <c:if test="${empty cartItems}">
            <div class="card">
                <p class="text-center">Giỏ hàng trống</p>
                <p class="text-center">
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">Tiếp tục mua sắm</a>
                </p>
            </div>
        </c:if>
        
        <c:if test="${not empty cartItems}">
            <table>
                <thead>
                    <tr>
                        <th>Sản phẩm</th>
                        <th>Đơn giá</th>
                        <th>Số lượng</th>
                        <th>Thành tiền</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td>
                                <div style="display: flex; align-items: center; gap: 10px;">
                                    <img src="${pageContext.request.contextPath}/images/${item.laptop.imageUrl}" 
                                         alt="${item.laptop.title}" 
                                         style="width: 60px; height: 60px; object-fit: cover; border-radius: 4px;"
                                         onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default.jpg';">
                                    <span>${item.laptop.title}</span>
                                </div>
                            </td>
                            <td><fmt:formatNumber value="${item.laptop.price}" type="number" groupingUsed="true"/> VNĐ</td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/cart" style="display: inline;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                    <input type="hidden" name="laptopId" value="${item.laptopId}">
                                    <input type="number" name="quantity" value="${item.quantity}" min="1" style="width: 60px;">
                                    <button type="submit" class="btn">Cập nhật</button>
                                </form>
                            </td>
                            <td><fmt:formatNumber value="${item.totalPrice}" type="number" groupingUsed="true"/> VNĐ</td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/cart" style="display: inline;">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                    <button type="submit" class="btn btn-danger">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <div class="card">
                <h2>Tổng tiền: <fmt:formatNumber value="${totalPrice}" type="number" groupingUsed="true"/> VNĐ</h2>
                <form method="post" action="${pageContext.request.contextPath}/checkout">
                    <button type="submit" class="btn btn-primary">Đặt hàng</button>
                </form>
            </div>
        </c:if>
    </div>
</body>
</html>
