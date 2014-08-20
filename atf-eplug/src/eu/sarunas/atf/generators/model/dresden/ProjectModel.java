package eu.sarunas.atf.generators.model.dresden;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import tudresden.ocl20.pivot.metamodels.java.JavaMetaModelPlugin;
import tudresden.ocl20.pivot.model.IModel;
import tudresden.ocl20.pivot.model.ModelAccessException;
import tudresden.ocl20.pivot.model.base.AbstractModel;
import tudresden.ocl20.pivot.pivotmodel.Namespace;
import tudresden.ocl20.pivot.pivotmodel.impl.NamespaceImpl;
import eu.sarunas.atf.meta.sut.Project;

public class ProjectModel extends AbstractModel implements IModel
{
	public ProjectModel(Project project)
	{
		super(project.getName(), new MetaModel());

		Logger logger = Logger.getLogger(NamespaceImpl.class);

		logger.setLevel(Level.ALL);
		
		this.project = project;
		this.rootNameSpace = new RootNamespace(this.project, this);
	};

	@Override
	public void dispose()
	{
		this.rootNameSpace = null;
	};

	@Override
	public Namespace getRootNamespace() throws ModelAccessException
	{
		return this.rootNameSpace;
	};

	private Project project = null;
	private RootNamespace rootNameSpace = null;
};
