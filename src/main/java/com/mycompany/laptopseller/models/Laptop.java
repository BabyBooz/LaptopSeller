package com.mycompany.laptopseller.models;

public class Laptop {
    private int laptopId;
    private String title;
    private double price;
    private String description;
    private int brandId;
    private int categoryId;
    private boolean status;
    private String imageUrl;
    private String brandName;
    private String categoryName;
    private int quantity;
    
    public Laptop() {}
    
    public int getLaptopId() { return laptopId; }
    public void setLaptopId(int laptopId) { this.laptopId = laptopId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public int getBrandId() { return brandId; }
    public void setBrandId(int brandId) { this.brandId = brandId; }
    
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
