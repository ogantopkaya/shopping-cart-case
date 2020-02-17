package com.otopkaya.shopping_cart.campaign;

import com.otopkaya.shopping_cart.DiscountType;
import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.shopping_cart.ShoppingCartItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@EqualsAndHashCode(of = {"campaignId"})
public abstract class DiscountCampaign implements Comparable {

    private static final AtomicInteger count = new AtomicInteger(0); // For generating incremental ids

    @Getter private int campaignId = count.incrementAndGet();

    public abstract int precedingOrder();

    @Override
    public int compareTo(Object o) {
        int result = Integer.compare(this.precedingOrder(), ((DiscountCampaign)o).precedingOrder());
        if (result == 0){
            result = Integer.compare(this.campaignId, ((DiscountCampaign)o).campaignId);
        }
        return result;
    }

    public abstract double applyDiscount(List<ShoppingCartItem> items);

    public static DiscountCampaign of(Category category, double value, int minQuantity, DiscountType discountType) {
        switch (discountType) {
            case RATE:
                return new CampaignRateDiscount(category, value, minQuantity);
            case AMOUNT:
                return new CampaignAmountDiscount(category, value, minQuantity);
        }
        throw new RuntimeException("Unsupported discount type");
    }

}
