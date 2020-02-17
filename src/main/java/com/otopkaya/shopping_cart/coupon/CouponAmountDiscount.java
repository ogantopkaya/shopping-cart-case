package com.otopkaya.shopping_cart.coupon;

import com.otopkaya.shopping_cart.shopping_cart.ShoppingCart;
import lombok.Getter;

public class CouponAmountDiscount extends DiscountCoupon {

    @Getter private double minTotalAmount;
    @Getter private double discountAmount;

    public CouponAmountDiscount(double discountAmount, double minTotalAmount) {
        this.minTotalAmount = minTotalAmount;
        this.discountAmount = discountAmount;
    }

    @Override
    public int precedingOrder() {
        return 1;
    }

    @Override
    public double couponDiscount(ShoppingCart shoppingCart) {
        if (shoppingCart.getTotalAmountAfterCoupons() >= minTotalAmount) {
            return this.discountAmount;
        }
        return 0;
    }

}
