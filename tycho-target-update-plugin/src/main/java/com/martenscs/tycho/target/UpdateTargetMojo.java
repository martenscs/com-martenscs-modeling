package com.martenscs.tycho.target;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.maven.project.MavenProject;
import org.eclipse.tycho.p2.target.facade.TargetDefinition;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.martenscs.tycho.target.TargetDefinitionFile.IULocation;
import com.martenscs.tycho.target.TargetDefinitionFile.Repository;

/**
 * @goal update-target
 */
public class UpdateTargetMojo extends AbstractUpdateMojo {

    /**
     * @parameter expression="${target}"
     */
    private File targetFile;
    /**
     * @parameter expression="${feature}"
     */
    private File featureFile;
    /**
     * @parameter expression="${featureVersion}"
     */
    private String featureVersion;
    /**
     * @parameter expression="${featureLabel}"
     */
    private String featureLabel;

    /**
     * @parameter
     */
    private List<Feature> features;

    /**
     * @parameter expression="${featureId}"
     */
    private String featureId;

    /**
     * @parameter expression="${featureProvider}"
     */
    private String featureProvider;
    /**
     * The maven project.
     * 
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    protected void doUpdate() throws IOException, URISyntaxException {
        URL file = UpdateTargetMojo.class.getResource("/" + Feature.FEATURE_XML);
        InputStream fileName = UpdateTargetMojo.class.getResourceAsStream("/" + Feature.FEATURE_XML);

        Feature in = Feature.read(fileName);
        in.setId(featureId);
        in.setVersion(featureVersion);
        in.setProvider(featureProvider);
        in.setLabel(featureLabel);
        TargetDefinitionFile target = TargetDefinitionFile.read(targetFile);
        for (TargetDefinition.Location location : target.getLocations()) {
            if (location instanceof IULocation) {
                IULocation locationImpl = (IULocation) location;
                locationImpl.getRepositoryImpls();
                for (Repository iterable_element : locationImpl.getRepositoryImpls()) {
                    iterable_element.getLocation();
                    URI uri = iterable_element.getLocation();
                    if (uri.toString().startsWith("file")) {
                        File folder = new File(uri);
                        String conPath = folder.getCanonicalPath();
                        conPath = folder.getAbsolutePath();

                        File f = new File(conPath + "/content.xml");
                        getNewInstalledUnits(locationImpl, f);
                        addPluginsToFeature(in, f);
                    }
                }
            }
        }
        Feature.write(in, featureFile);
        TargetDefinitionFile.write(target, targetFile);
    }

    /**
     * @param location
     * @param file
     */
    private void addPluginsToFeature(final Feature feature, File file) {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                        throws SAXException {
                    if (qName.equalsIgnoreCase(TargetDefinitionFile.UNIT)) {
                        if (attributes != null) {
                            for (int i = 0; i < attributes.getLength(); i++) {
                                String aName = attributes.getLocalName(i);
                                if (aName.equals(TargetDefinitionFile.ID)) {
                                    PluginRef plugin = new PluginRef("plugin");
                                    plugin.setId(attributes.getValue(i));
                                    // download-size="0"
                                    // install-size="0"
                                    // version="0.0.0"
                                    // unpack="false"
                                    plugin.setVersion("0.0.0");
                                    plugin.setDownloadSide(0);
                                    plugin.setInstallSize(0);
                                    plugin.setUnpack(false);
                                    feature.addPlugin(plugin);

                                }
                                if ("".equals(aName))
                                    aName = attributes.getQName(i);
                            }
                        }
                    }
                }
            };
            saxParser.parse(file, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param location
     * @param file
     */
    private void getNewInstalledUnits(final IULocation location, File file) {
        location.removeUnits();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                public void startElement(String uri, String localName, String qName, Attributes attributes)
                        throws SAXException {
                    if (qName.equalsIgnoreCase(TargetDefinitionFile.UNIT)) {
                        if (attributes != null) {
                            for (int i = 0; i < attributes.getLength(); i++) {
                                String aName = attributes.getLocalName(i);
                                if (aName.equals(TargetDefinitionFile.ID))
                                    location.addUnit(attributes.getValue(i));
                                if ("".equals(aName))
                                    aName = attributes.getQName(i);
                            }
                        }
                    }
                }
            };
            saxParser.parse(file, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected File getTargetFile() {
        return targetFile;
    }

}
