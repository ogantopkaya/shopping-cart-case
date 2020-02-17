package com.otopkaya.shopping_cart.campaign;

import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.shopping_cart.ShoppingCartItem;
import com.otopkaya.shopping_cart.shopping_cart.ShoppingCartItemUtils;
import lombok.Getter;

import java.util.List;

public class CampaignAmountDiscount extends DiscountCampaign {

    @Getter private Category category;
    @Getter private double amount;
    @Getter private int minQuantity;

    public CampaignAmountDiscount(Category category, double amount, int minQuantity) {
        this.category = category;
        this.amount = amount;
        this.minQuantity = minQuantity;
    }

    @Override
    public int precedingOrder() {
        return 1;
    }

    @Override
    public double applyDiscount(List<ShoppingCartItem> items) {
        double totalDiscount = 0;
        for (ShoppingCartItem item : ShoppingCartItemUtils.itemsByCategory(this.category, items)) {
            if (item.getQuantity() >= minQuantity) {
                totalDiscount += amount;
                item.addDiscount(amount);
            }
        }
        return totalDiscount;
    }

}
