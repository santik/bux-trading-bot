package bux.trading.bot.service;

import bux.trading.bot.generated.BuyOrder;
import bux.trading.bot.generated.RestApiResponse;
import bux.trading.bot.product.Product;
import bux.trading.bot.provider.RestTemplateProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestServiceTest {

    private RestService restService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateProvider restTemplateProvider;

    @Before
    public void setUp() {
        when(restTemplateProvider.provide()).thenReturn(restTemplate);
        restService = new RestService(restTemplateProvider);
    }

    @Test
    public void buyProduct_withOrderId_shouldCallRestTemplate() {
        //arrange
        String productId = "productId";
        ArgumentCaptor<BuyOrder> argument = ArgumentCaptor.forClass(BuyOrder.class);
        ResponseEntity responseEntity = mock(ResponseEntity.class);
        when(restTemplate.postForEntity(any(String.class), any(BuyOrder.class), eq(RestApiResponse.class))).thenReturn(responseEntity);

        //act
        RestApiResponse restApiResponse = restService.buyProduct(productId);

        //assert
        verify(restTemplate).postForEntity(any(String.class), argument.capture(), eq(RestApiResponse.class));
        assertEquals(productId, argument.getValue().getProductId());
    }

    @Test
    public void sellProduct_withProduct_shouldCallRestTemplate() {
        //arrange
        Product product = mock(Product.class);
        RestApiResponse response = mock(RestApiResponse.class);
        ResponseEntity responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(response);
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.DELETE), eq(null), eq(RestApiResponse.class))).thenReturn(responseEntity);

        //act
        RestApiResponse actualResponse = restService.sellProduct(product);

        //assert
        assertEquals(response, actualResponse);
    }
}
