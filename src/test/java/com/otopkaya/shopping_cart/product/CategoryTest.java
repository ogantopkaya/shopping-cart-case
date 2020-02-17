package com.otopkaya.shopping_cart.product;


import com.otopkaya.shopping_cart.test_utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();
    }

    @Test
    public void testConstuctor(){
        Category cat1 = new Category("cat1", null);
        Category cat2 = new Category("cat2", cat1);
        Category cat3 = new Category("cat3", cat1);
        Category cat4 = new Category("cat4", cat3);

        Assert.assertEquals(cat1.getCategoryId(), 1);
        Assert.assertEquals(cat2.getCategoryId(), 2);
        Assert.assertEquals(cat3.getCategoryId(), 3);
        Assert.assertEquals(cat4.getCategoryId(), 4);

        Assert.assertEquals(cat1.getTitle(), "cat1");
        Assert.assertEquals(cat2.getTitle(), "cat2");
        Assert.assertEquals(cat3.getTitle(), "cat3");
        Assert.assertEquals(cat4.getTitle(), "cat4");

        Assert.assertNull(cat1.getParent());
        Assert.assertEquals(cat2.getParent(), cat1);
        Assert.assertEquals(cat3.getParent(), cat1);
        Assert.assertEquals(cat4.getParent(), cat3);

        Assert.assertNotEquals(cat1, cat2);
        Assert.assertNotEquals(cat1, cat3);
        Assert.assertNotEquals(cat1, cat4);
        Assert.assertNotEquals(cat2, cat3);
        Assert.assertNotEquals(cat2, cat4);
        Assert.assertNotEquals(cat3, cat4);

    }

}
