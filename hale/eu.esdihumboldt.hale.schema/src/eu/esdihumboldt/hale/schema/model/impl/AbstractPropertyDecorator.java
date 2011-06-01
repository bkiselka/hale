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

package eu.esdihumboldt.hale.schema.model.impl;

import java.net.URI;

import javax.xml.namespace.QName;

import net.jcip.annotations.Immutable;

import eu.esdihumboldt.hale.core.io.supplier.Locatable;
import eu.esdihumboldt.hale.schema.model.Definition;
import eu.esdihumboldt.hale.schema.model.PropertyConstraint;
import eu.esdihumboldt.hale.schema.model.PropertyDefinition;
import eu.esdihumboldt.hale.schema.model.TypeDefinition;

/**
 * Abstract decorator for {@link PropertyDefinition}s
 * @author Simon Templer
 */
@Immutable
public abstract class AbstractPropertyDecorator implements PropertyDefinition {

	/**
	 * The internal property definition
	 */
	protected final PropertyDefinition property;

	/**
	 * Create a property definition decorator
	 * 
	 * @param property the internal property definition
	 */
	public AbstractPropertyDecorator(PropertyDefinition property) {
		super();
		this.property = property;
	}

	/**
	 * @see Locatable#getLocation()
	 */
	@Override
	public URI getLocation() {
		return property.getLocation();
	}

	/**
	 * @see Definition#getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return property.getIdentifier();
	}

	/**
	 * @see Definition#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return property.getDisplayName();
	}

	/**
	 * @see Definition#getName()
	 */
	@Override
	public QName getName() {
		return property.getName();
	}

	/**
	 * @see Definition#getDescription()
	 */
	@Override
	public String getDescription() {
		return property.getDescription();
	}

	/**
	 * @see Definition#getConstraint(Class)
	 */
	@Override
	public <T extends PropertyConstraint> T getConstraint(
			Class<T> constraintType) {
		return property.getConstraint(constraintType);
	}

	/**
	 * @see PropertyDefinition#getDeclaringType()
	 */
	@Override
	public TypeDefinition getDeclaringType() {
		return property.getDeclaringType();
	}

	/**
	 * @see PropertyDefinition#getParentType()
	 */
	@Override
	public TypeDefinition getParentType() {
		return property.getParentType();
	}

	/**
	 * @see PropertyDefinition#getPropertyType()
	 */
	@Override
	public TypeDefinition getPropertyType() {
		return property.getPropertyType();
	}

}