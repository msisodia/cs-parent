package com.capgemini.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.capgemini.bean.GiftCard;
import com.capgemini.bean.OrderEntity;
import com.capgemini.bean.ProductCatalog;
import com.capgemini.service.CatalogService;

public class CartServiceimplTest {

	@InjectMocks
	CartServiceimpl service;

	@Mock
	RestTemplate restTemplate;

	@Mock
	CatalogService catalogService;

	@Mock
	UserCartModel userCartModel;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCartServiceimpl_1() throws Exception {
		CartServiceimpl result = new CartServiceimpl();
		assertNotNull(result);
	}

	@Test
	public void testAddToCart_1() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("productId", "111");
		params.put("userId", "capgemini");

		Mockito.when(restTemplate.postForObject(Mockito.isA(String.class), Mockito.isA(String.class),Mockito.isA(Class.class), Mockito.isA(Class.class))).thenReturn(params);
		service.addToCart("111", "capgemini");
	}

	@Test
	public void testAddUserGiftCard_1() throws Exception {
		GiftCard card = new GiftCard();
		card.setGiftCardId("capgemini");
		card.setGiftCardValue("100000000");
		Mockito.when(restTemplate.getForObject(Mockito.isA(String.class), Mockito.isA(Class.class),Mockito.isA(Class.class), Mockito.isA(Class.class))).thenReturn(card);
		service.addUserGiftCard(card);

	}

	@Test
	public void testDeleteFromCart_1() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("productId", "1111");
		params.put("userId", "capgemini");
		Mockito.when(restTemplate.postForObject(Mockito.isA(String.class), Mockito.isA(String.class),Mockito.isA(Class.class), Mockito.isA(Class.class))).thenReturn(params);
		service.deleteFromCart("1111", "capgemini");
	}

	@Test
	public void testEmptyCart_1() throws Exception {
		Mockito.when(restTemplate.postForObject(Mockito.isA(String.class), Mockito.isA(String.class),Mockito.isA(Class.class), Mockito.isA(Class.class))).thenReturn(null);
		service.emptyCart("capgemini");

	}

	@Test
	public void testGetAllOrder_1() throws Exception {

		OrderEntity arr1[] = new OrderEntity[1];
		ResponseEntity<OrderEntity[]> orderlists = new ResponseEntity<>(arr1, HttpStatus.OK);

		Mockito.when(restTemplate.getForEntity(Mockito.isA(String.class), Mockito.isA(Class.class),Mockito.isA(String.class))).thenReturn(orderlists);
		service.getAllOrder("5");
	}

	@Test
	public void testGetCardDetails_1() throws Exception {
		UserCartModel user = new UserCartModel();
		ResponseEntity<UserCartModel> cart = new ResponseEntity<>(user, HttpStatus.OK);

		Mockito.when(restTemplate.getForEntity(Mockito.isA(String.class), Mockito.isA(Class.class),Mockito.isA(String.class))).thenReturn(cart);
		service.getCardDetails("capgemini");
	}

	@Test
	public void testGetProductPrice_1() throws Exception {
		String productId = "1221";
		List<ProductCatalog> list = new ArrayList<>();
		ProductCatalog catalog = new ProductCatalog();
		catalog.setProductIdChild("1221");
		catalog.setPrice("100");
		list.add(catalog);
		Mockito.when(catalogService.getProduct()).thenReturn(list);
		String result = service.getProductPrice(productId);
		assertEquals("100", result);
	}

	@Test
	public void testGetProductPrice_2() throws Exception {
		String productId = "1221";
		List<ProductCatalog> list = new ArrayList<>();
		ProductCatalog catalog = new ProductCatalog();
		catalog.setProductIdChild("1223");
		catalog.setPrice("100");
		list.add(catalog);
		Mockito.when(catalogService.getProduct()).thenReturn(list);
		String result = service.getProductPrice(productId);
		assertEquals(null, result);
	}

	@Test
	public void testSetProductPrice_1() throws Exception {
		List<ProductCartModel> list = new ArrayList<>();
		ProductCartModel cart = new ProductCartModel();
		cart.setPrice("800");
		cart.setProductId("111");
		cart.setQuantity("1");
		list.add(cart);
		Mockito.when(userCartModel.getCartItemList()).thenReturn(list);
		service.setProductPrice(userCartModel);
	}

	@Test(expected = Exception.class)
	public void testGetUserGiftCard_1() throws Exception {
		GiftCard giftCard = new GiftCard();
		Mockito.when(restTemplate.getForObject(Mockito.isA(String.class), Mockito.isA(Class.class))).thenReturn(giftCard);
		service.getUserGiftCard("dtp");
	}

	@Test(expected = Exception.class)
	public void testGetUserGiftCard_2() throws Exception {
		GiftCard giftCard = new GiftCard();
		giftCard.setGiftCardId("capgemini");
		giftCard.setGiftCardValue("10000");
		Mockito.when(restTemplate.getForObject(Mockito.isA(String.class), Mockito.isA(Class.class))).thenReturn(giftCard);
		GiftCard card = service.getUserGiftCard("dtp");
		assertNotNull(card);
		assertEquals("10000", card.getGiftCardValue());
	}

	@After
	public void tearDown() throws Exception {
		// Add additional tear down code here
	}

}