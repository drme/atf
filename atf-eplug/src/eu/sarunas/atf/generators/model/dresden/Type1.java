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
import eu.sarunas.atf.meta.sut.Field;
import tudresden.ocl20.pivot.pivotmodel.GenericElement;
import tudresden.ocl20.pivot.pivotmodel.GenericType;
import tudresden.ocl20.pivot.pivotmodel.NamedElement;
import tudresden.ocl20.pivot.pivotmodel.Namespace;
import tudresden.ocl20.pivot.pivotmodel.Operation;
import tudresden.ocl20.pivot.pivotmodel.Property;
import tudresden.ocl20.pivot.pivotmodel.Type;
import tudresden.ocl20.pivot.pivotmodel.TypeParameter;
import tudresden.ocl20.pivot.pivotmodel.base.AbstractType;

public class Type1 extends AbstractType implements Type
{
	public Type1(eu.sarunas.projects.atf.metadata.generic.Type type, Namespace namespace)
	{
		assert (null != namespace);
		
		this.type = type;
		this.namespace = namespace;
	};


	private eu.sarunas.projects.atf.metadata.generic.Type type = null;
private Namespace namespace = null;
	
	@Override
	public String getName()
	{
		System.out.println("Get Name: " + this.type.getName());
		
		return this.type.getName();
	}

	@Override
	public Namespace getNamespace()
	{
		System.out.println("GEtNamespace:" + this.namespace);
		
		// TODO Auto-generated method stub
		return this.namespace;
	}

	@Override
	protected List<Operation> getOwnedOperationImpl()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<Property> getOwnedPropertyImpl()
	{
		System.out.println("get property");
		
		List<Property> properties = new ArrayList<Property>();
		
		for (Field field : ((eu.sarunas.atf.meta.sut.Class)this.type).getFields())
		{
			properties.add(new Properrty1(field, this));
		}
		
		// TODO Auto-generated method stub
		return properties;
	}

	@Override
	protected List<Type> getSuperTypeImpl()
	{
		List<Type> result = new ArrayList<Type>();
		
		// TODO Auto-generated method stub
		return result;
	}	
};
