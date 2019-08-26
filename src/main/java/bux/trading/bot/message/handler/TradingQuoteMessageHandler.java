package bux.trading.bot.message.handler;

import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.generated.Body;
import bux.trading.bot.generated.WebsocketResponseMessage;
import bux.trading.bot.condition.BuyCondition;
import bux.trading.bot.condition.LowerLimitSellCondition;
import bux.trading.bot.condition.UpperLimitSellCondition;
import bux.trading.bot.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TradingQuoteMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradingQuoteMessageHandler.class);

    private BotConfiguration botConfiguration;
    private final BuyCondition buyCondition;
    private final LowerLimitSellCondition lowerLimitSellCondition;
    private final UpperLimitSellCondition upperLimitSellCondition;
    private final PositionService positionService;

    public TradingQuoteMessageHandler(
            BotConfiguration botConfiguration,
            BuyCondition buyCondition,
            LowerLimitSellCondition lowerLimitSellCondition,
            UpperLimitSellCondition upperLimitSellCondition,
            PositionService positionService
    ) {
        this.botConfiguration = botConfiguration;
        this.buyCondition = buyCondition;
        this.lowerLimitSellCondition = lowerLimitSellCondition;
        this.upperLimitSellCondition = upperLimitSellCondition;
        this.positionService = positionService;
    }

    public void handle(WebsocketResponseMessage message) {

        Body tradingQuote = message.getBody();
        if (!tradingQuote.getSecurityId().equals(botConfiguration.getProductId())) {
            LOGGER.warn("Quote is not for the target product {}", message);
            return;
        }
        LOGGER.info("Price for product {} is {}", tradingQuote.getSecurityId(), tradingQuote.getCurrentPrice());

        if (buyCondition.isMet(tradingQuote, botConfiguration)) {
            LOGGER.info("Opening position");
            positionService.openPosition(tradingQuote);
        } else if (lowerLimitSellCondition.isMet(tradingQuote, botConfiguration)) {
            LOGGER.info("Closing position by lower limit condition");
            positionService.closePosition(tradingQuote);
        } else if (upperLimitSellCondition.isMet(tradingQuote, botConfiguration)) {
            LOGGER.info("Closing position by upper limit condition");
            positionService.closePosition(tradingQuote);
        }
    }
}
