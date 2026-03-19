<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Laptop</title>
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
        <h1>Quản lý Laptop</h1>
        
        <div class="card">
            <h2>Thêm Laptop mới</h2>
            <form method="post" action="${pageContext.request.contextPath}/admin/laptops" enctype="multipart/form-data">
                <input type="hidden" name="action" value="add">
                
                <div class="form-group">
                    <label>Tên sản phẩm:</label>
                    <input type="text" name="title" required>
                </div>
                
                <div class="form-group">
                    <label>Giá:</label>
                    <input type="number" name="price" step="0.01" required>
                </div>
                
                <div class="form-group">
                    <label>Mô tả:</label>
                    <textarea name="description" required></textarea>
                </div>
                
                <div class="form-group">
                    <label>Hãng:</label>
                    <select name="brandId" required>
                        <c:forEach var="brand" items="${brands}">
                            <option value="${brand.brandId}">${brand.name}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Loại:</label>
                    <select name="categoryId" required>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Trạng thái:</label>
                    <select name="status">
                        <option value="1">Hiển thị</option>
                        <option value="0">Ẩn</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Hình ảnh:</label>
                    <input type="file" name="image" accept="image/*" required>
                </div>
                
                <button type="submit" class="btn btn-primary">Thêm</button>
            </form>
        </div>
        
        <h2 class="mt-20">Danh sách Laptop</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên</th>
                    <th>Giá</th>
                    <th>Hãng</th>
                    <th>Loại</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="laptop" items="${laptops}">
                    <tr>
                        <td>${laptop.laptopId}</td>
                        <td>${laptop.title}</td>
                        <td><fmt:formatNumber value="${laptop.price}" type="number" groupingUsed="true"/> VNĐ</td>
                        <td>${laptop.brandName}</td>
                        <td>${laptop.categoryName}</td>
                        <td>${laptop.status ? 'Hiển thị' : 'Ẩn'}</td>
                        <td>
                            <button onclick="editLaptop(${laptop.laptopId})" class="btn">Sửa</button>
                            <form method="post" action="${pageContext.request.contextPath}/admin/laptops" style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="laptopId" value="${laptop.laptopId}">
                                <button type="submit" class="btn btn-danger" onclick="return confirm('Xác nhận xóa?')">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <script>
        function editLaptop(id) {
            window.location.href = '${pageContext.request.contextPath}/admin/laptop-edit?id=' + id;
        }
    </script>
</body>
</html>
