package com.otopkaya.shopping_cart.test_utils;

import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.product.Product;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public class TestUtils {

    public static void resetCounters() throws NoSuchFieldException, IllegalAccessException {
        Field categoryCountField = Category.class.getDeclaredField("count");
        categoryCountField.setAccessible(true);
        ((AtomicInteger)categoryCountField.get(null)).set(0);

        Field productCountField = Product.class.getDeclaredField("count");
        productCountField.setAccessible(true);
        ((AtomicInteger)productCountField.get(null)).set(0);

    }

}
