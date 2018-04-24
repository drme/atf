package eu.sarunas.atf.eclipse.actions;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

class TypeNameExtractor
{
	public TypeNameExtractor()
	{
	};
	
	/**
	 * Attempts to get the fully-qualified class-name of the workspace-element
	 * represented by <code>source</code>.
	 * @param source the workspace-element from which the class-name must be determined. 
	 * @return the fully-qualified class-name, or <code>null</code> of no class-name
	 *         can be determined for <code>source</code>.
	 */
	public String getFqn(Object source)
	{
		if (source instanceof IType)
		{
			return getFqn((IType)source);
		}
		else if (source instanceof ICompilationUnit)
		{
			return getFqn((ICompilationUnit)source);
		}
		else if (source instanceof IClassFile)
		{
			return getFqn((IClassFile)source);
		}
		else
		{
			return null;
		}
	};
	
	private String getFqn(IType type)
	{
		try
		{
			return type.getFullyQualifiedName();
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
		}
		
		return "ADASD";
	};
	
	private String getFqn(ICompilationUnit compilationUnit)
	{
		try
		{
			IType[] types = compilationUnit.getTypes();
			
			if(types.length > 0)
			{
				return types[0].getFullyQualifiedName();
			}
			else
			{
				return null;
			}
		}
		catch (JavaModelException e)
		{
			return null;
		}		
	};
	
	private String getFqn(IClassFile classFile)
	{
		try
		{
			IType type = classFile.getType();
			
			if(type != null)
			{
				return type.getFullyQualifiedName();
			}
			else
			{
				return null;
			}
		}
		catch (Throwable e)
		{
			return null;
		}
	}
};
