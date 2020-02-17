package com.otopkaya.shopping_cart.campaign;

import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.product.Product;
import com.otopkaya.shopping_cart.shopping_cart.ShoppingCartItem;
import com.otopkaya.shopping_cart.test_utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CampaignRateDiscountTest {

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();
    }

    @Test
    public void testCampaignRateDiscount(){
        Category cat1 = new Category("cat1", null);
        Category cat2 = new Category("cat2", cat1);
        Category cat3 = new Category("cat3", null);

        Product prod1 = new Product("prod1", 100, cat1);
        Product prod2 = new Product("prod2", 200, cat2);
        Product prod3 = new Product("prod3", 300, cat3);

        List<ShoppingCartItem> shoppingCartItems = Arrays.asList(
                new ShoppingCartItem(prod1, 10),
                new ShoppingCartItem(prod2, 5),
                new ShoppingCartItem(prod3, 5));

        Assert.assertEquals(shoppingCartItems.get(0).getSalePrice(), 1000, 0);
        Assert.assertEquals(shoppingCartItems.get(1).getSalePrice(), 1000, 0);
        Assert.assertEquals(shoppingCartItems.get(2).getSalePrice(), 1500, 0);

        CampaignRateDiscount campaign1 = new CampaignRateDiscount(cat1, 10, 5);
        Assert.assertEquals(campaign1.getCategory(), cat1);
        Assert.assertEquals(campaign1.getRate(), 10, 0);
        Assert.assertEquals(campaign1.getMinQuantity(), 5);
        Assert.assertEquals(campaign1.precedingOrder(), 0);

        double campaign1Discounts = campaign1.applyDiscount(shoppingCartItems);
        Assert.assertEquals(shoppingCartItems.get(0).getSalePrice(), 900, 0);
        Assert.assertEquals(shoppingCartItems.get(1).getSalePrice(), 900, 0);
        Assert.assertEquals(shoppingCartItems.get(2).getSalePrice(), 1500, 0);
        Assert.assertEquals(campaign1Discounts, 200, 0);

        CampaignRateDiscount campaign2 = new CampaignRateDiscount(cat2, 20, 4);
        Assert.assertEquals(campaign2.getCategory(), cat2);
        Assert.assertEquals(campaign2.getRate(), 20, 0);
        Assert.assertEquals(campaign2.getMinQuantity(), 4);
        Assert.assertEquals(campaign2.precedingOrder(), 0);

        double campaign2Discounts = campaign2.applyDiscount(shoppingCartItems);
        Assert.assertEquals(shoppingCartItems.get(0).getSalePrice(), 900, 0);
        Assert.assertEquals(shoppingCartItems.get(1).getSalePrice(), 720, 0);
        Assert.assertEquals(shoppingCartItems.get(2).getSalePrice(), 1500, 0);
        Assert.assertEquals(campaign2Discounts, 180, 0);

        CampaignRateDiscount campaign3 = new CampaignRateDiscount(cat3, 100, 10);
        Assert.assertEquals(campaign3.getCategory(), cat3);
        Assert.assertEquals(campaign3.getRate(), 100, 0);
        Assert.assertEquals(campaign3.getMinQuantity(), 10);
        Assert.assertEquals(campaign3.precedingOrder(), 0);

        double campaign3Discounts = campaign3.applyDiscount(shoppingCartItems);
        Assert.assertEquals(shoppingCartItems.get(0).getSalePrice(), 900, 0);
        Assert.assertEquals(shoppingCartItems.get(1).getSalePrice(), 720, 0);
        Assert.assertEquals(shoppingCartItems.get(2).getSalePrice(), 1500, 0);
        Assert.assertEquals(campaign3Discounts, 0, 0);

        CampaignRateDiscount campaign4 = new CampaignRateDiscount(cat3, 50, 1);
        Assert.assertEquals(campaign4.getCategory(), cat3);
        Assert.assertEquals(campaign4.getRate(), 50, 0);
        Assert.assertEquals(campaign4.getMinQuantity(), 1);
        Assert.assertEquals(campaign4.precedingOrder(), 0);

        double campaign4Discounts = campaign4.applyDiscount(shoppingCartItems);
        Assert.assertEquals(shoppingCartItems.get(0).getSalePrice(), 900, 0);
        Assert.assertEquals(shoppingCartItems.get(1).getSalePrice(), 720, 0);
        Assert.assertEquals(shoppingCartItems.get(2).getSalePrice(), 750, 0);
        Assert.assertEquals(campaign4Discounts, 750, 0);
    }
    
}
