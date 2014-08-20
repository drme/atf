package eu.sarunas.atf.meta.sut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class Class extends Type
{
	public Class(String name, EnumSet<Modifier> modifiers, Package pckg, Object sourceElement)
	{
		super(name, sourceElement);
		this.modifiers = modifiers;
		this.pckg = pckg;
		this.referenceType = true;

		assert (false == name.contains("."));
	};

	public List<Constructor> getConstructors()
	{
		return Collections.unmodifiableList(this.constructors);
	};

	public List<Field> getFields()
	{
		return Collections.unmodifiableList(this.fields);
	};

	public List<Method> getMethods()
	{
		return Collections.unmodifiableList(this.methods);
	};

	public Package getPackage()
	{
		return this.pckg;
	};

	public void addMethod(Method method)
	{
		this.methods.add(method);
	};

	public void addField(Field field)
	{
		this.fields.add(field);
	}

	public void addConstructor(Constructor constructor)
	{
		this.constructors.add(constructor);
	};
	
	public EnumSet<Modifier> getModifiers()
	{
		return this.modifiers;
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Field getField(String name)
    {
		for (Field f : fields) {
			if(f.getName().equals(name))
				return f;
		}
		if(getSuperClass() != null){
			if(getSuperClass() instanceof Class){
				return ((Class)getSuperClass()).getField(name);
			}
		}
		
		return null;
    };

    
    public boolean isChild(Class parent)
    {
    	if (null == this.getSuperClass())
    	{
    		return false;
    	}
    	else if (parent == this.getSuperClass())
    	{
    		return true;
    	}
    	else
    	{
    		return ((Class)this.getSuperClass()).isChild(parent);
    	}
    };
    
    
    
    
    
    
    
    

	// garbage..
    
    
	public int getModifier()
    {
	    return this.modifier;
    };
    
    
    
    
    
    
    
    
 

    public boolean isFinal() {
        return (modifier & AccFinal) != 0;
    }

    public boolean isInterface() {
        return (modifier & AccInterface) != 0;
    }

    public boolean isNative() {
        return (modifier & AccNative) != 0;
    }

    public boolean isPackageDefault() {
        return (modifier & (AccPublic | AccPrivate | AccProtected)) == 0;
    }

    public boolean isPrivate() {
        return (modifier & AccPrivate) != 0;
    }

    public boolean isProtected() {
        return (modifier & AccProtected) != 0;
    }

    public boolean isPublic() {
        return (modifier & AccPublic) != 0;
    }

    public boolean isStatic() {
        return (modifier & AccStatic) != 0;
    }

    public boolean isSuper() {
        return (modifier & AccSuper) != 0;
    }

    public boolean isStrictfp() {
        return (modifier & AccStrictfp) != 0;
    }

    public boolean isSynchronized() {
        return (modifier & AccSynchronized) != 0;
    }

    public boolean isSynthetic() {
        return (modifier & AccSynthetic) != 0;
    }

    public boolean isTransient() {
        return (modifier & AccTransient) != 0;
    }

    public boolean isVolatile() {
        return (modifier & AccVolatile) != 0;
    }

    public boolean isBridge() {
        return (modifier & AccBridge) != 0;
    }

    public boolean isVarargs() {
        return (modifier & AccVarargs) != 0;
    }

    public boolean isEnum() {
        return (modifier & AccEnum) != 0;
    }
    
    public String getName(){
    	if(super.getName() == null){
    		return "ArrayList";
    	}
    	int index = super.getName().lastIndexOf(".");
    	if(index > 0){
    		return super.getName().substring(++index);
    	}
    	return super.getName();
    }
    public String getFullName(){
    	if(pckg != null && pckg.getName().length() != 0){
    		return pckg.getName() + "." + getName();
    	}
    	return getName();
    }
    
	public Type getSuperClass() {
		return superClass;
	}

	public void setSuperClass(Type type) {
		this.superClass = type;
	}

	
    public boolean instanceOf(Class clazz){
    	if(this.getPackage().getName().equals(clazz.getPackage().getName())){
	    	if(getName().equals(clazz.getName())){
	    		return true;
	    	}
	    	if(superClass != null){
	    		return ((Class)superClass).instanceOf(clazz);
	    	}
    	}
    	
    	return false;
    }    


    protected Element findElement(Object sourceElement)
    {
    	Element result = super.findElement(sourceElement);
    	
    	if (null == result)
    	{
    		for (Method method : this.methods)
    		{
    			result = method.findElement(sourceElement);
    			
    			if (null != result)
    			{
    				break;
    			}
    		}
    	}

    	return result;
    };
    
	public String toString()
	{
		String result = "Class: " + getFullName() + "\n";

		for (Field field : this.fields)
		{
			result += field.toString();
		}

		for (Constructor constructor : this.constructors)
		{
			result += constructor.toString();
		}

		for (Method method : this.methods)
		{
			result += method.toString();
		}

		return result;
	};    
    
	/**
	 * enum please..
	 */
	
    public static final int AccPublic       = 0x0001;
    public static final int AccPrivate      = 0x0002;
    public static final int AccProtected    = 0x0004;
    public static final int AccStatic       = 0x0008;
    public static final int AccFinal        = 0x0010;
    public static final int AccSynchronized = 0x0020;
    public static final int AccVolatile     = 0x0040;
    public static final int AccBridge       = 0x0040;
    public static final int AccTransient    = 0x0080;
    public static final int AccVarargs      = 0x0080;
    public static final int AccNative       = 0x0100;
    public static final int AccInterface    = 0x0200;
    public static final int AccStrictfp     = 0x0800;
    public static final int AccSynthetic    = 0x1000;
    public static final int AccAnnotation   = 0x2000;
    public static final int AccEnum         = 0x4000;
    public static final int AccSuper 		= 0x0020;	
	
    
    private int modifier = -1;
    
 
    private Type superClass;

    
    public List<Type> getInterfaces()
    {
    	return this.interfaces;
    };
    
    public void addInterface(Type intrfce)
    {
    	this.interfaces.add(intrfce);
    };

    private List<Type> interfaces = new ArrayList<Type>();
    
    
    
    
    private Package pckg = null;
    private List<Constructor> constructors = new ArrayList<Constructor>();
    private List<Method> methods = new ArrayList<Method>();
    private List<Field> fields = new ArrayList<Field>();
    private EnumSet<Modifier> modifiers = EnumSet.of(Modifier.None);
};
