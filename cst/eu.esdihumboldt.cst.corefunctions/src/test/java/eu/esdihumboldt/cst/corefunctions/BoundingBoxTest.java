/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                  01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2010.
 */

package eu.esdihumboldt.cst.corefunctions;

import static org.junit.Assert.assertTrue;

import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.junit.Test;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

import eu.esdihumboldt.commons.goml.align.Cell;
import eu.esdihumboldt.commons.goml.oml.ext.Transformation;
import eu.esdihumboldt.commons.goml.omwg.Property;
import eu.esdihumboldt.commons.goml.rdf.About;
import eu.esdihumboldt.commons.goml.rdf.Resource;


public class BoundingBoxTest {
	private final String sourceLocalname = "FT1"; //$NON-NLS-1$
	private final String sourceLocalnamePropertyAGeom = "PropertyAGeom"; //$NON-NLS-1$
	private final String sourceNamespace = "http://esdi-humboldt.eu"; //$NON-NLS-1$
	
	private final String targetLocalname = "FT2"; //$NON-NLS-1$
	private final String targetLocalnamePropertyBGeom = "PropertyBGeom"; //$NON-NLS-1$
	private final String targetNamespace = "http://xsdi.org"; //$NON-NLS-1$
	
	@Test
	public void testTransformFeatureFeature() {
	
		// set up cell to use for testing
		Cell cell = new Cell();
//		ComposedProperty cp = new ComposedProperty(
//				new About(this.sourceNamespace, this.sourceLocalname));
//		cp.getCollection().add(new Property(
//				new About(this.sourceNamespace, this.sourceLocalname, 
//						this.sourceLocalnamePropertyAGeom)));
		
		Transformation t = new Transformation();
		t.setService(new Resource(BoundingBoxFunction.class.toString()));
		Property p1 = new Property(new About(this.sourceNamespace, this.sourceLocalname, this.sourceLocalnamePropertyAGeom));
		p1.setTransformation(t);
		cell.setEntity1(p1);
//		cp.setTransformation(t);
//		cell.setEntity1(cp);
		cell.setEntity2(new Property(new About(this.targetNamespace, this.targetLocalname, this.targetLocalnamePropertyBGeom)));

		// build source and target Features
		SimpleFeatureType sourcetype = this.getFeatureType(
				this.sourceNamespace, 
				this.sourceLocalname, 
				Polygon.class);
		SimpleFeatureType targettype = this.getFeatureType(this.targetNamespace, this.targetLocalname, Polygon.class);
		GeometryFactory fac = new GeometryFactory();
				
		
		Feature source = SimpleFeatureBuilder.build(sourcetype, new Object[] {fac.createPolygon(fac.createLinearRing(new Coordinate[] {new Coordinate(0,2), new Coordinate (2,0), new Coordinate (8,6), new Coordinate(0,2)} ),null) }, "1"); //$NON-NLS-1$
		BoundingBoxFunction bBox = new BoundingBoxFunction();
		bBox.configure(cell);
		
		//perform test for Polygon
		Feature target = SimpleFeatureBuilder.build(targettype, new Object[]{}, "2"); //$NON-NLS-1$
		Feature newFeature = bBox.transform(source, target);
		
		Geometry geom = (Geometry) newFeature.getDefaultGeometryProperty().getValue();
		Coordinate[] coords = geom.getCoordinates();
		System.out.println("Bounding Box for Polygon"); //$NON-NLS-1$
		for (int i=0; i<coords.length; i++)
			System.out.println(coords[i].x+" "+coords[i].y+" "+coords[i].z); //$NON-NLS-1$ //$NON-NLS-2$
	
		assertTrue(newFeature.getDefaultGeometryProperty().getValue().getClass().equals(Polygon.class));

	}

	private SimpleFeatureType getFeatureType(String featureTypeNamespace, String featureTypeName,  Class <? extends Geometry> geom) {
		
		SimpleFeatureType ft = null;
		try {
			SimpleFeatureTypeBuilder ftbuilder = new SimpleFeatureTypeBuilder();
			ftbuilder.setName(featureTypeName);
			ftbuilder.setNamespaceURI(featureTypeNamespace);
			ftbuilder.add("geom", geom); //$NON-NLS-1$
			ft = ftbuilder.buildFeatureType();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return ft;
	}
}