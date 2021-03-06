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

package eu.esdihumboldt.hale.ui.views.schemas.explorer;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import eu.esdihumboldt.hale.common.align.model.EntityDefinition;
import eu.esdihumboldt.hale.common.schema.Classification;
import eu.esdihumboldt.hale.common.schema.model.Definition;

/**
 * Filters based on {@link Classification}s
 * 
 * @author Simon Templer
 */
public class ClassificationFilter extends ViewerFilter {

	private Set<Classification> hidden = new HashSet<Classification>();

	private final Viewer viewer;

	/**
	 * Create a classification filter
	 * 
	 * @param viewer the viewer the filter is associated to
	 */
	public ClassificationFilter(Viewer viewer) {
		super();
		this.viewer = viewer;
	}

	/**
	 * Set if definitions with the given classification shall be visible.
	 * 
	 * @param clazz the classification
	 * @param visible if the definitions with the classification shall be
	 *            visible
	 */
	public void setVisible(Classification clazz, boolean visible) {
		if (visible) {
			if (hidden.remove(clazz)) {
				viewer.refresh();
			}
		}
		else {
			if (hidden.add(clazz)) {
				viewer.refresh();
			}
		}
	}

	/**
	 * Determines if a definition classified with the given classification is
	 * currently configured as visible in the filter.
	 * 
	 * @param clazz the classification
	 * @return if the classified definitions are visible
	 */
	public boolean isVisible(Classification clazz) {
		return !hidden.contains(clazz);
	}

	/**
	 * @see ViewerFilter#select(Viewer, Object, Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof TreePath) {
			element = ((TreePath) element).getLastSegment();
		}

		if (element instanceof EntityDefinition) {
			element = ((EntityDefinition) element).getDefinition();
		}

		if (hidden.isEmpty() || !(element instanceof Definition<?>)) {
			// fast exit
			return true;
		}

		Definition<?> def = (Definition<?>) element;
		Classification clazz = Classification.getClassification(def);

		return !hidden.contains(clazz);
	}

}
