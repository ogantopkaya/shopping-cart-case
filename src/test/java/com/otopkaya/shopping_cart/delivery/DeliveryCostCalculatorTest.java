package com.otopkaya.shopping_cart.delivery;

import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.product.Product;
import com.otopkaya.shopping_cart.shopping_cart.ShoppingCartItem;
import com.otopkaya.shopping_cart.test_utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class DeliveryCostCalculatorTest {

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();
    }


    @Test
    public void testDelivery(){
        Category cat1 = new Category("cat1", null);
        Category cat2 = new Category("cat2", cat1);
        Category cat3 = new Category("cat3", cat1);

        Product product1 = new Product("product1", 10, cat1);
        Product product2 = new Product("product2", 20, cat1);
        Product product3 = new Product("product3", 30, cat2);
        Product product4 = new Product("product4", 40, cat3);

        ShoppingCartItem item1 = new ShoppingCartItem(product1, 10);
        ShoppingCartItem item2 = new ShoppingCartItem(product2, 5);
        ShoppingCartItem item3 = new ShoppingCartItem(product3, 2);
        ShoppingCartItem item4 = new ShoppingCartItem(product4, 8);

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(1.5, 3, 2.5);
        Assert.assertEquals(1.5, deliveryCostCalculator.getCostPerDelivery(), 0);
        Assert.assertEquals(3, deliveryCostCalculator.getCostPerProduct(), 0);
        Assert.assertEquals(2.5, deliveryCostCalculator.getFixedCost(), 0);

        Assert.assertEquals(7, deliveryCostCalculator.calculateFor(Arrays.asList(item1)), 0);
        Assert.assertEquals(10, deliveryCostCalculator.calculateFor(Arrays.asList(item1, item2)), 0);
        Assert.assertEquals(14.5, deliveryCostCalculator.calculateFor(Arrays.asList(item1, item2, item3)), 0);
        Assert.assertEquals(19, deliveryCostCalculator.calculateFor(Arrays.asList(item1, item2, item3, item4)), 0);
    }

    @Test
    public void testDeliveryCalculator(){
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(1.5, 3);
        Assert.assertEquals(1.5, deliveryCostCalculator.getCostPerDelivery(), 0);
        Assert.assertEquals(3, deliveryCostCalculator.getCostPerProduct(), 0);
        Assert.assertEquals(2.99, deliveryCostCalculator.getFixedCost(), 0);
    }

}
