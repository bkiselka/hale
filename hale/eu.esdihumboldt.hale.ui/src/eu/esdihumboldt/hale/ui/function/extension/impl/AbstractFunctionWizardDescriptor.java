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

package eu.esdihumboldt.hale.ui.function.extension.impl;

import java.net.URL;

import org.eclipse.core.runtime.IConfigurationElement;

import de.cs3d.util.eclipse.extension.AbstractConfigurationFactory;
import de.cs3d.util.eclipse.extension.AbstractObjectFactory;
import de.cs3d.util.eclipse.extension.ExtensionObjectDefinition;
import de.cs3d.util.eclipse.extension.ExtensionObjectFactory;
import eu.esdihumboldt.hale.align.extension.function.AbstractFunction;
import eu.esdihumboldt.hale.align.model.Cell;
import eu.esdihumboldt.hale.ui.function.FunctionWizard;
import eu.esdihumboldt.hale.ui.function.extension.FunctionWizardDescriptor;
import eu.esdihumboldt.hale.ui.function.extension.FunctionWizardFactory;
import eu.esdihumboldt.hale.ui.selection.SchemaSelection;

/**
 * Abstract function wizard descriptor based on a configuration element
 * @param <T> the function definition type
 * @author Simon Templer
 */
public abstract class AbstractFunctionWizardDescriptor<T extends AbstractFunction> 
		extends AbstractConfigurationFactory<FunctionWizardFactory> implements
		FunctionWizardDescriptor<T> {
	
	private FunctionWizardFactory factory;

	/**
	 * Create a function wizard descriptor based on the given configuration 
	 * element
	 * @param conf the configuration element
	 */
	protected AbstractFunctionWizardDescriptor(IConfigurationElement conf) {
		super(conf, "class");
	}

	/**
	 * @see ExtensionObjectFactory#dispose(Object)
	 */
	@Override
	public void dispose(FunctionWizardFactory instance) {
		// do nothing
	}

	/**
	 * @see ExtensionObjectDefinition#getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return conf.getAttribute("id");
	}

	/**
	 * @see ExtensionObjectDefinition#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return getFunction().getDisplayName();
	}

	/**
	 * @see FunctionWizardDescriptor#getFunctionId()
	 */
	@Override
	public String getFunctionId() {
		return conf.getAttribute("function");
	}

	/**
	 * @see AbstractObjectFactory#getIconURL()
	 */
	@Override
	public URL getIconURL() {
		return getFunction().getIconURL();
	}

	/**
	 * @see AbstractConfigurationFactory#createExtensionObject()
	 */
	@Override
	public FunctionWizardFactory createExtensionObject() throws Exception {
		String clazz = conf.getAttribute("class");
		try {
			if (clazz != null && !clazz.isEmpty()) {
				return super.createExtensionObject();
			}
		} catch (Exception e) {
			//TODO log message
		}
		
		return createDefaultFactory();
	}

	/**
	 * Create the default function wizard factory for the function if none is
	 * explicitly defined or its creation fails.
	 * @return the default function wizard factory
	 */
	protected abstract FunctionWizardFactory createDefaultFactory();

	/**
	 * Get the associated function wizard factory
	 * @return the function wizard factory
	 */
	protected FunctionWizardFactory getFactory() {
		if (factory == null) {
			try {
				factory = createExtensionObject();
			} catch (Exception e) {
				throw new IllegalStateException("Error creating function wizard factory");
			}
		}
		return factory;
	}
	
	/**
	 * @see FunctionWizardFactory#createWizard(SchemaSelection)
	 */
	@Override
	public FunctionWizard createWizard(SchemaSelection schemaSelection) {
		return getFactory().createWizard(schemaSelection);
	}

	/**
	 * @see FunctionWizardFactory#createWizard(Cell)
	 */
	@Override
	public FunctionWizard createWizard(Cell cell) {
		return getFactory().createWizard(cell);
	}

}