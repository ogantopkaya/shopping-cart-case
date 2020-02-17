package com.otopkaya.shopping_cart.shopping_cart;

import com.otopkaya.shopping_cart.product.Product;
import lombok.Getter;

public class ShoppingCartItem {

    @Getter
    private Product product;
    @Getter
    private int quantity;
    @Getter
    private final double originalSalePrice;
    @Getter
    private double salePrice;

    public ShoppingCartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.originalSalePrice = product.getPrice() * quantity;
        this.salePrice = this.originalSalePrice;
    }

    public void addDiscount(double amount) {
        salePrice = Math.max(salePrice - amount, 0);
    }

    @Override
    public String toString() {
        return String.format("%15s %1s %10s %1s %8s %1s %10s %1s %10s %1s %10s",
                product.toString(), "|", quantity, "|",
                product.getPrice(), "|", originalSalePrice, "|",
                salePrice - originalSalePrice, "|", getSalePrice());
    }
}
