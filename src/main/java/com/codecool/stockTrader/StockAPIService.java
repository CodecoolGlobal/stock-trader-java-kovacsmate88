package com.codecool.stockTrader;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Stock price service that gets prices from Yahoo.
 **/
public class StockAPIService {

	private static final String apiPath = "https://run.mocky.io/v3/9e14e086-84c2-4f98-9e36-54928830c980?stock=%s";
	private RemoteURLReader reader = new RemoteURLReader();
	
	/** Get stock price from iex and return as a double
     *  @param symbol Stock symbol, for example "apl"
     **/
	public double getPrice(String symbol) throws IOException {
        String url = String.format(apiPath, symbol);
        String result = RemoteURLReader.readFromUrl(url);
        JSONObject json = new JSONObject(result);
        String price = json.get("price").toString();
        return Double.parseDouble(price);
	}
	
	/** Buys a share of the given stock at the current price. Returns false if the purchase fails */
	public boolean buy(String symbol) {
		// Stub. No need to implement this.
		return true;
	}

	public void setReader(RemoteURLReader reader) {
		this.reader = reader;
	}
}
