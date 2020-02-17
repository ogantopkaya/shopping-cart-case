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

public class CampaignAmountDiscountTest {

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();
    }

    @Test
    public void testCampaignAmountDiscount(){
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

        CampaignAmountDiscount campaign1 = new CampaignAmountDiscount(cat1, 10, 5);
        Assert.assertEquals(campaign1.getCategory(), cat1);
        Assert.assertEquals(campaign1.getAmount(), 10, 0);
        Assert.assertEquals(campaign1.getMinQuantity(), 5);
        Assert.assertEquals(campaign1.precedingOrder(), 1);

        double campaign1Discounts = campaign1.applyDiscount(shoppingCartItems);
        Assert.assertEquals(shoppingCartItems.get(0).getSalePrice(), 990, 0);
        Assert.assertEquals(shoppingCartItems.get(1).getSalePrice(), 990, 0);
        Assert.assertEquals(shoppingCartItems.get(2).getSalePrice(), 1500, 0);
        Assert.assertEquals(campaign1Discounts, 20, 0);

        CampaignAmountDiscount campaign2 = new CampaignAmountDiscount(cat2, 20, 4);
        Assert.assertEquals(campaign2.getCategory(), cat2);
        Assert.assertEquals(campaign2.getAmount(), 20, 0);
        Assert.assertEquals(campaign2.getMinQuantity(), 4);
        Assert.assertEquals(campaign2.precedingOrder(), 1);

        double campaign2Discounts = campaign2.applyDiscount(shoppingCartItems);
        Assert.assertEquals(shoppingCartItems.get(0).getSalePrice(), 990, 0);
        Assert.assertEquals(shoppingCartItems.get(1).getSalePrice(), 970, 0);
        Assert.assertEquals(shoppingCartItems.get(2).getSalePrice(), 1500, 0);
        Assert.assertEquals(campaign2Discounts, 20, 0);

        CampaignAmountDiscount campaign3 = new CampaignAmountDiscount(cat3, 100, 10);
        Assert.assertEquals(campaign3.getCategory(), cat3);
        Assert.assertEquals(campaign3.getAmount(), 100, 0);
        Assert.assertEquals(campaign3.getMinQuantity(), 10);
        Assert.assertEquals(campaign3.precedingOrder(), 1);

        double campaign3Discounts = campaign3.applyDiscount(shoppingCartItems);
        Assert.assertEquals(shoppingCartItems.get(0).getSalePrice(), 990, 0);
        Assert.assertEquals(shoppingCartItems.get(1).getSalePrice(), 970, 0);
        Assert.assertEquals(shoppingCartItems.get(2).getSalePrice(), 1500, 0);
        Assert.assertEquals(campaign3Discounts, 0, 0);

        CampaignAmountDiscount campaign4 = new CampaignAmountDiscount(cat3, 100, 1);
        Assert.assertEquals(campaign4.getCategory(), cat3);
        Assert.assertEquals(campaign4.getAmount(), 100, 0);
        Assert.assertEquals(campaign4.getMinQuantity(), 1);
        Assert.assertEquals(campaign4.precedingOrder(), 1);

        double campaign4Discounts = campaign4.applyDiscount(shoppingCartItems);
        Assert.assertEquals(shoppingCartItems.get(0).getSalePrice(), 990, 0);
        Assert.assertEquals(shoppingCartItems.get(1).getSalePrice(), 970, 0);
        Assert.assertEquals(shoppingCartItems.get(2).getSalePrice(), 1400, 0);
        Assert.assertEquals(campaign4Discounts, 100, 0);


        Assert.assertNotEquals(campaign1, campaign2);
        Assert.assertNotEquals(campaign1, campaign3);

    }


}
