package eu.sarunas.projects.atf.metadata.generic;

import eu.sarunas.atf.meta.sut.Element;

/**
 * Base class for all meta data.
 */
public class Type extends Element
{
	public Type(String name, Object sourceElement)
	{
		super(name, sourceElement);
    };
    
    /**
     * Specifies if type is basic language type or composed type (like class).
     * 
     * @return true if it's reference type.
     */
	public boolean isReferenceType()
    {
    	return this.referenceType;
    };

	public void setReferenceType(boolean referenceType)
    {
    	this.referenceType = referenceType;
    };
    
	protected boolean referenceType = false;
	   
    public String getFullName(){
    	return getName();
    }
};
