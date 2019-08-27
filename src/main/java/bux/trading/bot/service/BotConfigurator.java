package bux.trading.bot.service;

import bux.trading.bot.config.BotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            try {
                Method method = BotConfiguration.class.getMethod(getSetterName(name), String.class);
                method.invoke(botConfiguration, value);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.error(e.getMessage());
            }
        }
        botConfiguration.init();
        LOGGER.info("Bot configured with parameters {}", botConfiguration);
    }

    private String getSetterName(String parameterName) {
        return "set" + parameterName.substring(0, 1).toUpperCase() + parameterName.substring(1);
    }
}
