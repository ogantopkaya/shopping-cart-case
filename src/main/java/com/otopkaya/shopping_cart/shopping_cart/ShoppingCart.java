package com.otopkaya.shopping_cart.shopping_cart;

import com.otopkaya.shopping_cart.delivery.DeliveryCostCalculator;
import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.product.Product;
import com.otopkaya.shopping_cart.coupon.DiscountCoupon;
import com.otopkaya.shopping_cart.campaign.DiscountCampaign;
import lombok.Getter;

import java.util.*;

public class ShoppingCart {

    final HashMap<Product, Integer> products = new HashMap<>();
    final SortedSet<DiscountCampaign> appliedDiscountCampaigns = new TreeSet<>();
    final SortedSet<DiscountCoupon> appliedDiscountCoupons = new TreeSet<>();
    ArrayList<ShoppingCartItem> shoppingCartItems;

    @Getter private DeliveryCostCalculator deliveryCostCalculator;

    public void add(Product product, int quantity) {
        int newQuantity = products.getOrDefault(product, 0) + quantity;
        if (newQuantity <= 0) {
            products.remove(product);
        }
        else {
            products.put(product, newQuantity);
        }
        reCalculate();
    }

    public void setDeliveryCostCalculator(DeliveryCostCalculator deliveryCostCalculator) {
        this.deliveryCostCalculator = deliveryCostCalculator;
        reCalculate();
    }

    //region Discounts
    public void applyDiscounts(DiscountCampaign... discounts) {
        appliedDiscountCampaigns.addAll(Arrays.asList(discounts));
        reCalculate();
    }

    public void removeDiscounts(DiscountCampaign... discounts) {
        appliedDiscountCampaigns.removeAll(Arrays.asList(discounts));
        reCalculate();
    }

    public void resetDiscounts(){
        appliedDiscountCampaigns.clear();
        reCalculate();
    }
    //endregion

    //region Coupons
    public void applyCoupons(DiscountCoupon... discountCoupons) {
        appliedDiscountCoupons.addAll(Arrays.asList(discountCoupons));
        reCalculate();
    }
    public void removeCoupons(DiscountCoupon... discountCoupons) {
        appliedDiscountCoupons.removeAll(Arrays.asList(discountCoupons));
        reCalculate();
    }
    public void resetCoupons() {
        appliedDiscountCoupons.clear();
        reCalculate();
    }
    //endregion

    // Recalculate
    void reCalculate(){
        shoppingCartItems = new ArrayList<>();
        products.forEach((product,quantity) -> {
            shoppingCartItems.add(new ShoppingCartItem(product, quantity));
        });

        totalAmountWithoutDiscount = ShoppingCartItemUtils.totalSalePrice(shoppingCartItems);

        // Apply discounts
        campaignDiscounts = 0;
        for (DiscountCampaign discount : appliedDiscountCampaigns) {
            double discountAmount = discount.applyDiscount(this.shoppingCartItems);
            campaignDiscounts += discountAmount;
        }

        couponDiscounts = 0;
        // Apply coupons
        for (DiscountCoupon coupon : appliedDiscountCoupons) {
            couponDiscounts += coupon.couponDiscount(this);
        }

        if (deliveryCostCalculator != null) {
            this.deliveryPrice = this.deliveryCostCalculator.calculateFor(this.shoppingCartItems);
        }
        else {
            this.deliveryPrice = 0;
        }

    }

    //region price info

    @Getter private double totalAmountWithoutDiscount;
    @Getter private double campaignDiscounts;
    @Getter private double couponDiscounts;
    @Getter private double deliveryPrice;

    public double getTotalAmountAfterDiscounts(){
        return totalAmountWithoutDiscount - campaignDiscounts;
    }

    public double getTotalAmountAfterCoupons(){
        return getTotalAmountAfterDiscounts() - couponDiscounts;
    }

    public double getGrandTotal() {
        return getTotalAmountAfterCoupons() + getDeliveryPrice();
    }

    //endregion price info

    public void print(){
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        HashMap<Category, List<ShoppingCartItem>> groupedByCategory = ShoppingCartItemUtils.groupedByCategory(this.shoppingCartItems);

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%10s %1s", "Category", "|"));
        stringBuilder.append(String.format("%15s %1s %10s %1s %8s %1s %10s %1s %10s %1s %10s",
                "Product", "|", "Quantity", "|", "Price", "|", "Total", "|",
                "Discount", "|", "Total"));

        stringBuilder.append("\n");
        stringBuilder.append(String.format("%s", "-------------------------------------------------------------------------------------------"));
        stringBuilder.append("\n");

        groupedByCategory.forEach( (category, items) -> {
            for (int i = 0; i < items.size(); i++) {
                String categoryTitle = i == 0 ? category.getTitle() : "";
                stringBuilder
                        .append(String.format("%10s %1s", categoryTitle, "|"))
                        .append(items.get(i).toString()).append("\n");
            }

            stringBuilder.append(String.format("%s", "-------------------------------------------------------------------------------------------"));

            stringBuilder.append("\n");

        });


        stringBuilder.append(String.format("%11s %78s", "Total", this.getTotalAmountAfterDiscounts()));
        stringBuilder.append("\n");
        stringBuilder.append(String.format("%11s %78s", "Coupons", -this.getCouponDiscounts()));
        stringBuilder.append("\n");
        stringBuilder.append(String.format("%11s %78s", "Total", this.getTotalAmountAfterCoupons()));
        stringBuilder.append("\n");
        stringBuilder.append(String.format("%11s %78s", "Delivery", this.deliveryPrice));
        stringBuilder.append("\n");
        stringBuilder.append(String.format("%11s %78s", "Grand Total", this.getGrandTotal()));

        return stringBuilder.toString();
    }
}
