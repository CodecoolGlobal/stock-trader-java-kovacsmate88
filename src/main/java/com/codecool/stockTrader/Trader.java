package com.codecool.stockTrader;


import java.io.IOException;

/**
 * Business logic for stock trading
 **/
public class Trader {

    private static Trader instance;

    public static Trader getInstance() {
        if (instance == null) {
            instance = new Trader();
        }
        return instance;
    }

    private StockAPIService stockService;
    private RemoteURLReader reader = new RemoteURLReader();

    public Trader() {
        this.stockService = new StockAPIService(reader);
    }

    public void setStockService(StockAPIService stockService) {
        this.stockService = stockService;
    }

    /**
     * Checks the price of a stock, and buys it if the price is not greater than the bid amount.
     *
     * @return whether any stock was bought
     */
    public boolean buy(String symbol, double bid) throws IOException {
        double price = stockService.getPrice(symbol);

        boolean result;
        if (price <= bid) {
            result = true;
            stockService.buy(symbol);
            FileLogger.getInstance().log("Purchased " + symbol + " stock at $" + bid + ", since its higher that the current price ($" + price + ")");
        } else {
            FileLogger.getInstance().log("Bid for " + symbol + " was $" + bid + " but the stock price is $" + price + ", no purchase was made.");
            result = false;
        }
        return result;
    }

}