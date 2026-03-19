<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch sử đơn hàng</title>
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
        <h1>Lịch sử đơn hàng</h1>
        
        <c:if test="${empty orders}">
            <div class="card">
                <p class="text-center">Bạn chưa có đơn hàng nào</p>
                <p class="text-center">
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">Mua sắm ngay</a>
                </p>
            </div>
        </c:if>
        
        <c:if test="${not empty orders}">
            <table>
                <thead>
                    <tr>
                        <th>Mã đơn hàng</th>
                        <th>Ngày đặt</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Cập nhật lần cuối</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>#${order.orderId}</td>
                            <td><fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td><fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/> VNĐ</td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.status == 'wait'}">
                                        <span class="status-wait">⏳ Chờ xử lý</span>
                                    </c:when>
                                    <c:when test="${order.status == 'process'}">
                                        <span class="status-process">🚚 Đang xử lý</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-done">✅ Hoàn thành</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><fmt:formatDate value="${order.updatedAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/my-orders?action=view&id=${order.orderId}" class="btn">Xem chi tiết</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>
