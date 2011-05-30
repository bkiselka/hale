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


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.ui.model.augmentations.AugmentationWizardFactory;

/**
 * Descriptor for {@link FunctionWizardFactory}(ie)s
 * 
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$ 
 */
public class DefaultFunctionWizardDescriptor extends AbstractFunctionWizardDescriptor {
	
	private static final ALogger log = ALoggerFactory.getLogger(DefaultFunctionWizardDescriptor.class);

	private FunctionWizardFactory factory = null;
	
	/**
	 * Constructor
	 * 
	 * @param conf the configuration element describing the
	 *   {@link FunctionWizardFactory}
	 */
	public DefaultFunctionWizardDescriptor(final IConfigurationElement conf) {
		super(conf);
	}
	
	/**
	 * @see FunctionWizardDescriptor#getFactory()
	 */
	@Override
	public FunctionWizardFactory getFactory() {
		if (factory == null) {
			try {
				factory = (FunctionWizardFactory) conf.createExecutableExtension("class"); //$NON-NLS-1$
			} catch (CoreException e) {
				log.error("Error creating the function wizard factory", e); //$NON-NLS-1$
			}
		}
		
		return factory;
	}
	
	/**
	 * @see FunctionWizardDescriptor#isAugmentation()
	 */
	@Override
	public boolean isAugmentation() {
		return getFactory() instanceof AugmentationWizardFactory;
	}

}