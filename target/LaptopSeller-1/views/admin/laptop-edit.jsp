<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chỉnh sửa Laptop</title>
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
        <div class="card" style="max-width: 600px; margin: 50px auto;">
            <h2>Chỉnh sửa Laptop</h2>
            
            <form method="post" action="${pageContext.request.contextPath}/admin/laptops" enctype="multipart/form-data">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="laptopId" value="${laptop.laptopId}">
                <input type="hidden" name="currentImage" value="${laptop.imageUrl}">
                
                <div class="form-group">
                    <label>Tên sản phẩm:</label>
                    <input type="text" name="title" value="${laptop.title}" required>
                </div>
                
                <div class="form-group">
                    <label>Giá:</label>
                    <input type="number" name="price" value="${laptop.price}" step="0.01" required>
                </div>
                
                <div class="form-group">
                    <label>Mô tả:</label>
                    <textarea name="description" required>${laptop.description}</textarea>
                </div>
                
                <div class="form-group">
                    <label>Hãng:</label>
                    <select name="brandId" required>
                        <c:forEach var="brand" items="${brands}">
                            <option value="${brand.brandId}" ${brand.brandId == laptop.brandId ? 'selected' : ''}>
                                ${brand.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Loại:</label>
                    <select name="categoryId" required>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.categoryId}" ${category.categoryId == laptop.categoryId ? 'selected' : ''}>
                                ${category.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Trạng thái:</label>
                    <select name="status">
                        <option value="1" ${laptop.status ? 'selected' : ''}>Hiển thị</option>
                        <option value="0" ${!laptop.status ? 'selected' : ''}>Ẩn</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label><i class="fas fa-box"></i> Số lượng:</label>
                    <input type="number" name="quantity" value="${laptop.quantity}" min="0" required>
                </div>
                
                <div class="form-group">
                    <label>Hình ảnh hiện tại:</label>
                    <div style="background: #f5f5f5; padding: 10px; border-radius: 4px; text-align: center;">
                        <img src="${pageContext.request.contextPath}/images/${laptop.imageUrl}" 
                             alt="${laptop.title}" 
                             style="max-width: 200px; max-height: 200px; object-fit: contain;"
                             onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default.jpg'; this.alt='No image';">
                    </div>
                </div>
                
                <div class="form-group">
                    <label><i class="fas fa-image"></i> Thay đổi hình ảnh (để trống nếu không đổi):</label>
                    <input type="file" name="image" accept="image/*">
                </div>
                
                <button type="submit" class="btn btn-primary">Cập nhật</button>
                <a href="${pageContext.request.contextPath}/admin/laptops" class="btn">Hủy</a>
            </form>
        </div>
    </div>
</body>
</html>
