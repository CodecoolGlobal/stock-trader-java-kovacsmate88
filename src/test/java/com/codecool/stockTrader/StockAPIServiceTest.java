package com.codecool.stockTrader;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockAPIServiceTest {

    @Test
    void getPrice_existingSymbol_returnsPriceAsDouble() throws IOException {
        RemoteURLReader mockReader = mock(RemoteURLReader.class);
        StockAPIService stock = new StockAPIService(mockReader);
        double expectedPrice = 338.85;
        // mock the readFromUrl method to return a json object with a price of 338.85
        when(mockReader.readFromUrl("https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=AAPL"))
                .thenReturn("{\"price\":338.85}");
        double price = stock.getPrice("AAPL");
        assertEquals(expectedPrice, price, 0.001);
    }

    @Test
    void getPrice_nonExistingSymbol_throwsIllegalArgumentException() throws IOException {
        RemoteURLReader mockReader = mock(RemoteURLReader.class);
        StockAPIService stock = new StockAPIService(mockReader);
        doThrow(new IllegalArgumentException("Symbol does not exist!")).
                when(mockReader).readFromUrl("https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=abc");
        assertThrows(IllegalArgumentException.class, () -> stock.getPrice("abc"));
    }

    @Test
    void getPrice_serverDown_throwsIOException() throws IOException {
        RemoteURLReader mockReader = mock(RemoteURLReader.class);
        StockAPIService service = new StockAPIService(mockReader);
        when(mockReader.readFromUrl("https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=abc")).thenThrow(IOException.class);
        assertThrows(IOException.class, () -> service.getPrice("abc"));
    }

    @Test
    void getPrice_malformedResponseFromAPI_throwsJSONException() throws IOException {
        RemoteURLReader mockReader = mock(RemoteURLReader.class);
        StockAPIService service = new StockAPIService(mockReader);
        when(mockReader.readFromUrl("https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=abc")).thenThrow(JSONException.class);
        assertThrows(JSONException.class, () -> service.getPrice("abc"));
    }
}