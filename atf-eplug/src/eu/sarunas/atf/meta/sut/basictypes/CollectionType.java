package eu.sarunas.atf.meta.sut.basictypes;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class CollectionType extends Type
{
	public CollectionType(CollectionStyle style, Type enclosingType)
    {
	    super(enclosingType.getName(), null);
	    
	    this.style = style;
	    this.enclosingType = enclosingType;
	    
	    setReferenceType(false);
    };
    
    public Type getEnclosingType()
    {
	    return this.enclosingType;
    };
    
    public int getDimensions()
    {
    	if ((CollectionStyle.Array == this.style) && (this.enclosingType instanceof CollectionType))
    	{
    		CollectionType array = (CollectionType)this.enclosingType;
    		
    		if (CollectionStyle.Array == array.getStyle())
    		{
        		return 1 + array.getDimensions();
    		}
    		else
    		{
    			return 1;
    		}
    	}
    	else
    	{
    		return 1;
    	}
    };
    
    public CollectionStyle getStyle()
    {
    	return this.style;
    };

    private CollectionStyle style = CollectionStyle.Array;
	private Type enclosingType = null;
};
