package eu.sarunas.atf.meta.sut;

import java.util.ArrayList;
import java.util.List;


public class Library extends Element implements ILibrary
{
	public Library(Project project, String name, Object sourceElement)
    {
	    super(name, sourceElement);

    	this.project = project;
    };

    public void addPackage(String packageName, Object sourceElement)
    {
    	Package p = getPackage(packageName);
    	
    	if (null == p)
    	{
    		p = new Package(this.project, packageName, sourceElement);
    		
    		this.packages.add(p);
    	}
    };
    
    public Package getPackage(String packageName)
    {
    	for (Package p : this.packages)
    	{
    		if (true == p.getName().equals(packageName))
    		{
    			return p;
    		}
    	}
    	
    	return null;
    };
    
	public List<Package> getPackages()
    {
	    return this.packages;
    };

    public Package getDefaultPackage()
    {
    	Package p = getPackage("");
    	
    	if (null == p)
    	{
    		addPackage("", null);

        	return getPackage("");
    	}
    	else
    	{
    		return p;
    	}
    };

    private Project project = null;
	protected List<Package> packages = new ArrayList<Package>();
};
