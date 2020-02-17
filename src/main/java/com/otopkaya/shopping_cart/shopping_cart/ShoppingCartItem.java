package com.otopkaya.shopping_cart.shopping_cart;

import com.otopkaya.shopping_cart.product.Product;
import lombok.Getter;

public class ShoppingCartItem {

    @Getter
    private Product product;
    @Getter
    private int quantity;
    @Getter
    private double salePrice;

    public ShoppingCartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.salePrice = product.getPrice() * quantity;
    }

    public void addDiscount(double amount) {
        salePrice = Math.max(salePrice - amount, 0);
    }

}
