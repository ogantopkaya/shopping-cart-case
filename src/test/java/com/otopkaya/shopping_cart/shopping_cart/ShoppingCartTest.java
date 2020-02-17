package com.otopkaya.shopping_cart.shopping_cart;

import com.otopkaya.shopping_cart.campaign.CampaignAmountDiscount;
import com.otopkaya.shopping_cart.campaign.CampaignRateDiscount;
import com.otopkaya.shopping_cart.coupon.CouponAmountDiscount;
import com.otopkaya.shopping_cart.coupon.CouponRateDiscount;
import com.otopkaya.shopping_cart.delivery.DeliveryCostCalculator;
import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.product.Product;
import com.otopkaya.shopping_cart.test_utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartTest {

    Category cat1;
    Category cat2;
    Category cat3;
    Category cat4;

    Product product1;
    Product product2;
    Product product3;
    Product product4;

    ShoppingCart shoppingCart;

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();

        cat1 = new Category("cat1", null);
        cat2 = new Category("cat2", cat1);
        cat3 = new Category("cat3", cat1);
        cat4 = new Category("cat4", cat3);

        product1 = new Product("product1", 10, cat1);
        product2 = new Product("product2", 20, cat2);
        product3 = new Product("product3", 30, cat3);
        product4 = new Product("product4", 40, cat4);

        shoppingCart = spy(ShoppingCart.class);
    }

    @Test
    public void testAddCart(){
        shoppingCart.add(product1, 0);
        Assert.assertEquals(shoppingCart.products.size(), 0);

        shoppingCart.add(product1, 1);
        Assert.assertEquals(shoppingCart.products.size(), 1);
        Assert.assertEquals(shoppingCart.products.get(product1).intValue(), 1);

        shoppingCart.add(product1, 2);
        Assert.assertEquals(shoppingCart.products.size(), 1);
        Assert.assertEquals(shoppingCart.products.get(product1).intValue(), 3);

        shoppingCart.add(product2, 2);
        Assert.assertEquals(shoppingCart.products.size(), 2);
        Assert.assertEquals(shoppingCart.products.get(product1).intValue(), 3);
        Assert.assertEquals(shoppingCart.products.get(product2).intValue(), 2);

        shoppingCart.add(product1, -3);
        Assert.assertEquals(shoppingCart.products.size(), 1);
        Assert.assertEquals(shoppingCart.products.get(product1), null);
        Assert.assertEquals(shoppingCart.products.get(product2).intValue(), 2);

        shoppingCart.add(product1, -3);
        Assert.assertEquals(shoppingCart.products.size(), 1);
        Assert.assertEquals(shoppingCart.products.get(product1), null);
        Assert.assertEquals(shoppingCart.products.get(product2).intValue(), 2);

        shoppingCart.add(product2, -2);
        Assert.assertEquals(shoppingCart.products.size(), 0);
        Assert.assertEquals(shoppingCart.products.get(product1), null);
        Assert.assertEquals(shoppingCart.products.get(product2), null);

        verify(shoppingCart, times(7)).reCalculate();
    }


    @Test
    public void testApplyDiscounts(){
        CampaignAmountDiscount camp1 = new CampaignAmountDiscount(cat1, 10, 5);
        CampaignAmountDiscount camp2 = new CampaignAmountDiscount(cat2, 20, 15);
        CampaignRateDiscount camp3 = new CampaignRateDiscount(cat1, 20, 10);
        CampaignRateDiscount camp4 = new CampaignRateDiscount(cat3, 20, 10);

        shoppingCart.applyDiscounts(camp1, camp2, camp3, camp4);

        Assert.assertArrayEquals(
                shoppingCart.appliedDiscountCampaigns.toArray(),
                Arrays.asList(camp3, camp4, camp1, camp2).toArray()
        );

        shoppingCart.removeDiscounts(camp1);
        Assert.assertArrayEquals(
                shoppingCart.appliedDiscountCampaigns.toArray(),
                Arrays.asList(camp3, camp4, camp2).toArray()
        );

        shoppingCart.resetDiscounts();
        Assert.assertEquals(shoppingCart.appliedDiscountCampaigns.size(), 0);

        verify(shoppingCart, times(3)).reCalculate();
    }

    @Test
    public void testApplyCoupons(){
        CouponAmountDiscount coupon1 = new CouponAmountDiscount(10, 5);
        CouponAmountDiscount coupon2 = new CouponAmountDiscount(20, 15);
        CouponRateDiscount coupon3 = new CouponRateDiscount( 20, 10);
        CouponRateDiscount coupon4 = new CouponRateDiscount( 20, 10);

        shoppingCart.applyCoupons(coupon1, coupon2, coupon3, coupon4);

        Assert.assertArrayEquals(
                shoppingCart.appliedDiscountCoupons.toArray(),
                Arrays.asList(coupon3, coupon4, coupon1, coupon2).toArray()
        );

        shoppingCart.removeCoupons(coupon1);
        Assert.assertArrayEquals(
                shoppingCart.appliedDiscountCoupons.toArray(),
                Arrays.asList(coupon3, coupon4, coupon2).toArray()
        );

        shoppingCart.resetCoupons();
        Assert.assertEquals(shoppingCart.appliedDiscountCoupons.size(), 0);

        verify(shoppingCart, times(3)).reCalculate();
    }

    @Test
    public void testSetDeliveryCostCalculator(){
        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(5, 1.5);
        shoppingCart.setDeliveryCostCalculator(deliveryCostCalculator);
        Assert.assertEquals(shoppingCart.getDeliveryCostCalculator(), deliveryCostCalculator);
        verify(shoppingCart, times(1)).reCalculate();
    }

    @Test
    public void testRecalculate(){
        shoppingCart.add(product1, 10);
        shoppingCart.add(product2, 5);
        shoppingCart.add(product3, 5);
        shoppingCart.add(product4, 5);

        CampaignAmountDiscount camp1 = new CampaignAmountDiscount(cat2, 2, 15);
        CampaignAmountDiscount camp2 = new CampaignAmountDiscount(cat1, 5, 10);
        CampaignRateDiscount camp3 = new CampaignRateDiscount(cat1, 25, 5);

        CouponAmountDiscount coupon1 = new CouponAmountDiscount(20, 15);
        CouponRateDiscount coupon2 = new CouponRateDiscount( 10, 10);

        Assert.assertEquals(550, shoppingCart.getTotalAmountWithoutDiscount(), 0.0);
        Assert.assertEquals(0, shoppingCart.getCampaignDiscounts(),0);
        Assert.assertEquals(550, shoppingCart.getTotalAmountAfterDiscounts(),0);
        Assert.assertEquals(0, shoppingCart.getCouponDiscounts(), 0);
        Assert.assertEquals(550, shoppingCart.getTotalAmountAfterCoupons(), 0);
        Assert.assertEquals(0, shoppingCart.getDeliveryPrice(), 0);
        Assert.assertEquals(550, shoppingCart.getGrandTotal(), 0);

        shoppingCart.applyDiscounts(camp1); // N/A

        Assert.assertEquals(550, shoppingCart.getTotalAmountWithoutDiscount(), 0.0);
        Assert.assertEquals(0, shoppingCart.getCampaignDiscounts(),0);
        Assert.assertEquals(550, shoppingCart.getTotalAmountAfterDiscounts(),0);
        Assert.assertEquals(0, shoppingCart.getCouponDiscounts(), 0);
        Assert.assertEquals(550, shoppingCart.getTotalAmountAfterCoupons(), 0);
        Assert.assertEquals(0, shoppingCart.getDeliveryPrice(), 0);
        Assert.assertEquals(550, shoppingCart.getGrandTotal(), 0);

        shoppingCart.applyDiscounts(camp2); // Only camp2 is applicable

        Assert.assertEquals(550, shoppingCart.getTotalAmountWithoutDiscount(), 0.0);
        Assert.assertEquals(5, shoppingCart.getCampaignDiscounts(),0);
        Assert.assertEquals(545, shoppingCart.getTotalAmountAfterDiscounts(),0);
        Assert.assertEquals(0, shoppingCart.getCouponDiscounts(), 0);
        Assert.assertEquals(545, shoppingCart.getTotalAmountAfterCoupons(), 0);
        Assert.assertEquals(0, shoppingCart.getDeliveryPrice(), 0);
        Assert.assertEquals(545, shoppingCart.getGrandTotal(), 0);

        shoppingCart.applyDiscounts(camp3); // First camp3 then camp2

        Assert.assertEquals(550, shoppingCart.getTotalAmountWithoutDiscount(), 0.0);
        Assert.assertEquals(142.5, shoppingCart.getCampaignDiscounts(),0);
        Assert.assertEquals(407.5, shoppingCart.getTotalAmountAfterDiscounts(),0);
        Assert.assertEquals(0, shoppingCart.getCouponDiscounts(), 0);
        Assert.assertEquals(407.5, shoppingCart.getTotalAmountAfterCoupons(), 0);
        Assert.assertEquals(0, shoppingCart.getDeliveryPrice(), 0);
        Assert.assertEquals(407.5, shoppingCart.getGrandTotal(), 0);

        shoppingCart.removeDiscounts(camp2); // First camp3 then camp2

        Assert.assertEquals(550, shoppingCart.getTotalAmountWithoutDiscount(), 0.0);
        Assert.assertEquals(137.5, shoppingCart.getCampaignDiscounts(),0);
        Assert.assertEquals(412.5, shoppingCart.getTotalAmountAfterDiscounts(),0);
        Assert.assertEquals(0, shoppingCart.getCouponDiscounts(), 0);
        Assert.assertEquals(412.5, shoppingCart.getTotalAmountAfterCoupons(), 0);
        Assert.assertEquals(0, shoppingCart.getDeliveryPrice(), 0);
        Assert.assertEquals(412.5, shoppingCart.getGrandTotal(), 0);

        shoppingCart.applyCoupons(coupon1);
        Assert.assertEquals(550, shoppingCart.getTotalAmountWithoutDiscount(), 0.0);
        Assert.assertEquals(137.5, shoppingCart.getCampaignDiscounts(),0);
        Assert.assertEquals(412.5, shoppingCart.getTotalAmountAfterDiscounts(),0);
        Assert.assertEquals(20, shoppingCart.getCouponDiscounts(), 0);
        Assert.assertEquals(392.5, shoppingCart.getTotalAmountAfterCoupons(), 0);
        Assert.assertEquals(0, shoppingCart.getDeliveryPrice(), 0);
        Assert.assertEquals(392.5, shoppingCart.getGrandTotal(), 0);

        shoppingCart.applyCoupons(coupon2);
        Assert.assertEquals(550, shoppingCart.getTotalAmountWithoutDiscount(), 0.0);
        Assert.assertEquals(137.5, shoppingCart.getCampaignDiscounts(),0);
        Assert.assertEquals(412.5, shoppingCart.getTotalAmountAfterDiscounts(),0);
        Assert.assertEquals(61.25, shoppingCart.getCouponDiscounts(), 0);
        Assert.assertEquals(351.25, shoppingCart.getTotalAmountAfterCoupons(), 0);
        Assert.assertEquals(0, shoppingCart.getDeliveryPrice(), 0);
        Assert.assertEquals(351.25, shoppingCart.getGrandTotal(), 0);

        shoppingCart.setDeliveryCostCalculator(new DeliveryCostCalculator(5,1.5));
        Assert.assertEquals(550, shoppingCart.getTotalAmountWithoutDiscount(), 0.0);
        Assert.assertEquals(137.5, shoppingCart.getCampaignDiscounts(),0);
        Assert.assertEquals(412.5, shoppingCart.getTotalAmountAfterDiscounts(),0);
        Assert.assertEquals(61.25, shoppingCart.getCouponDiscounts(), 0);
        Assert.assertEquals(351.25, shoppingCart.getTotalAmountAfterCoupons(), 0);
        Assert.assertEquals(28.99, shoppingCart.getDeliveryPrice(), 0.001);
        Assert.assertEquals(380.24, shoppingCart.getGrandTotal(), 0);
    }

}
