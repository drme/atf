package eu.sarunas.atf.generators.model.dresden;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import tudresden.ocl20.pivot.pivotmodel.Namespace;
import tudresden.ocl20.pivot.pivotmodel.PrimitiveTypeKind;
import tudresden.ocl20.pivot.pivotmodel.base.AbstractPrimitiveType;

public class WrapperPrimitiveType extends AbstractPrimitiveType
{
	public WrapperPrimitiveType(eu.sarunas.projects.atf.metadata.generic.Type type, PrimitiveTypeKind kind, ProjectModel model)
	{
		this.type = type;
		this.kind = kind;
		this.model = model;
		
		this.model.addType(type, this);
	};

	@Override
	public PrimitiveTypeKind getKind()
	{
		return this.kind;
	};

	@Override
	public String getName()
	{
		return this.type.getName();
	};

	@Override
	public Namespace getNamespace()
	{
		return null;
		
/*
		Namespace result;

		String[] namespacePath;
		List<String> namespaceList;

		namespaceList = new ArrayList<String>();
		namespaceList.add(ModelConstants.ROOT_PACKAGE_NAME);

		/* Add all packages of the canonical name to the path. *-/
		namespacePath = this.myClass.getCanonicalName().split("\\.");

		for (int index = 0; index < namespacePath.length - 1; index++)
		{
			namespaceList.add(namespacePath[index]);
		}

		/* Create the name space. *-/
		result = this.myFactory.createNamespace(namespaceList);

		return result; */
	}

	private eu.sarunas.projects.atf.metadata.generic.Type type;
	private WrapperNamespace namespace;
	private ProjectModel model;
	private PrimitiveTypeKind kind;
};
