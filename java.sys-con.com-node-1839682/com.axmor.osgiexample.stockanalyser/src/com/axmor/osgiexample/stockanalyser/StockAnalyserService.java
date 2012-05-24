package com.axmor.osgiexample.stockanalyser;

import com.axmor.osgiexample.providera.PriceAService;
import com.axmor.osgiexample.providerb.PriceBService;

public interface StockAnalyserService {

	void setServiceA(PriceAService serviceA);

	void setServiceB(PriceBService serviceB);

	public int getMaxPrice();

	public int getMinPrice();

	public double getAverageValue();

}
