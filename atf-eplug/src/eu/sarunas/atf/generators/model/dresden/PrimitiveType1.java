package eu.sarunas.atf.generators.model.dresden;

import java.util.ArrayList;
import java.util.List;
//import tudresden.ocl20.pivot.metamodels.uml2.internal.model.UML2AdapterFactory;
import tudresden.ocl20.pivot.pivotmodel.Namespace;
import tudresden.ocl20.pivot.pivotmodel.Operation;
import tudresden.ocl20.pivot.pivotmodel.Property;
import tudresden.ocl20.pivot.pivotmodel.Type;
import tudresden.ocl20.pivot.pivotmodel.base.AbstractType;

public class PrimitiveType1 extends AbstractType implements Type
{

	/** The adapted {@link org.eclipse.uml2.uml.PrimitiveType} class. */
	private eu.sarunas.projects.atf.metadata.generic.Type dslPrimitiveType;

	/**
	 * <p> The {@link UML2AdapterFactory} used to create nested elements. </p>
	 * 
	 * @generate NOT
	 */
	// private UML2AdapterFactory factory;

	/**
	 * <p> Creates a new <code>UML2Class</code> instance. </p>
	 * 
	 * @param dslPrimitiveType the {@link org.eclipse.uml2.uml.PrimitiveType} that is adopted by this class
	 * @param factory The {@link UML2AdapterFactory} used to create nested elements.
	 * @generated NOT
	 */
	public PrimitiveType1(eu.sarunas.projects.atf.metadata.generic.Type dslPrimitiveType, Namespace namespace /*
																																												 * , UML2AdapterFactory factory
																																												 */)
	{

		// no else.

		/* Initialize the adapted object. */
		this.dslPrimitiveType = dslPrimitiveType;
		// this.factory = factory;
this.namespace = namespace;
	}

	/**
	 * @see org.dresdenocl.pivotmodel.base.AbstractType#getName()
	 * @generated NOT
	 */
	@Override
	public String getName()
	{

		return this.dslPrimitiveType.getName();
	}

	/**
	 * @see org.dresdenocl.pivotmodel.base.AbstractType#getNamespace()
	 * @generated NOT
	 */
	@Override
	public Namespace getNamespace()
	{

		return this.namespace;// this.factory.createNamespace(dslPrimitiveType.getPackage());
	}

	/**
	 * @see org.dresdenocl.pivotmodel.base.AbstractType#getOwnedPropertyImpl()
	 * @generated NOT
	 */
	@Override
	protected List<Property> getOwnedPropertyImpl()
	{

		List<Property> result;

		result = new ArrayList<Property>();

		// for (org.eclipse.uml2.uml.Property property : this.dslPrimitiveType
		// .getOwnedAttributes()) {

		// result.add(this.factory.createProperty(property));
		// }

		return result;
	}

	/**
	 * @see org.dresdenocl.pivotmodel.base.AbstractType#getOwnedOperationImpl()
	 * @generated NOT
	 */
	@Override
	protected List<Operation> getOwnedOperationImpl()
	{

		List<Operation> result;

		result = new ArrayList<Operation>();

		// for (org.eclipse.uml2.uml.Operation operation : this.dslPrimitiveType
		// .getOwnedOperations()) {
		//
		// result.add(this.factory.createOperation(operation));
		// }

		return result;
	}

	/**
	 * @see org.dresdenocl.pivotmodel.base.AbstractType#getSuperTypeImpl()
	 * @generated NOT
	 */
	@Override
	protected List<Type> getSuperTypeImpl()
	{

		List<Type> result;

		result = new ArrayList<Type>();

		/* Primitive types in UML do not have super types. */

		return result;
	}


	Namespace namespace;
	}
