package eu.sarunas.atf.generators.model.dresden;

import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.sut.Modifier;
import tudresden.ocl20.pivot.pivotmodel.Type;
import tudresden.ocl20.pivot.pivotmodel.base.AbstractProperty;

class WrapperProperty extends AbstractProperty
{
	public WrapperProperty(Field field, Type parentType, ProjectModel model)
	{
		this.field = field;
		this.parentType = parentType;
		this.model = model;
	};

	@Override
	public String getName()
	{
		return this.field.getName();
	};

	@Override
	public Type getOwningType()
	{
		return this.parentType;
	};

	@Override
	public Type getType()
	{
		return this.model.getType(this.field.getType(), null);
	};

	
	/*
	
	
	public Type createType(eu.sarunas.projects.atf.metadata.generic.Type dslType) {

		Type result;

		result = null;

		/* Check if the given type is null. *-/
		if (dslType == null) {
			result = EssentialOclPlugin.getOclLibraryProvider().getOclLibrary()
					.getOclVoid();
		}

		/* Else if the given type is a class, adapt to Type. *-/
		else if (dslType instanceof eu.sarunas.atf.meta.sut.Class) {
			result = createType((eu.sarunas.atf.meta.sut.Class) dslType);
		}

		/* If the given type is a primitive Type. *-/
		else if (false == dslType.isReferenceType()) {

			/* Check if the type can be adapted to a primitive type. *-/
			if (!PrimitiveType2.getKind(dslType).equals(
					PrimitiveTypeKind.UNKNOWN)) {
				result = createPrimitiveType( dslType);
			}

			/* Else adapt to Type. *-/
			else {
	//			result = createTypePrimitiveType((org.eclipse.uml2.uml.PrimitiveType) dslType);
			}
		}

	//	else if (dslType instanceof org.eclipse.uml2.uml.Enumeration) {
	//		result = createEnumeration((org.eclipse.uml2.uml.Enumeration) dslType);
	//	}

	//	else if (dslType instanceof Interface) {
//			result = createType((Interface) dslType);
//		}

		/* Else if the given type is a datatype, adapt to Type. */
//		else if (dslType instanceof DataType) {
	//		result = createType((DataType) dslType);
//		}

		/* Check if aType is an association class. *-/
		/*
		 * FIXME Claas: Discussion with Micha: should we support association
		 * classes?
		 *-/
	//	else if (dslType instanceof AssociationClass) {

	//		AssociationClass anAssociationClass;
//			List<org.eclipse.uml2.uml.Property> allEnds;

			/* Cast to AssociationClass. *-/
//			anAssociationClass = (AssociationClass) dslType;

			/* Get all association ends. *-/
//			allEnds = anAssociationClass.getOwnedEnds();

			/* Add all other ends to each navigable end. *-/
//			this.addNavigableAssociationEnds(allEnds);
//		}

		/* Else check if aType is another kind of association. *-/
//		else if (dslType instanceof Association) {

	//		Association anAssociation;

	//		List<org.eclipse.uml2.uml.Property> allEnds;

			/* Cast to association. *-/
	//		anAssociation = (Association) dslType;

			/* Get all association ends. *-/
	//		allEnds = anAssociation.getOwnedEnds();

			/* Add all other ends to each navigable end. *-/
	//		this.addNavigableAssociationEnds(allEnds);
	//	}
		// no else.

		else {
			/* Should not happen. *-/
			throw new IllegalArgumentException("Unknown Type: " + dslType);
		}

		return result;
	}
	
	

	private Type createType(org.eclipse.uml2.uml.Class dslClass) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("createType(dslClass=" + dslClass + ") - enter"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (dslClass == null) {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("createType() - exit: dslClass is null");
			return null;
		}

		Type type = (Type) adapters.get(dslClass);

		if (type == null) {
			type = new UML2Class(dslClass, this);
			adapters.put(dslClass, type);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("createType() - exit - return value=" + type); //$NON-NLS-1$
		}

		return type;
	}

	*/
	
	/*
	
	private Type createType(eu.sarunas.atf.meta.sut.Class dslClass) {


		if (dslClass == null) {
			return null;
		}

		Type type = (Type) adapters.get(dslClass);

		if (type == null) {
			type = new WrapperType(dslClass/*, this*-/, null);
			adapters.put(dslClass, type);
		}


		return type;
	}
	
	
	
	private Type createTypePrimitiveType(
			eu.sarunas.projects.atf.metadata.generic.Type dslPrimitiveType) {

		
		

		Type result;

		/* Probably get a cached result. *-/
		result =  adapters.get(dslPrimitiveType);

		/* If the type has not been adapted before, create a new adaptation. *-/
		if (result == null) {
			result = new PrimitiveType1(dslPrimitiveType, this.parentType.getNamespace()/*, this*-/);

			/* Cache the result. *-/
			adapters.put(dslPrimitiveType, result);
		}
		// no else.



		return result;
	}
	
	
	private PrimitiveType createPrimitiveType(
			eu.sarunas.projects.atf.metadata.generic.Type dslPrimitiveType) {


		if (dslPrimitiveType == null) {
			return null;
		}

		PrimitiveType primitiveType = (PrimitiveType) adapters
				.get(dslPrimitiveType);

		if (primitiveType == null) {
			primitiveType = new PrimitiveType2(dslPrimitiveType, this.parentType.getNamespace() /*, this*-/);
			adapters.put(dslPrimitiveType, primitiveType);
		}



		return primitiveType;
	}

	
	
	*/
	
	@Override
	public boolean isStatic()
	{
		return this.field.getModifiers().contains(Modifier.Static);
	};
	
	public Field field;
	private Type parentType;
	private ProjectModel model;
};
