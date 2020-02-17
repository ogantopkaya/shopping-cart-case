package com.otopkaya.shopping_cart;

import com.otopkaya.shopping_cart.campaign.CampaignAmountDiscount;
import com.otopkaya.shopping_cart.campaign.CampaignRateDiscount;
import com.otopkaya.shopping_cart.coupon.CouponAmountDiscount;
import com.otopkaya.shopping_cart.coupon.CouponRateDiscount;
import com.otopkaya.shopping_cart.delivery.DeliveryCostCalculator;
import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.product.Product;
import com.otopkaya.shopping_cart.shopping_cart.ShoppingCart;

public class Main {

    public static void main(String[] args) {
        Category cat1 = new Category("cat1", null);
        Category cat2 = new Category("cat2", cat1);
        Category cat3 = new Category("cat3", cat1);
        Category cat4 = new Category("cat4", cat2);

        Product product1 = new Product("product1", 10, cat1);
        Product product2 = new Product("product2", 20, cat1);
        Product product3 = new Product("product3", 30, cat1);
        Product product4 = new Product("product4", 40, cat2);
        Product product5 = new Product("product5", 50, cat2);
        Product product6 = new Product("product6", 60, cat3);
        Product product7 = new Product("product7", 70, cat3);
        Product product8 = new Product("product8", 80, cat4);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDeliveryCostCalculator(new DeliveryCostCalculator(5,1.5));
        shoppingCart.add(product1, 10);
        shoppingCart.add(product2, 5);
        shoppingCart.add(product3, 5);
        shoppingCart.add(product4, 5);
        shoppingCart.add(product5, 5);
        shoppingCart.add(product6, 5);
        shoppingCart.add(product7, 5);
        shoppingCart.add(product8, 5);

        CampaignAmountDiscount camp1 = new CampaignAmountDiscount(cat2, 2, 15);
        CampaignAmountDiscount camp2 = new CampaignAmountDiscount(cat1, 5, 10);
        CampaignRateDiscount camp3 = new CampaignRateDiscount(cat1, 25, 5);

        CouponAmountDiscount coupon1 = new CouponAmountDiscount(20, 15);
        CouponRateDiscount coupon2 = new CouponRateDiscount( 10, 10);
        shoppingCart.applyDiscounts(camp1, camp2, camp3); // N/A
        shoppingCart.applyCoupons(coupon1, coupon2);

        shoppingCart.print();

    }

}
