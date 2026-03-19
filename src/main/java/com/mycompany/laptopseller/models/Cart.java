package com.mycompany.laptopseller.models;

import java.util.Date;

public class Cart {
    private int cartId;
    private int userId;
    private Date createdAt;
    
    public Cart() {}
    
    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
