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

package eu.esdihumboldt.hale.schema.model.constraints.type;

import net.jcip.annotations.Immutable;
import eu.esdihumboldt.hale.schema.model.Constraint;
import eu.esdihumboldt.hale.schema.model.TypeConstraint;
import eu.esdihumboldt.hale.schema.model.constraints.AbstractFlagConstraint;

/**
 * Flags if a type is mappable, i.e. that it is a valid source or target for a
 * retype. Disabled by default.
 * @author Simon Templer
 */
@Immutable
public final class MappableFlag extends AbstractFlagConstraint implements TypeConstraint {

	/**
	 * Enabled mappable flag
	 */
	public static final MappableFlag ENABLED = new MappableFlag(true);
	
	/**
	 * Disabled mappable flag
	 */
	public static final MappableFlag DISABLED = new MappableFlag(false);
	
	/**
	 * Creates a default mappable flag, which is enabled. If possible, instead
	 * of creating an instance, use {@link #ENABLED} or {@link #DISABLED}.
	 * @see Constraint
	 */
	public MappableFlag() {
		this(true);
	}
	
	/**
	 * @see AbstractFlagConstraint#AbstractFlagConstraint(boolean)
	 */
	private MappableFlag(boolean enabled) {
		super(enabled);
	}

}