package com.otopkaya.shopping_cart.product;

import com.otopkaya.shopping_cart.test_utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();
    }

    @Test
    public void testConstructor(){
        Category cat1 = new Category("cat1", null);
        Category cat2 = new Category("cat2", cat1);
        Category cat3 = new Category("cat3", cat1);
        Category cat4 = new Category("cat4", cat3);

        Product product1 = new Product("product1", 1.0, cat1);
        Product product2 = new Product("product2", 2.0, cat2);
        Product product3 = new Product("product3", 3.0, cat3);
        Product product4 = new Product("product4", 4.0, cat4);

        Assert.assertEquals(product1.getTitle(), "product1");
        Assert.assertEquals(product2.getTitle(), "product2");
        Assert.assertEquals(product3.getTitle(), "product3");
        Assert.assertEquals(product4.getTitle(), "product4");

        Assert.assertEquals(product1.getProductId(), 1);
        Assert.assertEquals(product2.getProductId(), 2);
        Assert.assertEquals(product3.getProductId(), 3);
        Assert.assertEquals(product4.getProductId(), 4);

        Assert.assertEquals(product1.getPrice(), 1.0, 0.0);
        Assert.assertEquals(product2.getPrice(), 2.0, 0.0);
        Assert.assertEquals(product3.getPrice(), 3.0, 0.0);
        Assert.assertEquals(product4.getPrice(), 4.0, 0.0);

        Assert.assertEquals(cat1.getCategoryId(), 1);
        Assert.assertEquals(cat2.getCategoryId(), 2);
        Assert.assertEquals(cat3.getCategoryId(), 3);
        Assert.assertEquals(cat4.getCategoryId(), 4);

        Assert.assertFalse(product1.hasCategory(null));
        Assert.assertTrue(product1.hasCategory(cat1));
        Assert.assertFalse(product1.hasCategory(cat2));
        Assert.assertFalse(product1.hasCategory(cat3));
        Assert.assertFalse(product1.hasCategory(cat4));

        Assert.assertFalse(product2.hasCategory(null));
        Assert.assertTrue(product2.hasCategory(cat1));
        Assert.assertTrue(product2.hasCategory(cat2));
        Assert.assertFalse(product1.hasCategory(cat3));
        Assert.assertFalse(product1.hasCategory(cat4));

        Assert.assertFalse(product3.hasCategory(null));
        Assert.assertTrue(product3.hasCategory(cat1));
        Assert.assertFalse(product3.hasCategory(cat2));
        Assert.assertTrue(product3.hasCategory(cat3));
        Assert.assertFalse(product3.hasCategory(cat4));

        Assert.assertFalse(product4.hasCategory(null));
        Assert.assertTrue(product4.hasCategory(cat1));
        Assert.assertFalse(product4.hasCategory(cat2));
        Assert.assertTrue(product4.hasCategory(cat3));
        Assert.assertTrue(product4.hasCategory(cat4));
    }

}
