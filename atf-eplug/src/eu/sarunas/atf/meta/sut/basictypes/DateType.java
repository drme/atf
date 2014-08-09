package eu.sarunas.atf.meta.sut.basictypes;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class DateType extends Type
{
	public DateType()
    {
	    super("date", null);
	    setReferenceType(true);
    };

	public DateType(String nativeType)
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
