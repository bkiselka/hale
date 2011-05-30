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

package eu.esdihumboldt.hale.ui.views.report;

import java.util.ArrayList;

import org.xml.sax.SAXParseException;

/**
 * This class contains information about an error or warning
 * generated by {@link SAXParseException} and saves the message
 * and the lines.
 * 
 * @author Andreas Burchert
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$
 */
public class TransformationResultItem {

	/**
	 * Contains the message.
	 */
	private String message;
	
	/**
	 * Contains all occurrence.
	 */
	private ArrayList<String> lines = new ArrayList<String>();
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param line first occurrence
	 */
	public TransformationResultItem(String message, String line) {
		this.message = message;
		this.addLine(line);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param message the message
	 * @param line first occurrence
	 */
	public TransformationResultItem(String message, int line) {
		this.message = message;
		this.addLine(line);
	}
	
	/**
	 * Add a new line.
	 * 
	 * @param line occurrence
	 */
	public void addLine(String line) {
		if (line.isEmpty()) {
			return;
		}
		this.lines.add(line);
	}
	
	/**
	 * Add a new line.
	 * 
	 * @param line occurrence
	 */
	public void addLine(int line) {
		this.addLine(""+line); //$NON-NLS-1$
	}
	
	/**
	 * Returns the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Returns all lines.
	 * 
	 * @return all lines as Strings
	 */
	public ArrayList<String> getLines() {
		return this.lines;
	}
}