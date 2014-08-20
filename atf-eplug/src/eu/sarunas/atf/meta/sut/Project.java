package eu.sarunas.atf.meta.sut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * Project class contains all the model of software under test.
 */
public class Project extends Element
{
	public Project(String name, Object sourceElement)
	{
		super(name, sourceElement);
	};

	/**
	 * Adds package, or merges with the one that is already is added with the same name.
	 * 
	 * @param pckg package to add.
	 * 
	 * @return resulting package: the original or the merged one. 
	 */
	public Package addPackage(Package pckg)
	{
		assert(null != pckg);
		
		Package p = this.packages.get(pckg.getName());
	
		if (null != p)
		{
			p.merge(pckg);
			
			return p;
		}
		else
		{
			this.packages.put(pckg.getName(), pckg);
			
			return pckg;
		}
	};

	public Collection<Package> getPackages()
	{
		return Collections.unmodifiableCollection(this.packages.values());
	};
	
	public Package getPackage(String packageName)
	{
		return this.packages.get(packageName);
	};

	public String toString()
	{
		String result = "Project: " + getName() + "\n";

		for (Package pckage : this.packages.values())
		{
			result += pckage.toString();
		}

		return result;
	};

	public Method findMethod(Object sourceMethod)
    {
	    Element result = findElement(sourceMethod);
	    
	    if (result instanceof Method)
	    {
	    	return (Method)result;
	    }
	    else
	    {
	    	return null;
	    }
    };
    
    public List<Class> findChilds(Class cls)
    {
    	List<Class> result = new ArrayList<Class>();
    	
    	for (Package pckge : this.packages.values())
    	{
    		result.addAll(pckge.findChilds(cls));
    	}
    	
    	return result;
    };
	
    protected Element findElement(Object sourceElement)
    {
    	Element result = super.findElement(sourceElement);
    	
    	if (null == result)
    	{
    		for (Package pckg : this.packages.values())
    		{
    			result = pckg.findElement(sourceElement);
    			
    			if (null != result)
    			{
    				break;
    			}
    		}
    	}

    	return result;
    };

	public Type findType(String name)
	{
		for (Package packge : this.packages.values())
		{
			Type type = packge.findType(name);

			if (null != type)
			{
				return type;
			}
		}

		return null;
	};

	private HashMap<String, Package> packages = new HashMap<String, Package>();
};
