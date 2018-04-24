package eu.sarunas.atf.generators.model.dresden;

import java.util.ArrayList;
import java.util.List;
import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.sut.Method;
import tudresden.ocl20.pivot.pivotmodel.Namespace;
import tudresden.ocl20.pivot.pivotmodel.Operation;
import tudresden.ocl20.pivot.pivotmodel.Property;
import tudresden.ocl20.pivot.pivotmodel.Type;
import tudresden.ocl20.pivot.pivotmodel.base.AbstractType;

class WrapperType extends AbstractType
{
	public WrapperType(eu.sarunas.projects.atf.metadata.generic.Type type, WrapperNamespace namespace, ProjectModel model)
	{
		this.type = type;
		this.namespace = namespace;
		this.model = model;
		
		this.model.addType(type, this);

		if (this.type instanceof eu.sarunas.atf.meta.sut.Class)
		{
			eu.sarunas.atf.meta.sut.Class cls = (eu.sarunas.atf.meta.sut.Class) this.type;
			
			for (Field field : cls.getFields())
			{
				this.ownedProperties.add(new WrapperProperty(field, this, model));
			}
			
			for (Method method : cls.getMethods())
			{
				this.ownedOperations.add(new WrapperOperation(method, this, model));
			}
		}
	};
	
	@Override
	public String getName()
	{
		return this.type.getName();
	};

	@Override
	public Namespace getNamespace()
	{
		return this.namespace;
	};

	@Override
	protected List<Operation> getOwnedOperationImpl()
	{
		return this.ownedOperations;
	};

	@Override
	protected List<Property> getOwnedPropertyImpl()
	{
		return this.ownedProperties;
	};

	@Override
	protected List<Type> getSuperTypeImpl()
	{
		List<Type> result = new ArrayList<Type>();
		
		// TODO Auto-generated method stub
		return result;
	}	


	private List<Operation> ownedOperations = new ArrayList<Operation>();
	private List<Property> ownedProperties = new ArrayList<Property>();
	private eu.sarunas.projects.atf.metadata.generic.Type type;
	private WrapperNamespace namespace;
	private ProjectModel model;
};
