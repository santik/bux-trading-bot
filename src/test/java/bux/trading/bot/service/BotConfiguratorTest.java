package bux.trading.bot.service;

import bux.trading.bot.config.BotConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.ApplicationArguments;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BotConfiguratorTest {

    @Test
    public void configure_withArguments_shouldReturnCorrectConfiguration() {
        //arrange
        BotConfiguration configuration = new BotConfiguration();
        BotConfigurator botConfigurator = new BotConfigurator(configuration);
        ApplicationArguments args = mock(ApplicationArguments.class);
        Set<String> names = new HashSet<>();
        String productIdName = "productId";
        String buyingPriceName = "buyingPrice";
        String upperLimitPriceName = "upperLimitPrice";
        String lowerLimitPriceName = "lowerLimitPrice";
        names.add(productIdName);
        names.add(buyingPriceName);
        names.add(upperLimitPriceName);
        names.add(lowerLimitPriceName);
        when(args.getOptionNames()).thenReturn(names);
        String productId = "productId";
        when(args.getOptionValues(productIdName)).thenReturn(Collections.singletonList(productId));
        String buyingPrice = "123";
        when(args.getOptionValues(buyingPriceName)).thenReturn(Collections.singletonList(buyingPrice));
        String upperLimitPrice = "125";
        when(args.getOptionValues(upperLimitPriceName)).thenReturn(Collections.singletonList(upperLimitPrice));
        String lowerLimitPrice = "122";
        when(args.getOptionValues(lowerLimitPriceName)).thenReturn(Collections.singletonList(lowerLimitPrice));

        //act
        botConfigurator.configure(args);

        //assert
        assertEquals(productId, configuration.getProductId());
        assertEquals(Double.parseDouble(buyingPrice), configuration.getBuyingPrice(), 0);
        assertEquals(Double.parseDouble(upperLimitPrice), configuration.getUpperLimitPrice(), 0);
        assertEquals(Double.parseDouble(lowerLimitPrice), configuration.getLowerLimitPrice(), 0);
    }
}
