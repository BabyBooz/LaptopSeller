<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trang chủ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <div class="navbar">
        <div class="navbar-content">
            <div>
                <a href="${pageContext.request.contextPath}/home"><i class="fas fa-home"></i> Trang chủ</a>
                <c:if test="${user.role == 'admin'}">
                    <a href="${pageContext.request.contextPath}/admin/laptops"><i class="fas fa-laptop"></i> Quản lý Laptop</a>
                    <a href="${pageContext.request.contextPath}/admin/orders"><i class="fas fa-box"></i> Quản lý Đơn hàng</a>
                </c:if>
                <c:if test="${user.role == 'customer'}">
                    <a href="${pageContext.request.contextPath}/cart"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a>
                    <a href="${pageContext.request.contextPath}/my-orders"><i class="fas fa-receipt"></i> Đơn hàng của tôi</a>
                </c:if>
            </div>
            <div>
                <span><i class="fas fa-user"></i> Xin chào, ${user.fullName}</span>
                <a href="${pageContext.request.contextPath}/profile"><i class="fas fa-user-circle"></i> Thông tin cá nhân</a>
                <a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <div class="container">
        <div class="main-layout">
            <!-- Sidebar Filter -->
            <div class="sidebar">
                <div class="card">
                    <h3><i class="fas fa-filter"></i> Lọc theo loại</h3>
                    <ul class="category-list">
                        <li>
                            <a href="${pageContext.request.contextPath}/home?category=all" 
                               class="${empty selectedCategory || selectedCategory == 'all' ? 'active' : ''}">
                                <i class="fas fa-th"></i> Tất cả
                            </a>
                        </li>
                        <c:forEach var="category" items="${categories}">
                            <li>
                                <a href="${pageContext.request.contextPath}/home?category=${category.categoryId}" 
                                   class="${selectedCategory eq category.categoryId.toString() ? 'active' : ''}">
                                    <i class="fas fa-laptop"></i> ${category.name}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            
            <!-- Main Content -->
            <div class="main-content">
                <h1>Danh sách Laptop</h1>
                
                <c:if test="${empty laptops}">
                    <div class="card">
                        <p class="text-center">Không có sản phẩm nào</p>
                    </div>
                </c:if>
                
                <div class="laptop-grid">
                    <c:forEach var="laptop" items="${laptops}">
                        <div class="laptop-card">
                            <img src="${pageContext.request.contextPath}/images/${laptop.imageUrl}" 
                                 alt="${laptop.title}" 
                                 onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default.jpg';">
                            <h3>${laptop.title}</h3>
                            <p>${laptop.brandName} - ${laptop.categoryName}</p>
                            <p class="price"><fmt:formatNumber value="${laptop.price}" type="number" groupingUsed="true"/> VNĐ</p>
                            <p>${laptop.description}</p>
                            
                            <c:if test="${user.role == 'customer'}">
                                <form method="post" action="${pageContext.request.contextPath}/cart">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="laptopId" value="${laptop.laptopId}">
                                    <input type="number" name="quantity" value="1" min="1" style="width: 60px;">
                                    <button type="submit" class="btn btn-primary">Thêm vào giỏ</button>
                                </form>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
