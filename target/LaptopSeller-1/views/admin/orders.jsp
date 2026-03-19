<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Đơn hàng</title>
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
                <span><i class="fas fa-user"></i> Xin chào, ${user.fullName}</span>
                <a href="${pageContext.request.contextPath}/profile"><i class="fas fa-user-circle"></i> Thông tin cá nhân</a>
                <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <div class="container">
        <h1>Quản lý Đơn hàng</h1>
        
        <table>
            <thead>
                <tr>
                    <th>Mã ĐH</th>
                    <th>Khách hàng</th>
                    <th>SĐT</th>
                    <th>Địa chỉ</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th>Ngày đặt</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${order.user.fullName}</td>
                        <td>${order.user.phone}</td>
                        <td>${order.user.address}</td>
                        <td><fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/> VNĐ</td>
                        <td>
                            <c:choose>
                                <c:when test="${order.status == 'wait'}">
                                    <span class="status-wait">Chờ xử lý</span>
                                </c:when>
                                <c:when test="${order.status == 'process'}">
                                    <span class="status-process">Đang xử lý</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status-done">Hoàn thành</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatDate value="${order.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/orders?action=view&id=${order.orderId}" class="btn">Xem</a>
                            <form method="post" action="${pageContext.request.contextPath}/admin/orders" style="display: inline;">
                                <input type="hidden" name="orderId" value="${order.orderId}">
                                <select name="status" onchange="this.form.submit()">
                                    <option value="wait" ${order.status == 'wait' ? 'selected' : ''}>Chờ xử lý</option>
                                    <option value="process" ${order.status == 'process' ? 'selected' : ''}>Đang xử lý</option>
                                    <option value="done" ${order.status == 'done' ? 'selected' : ''}>Hoàn thành</option>
                                </select>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
