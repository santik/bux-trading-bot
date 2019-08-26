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
            String value = args.getOptionValues(name).get(0);
            switch (name) {
                case "productId":
                    botConfiguration.setProductId(value);
                    break;
                case "buyingPrice":
                    botConfiguration.setBuyingPrice(Double.parseDouble(value));
                    break;
                case "upperLimitPrice":
                    botConfiguration.setUpperLimitPrice(Double.parseDouble(args.getOptionValues(name).get(0)));
                    break;
                case "lowerLimitPrice":
                    botConfiguration.setLowerLimitPrice(Double.parseDouble(args.getOptionValues(name).get(0)));
                    break;
            }
        }
        botConfiguration.init();
        LOGGER.info("Bot configured with parameters {}", botConfiguration);
    }
}
