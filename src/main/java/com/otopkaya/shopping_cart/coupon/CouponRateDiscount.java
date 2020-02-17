package com.otopkaya.shopping_cart.coupon;

import com.otopkaya.shopping_cart.shopping_cart.ShoppingCart;
import lombok.Getter;

public class CouponRateDiscount extends DiscountCoupon {

    @Getter private double rate;
    @Getter private double minTotalAmount;

    public CouponRateDiscount(double rate, double minTotalAmount) {
        this.rate = rate;
        this.minTotalAmount = minTotalAmount;
    }

    @Override
    public int precedingOrder() {
        return 0;
    }

    @Override
    public double couponDiscount(ShoppingCart shoppingCart) {
        if (shoppingCart.getTotalAmountAfterCoupons() >= minTotalAmount) {
            return shoppingCart.getTotalAmountAfterCoupons() * rate / 100;
        }
        return 0;
    }

}
