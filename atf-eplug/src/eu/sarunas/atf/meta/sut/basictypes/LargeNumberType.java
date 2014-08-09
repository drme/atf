package eu.sarunas.atf.meta.sut.basictypes;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class LargeNumberType extends Type
{
	public LargeNumberType()
    {
	    super("bigDecimal", null);
	    setReferenceType(true);
    };
    
	public LargeNumberType(String nativeType)
    {
		this();
		
		this.nativeType = nativeType;
    };

    public String getNativeType()
    {
    	return this.nativeType;
    };
    
    private String nativeType = "";    
};
