package eu.sarunas.atf.meta.sut;

import eu.sarunas.projects.atf.metadata.generic.Type;

public class Field extends Element
{
	public Field(String name, Modifier modifer, Type type, Class parent, Object sourceElement)
    {
	    super(name, sourceElement);
	    this.type = type;
	    this.modifier = modifer;
	    this.parent = parent;
    };

	public Modifier getModifier()
    {
	    return this.modifier;
    };

	public Type getType()
    {
	    return this.type;
    };

    public Class getParent()
    {
    	return this.parent;
    };
    
    /*
    
    public boolean isPrivate() {
        return (modifier & Class.AccPrivate) != 0;
    }

    public boolean isProtected() {
        return (modifier & Class.AccProtected) != 0;
    }

    public boolean isPublic() {
        return (modifier & Class.AccPublic) != 0;
    }
    
    */
    
	private Type genericType;

	public Type getGenericType() {
		return genericType;
	}

	public void setGenericType(Type genericType) {
		this.genericType = genericType;
	}

	public String toString()
	{
		return "Field: " + getName() + "\n";
	};
	

	
	
	

	private Type type = null;
	private Modifier modifier = Modifier.None;
	private Class parent = null;
};
