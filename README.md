# Bux Trading Bot

Running

`java -jar target/bux-trading-bot.jar --productId=sb26496 --buyingPrice=123 --upperLimitPrice=124 --lowerLimitPrice=122`

Every argument is optional. If argument was not provided it will take value from `application.properties`

If prices don't not follow condition `lower limit sell price < buy price < upper limit sell price` exception will be thrown.

