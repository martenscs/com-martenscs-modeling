package com.axmor.osgiexample.providerb;



public class PriceBServiceImpl implements  PriceBService {

	private static final int REFRESH_RATE = 3 * 1000;
	private int price;
	private long time = 0;

	public int getPrice() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - time > REFRESH_RATE) {
			price = (int) (Math.random() * 100);
			time = currentTime;
		}
		return price;
	}
}