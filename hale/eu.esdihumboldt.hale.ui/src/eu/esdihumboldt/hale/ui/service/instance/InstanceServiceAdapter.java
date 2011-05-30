/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2011.
 */

package eu.esdihumboldt.hale.ui.service.instance;

import eu.esdihumboldt.hale.ui.service.instance.InstanceService.DatasetType;
import eu.esdihumboldt.hale.ui.service.instance.crs.CRSDefinition;

/**
 * Instance service listener adapter
 * @author Simon Templer
 */
public class InstanceServiceAdapter implements InstanceServiceListener {

	/**
	 * @see InstanceServiceListener#datasetChanged(DatasetType)
	 */
	@Override
	public void datasetChanged(DatasetType type) {
		// please override me
	}

	/**
	 * @see InstanceServiceListener#crsChanged(CRSDefinition)
	 */
	@Override
	public void crsChanged(CRSDefinition crs) {
		// please override me
	}

}