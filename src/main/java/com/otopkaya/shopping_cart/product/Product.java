package com.otopkaya.shopping_cart.product;

import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@EqualsAndHashCode(of = "productId")
public class Product {

    private static final AtomicInteger count = new AtomicInteger(0); // For generating incremental ids

    public Product(String title, double price, Category category) {
        this.productId = count.incrementAndGet();
        this.title = title;
        this.price = price;
        this.category = category;
    }

    @Setter(AccessLevel.NONE)
    private long productId;

    @Setter(AccessLevel.NONE)
    private String title;

    @Setter(AccessLevel.NONE)
    private double price;

    @Setter(AccessLevel.NONE)
    private Category category;

    public boolean hasCategory(Category category) {
        if (category == null) {
            return false;
        }

        Category c = getCategory();
        while (c != null) {
            if (c.equals(category)) {
                return true;
            }
            c = c.getParent();
        }
        return false;
    }

    @Override
    public String toString() {
        return title;
    }
}
