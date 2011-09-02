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

package eu.esdihumboldt.hale.ui.wizards.functions.contribution.internal;

import eu.esdihumboldt.hale.align.model.Cell;
import eu.esdihumboldt.hale.ui.service.align.AlignmentService;
import eu.esdihumboldt.hale.ui.wizards.functions.FunctionWizard;
import eu.esdihumboldt.hale.ui.wizards.functions.contribution.AbstractFunctionWizardContribution;
import eu.esdihumboldt.hale.ui.wizards.functions.contribution.CellFunctionContribution;
import eu.esdihumboldt.hale.ui.wizards.functions.extension.FunctionWizardDescriptor;


/**
 * Action for creating a function wizard based on a {@link Cell}
 * @author Simon Templer
 */
public class CellWizardAction extends AbstractWizardAction<CellFunctionContribution> {

	/**
	 * @see AbstractWizardAction#AbstractWizardAction(AbstractFunctionWizardContribution, FunctionWizardDescriptor, AlignmentService)
	 */
	public CellWizardAction(
			CellFunctionContribution functionContribution,
			FunctionWizardDescriptor<?> descriptor,
			AlignmentService alignmentService) {
		super(functionContribution, descriptor, alignmentService);
	}

	/**
	 * @see AbstractWizardAction#createWizard()
	 */
	@Override
	protected FunctionWizard createWizard() {
		return descriptor.createWizard(functionContribution.getCell());
	}

}