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

package eu.esdihumboldt.util.validator;

/**
 * Validator using pattern matching.
 *
 * @author Kai Schwierczek
 */
public class PatternValidator extends AbstractValidator {
	private String pattern;

	/**
	 * Construct a PatternValidator with the given pattern.
	 * 
	 * @param pattern the pattern to use
	 */
	public PatternValidator(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * @see eu.esdihumboldt.util.validator.Validator#validate(Object)
	 */
	@Override
	public String validate(Object value) {
		String s = getObjectAs(value, String.class);
		if (s.matches(pattern))
			return null;
		else
			return "Input doesn't match " + pattern + ".";
	}

	/**
	 * @see eu.esdihumboldt.util.validator.Validator#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Input must match this pattern: " + pattern + ".";
	}
}