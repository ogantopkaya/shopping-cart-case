package com.otopkaya.shopping_cart.coupon;

import com.otopkaya.shopping_cart.shopping_cart.ShoppingCart;
import com.otopkaya.shopping_cart.test_utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CouponRateDiscountTest {

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();
    }

    @Mock
    ShoppingCart mockedShoppingCart;

    @Test
    public void testCouponAmountDiscount() {
        CouponRateDiscount couponRateDiscount = new CouponRateDiscount(50, 250);

        Assert.assertEquals(couponRateDiscount.getRate(), 50, 0);
        Assert.assertEquals(couponRateDiscount.getMinTotalAmount(), 250, 0);
        Assert.assertEquals(couponRateDiscount.precedingOrder(), 0);

        Mockito.when(mockedShoppingCart.getTotalAmountAfterCoupons()).thenReturn(100.0);
        Assert.assertEquals(100.0, mockedShoppingCart.getTotalAmountAfterCoupons(), 0.0);
        Assert.assertEquals(couponRateDiscount.couponDiscount(mockedShoppingCart), 0, 0);

        Mockito.when(mockedShoppingCart.getTotalAmountAfterCoupons()).thenReturn(250.0);
        Assert.assertEquals(250.0, mockedShoppingCart.getTotalAmountAfterCoupons(), 0.0);
        Assert.assertEquals(couponRateDiscount.couponDiscount(mockedShoppingCart), 125, 0);

    }

}
