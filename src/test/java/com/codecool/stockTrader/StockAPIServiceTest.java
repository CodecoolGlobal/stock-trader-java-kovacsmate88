package com.codecool.stockTrader;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockAPIServiceTest {

    @Test
    void getPrice_existingSymbol_returnsPriceAsDouble() {

    }

    @Test
    void getPrice_nonExistingSymbol_throwsIllegalArgumentException() {

    }

    @Test
    void getPrice_serverDown_throwsIOException() throws IOException {
        RemoteURLReader reader  = mock(RemoteURLReader.class);
        StockAPIService service = new StockAPIService();
        service.setReader(reader);
        when(reader.readFromUrl("https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=abc")).thenThrow(IOException.class);
        assertThrows(IOException.class, () -> service.getPrice("abc"));
    }

    @Test
    void getPrice_malformedResponseFromAPI_throwsJSONException() throws IOException {
        RemoteURLReader reader = mock(RemoteURLReader.class);
        StockAPIService service = new StockAPIService();
        service.setReader(reader);
        when(reader.readFromUrl("https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=abc")).thenReturn("");
        assertThrows(JSONException.class, () -> service.getPrice("abc"));
    }

}