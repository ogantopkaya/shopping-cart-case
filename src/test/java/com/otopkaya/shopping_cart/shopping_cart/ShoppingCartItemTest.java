package com.otopkaya.shopping_cart.shopping_cart;

import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.product.Product;
import com.otopkaya.shopping_cart.test_utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShoppingCartItemTest {

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();
    }

    @Test
    public void testShoppingCartItem(){

        Category category = new Category("cat1", null);
        Product product = new Product("product1", 1.5, category);
        ShoppingCartItem item = new ShoppingCartItem(product, 10);

        Assert.assertEquals(item.getProduct(),product);
        Assert.assertEquals(item.getProduct().getCategory(),category);
        Assert.assertEquals(item.getSalePrice(), 15, 0);
        Assert.assertEquals(item.getQuantity(), 10);

        item.addDiscount(10);
        Assert.assertEquals(item.getSalePrice(), 5, 0);
    }

}
