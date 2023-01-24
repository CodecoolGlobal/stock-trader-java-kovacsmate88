package com.codecool.stockTrader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import static org.mockito.Mockito.*;


class TraderTest {
    @Test
    void buy_bidLowerThanPrice_returnFalse() throws IOException {
        RemoteURLReader mockReader = mock(RemoteURLReader.class);
        StockAPIService stockService = new StockAPIService(mockReader);
        Trader trader = new Trader();
        trader.setStockService(stockService);
        double lowerBid = 23;
        // mock the readFromUrl method to return a json object with a price of 338.85
        when(mockReader.readFromUrl("https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=AAPL"))
                .thenReturn("{\"price\":338.85}");
        boolean bidLowerThanPrice = trader.buy("AAPL", lowerBid);
        assertFalse(bidLowerThanPrice);
    }

    @Test
    void buy_bidHigherThanPrice_returnTrue() throws IOException {
        RemoteURLReader mockReader = mock(RemoteURLReader.class);
        StockAPIService stockService = new StockAPIService(mockReader);
        Trader trader = new Trader();
        trader.setStockService(stockService);
        double higherBid = 400;
        when(mockReader.readFromUrl("https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=AAPL"))
                .thenReturn("{\"price\":338.85}");
        boolean bidHigherThanPrice = trader.buy("AAPL", higherBid);
        assertTrue(bidHigherThanPrice);
    }

    @Test
    void buy_nonExistingSymbol_throwsIllegalArgumentException() throws IOException {
        RemoteURLReader mockReader = mock(RemoteURLReader.class);
        StockAPIService stockService = new StockAPIService(mockReader);
        Trader trader = new Trader();
        trader.setStockService(stockService);
        double bid = 34;
        when(mockReader.readFromUrl("https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=abc"))
                .thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> trader.buy("abc", bid));
    }
}