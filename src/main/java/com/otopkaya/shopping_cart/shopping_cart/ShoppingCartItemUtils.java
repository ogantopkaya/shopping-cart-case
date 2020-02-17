package com.otopkaya.shopping_cart.shopping_cart;

import com.otopkaya.shopping_cart.product.Category;

import java.util.*;
import java.util.stream.Collectors;

public class ShoppingCartItemUtils {

    public static double totalSalePrice(List<ShoppingCartItem> items) {
        return items.stream()
                .map(ShoppingCartItem::getSalePrice)
                .reduce(0.0, Double::sum);
    }

    public static List<ShoppingCartItem> itemsByCategory(Category category, List<ShoppingCartItem> items) {
        return items.stream().filter(item -> item.getProduct().hasCategory(category)).collect(Collectors.toList());
    }

    public static int distinctCategoryCount(List<ShoppingCartItem> items) {
        return (int) items.stream().map(x -> x.getProduct().getCategory()).distinct().count();
    }

    public static HashMap<Category, List<ShoppingCartItem>> groupedByCategory(List<ShoppingCartItem> items) {
        HashMap<Category, List<ShoppingCartItem>> result = new HashMap<>();
        for (ShoppingCartItem item : items) {
            Category category = item.getProduct().getCategory();
            List<ShoppingCartItem> cartItems = result.getOrDefault(category, new ArrayList<ShoppingCartItem>());
            cartItems.add(item);
            result.put(category, cartItems);
        }
        return result;
    }

}
