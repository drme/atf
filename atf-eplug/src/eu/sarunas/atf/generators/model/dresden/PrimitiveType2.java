package eu.sarunas.atf.generators.model.dresden;

import java.math.BigDecimal;
import java.math.BigInteger;
import tudresden.ocl20.pivot.pivotmodel.Namespace;
import tudresden.ocl20.pivot.pivotmodel.PrimitiveType;
import tudresden.ocl20.pivot.pivotmodel.PrimitiveTypeKind;
import tudresden.ocl20.pivot.pivotmodel.base.AbstractPrimitiveType;

public class PrimitiveType2 extends AbstractPrimitiveType implements PrimitiveType
{
	private static String booleanKindNames[] = new String[] { "Boolean", boolean.class.getCanonicalName(), Boolean.class.getCanonicalName(), "EBoolean", "EBooleanObject" };
	private static String integerKindNames[] = new String[] { "Integer", "UnlimitedNatural", byte.class.getCanonicalName(), Byte.class.getCanonicalName(), short.class.getCanonicalName(), Short.class.getCanonicalName(), int.class.getCanonicalName(), Integer.class.getCanonicalName(), long.class.getCanonicalName(), Long.class.getCanonicalName(), BigInteger.class.getCanonicalName(), BigDecimal.class.getCanonicalName(), "EByte", "EByteObject", "EShort", "EShortObject", "EInt", "EIntegerObject", "ELong", "ELongObject", "EBigInteger", "EBigDecimal" };
	private static String realKindNames[] = new String[] { float.class.getCanonicalName(), Float.class.getCanonicalName(), double.class.getCanonicalName(), Double.class.getCanonicalName(), "EFloat", "EFloatObject", "EDouble", "EDoubleObject" };
	private static String stringKindNames[] = new String[] { "String", char.class.getCanonicalName(), Character.class.getCanonicalName(), String.class.getCanonicalName(), "EChar", "ECharacter", "EString" };

	private eu.sarunas.projects.atf.metadata.generic.Type dslPrimitiveType;
	Namespace namespace;

	public PrimitiveType2(eu.sarunas.projects.atf.metadata.generic.Type dslPrimitiveType, Namespace namespace /*
																																																						 * , UML2AdapterFactory factory
																																																						 */)
	{

		// initialize
		this.dslPrimitiveType = dslPrimitiveType;
		// this.factory = factory;

		this.namespace = namespace;
	}

	static PrimitiveTypeKind getKind(eu.sarunas.projects.atf.metadata.generic.Type aUmlType)
	{
		PrimitiveTypeKind result;

		result = null;

		for (String aName : booleanKindNames)
		{
			if (aName.equals(aUmlType.getName()))
			{
				result = PrimitiveTypeKind.BOOLEAN;
				break;
			}
		}

		if (result == null)
		{
			for (String aName : integerKindNames)
			{
				if (aName.equals(aUmlType.getName()))
				{
					result = PrimitiveTypeKind.INTEGER;
					break;
				}
			}
		}

		if (result == null)
		{
			for (String aName : realKindNames)
			{
				if (aName.equals(aUmlType.getName()))
				{
					result = PrimitiveTypeKind.REAL;
					break;
				}
			}
		}

		if (result == null)
		{
			for (String aName : stringKindNames)
			{
				if (aName.equals(aUmlType.getName()))
				{
					result = PrimitiveTypeKind.STRING;
					break;
				}
			}
		}

		if (result == null)
		{
			result = PrimitiveTypeKind.UNKNOWN;
		}

		return result;
	};

	@Override
	public PrimitiveTypeKind getKind()
	{
		PrimitiveTypeKind result;

		result = getKind(this.dslPrimitiveType);

		if (result == PrimitiveTypeKind.UNKNOWN)
		{
			System.out.println("DFGSDFGSDGF");
		}

		return result;
	};

	@Override
	public String getName()
	{
		String result;
		result = this.dslPrimitiveType.getName();

		if (result == null)
		{
			result = this.getKind().getName();
		}

		return result;
	}

	/**
	 * @see org.dresdenocl.pivotmodel.base.AbstractPrimitiveType#getNamespace()
	 * @generated NOT
	 */
	@Override
	public Namespace getNamespace()
	{

		return this.namespace;// this.factory.createNamespace(this.dslPrimitiveType.getPackage());
	}
}
