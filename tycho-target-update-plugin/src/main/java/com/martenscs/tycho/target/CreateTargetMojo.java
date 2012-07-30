package com.martenscs.tycho.target;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.tycho.p2.target.facade.TargetDefinition;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.martenscs.tycho.target.TargetDefinitionFile.IULocation;
import com.martenscs.tycho.target.TargetDefinitionFile.Repository;

/**
 * @goal create-target
 */
public class CreateTargetMojo extends AbstractUpdateMojo {

    /**
     * @parameter expression="${target}"
     */
    private File targetFile;

    /**
     * @parameter expression="${feature}"
     */
    private File featureFile;

    protected void doUpdate() throws IOException, URISyntaxException {
        Feature in = Feature.read(featureFile);
        featureFile.delete();
        featureFile.createNewFile();

        Feature out = Feature.read(featureFile);

        Feature.write(out, featureFile);
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
                    }
                }
            }
        }

        TargetDefinitionFile.write(target, targetFile);
    }

    private void updateFeature(Feature in, Feature out) {

        out.setId(in.getId());
        out.setVersion(in.getVersion());

        List<PluginRef> plugins = in.getPlugins();
        for (PluginRef pluginRef : plugins) {

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
