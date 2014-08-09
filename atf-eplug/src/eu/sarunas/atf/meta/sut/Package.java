package eu.sarunas.atf.meta.sut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Package extends Element
{
	public Package(Project project, String name, Object sourceElement)
	{
		super(name, sourceElement);
		
		this.project = project;
	};

	public Collection<Class> getClasses()
	{
		return Collections.unmodifiableCollection(this.classes.values());
	};

	public void addClass(Class clazz)
	{
		assert (false == clazz.getName().contains("."));
		assert (false == this.classes.containsKey(clazz.getName()));
		
		this.classes.put(clazz.getName(), clazz);
	};
	
	public Class getClass(String className)
	{
		assert (false == className.contains("."));
		
		return this.classes.get(className);
	};
	
    protected Element findElement(Object sourceElement)
    {
    	Element result = super.findElement(sourceElement);
    	
    	if (null == result)
    	{
    		for (Class cls : this.classes.values())
    		{
    			result = cls.findElement(sourceElement);
    			
    			if (null != result)
    			{
    				break;
    			}
    		}
    	}

    	return result;
    };
    
    public Project getProject()
    {
    	return this.project;
    };

    public void merge(Package pckg)
    {
    	assert (pckg.getName().equals(this.getName()));
    	
    	for (Class cls : pckg.getClasses())
    	{
    		addClass(cls);
    	}
    };
    
    public List<Class> findChilds(Class cls)
    {
    	List<Class> result = new ArrayList<Class>();
    	
    	for (Class c : this.classes.values())
    	{
    		if (true == c.isChild(cls))
    		{
    			result.add(c);
    		}
    	}
    	
    	return result;
    };
    
	public String toString()
	{
		String result = "\tPackage: " + getName() + "\n";

		for (Class cl : this.classes.values())
		{
			result += cl.toString();
		}

		return result;
	};

	private Project project = null;
	private HashMap<String, Class> classes = new HashMap<String, Class>();
};
