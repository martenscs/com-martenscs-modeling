/**
 * 
 */
package com.martenscs.feature.p2.advice;

import java.util.Properties;

/**
 * @author cmartens
 * 
 */
public class Advice {

	private static final String ADVICE_VERSION = "advice.version"; //$NON-NLS-1$
	public static final String ADVICE_FILE = "p2.inf"; //$NON-NLS-1$
	private static final String QUALIFIER_SUBSTITUTION = "$qualifier$"; //$NON-NLS-1$
	private static final String VERSION_SUBSTITUTION = "$version$"; //$NON-NLS-1$

	private static final String UPDATE_DESCRIPTION = "update.description"; //$NON-NLS-1$
	private static final String UPDATE_SEVERITY = "update.severity"; //$NON-NLS-1$
	private static final String UPDATE_RANGE = "update.range"; //$NON-NLS-1$
	private static final String UPDATE_ID = "update.id"; //$NON-NLS-1$
	private static final String CLASSIFIER = "classifier"; //$NON-NLS-1$
	private static final String TOUCHPOINT_VERSION = "touchpoint.version"; //$NON-NLS-1$
	private static final String TOUCHPOINT_ID = "touchpoint.id"; //$NON-NLS-1$
	private static final String COPYRIGHT_LOCATION = "copyright.location"; //$NON-NLS-1$
	private static final String COPYRIGHT = "copyright"; //$NON-NLS-1$
	private static final String ID = "id"; //$NON-NLS-1$
	private static final String SINGLETON = "singleton"; //$NON-NLS-1$
	private static final String IMPORT = "import"; //$NON-NLS-1$
	private static final String RANGE = "range"; //$NON-NLS-1$
	private static final String FILTER = "filter"; //$NON-NLS-1$
	private static final String MULTIPLE = "multiple"; //$NON-NLS-1$
	private static final String OPTIONAL = "optional"; //$NON-NLS-1$
	private static final String INSTALL = "install"; //$NON-NLS-1$
	private static final String UNINSTALL = "uninstall"; //$NON-NLS-1$
	private static final String CONFIGURE = "configure"; //$NON-NLS-1$
	private static final String UNCONFIGURE = "unconfigure"; //$NON-NLS-1$
	private static final String GREEDY = "greedy"; //$NON-NLS-1$
	private static final String VERSION = "version"; //$NON-NLS-1$
	private static final String NAMESPACE = "namespace"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String LOCATION = "location"; //$NON-NLS-1$
	private static final String VALUE = "value"; //$NON-NLS-1$

	private static final String UNITS_PREFIX = "units."; //$NON-NLS-1$
	private static final String INSTRUCTIONS_PREFIX = "instructions."; //$NON-NLS-1$
	private static final String REQUIRES_PREFIX = "requires."; //$NON-NLS-1$
	private static final String META_REQUIREMENTS_PREFIX = "metaRequirements."; //$NON-NLS-1$
	private static final String PROVIDES_PREFIX = "provides."; //$NON-NLS-1$
	private static final String PROPERTIES_PREFIX = "properties."; //$NON-NLS-1$
	private static final String LICENSES_PREFIX = "licenses."; //$NON-NLS-1$
	private static final String ARTIFACTS_PREFIX = "artifacts."; //$NON-NLS-1$
	private static final String HOST_REQUIREMENTS_PREFIX = "hostRequirements."; //$NON-NLS-1$
	private static final String UPDATE_DESCRIPTOR_PREFIX = "update."; //$NON-NLS-1$
	private static final String P2_NAMESPAC = "org.eclipse.equinox.p2.iu";//$NON-NLS-1$
	private static final String TOOLING = "tooling";//$NON-NLS-1$
	private static final String PROP_DELIM = ".";
	private static final String INSTUCT_INSTALL_BUNDLE = "installBundle(bundle:${artifact});";
	private static final String INSTUCT_UNINSTALL_BUNDLE = "uninstallBundle(bundle:${artifact});";
	private static final String INSTUCT_CONFIGURE_START = "setStartLevel(startLevel:";
	private static final String INSTUCT_CONFIGURE_END = ");markStarted(started:true)";
	private static final String INSTUCT_UNCONFIGURE = "setStartLevel(startLevel:-1);markStarted(started:false);";

	private int unit;

	private String bundleId;

	private String startLevel;

	public Advice() {

	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(String startLevel) {
		this.startLevel = startLevel;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public Properties getAdvice(Properties props) {

		if (props == null)
			props = new Properties();
		props = this.addRequires(props);

		return props;

	}

	public Properties addRequires(Properties props) {
		props.put(REQUIRES_PREFIX + unit + PROP_DELIM + NAMESPACE, P2_NAMESPAC);
		props.put(REQUIRES_PREFIX + unit + PROP_DELIM + NAME, TOOLING
				+ PROP_DELIM + bundleId);
		props.put(REQUIRES_PREFIX + unit + PROP_DELIM + GREEDY, "true");

		return props;
	}

	public Properties addUnits(Properties props) {

		// units.0.id=configure.org.knowhowlab.osgi.shell.equinox
		props.put(UNITS_PREFIX + unit + PROP_DELIM + ID, TOOLING + PROP_DELIM
				+ bundleId);

		// units.0.version=1.0.1
		props.put(UNITS_PREFIX + unit + PROP_DELIM + VERSION,
				VERSION_SUBSTITUTION);
		// units.0.provides.1.namespace=org.eclipse.equinox.p2.iu
		props.put(UNITS_PREFIX + unit + PROP_DELIM + PROVIDES_PREFIX + "1"
				+ PROP_DELIM + NAMESPACE, P2_NAMESPAC);
		// units.0.provides.1.name=configure.org.knowhowlab.osgi.shell.equinox
		props.put(UNITS_PREFIX + unit + PROP_DELIM + PROVIDES_PREFIX + "1"
				+ PROP_DELIM + NAME, TOOLING + PROP_DELIM + bundleId);
		// units.0.provides.1.version=1.0.1
		props.put(UNITS_PREFIX + unit + PROP_DELIM + PROVIDES_PREFIX + "1"
				+ PROP_DELIM + VERSION, VERSION_SUBSTITUTION);
		// units.0.touchpoint.id=org.eclipse.equinox.p2.native
		// units.0.touchpoint.version=1.0.0


		// TODO:Import Example
		// props.put(UNITS_PREFIX + unit + PROP_DELIM + INSTRUCTIONS_PREFIX
		// + INSTALL + "." + IMPORT,
		// "org.eclipse.equinox.p2.touchpoint.eclipse");
		// units.0.instructions.install=org.eclipse.equinox.p2.touchpoint.eclipse.installBundle(bundle:${artifact});
		props.put(UNITS_PREFIX + unit + PROP_DELIM + INSTRUCTIONS_PREFIX
				+ INSTALL, INSTUCT_INSTALL_BUNDLE);

		// units.0.instructions.uninstall=org.eclipse.equinox.p2.touchpoint.eclipse.uninstallBundle(bundle:${artifact});
		props.put(UNITS_PREFIX + unit + PROP_DELIM + INSTRUCTIONS_PREFIX
				+ UNINSTALL, INSTUCT_UNINSTALL_BUNDLE);
		// units.0.instructions.unconfigure=setStartLevel(startLevel:-1);markStarted(started:false);

		props.put(UNITS_PREFIX + unit + PROP_DELIM + INSTRUCTIONS_PREFIX
				+ UNCONFIGURE, INSTUCT_UNCONFIGURE);
		// units.0.instructions.configure=setStartLevel(startLevel:2);markStarted(started:true);
		props.put(UNITS_PREFIX + unit + PROP_DELIM + INSTRUCTIONS_PREFIX
				+ CONFIGURE, INSTUCT_CONFIGURE_START + startLevel
				+ INSTUCT_CONFIGURE_END);
		// units.0.hostRequirements.1.namespace=osgi.bundle
		props.put(UNITS_PREFIX + unit + PROP_DELIM + HOST_REQUIREMENTS_PREFIX
				+ "1" + PROP_DELIM + NAMESPACE, "osgi.bundle");

		// units.0.hostRequirements.1.name=org.knowhowlab.osgi.shell.equinox
		props.put(UNITS_PREFIX + unit + PROP_DELIM + HOST_REQUIREMENTS_PREFIX
				+ "1" + PROP_DELIM + NAME, bundleId);

		// units.0.hostRequirements.1.greedy=false
		props.put(UNITS_PREFIX + unit + PROP_DELIM + HOST_REQUIREMENTS_PREFIX
				+ "1" + PROP_DELIM + GREEDY, "false");

		// units.0.hostRequirements.2.namespace=org.eclipse.equinox.p2.eclipse.type
		props.put(UNITS_PREFIX + unit + PROP_DELIM + HOST_REQUIREMENTS_PREFIX
				+ "2" + PROP_DELIM + NAMESPACE,
				"org.eclipse.equinox.p2.eclipse.type");

		// units.0.hostRequirements.2.name=bundle
		props.put(UNITS_PREFIX + unit + PROP_DELIM + HOST_REQUIREMENTS_PREFIX
				+ "2" + PROP_DELIM + NAME, "bundle");

		// units.0.hostRequirements.2.range=[1.0.0,2.0.0)
		props.put(UNITS_PREFIX + unit + PROP_DELIM + HOST_REQUIREMENTS_PREFIX
				+ "2" + PROP_DELIM + RANGE, "[1.0.0,2.0.0)");
		// units.0.hostRequirements.2.greedy=false
		props.put(UNITS_PREFIX + unit + PROP_DELIM + HOST_REQUIREMENTS_PREFIX
				+ "2" + PROP_DELIM + GREEDY, "false");
		// units.0.requires.1.namespace=osgi.bundle
		props.put(UNITS_PREFIX + unit + PROP_DELIM + REQUIRES_PREFIX + "1"
				+ PROP_DELIM + NAMESPACE, "osgi.bundle");
		// units.0.requires.1.name=org.knowhowlab.osgi.shell.equinox
		props.put(UNITS_PREFIX + unit + PROP_DELIM + REQUIRES_PREFIX + "1"
				+ PROP_DELIM + NAME, bundleId);
		// units.0.requires.1.greedy=false
		props.put(UNITS_PREFIX + unit + PROP_DELIM + REQUIRES_PREFIX + "1"
				+ PROP_DELIM + GREEDY, "false");

		return props;

	}
}
