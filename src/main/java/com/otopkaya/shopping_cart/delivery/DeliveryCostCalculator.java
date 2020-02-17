package com.otopkaya.shopping_cart.delivery;

import com.otopkaya.shopping_cart.shopping_cart.ShoppingCartItem;
import com.otopkaya.shopping_cart.shopping_cart.ShoppingCartItemUtils;
import lombok.Getter;

import java.util.List;

public class DeliveryCostCalculator {

    static final double FIXED_COST = 2.99;

    @Getter private double costPerDelivery;
    @Getter private double costPerProduct;
    @Getter private double fixedCost;

    public DeliveryCostCalculator(double costPerDelivery, double costPerProduct) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = FIXED_COST;
    }

    public DeliveryCostCalculator(double costPerDelivery, double costPerProduct, double fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    public double calculateFor(List<ShoppingCartItem> items) {
        int categoryCount = ShoppingCartItemUtils.distinctCategoryCount(items);
        return ( categoryCount * costPerDelivery ) + (items.size() * costPerProduct) + fixedCost;
    }

}
