package com.axmor.osgiexample.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

import org.junit.Test;

import org.osgi.framework.BundleContext;

import org.osgi.framework.FrameworkUtil;

import org.osgi.framework.ServiceReference;

import com.axmor.osgiexample.providera.PriceAService;

import com.axmor.osgiexample.providerb.PriceBService;

import com.axmor.osgiexample.stockanalyser.StockAnalyserService;

public class TestAnalyser {

	private BundleContext bundleContext = null;

	private StockAnalyserService analyzerService = null;

	private PriceAService serviceA = null;

	private PriceBService serviceB = null;

	@Before
	public void init() {

		bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();

		ServiceReference<?> ref = bundleContext
				.getServiceReference(StockAnalyserService.class

				.getName());

		analyzerService = (StockAnalyserService) bundleContext.getService(ref);

		ref = bundleContext.getServiceReference(PriceAService.class.getName());

		serviceA = (PriceAService) bundleContext.getService(ref);

		ref = bundleContext.getServiceReference(PriceBService.class.getName());

		serviceB = (PriceBService) bundleContext.getService(ref);

	}

	@Test
	public void testCheckAverageValue() throws Exception {

		System.out.println("***************");

		for (int i = 0; i < 3; i++) {

			Thread.sleep(3500);

			int priceA = serviceA.getPrice();

			int priceB = serviceB.getPrice();

			double avg = analyzerService.getAverageValue();

			double expectedAvgVal = (double) (priceA + priceB) / 2;

			assertEquals(expectedAvgVal, avg, 0);

			System.out.println("Price A = " + priceA + " Price B = " + priceB);

			System.out.println("Stock average value: " + avg + " OK");

		}

	}

	@Test
	public void testCheckMinPrice() throws Exception {

		System.out.println("***************");

		for (int i = 0; i < 3; i++) {

			Thread.sleep(3500);

			int priceA = serviceA.getPrice();

			int priceB = serviceB.getPrice();

			int min = analyzerService.getMinPrice();

			int expectedMin = Math.min(priceA, priceB);

			assertEquals(expectedMin, min);

			System.out.println("Price A = " + priceA + " Price B = " + priceB);

			System.out.println("Stock min price: " + min + " OK");

		}

	}

	@Test
	public void testCheckMaxPrice() throws Exception {

		System.out.println("***************");

		for (int i = 0; i < 3; i++) {

			Thread.sleep(3500);

			int priceA = serviceA.getPrice();

			int priceB = serviceB.getPrice();

			int max = analyzerService.getMaxPrice();

			int expectedMax = Math.max(priceA, priceB);

			assertEquals(expectedMax, max);

			System.out.println("Price A = " + priceA + " Price B = " + priceB);

			System.out.println("Stock max price: " + max + " OK");

		}

	}

}