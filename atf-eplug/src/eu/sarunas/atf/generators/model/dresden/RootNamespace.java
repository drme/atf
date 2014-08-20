package eu.sarunas.atf.generators.model.dresden;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import eu.sarunas.atf.meta.sut.Project;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.pivotmodel.GenericElement;
import tudresden.ocl20.pivot.pivotmodel.NamedElement;
import tudresden.ocl20.pivot.pivotmodel.Namespace;
import tudresden.ocl20.pivot.pivotmodel.Type;
import tudresden.ocl20.pivot.pivotmodel.TypeParameter;
import tudresden.ocl20.pivot.pivotmodel.base.AbstractNamespace;

public class RootNamespace extends AbstractNamespace implements Namespace
{
	public RootNamespace(Project project, ProjectModel model)
	{
		this.project = project;
		this.model = model;
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	protected List<Namespace> getNestedNamespaceImpl()
	{
		List<Namespace> result = new ArrayList<Namespace>();
		
		return result;
	}

	@Override
	public Namespace getNestingNamespace()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Type> getOwnedType()
	{
		List<Type> types = new ArrayList<Type>();
		
		for (eu.sarunas.atf.meta.sut.Package packge : this.project.getPackages())
		{
		for (eu.sarunas.projects.atf.metadata.generic.Type type : packge.getClasses())
		{
			types.add(new Type1(type, this));
		}
		}
		
		return types;
	};

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private Project project = null;
	private ProjectModel model = null;
};
