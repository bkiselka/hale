
package eu.esdihumboldt.cst.transformer.service.rename;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.FeatureType;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.expression.Function;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import eu.esdihumboldt.cst.transformer.Messages;





public class FeatureAggregator {
	
	//Aggregation Functions from Geotools
	
	public static final String SUM ="Collection_Sum"; //$NON-NLS-1$
	public static final String AVERAGE ="Collection_Average"; //$NON-NLS-1$
	public static final String BOUNDS = "Collection_Bounds"; //$NON-NLS-1$
	public static final String COUNT = "Collection_Count"; //$NON-NLS-1$
	public static final String MAX = "Collection_Max";	 //$NON-NLS-1$
	public static final String MEDIAN = "Collection_Median";	 //$NON-NLS-1$
	public static final String MIN = "Collection_Min"; //$NON-NLS-1$
	public static final String UNIQUE = "Collection_Unique"; //$NON-NLS-1$
	public static final String MULTI = "Multi"; //$NON-NLS-1$


	private String onAttributeName = null;
	private String targetAttribute = null;
	private String aggregationRule = null;
	//private String groupingAttribute = null;
/**
 * 
 * @param onAttributeName
 * @param aggregationRule
 */
	public FeatureAggregator(String onAttributeName, String aggregationRule, String targetAttribute) {
		this.onAttributeName = onAttributeName;
		this.targetAttribute = targetAttribute;

		String[] aggrule = aggregationRule.split(":"); //$NON-NLS-1$
		if (aggrule[0].equals("aggregate")) { //$NON-NLS-1$
			if (aggrule[1].startsWith("Collection_Sum")) { //$NON-NLS-1$
				this.aggregationRule = FeatureAggregator.SUM;
			}
			else if (aggrule[1].startsWith("Collection_Average")) { //$NON-NLS-1$
				this.aggregationRule = FeatureAggregator.AVERAGE;
			}
			else if (aggrule[1].startsWith("Collection_Bounds")) { //$NON-NLS-1$
				this.aggregationRule = FeatureAggregator.BOUNDS;
			}
			else if (aggrule[1].startsWith("Collection_Count")) { //$NON-NLS-1$
				this.aggregationRule = FeatureAggregator.COUNT;
			}
			else if (aggrule[1].startsWith("Collection_Max")) { //$NON-NLS-1$
				this.aggregationRule = FeatureAggregator.MAX;
			}
			else if (aggrule[1].startsWith("Collection_Median")) { //$NON-NLS-1$
				this.aggregationRule = FeatureAggregator.MEDIAN;
			}
			else if (aggrule[1].startsWith("Collection_Min")) { //$NON-NLS-1$
				this.aggregationRule = FeatureAggregator.MIN;
			}
			else if (aggrule[1].startsWith("Collection_Unique")) { //$NON-NLS-1$
				this.aggregationRule = FeatureAggregator.UNIQUE;
			}
			else if (aggrule[1].startsWith("Multi")) { //$NON-NLS-1$
				this.aggregationRule = FeatureAggregator.MULTI;
			}
			else{
				throw new RuntimeException (aggrule[1] + " is not a valid aggregation rule."); //$NON-NLS-1$
			}

		}
		else {
			throw new RuntimeException("You can only create a " + //$NON-NLS-1$
				"FeatureAggregator from a aggregate rule."); //$NON-NLS-1$
		}
	}
	
	
	
	public List<Feature> aggregate(Collection<? extends Feature> source, FeatureType targetType) {
		
		List<Feature> result = new ArrayList<Feature>();
		SimpleFeature target = null;
		if (this.aggregationRule.equals(FeatureAggregator.AVERAGE)||
				this.aggregationRule.equals(FeatureAggregator.BOUNDS)||
				this.aggregationRule.equals(FeatureAggregator.COUNT)||
				this.aggregationRule.equals(FeatureAggregator.MAX)||
				this.aggregationRule.equals(FeatureAggregator.MEDIAN)||
				this.aggregationRule.equals(FeatureAggregator.MIN)||
				this.aggregationRule.equals(FeatureAggregator.SUM)||
				this.aggregationRule.equals(FeatureAggregator.UNIQUE)){
			
			FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2(null);
			Function sum = ff.function(this.aggregationRule, ff.property(this.onAttributeName));
			FeatureCollection sourceFeatures = FeatureCollections.newCollection(); 
			for (Feature f : source){
				sourceFeatures.add(f);
			}
			Object value = sum.evaluate(sourceFeatures);
			target = SimpleFeatureBuilder.build(
						(SimpleFeatureType) targetType, new Object[]{}, 
						UUID.randomUUID().toString());
				
			target.setAttribute(this.targetAttribute,value);
			result.add(target);
		}
		
		else if (this.aggregationRule.equals(FeatureAggregator.MULTI)){
			GeometryFactory geomFactory = new GeometryFactory();
			Object geom = ((SimpleFeature)source.iterator().next()).getAttribute(this.onAttributeName);;
			Geometry newGeometry = null;
			System.out.println("targetGEOM " + targetType.getGeometryDescriptor().getType().getBinding()); //$NON-NLS-1$
			if (geom.getClass().equals(Point.class) && targetType.getGeometryDescriptor().getType().getBinding().equals(MultiPoint.class)) {
				//Aggregation from multiple point Features to a MultiPoint
				List<Point> points = new ArrayList<Point>();
				for (Feature f : source){
					Point p = (Point)((SimpleFeature)f).getAttribute(this.onAttributeName);
					points.add(p);
				}
				Point[] pointsArray = new Point[points.size()];
				System.arraycopy(points.toArray(), 0, pointsArray, 0, points.size());
				newGeometry= geomFactory.createMultiPoint(pointsArray);
				target = SimpleFeatureBuilder.build(
						(SimpleFeatureType) targetType, new Object[]{}, 
						UUID.randomUUID().toString());
				
//				target.setDefaultGeometry(newGeometry);
				target.setAttribute(this.targetAttribute,newGeometry);
				result.add(target);			
				
			}
			else if (geom.getClass().equals(LineString.class) && targetType.getGeometryDescriptor().getType().getBinding().equals(MultiLineString.class)) {
				//Aggregation from multiple LineString Features to a MultiLineString
				List<LineString> lines = new ArrayList<LineString>();
				for (Feature f : source){
					LineString l = (LineString)((SimpleFeature)f).getAttribute(this.onAttributeName);
					lines.add(l);
				}
				LineString[] linesArray = new LineString[lines.size()];
				System.arraycopy(lines.toArray(), 0, linesArray, 0, lines.size());
				newGeometry= geomFactory.createMultiLineString(linesArray);
				target = SimpleFeatureBuilder.build(
						(SimpleFeatureType) targetType, new Object[]{}, 
						UUID.randomUUID().toString());
				
				target.setAttribute(this.targetAttribute,newGeometry);
				result.add(target);
			}
			else if (geom.getClass().equals(Polygon.class) && targetType.getGeometryDescriptor().getType().getBinding().equals(MultiPolygon.class)) {
				//Aggregation from multiple Polygon Features to a MultiPolygon
				List<Polygon> polys = new ArrayList<Polygon>();
				for (Feature f : source){
					Polygon p = (Polygon)((SimpleFeature)f).getAttribute(this.onAttributeName);
					polys.add(p);
				}
				Polygon[] polysArray = new Polygon[polys.size()];
				System.arraycopy(polys.toArray(), 0, polysArray, 0, polys.size());
				newGeometry= geomFactory.createMultiPolygon(polysArray);
				target = SimpleFeatureBuilder.build(
						(SimpleFeatureType) targetType, new Object[]{}, 
						UUID.randomUUID().toString());
				
				target.setAttribute(this.targetAttribute,newGeometry);
				result.add(target);
			}
			else{
				throw new RuntimeException("An aggregate:multi rule was " + //$NON-NLS-1$
				"defined on a non-Geometry property."); //$NON-NLS-1$
			}
		}
		return result;
	}
}