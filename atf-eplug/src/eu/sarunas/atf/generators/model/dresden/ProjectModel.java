package eu.sarunas.atf.generators.model.dresden;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.sut.basictypes.FloatType;
import eu.sarunas.atf.meta.sut.basictypes.IntegerType;
import eu.sarunas.atf.meta.testdata.TestObject;
import eu.sarunas.atf.meta.testdata.TestObjectCollection;
import tudresden.ocl20.pivot.essentialocl.EssentialOclPlugin;
import tudresden.ocl20.pivot.model.ModelAccessException;
import tudresden.ocl20.pivot.model.base.AbstractModel;
import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceElement;
import tudresden.ocl20.pivot.modelinstancetype.types.base.JavaModelInstanceCollection;
import tudresden.ocl20.pivot.modelinstancetype.types.base.JavaModelInstanceInteger;
import tudresden.ocl20.pivot.modelinstancetype.types.base.JavaModelInstanceReal;
import tudresden.ocl20.pivot.pivotmodel.Namespace;
import tudresden.ocl20.pivot.pivotmodel.Operation;
import tudresden.ocl20.pivot.pivotmodel.PrimitiveTypeKind;
import tudresden.ocl20.pivot.pivotmodel.Type;

public class ProjectModel extends AbstractModel
{
	public ProjectModel(Project project)
	{
		super(project.getName(), new MetaModel());

		this.project = project;
	};

	@Override
	public void dispose()
	{
		this.rootNameSpace = null;
	};

	@Override
	public Namespace getRootNamespace() throws ModelAccessException
	{
		if (null == this.rootNameSpace)
		{
			this.rootNameSpace = new WrapperNamespace(this.project, "", "", null, this);
		}

		return this.rootNameSpace;
	};
	
	Type getType(eu.sarunas.projects.atf.metadata.generic.Type type, WrapperNamespace namespace)
	{
		if (this.adaptedTypes.containsKey(type))
		{
			return this.adaptedTypes.get(type);
		}
		
		if (type instanceof FloatType)
		{
			return new WrapperPrimitiveType(type, PrimitiveTypeKind.REAL, this);
		}
		else if (type instanceof IntegerType)
		{
			return EssentialOclPlugin.getOclLibraryProvider().getOclLibrary().getOclInteger();
					//new WrapperPrimitiveType(type, PrimitiveTypeKind.INTEGER, this);
		}
		else if (type instanceof CollectionType)
		{
			Type result = EssentialOclPlugin.getOclLibraryProvider().getOclLibrary().getCollectionType(getType(((CollectionType)type).getEnclosingType(), namespace));
			
			this.adaptedTypes.put(type, result);
			
			return result;
		}
		else
		{
		
		
		
		return new WrapperType(type, namespace, this);		

		}
		

		
		
		
		/*

		Type result;
		Type elementType;

		elementType = createType(this.field.getType());
/*
		/* Probably adapt type into a collection. *-/
		if (this.dslProperty.isMultivalued()) {

			if (this.dslProperty.isOrdered()) {

				/* OrderedSet. *-/
				if (this.dslProperty.isUnique()) {
					result = EssentialOclPlugin.getOclLibraryProvider()
							.getOclLibrary().getOrderedSetType(elementType);
				}

				/* Sequence. *-/
				else {
				
				}
			}

			else {
				/* Set. *-/
				if (this.dslProperty.isUnique()) {
					result = EssentialOclPlugin.getOclLibraryProvider()
							.getOclLibrary().getSetType(elementType);
				}

				/* Bag. *-/
				else {
					result = EssentialOclPlugin.getOclLibraryProvider()
							.getOclLibrary().getBagType(elementType);
				}
			}
		}

		else *-/{
			result = elementType;
		}

		return result;
		
		*/		
		
		
		
//		return null;
		
		/*
		
		/* Check if the given type is the void type. *-/
		else if (aClass.equals(void.class)) {
			result = EssentialOclPlugin.getOclLibraryProvider().getOclLibrary()
					.getOclVoid();
		}

		/* Else create the result. *-/
		else {

			/* Check if the class is a primitive type. *-/
			if (!JavaPrimitiveType.getPrimitiveTypeKind(aClass).equals(
					PrimitiveTypeKind.UNKNOWN)) {
				result = new JavaPrimitiveType(aClass, this);
			}

			/* Else check if the class is an Enumeration. *-/
			else if (aClass.isEnum()) {
				result = new JavaEnumeration(aClass, this);
			}

			/* Else create a regular type. *-/
			else {
				result = new JavaClass(aClass, this);
			}

			/*
			 * Navigate to the root name space to register this packages
			 * transitively with the root name space (eventually unknown sub
			 * name spaces are adapted).
			 *-/
			Namespace aNamespace;

			aNamespace = result.getNamespace();

			while (aNamespace != null) {
				aNamespace = aNamespace.getNestingNamespace();
			}

			/* Store the result. *-/
			adaptedTypes.put(aClass, result);
		}

		/* Eventually log the exit of this method. *-/
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("createType() - exit"); //$NON-NLS-1$
		}
		// no else.

		return result;		
		
		*/
	};

	public IModelInstanceElement getInstance(Object testInstance, eu.sarunas.projects.atf.metadata.generic.Type type)
	{
		if ((testInstance instanceof Float) || (testInstance instanceof Double))
		{
			return new JavaModelInstanceReal((Number)testInstance) { };
		}
		else if (testInstance instanceof Long)
		{
			return new JavaModelInstanceInteger((Long)testInstance) { };
		}
		else if (testInstance instanceof Integer)
		{
			return new JavaModelInstanceInteger(new Long((Integer)testInstance)) { };
		}
		else if (testInstance instanceof Short)
		{
			return new JavaModelInstanceInteger(new Long((Short)testInstance)) { };
		}
		else if (testInstance instanceof TestObjectCollection)
		{
			TestObjectCollection collection = (TestObjectCollection)testInstance;
			CollectionType collectionType = (CollectionType)type;
			
			List<IModelInstanceElement> elements = new ArrayList<>();
			
			for (Object element : collection.getElements())
			{
				elements.add(getInstance(element, collectionType.getEnclosingType()));
			}
			
			return new JavaModelInstanceCollection(elements, getType(collectionType.getEnclosingType(), (WrapperNamespace)null))
					{
					};
		}
		
		
		return null;
	};	
	
	public Operation getOperation(eu.sarunas.atf.meta.sut.Method source)
	{
		if (this.adaptedMethods.containsKey(source))
		{
			return this.adaptedMethods.get(source);
		}

		return null;
	};	
	
	Type addType(eu.sarunas.projects.atf.metadata.generic.Type source, Type target)
	{
		this.adaptedTypes.put(source, target);
		
		return target;
	};

	Operation addMethod(eu.sarunas.atf.meta.sut.Method source, WrapperOperation target)
	{
		this.adaptedMethods.put(source, target);
		
		return target;
	};

	private Map<eu.sarunas.atf.meta.sut.Method, WrapperOperation> adaptedMethods = new HashMap<>();
	private Map<eu.sarunas.projects.atf.metadata.generic.Type, Type> adaptedTypes = new HashMap<>();
	private Project project;
	private WrapperNamespace rootNameSpace = null;
};
