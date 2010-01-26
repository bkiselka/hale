/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2.1</a>, using an XML
 * Schema.
 * $Id: ContextImpl.java,v 1.4 2007-11-16 12:51:16 pitaeva Exp $
 */

package eu.esdihumboldt.mediator.context.impl;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import eu.esdihumboldt.mediator.constraints.Constraint;

/**
 * Class Context.
 * 
 * @version $Revision: 1.4 $ $Date: 2007-11-16 12:51:16 $
 */
public class ContextImpl extends Contexts 
implements java.io.Serializable
{


      //----------------/
     //- Constructors -/
    //----------------/

    public ContextImpl() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, handler);
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * eu.esdihumboldt.mediator.usermanagement.Contexts
     */
    public static eu.esdihumboldt.mediator.context.impl.Contexts unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (eu.esdihumboldt.mediator.context.impl.Contexts) Unmarshaller.unmarshal(eu.esdihumboldt.mediator.context.impl.ContextImpl.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }


	public Map<ContextType, Set<Constraint>> getAllConstraints() {
		// TODO Auto-generated method stub
		return null;
	}


	public Set<Constraint> getAllConstraints(ContextType type) {
		// TODO Auto-generated method stub
		return null;
	}


	public Set<Constraint> getCombinedConstraints(ContextType type) {
		// TODO Auto-generated method stub
		return null;
	}


	public UUID getContextID() {
		// TODO Auto-generated method stub
		return null;
	}


	public ContextType getContextType() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getTitle()
	{
		return null;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}


	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

}
