package com.otopkaya.shopping_cart.campaign;

import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.shopping_cart.ShoppingCartItem;
import com.otopkaya.shopping_cart.shopping_cart.ShoppingCartItemUtils;
import lombok.Getter;

import java.util.List;

public class CampaignRateDiscount extends DiscountCampaign {

    @Getter private Category category;
    @Getter private double rate;
    @Getter private int minQuantity;

    public CampaignRateDiscount(Category category, double rate, int minQuantity) {
        this.category = category;
        this.rate = rate;
        this.minQuantity = minQuantity;
    }

    @Override
    public int precedingOrder() {
        return 0;
    }

    @Override
    public double applyDiscount(List<ShoppingCartItem> items) {
        double totalDiscount = 0;
        for (ShoppingCartItem item : ShoppingCartItemUtils.itemsByCategory(this.category, items)) {
            if (item.getQuantity() >= minQuantity) {
                double discount = item.getSalePrice() * rate / 100;
                totalDiscount += discount;
                item.addDiscount(discount);
            }
        }
        return totalDiscount;
    }
}
