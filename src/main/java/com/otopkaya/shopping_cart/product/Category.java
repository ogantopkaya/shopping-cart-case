package com.otopkaya.shopping_cart.product;

import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@EqualsAndHashCode(of = "categoryId")
@ToString(of = {"title"})
public class Category implements Comparable {

    private static final AtomicInteger count = new AtomicInteger(0); // For generating incremental ids

    public Category(String title, Category parent) {
        this.categoryId = count.incrementAndGet();
        this.title = title;
        this.parent = parent;
    }

    @Setter(AccessLevel.PRIVATE)
    private int categoryId;
    @Setter(AccessLevel.PRIVATE)
    private String title;
    @Setter(AccessLevel.PRIVATE)
    private Category parent;

    @Override
    public int compareTo(Object o) {
        return Integer.compare(this.categoryId, ((Category) o).categoryId);
    }
}
