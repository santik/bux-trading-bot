package bux.trading.bot.service;

import bux.trading.bot.config.BotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

@Service
public class BotConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotConfigurator.class);

    private final BotConfiguration botConfiguration;

    public BotConfigurator(BotConfiguration botConfiguration) {
        this.botConfiguration = botConfiguration;
    }

    public void configure(ApplicationArguments args) {
        for (String name : args.getOptionNames()) {
            if (name.equals("productId")) {
                botConfiguration.setProductId(args.getOptionValues(name).get(0));
            }
            if (name.equals("buyingPrice")) {
                botConfiguration.setBuyingPrice(Double.parseDouble(args.getOptionValues(name).get(0)));
            }
            if (name.equals("upperLimitPrice")) {
                botConfiguration.setUpperLimitPrice(Double.parseDouble(args.getOptionValues(name).get(0)));
            }
            if (name.equals("lowerLimitPrice")) {
                botConfiguration.setLowerLimitPrice(Double.parseDouble(args.getOptionValues(name).get(0)));
            }
        }
        botConfiguration.init();
        LOGGER.info("Bot configured with parameters {}", botConfiguration);
    }
}
