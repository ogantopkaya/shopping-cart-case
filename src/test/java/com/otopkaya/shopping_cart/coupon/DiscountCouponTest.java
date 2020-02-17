package com.otopkaya.shopping_cart.coupon;

import com.otopkaya.shopping_cart.DiscountType;
import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.test_utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiscountCouponTest {

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();
    }

    @Test
    public void testDiscountCoupon(){
        Category cat1 = new Category("cat1", null);

        DiscountCoupon coupon1 = DiscountCoupon.of(10, 5, DiscountType.AMOUNT);
        DiscountCoupon coupon2 = DiscountCoupon.of(25, 1, DiscountType.RATE);

        Assert.assertEquals(coupon1.getClass(), CouponAmountDiscount.class);
        Assert.assertEquals(((CouponAmountDiscount)coupon1).getDiscountAmount(), 10, 0);
        Assert.assertEquals(((CouponAmountDiscount)coupon1).getMinTotalAmount(), 5, 0);
        Assert.assertEquals(((CouponAmountDiscount)coupon1).precedingOrder(), 1);

        Assert.assertEquals(coupon2.getClass(), CouponRateDiscount.class);
        Assert.assertEquals(((CouponRateDiscount)coupon2).getRate(), 25, 0);
        Assert.assertEquals(((CouponRateDiscount)coupon2).getMinTotalAmount(), 1, 0);
        Assert.assertEquals(((CouponRateDiscount)coupon2).precedingOrder(), 0);

        Assert.assertEquals(coupon1.compareTo(coupon2), 1);

        Assert.assertNotEquals(coupon1, coupon2);
    }

    @Test(expected = NullPointerException.class)
    public void testFaultyInit(){
        Category cat1 = new Category("cat1", null);
        DiscountCoupon.of(25, 1, null);
    }

}
