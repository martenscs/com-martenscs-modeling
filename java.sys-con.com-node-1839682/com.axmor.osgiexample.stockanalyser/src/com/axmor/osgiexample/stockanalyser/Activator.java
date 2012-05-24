package com.axmor.osgiexample.stockanalyser;

import org.osgi.framework.BundleActivator;

import org.osgi.framework.BundleContext;

import org.osgi.framework.ServiceReference;

import com.axmor.osgiexample.providera.PriceAService;

import com.axmor.osgiexample.providerb.PriceBService;

public class Activator implements BundleActivator {

	private BundleContext context;

	public void start(BundleContext bundleContext) throws Exception {

		this.context = bundleContext;

		ServiceReference<?> reference1 = context
				.getServiceReference(PriceAService.class.getName());

		PriceAService serviceA = (PriceAService) context.getService(reference1);

		ServiceReference<?> reference2 = context
				.getServiceReference(PriceBService.class.getName());

		PriceBService serviceB = (PriceBService) context.getService(reference2);

		StockAnalyserService analyserService = new StockAnalyserServiceImpl();

		analyserService.setServiceA(serviceA);

		analyserService.setServiceB(serviceB);

		context.registerService(StockAnalyserService.class.getName(),
				analyserService, null);

		System.out.println("Stock Analyser service is registered");

	}

	public void stop(BundleContext bundleContext) throws Exception {

		System.out.println("Stoping Stock Analyser");

		this.context = null;

	}

}
