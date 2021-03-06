/*
 * Copyright (c) 2012 Data Harmonisation Panel
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     HUMBOLDT EU Integrated Project #030962
 *     Data Harmonisation Panel <http://www.dhpanel.eu>
 */

package eu.esdihumboldt.hale.io.gml.writer.internal.geometry.converters;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Polygon;

import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.GeometryConverter;

/**
 * Converts a {@link Polygon} to a {@link MultiLineString}.
 * 
 * The polygon is divided into multiple LineStrings, each containing two points.
 * Needed for polygons that represent curves.
 * 
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$
 */
public class PolygonToMultiLineString extends AbstractGeometryConverter<Polygon, MultiLineString> {

	/**
	 * Default constructor
	 */
	public PolygonToMultiLineString() {
		super(Polygon.class, MultiLineString.class);
	}

	/**
	 * @see GeometryConverter#convert(Geometry)
	 */
	@Override
	public MultiLineString convert(Polygon polygon) {
		LineString exterior = polygon.getExteriorRing();

		// the line string is no ring in itself, therefore we create multiple
		// line strings that form up a curve

		Coordinate[] coordinates = exterior.getCoordinates();
		int length = coordinates.length;
		if (length > 1) {
			// test if first equals last
			if (coordinates[0].equals(coordinates[length - 1])) {
				// first and last match -> we reduce the virtual size by 1
				length--;
			}
		}

		if (length <= 2) {
			return geomFactory.createMultiLineString(new LineString[] { exterior });
		}
		else {
			LineString[] segments = new LineString[length];
			for (int i = 0; i < length; i++) {
				Coordinate[] segmentCoords = new Coordinate[2];
				segmentCoords[0] = coordinates[i];
				segmentCoords[1] = coordinates[(i + 1 < length) ? (i + 1) : (0)];
				segments[i] = geomFactory.createLineString(segmentCoords);
			}

			return geomFactory.createMultiLineString(segments);
		}
	}

	/**
	 * @see GeometryConverter#lossOnConversion(Geometry)
	 */
	@Override
	public boolean lossOnConversion(Polygon geometry) {
		// we classify the conversion as a loss because it's a change from a
		// surface to a curve and the interior is lost
		return true;
	}

}
