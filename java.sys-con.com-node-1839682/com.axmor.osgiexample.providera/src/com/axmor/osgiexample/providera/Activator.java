package com.axmor.osgiexample.providera;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;



public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		PriceAService service = new PriceAServiceImpl();
		bundleContext.registerService(PriceAService.class.getName(), service,
				null);
		System.out.println("Stock price provider A is registered");
		Activator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stopping provider A");
		Activator.context = null;
	}
}