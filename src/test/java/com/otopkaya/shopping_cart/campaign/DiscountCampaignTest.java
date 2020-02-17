package com.otopkaya.shopping_cart.campaign;

import com.otopkaya.shopping_cart.DiscountType;
import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.test_utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiscountCampaignTest {

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();
    }

    @Test
    public void testDiscountCampaign(){
        Category cat1 = new Category("cat1", null);

        DiscountCampaign campaign1 = DiscountCampaign.of(cat1, 10, 5, DiscountType.AMOUNT);
        DiscountCampaign campaign2 = DiscountCampaign.of(cat1, 25, 1, DiscountType.RATE);

        Assert.assertEquals(campaign1.getClass(), CampaignAmountDiscount.class);
        Assert.assertEquals(((CampaignAmountDiscount)campaign1).getCategory(), cat1);
        Assert.assertEquals(((CampaignAmountDiscount)campaign1).getAmount(), 10, 0);
        Assert.assertEquals(((CampaignAmountDiscount)campaign1).getMinQuantity(), 5);
        Assert.assertEquals(((CampaignAmountDiscount)campaign1).precedingOrder(), 1);

        Assert.assertEquals(campaign2.getClass(), CampaignRateDiscount.class);
        Assert.assertEquals(((CampaignRateDiscount)campaign2).getCategory(), cat1);
        Assert.assertEquals(((CampaignRateDiscount)campaign2).getRate(), 25, 0);
        Assert.assertEquals(((CampaignRateDiscount)campaign2).getMinQuantity(), 1);
        Assert.assertEquals(((CampaignRateDiscount)campaign2).precedingOrder(), 0);

        Assert.assertEquals(campaign1.compareTo(campaign2), 1);
    }

    @Test(expected = NullPointerException.class)
    public void testFaultyInit(){
        Category cat1 = new Category("cat1", null);
        DiscountCampaign.of(cat1, 25, 1, null);
    }

}
