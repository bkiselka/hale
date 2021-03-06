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

package eu.esdihumboldt.hale.ui.io.action;

import de.cs3d.util.eclipse.extension.ExtensionObjectFactoryCollection;
import de.cs3d.util.eclipse.extension.FactoryFilter;
import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.common.core.io.ExportProvider;
import eu.esdihumboldt.hale.common.core.io.IOAction;
import eu.esdihumboldt.hale.common.core.io.extension.IOActionExtension;
import eu.esdihumboldt.hale.common.core.io.extension.IOAdvisorExtension;
import eu.esdihumboldt.hale.common.core.io.extension.IOAdvisorFactory;
import eu.esdihumboldt.hale.ui.io.IOWizard;

/**
 * Contribution for export advisors
 * 
 * @author Simon Templer
 */
public class ExportContribution extends ActionUIContribution {

	private static final ALogger log = ALoggerFactory.getLogger(ExportContribution.class);

	/**
	 * Filter for export {@link ActionUI}s
	 */
	public static final FactoryFilter<IOWizard<?>, ActionUI> EXPORT_FILTER = new FactoryFilter<IOWizard<?>, ActionUI>() {

		@Override
		public boolean acceptFactory(ActionUI factory) {
			// accept if action is an export action
			final String actionId = factory.getActionID();
			IOAction action = IOActionExtension.getInstance().get(actionId);
			boolean isExport = ExportProvider.class.isAssignableFrom(action.getProviderType());
			if (isExport) {
				// and if there are any advisors present for the action
				for (IOAdvisorFactory advisorFactory : IOAdvisorExtension.getInstance()
						.getFactories()) {
					if (advisorFactory.getActionID().equals(actionId)) {
						return true;
					}
				}

				log.warn("No advisors present for action " + actionId);
			}
			return false;
		}

		@Override
		public boolean acceptCollection(
				ExtensionObjectFactoryCollection<IOWizard<?>, ActionUI> collection) {
			return true;
		}
	};

	/**
	 * Default constructor
	 */
	public ExportContribution() {
		super();

		setFilter(EXPORT_FILTER);
	}

}
