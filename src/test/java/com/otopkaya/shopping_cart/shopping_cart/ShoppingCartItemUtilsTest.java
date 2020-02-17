package com.otopkaya.shopping_cart.shopping_cart;

import com.otopkaya.shopping_cart.product.Category;
import com.otopkaya.shopping_cart.product.Product;
import com.otopkaya.shopping_cart.test_utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartItemUtilsTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    ShoppingCartItem mockItem1, mockItem2;

    @Before
    public void before() throws NoSuchFieldException, IllegalAccessException {
        TestUtils.resetCounters();
    }

    @Test
    public void testTotalSalePrice(){
        Mockito.when(mockItem1.getSalePrice()).thenReturn(100.0);
        Mockito.when(mockItem2.getSalePrice()).thenReturn(200.0);

        Assert.assertEquals(ShoppingCartItemUtils.totalSalePrice(Arrays.asList(mockItem1, mockItem2)), 300 , 0);
    }


    @Test
    public void testItemsByCategory(){
        Category cat1 = new Category("cat1", null);
        Category cat2 = new Category("cat2", cat1);
        Category cat3 = new Category("cat3", cat1);
        Category cat4 = new Category("cat4", cat3);

        Product product1 = new Product("product1", 1.0, cat1);
        Product product2 = new Product("product2", 2.0, cat2);
        Product product3 = new Product("product3", 3.0, cat3);
        Product product4 = new Product("product4", 4.0, cat4);

        ShoppingCartItem item1 = new ShoppingCartItem(product1, 10);
        ShoppingCartItem item2 = new ShoppingCartItem(product2, 5);
        ShoppingCartItem item3 = new ShoppingCartItem(product3, 5);
        ShoppingCartItem item4 = new ShoppingCartItem(product4, 5);

        List<ShoppingCartItem> shoppingCartItems = Arrays.asList( item1, item2, item3, item4);

        Assert.assertArrayEquals(
                ShoppingCartItemUtils.itemsByCategory(cat1, shoppingCartItems).toArray(),
                Arrays.asList(item1, item2, item3, item4).toArray()
        );

        Assert.assertArrayEquals(
                ShoppingCartItemUtils.itemsByCategory(cat2, shoppingCartItems).toArray(),
                Arrays.asList(item2).toArray()
        );

        Assert.assertArrayEquals(
                ShoppingCartItemUtils.itemsByCategory(cat3, shoppingCartItems).toArray(),
                Arrays.asList(item3, item4).toArray()
        );

        Assert.assertArrayEquals(
                ShoppingCartItemUtils.itemsByCategory(cat4, shoppingCartItems).toArray(),
                Arrays.asList(item4).toArray()
        );

    }

    @Test
    public void testDistinctCategoryItemCount(){
        Category cat1 = new Category("cat1", null);
        Category cat2 = new Category("cat2", cat1);
        Category cat3 = new Category("cat3", cat1);

        Product product1 = new Product("product1", 10, cat1);
        Product product2 = new Product("product2", 20, cat1);
        Product product3 = new Product("product3", 30, cat2);
        Product product4 = new Product("product4", 40, cat3);

        ShoppingCartItem item1 = new ShoppingCartItem(product1, 10);
        ShoppingCartItem item2 = new ShoppingCartItem(product2, 5);
        ShoppingCartItem item3 = new ShoppingCartItem(product3, 2);
        ShoppingCartItem item4 = new ShoppingCartItem(product4, 8);

        Assert.assertEquals(1, ShoppingCartItemUtils.distinctCategoryCount(Arrays.asList(item1, item2)));
        Assert.assertEquals(2, ShoppingCartItemUtils.distinctCategoryCount(Arrays.asList(item1, item2, item3)));
        Assert.assertEquals(3, ShoppingCartItemUtils.distinctCategoryCount(Arrays.asList(item1, item2, item3, item4)));

    }

}
