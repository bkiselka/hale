/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2010.
 */
package eu.esdihumboldt.hale.ui.model.functions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;

/**
 * Wizard page that knows about its {@link AbstractSingleComposedCellWizard}
 * 
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$ 
 */
public abstract class AbstractSingleComposedCellWizardPage extends WizardPage {

	private AbstractSingleComposedCellWizard parent;

	/**
	 * @see WizardPage#WizardPage(String, String, ImageDescriptor)
	 */
	public AbstractSingleComposedCellWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	/**
	 * @see WizardPage#WizardPage(String)
	 */
	public AbstractSingleComposedCellWizardPage(String pageName) {
		super(pageName);
	}

	/**
	 * @return the parent
	 */
	public AbstractSingleComposedCellWizard getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(AbstractSingleComposedCellWizard parent) {
		this.parent = parent;
	}

}