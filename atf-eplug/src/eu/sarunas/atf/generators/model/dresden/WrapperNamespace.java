package eu.sarunas.atf.generators.model.dresden;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import eu.sarunas.atf.meta.sut.Project;
import tudresden.ocl20.pivot.pivotmodel.Namespace;
import tudresden.ocl20.pivot.pivotmodel.Type;
import tudresden.ocl20.pivot.pivotmodel.base.AbstractNamespace;

class WrapperNamespace extends AbstractNamespace
{
	public WrapperNamespace(Project project, String fullPackageName, String localName, WrapperNamespace nestingNamespace, ProjectModel model)
	{
		this.model = model;
		this.localName = localName;
		this.nestingNamespace = nestingNamespace;
		this.project = project;

		HashSet<String> localNames = new HashSet<String>();

		for (eu.sarunas.atf.meta.sut.Package packge : this.project.getPackages())
		{
			if (packge.getName().startsWith(fullPackageName))
			{
				String name = packge.getName().substring(fullPackageName.length());

				if (name.length() > 0)
				{

					if (name.contains("."))
					{
						name = name.substring(0, name.indexOf("."));
					}

					localNames.add(name);
				}
			}
		}

		for (String name : localNames)
		{
			this.nestedNamespaces.add(new WrapperNamespace(project, (fullPackageName.length() > 0 ? fullPackageName + name : name) + ".", name, this, this.model));
		}

		if (fullPackageName.endsWith("."))
		{
			fullPackageName = fullPackageName.substring(0, fullPackageName.length() - 1);
		}

		for (eu.sarunas.atf.meta.sut.Package packge : this.project.getPackages())
		{
			if (packge.getName().equals(fullPackageName))
			{
				for (eu.sarunas.projects.atf.metadata.generic.Type type : packge.getClasses())
				{
					this.ownedTypes.add(this.model.getType(type, this));
				}
			}
		}
	};

	@Override
	public String getName()
	{
		return this.localName;
	};

	@Override
	protected List<Namespace> getNestedNamespaceImpl()
	{
		return this.nestedNamespaces;
	};

	@Override
	public Namespace getNestingNamespace()
	{
		return this.nestingNamespace;
	};

	@Override
	public List<Type> getOwnedType()
	{
		return this.ownedTypes;
	};

	private List<Namespace> nestedNamespaces = new ArrayList<Namespace>();
	private List<Type> ownedTypes = new ArrayList<Type>();
	private WrapperNamespace nestingNamespace;
	private String localName;
	private Project project;
	private ProjectModel model;
};
