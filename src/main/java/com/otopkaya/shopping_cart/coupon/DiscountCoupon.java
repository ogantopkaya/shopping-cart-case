package com.otopkaya.shopping_cart.coupon;

import com.otopkaya.shopping_cart.DiscountType;
import com.otopkaya.shopping_cart.shopping_cart.ShoppingCart;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class DiscountCoupon implements Comparable{

    private static final AtomicInteger count = new AtomicInteger(0); // For generating incremental ids

    @Getter private int couponId = count.incrementAndGet();

    public abstract int precedingOrder();

    @Override
    public int compareTo(Object o) {
        int result = Integer.compare(this.precedingOrder(), ((DiscountCoupon)o).precedingOrder());
        if (result == 0){
            result = Integer.compare(this.couponId, ((DiscountCoupon)o).couponId);
        }
        return result;
    }

    public abstract double couponDiscount(ShoppingCart shoppingCart);

    //Factory Method
    public static DiscountCoupon of(double value, int minTotalAmount, DiscountType discountType) {
        switch (discountType) {
            case RATE:
                return new CouponRateDiscount(value, minTotalAmount);
            case AMOUNT:
                return new CouponAmountDiscount(value, minTotalAmount);
            default:
                throw new RuntimeException("Unsupported discount type");
        }
    }

}
