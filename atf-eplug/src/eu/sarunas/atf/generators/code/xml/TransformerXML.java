package eu.sarunas.atf.generators.code.xml;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang.NotImplementedException;
import eu.sarunas.atf.generators.code.ITestTransformer;
import eu.sarunas.atf.generators.code.TestObjectVariable;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.body.ObjectConstruct;
import eu.sarunas.atf.meta.testdata.TestObject;
import eu.sarunas.atf.meta.testdata.TestObjectCollection;
import eu.sarunas.atf.meta.testdata.TestObjectComplex;
import eu.sarunas.atf.meta.testdata.TestObjectField;
import eu.sarunas.atf.meta.testdata.TestObjectSimple;
import eu.sarunas.atf.meta.tests.TestSuite;
import eu.sarunas.atf.utils.Logger;

public class TransformerXML implements ITestTransformer
{
	public Class transformTest(TestSuite testSuite)
	{
		return null;
	};

	public List<String> transformTestObject(TestObject testDataToValidate, String packageName)
	{
		List<String> result = new ArrayList<String>();

		if (testDataToValidate instanceof TestObjectCollection)
		{
			transformTestObject(testDataToValidate, packageName, result);
		}
		else
		{
			result.add(transform(testDataToValidate, packageName));
		}

		return result;
	};

	private void transformTestObject(TestObject testDataToValidate, String packageName, List<String> result)
	{
		if (testDataToValidate instanceof TestObjectCollection)
		{
			TestObjectCollection toc = (TestObjectCollection) testDataToValidate;

			for (Object element : toc.getElements())
			{
				if (element instanceof TestObjectCollection)
				{
					transformTestObject((TestObject) element, packageName, result);
				}
				else if (element instanceof TestObject)
				{
					result.add(transform((TestObject) element, packageName));
				}
				else
				{
					Logger.logger.severe("TransformerXML.transformTestObject() - can transform object: " + element.getClass().getName());
				}
			}
		}
		else
		{
			result.add(transform(testDataToValidate, packageName));
		}
	};

	public String transform(TestObject testDataToValidate, String packageName)
	{
		String name = null;

		if (testDataToValidate.getType().getName().indexOf("<") > 0)
		{
			name = testDataToValidate.getType().getName().substring(0, testDataToValidate.getType().getName().indexOf("<"));
		}
		else
		{
			name = testDataToValidate.getType().getName();
		}

		XmlElement xml = new XmlElement("HEAD");
		xml.addParam("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");

		if (testDataToValidate.getType() instanceof eu.sarunas.atf.meta.sut.Class)
		{
			xml.addParam("xmlns=\"urn:" + packageName.replaceAll("[\\._]", "") + "\""); // Hack for namespace
		}
		else
		{
			Logger.logger.severe("TransformerXML.transform(): " + testDataToValidate.getType().getClass().getName());

			return "";
		}

		XmlElement child = new XmlElement(name);
		xml.appendChild(child);

		if (testDataToValidate instanceof TestObjectCollection)
		{
			throw new NotImplementedException(testDataToValidate.getClass().getName());
		}
		else if (testDataToValidate instanceof TestObjectComplex)
		{
			transform(child, testDataToValidate);
		}
		else
		{
			throw new NotImplementedException(testDataToValidate.getClass().getName());
		}

		StringBuilder stringBuilder = new StringBuilder(500);
		stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.toString(stringBuilder, "");

		return stringBuilder.toString();
	};

	private void transform(XmlElement xml, TestObject testObject)
	{
		if (testObject instanceof TestObjectCollection)
		{
			TestObjectCollection toc = (TestObjectCollection) testObject;

			for (Object element : toc.getElements())
			{
				if (element instanceof TestObject)
				{
					transform(xml, (TestObject) element);
				}
				else
				{
					Logger.logger.severe("TransformerXML.transform(): collections:  " + element.getClass().getName());
				}
			}
		}
		else if (testObject instanceof TestObjectComplex)
		{
			TestObjectComplex toc = (TestObjectComplex) testObject;

			for (TestObjectField field : toc.getFields())
			{
				TestObject ccv = field.getValue();

				if (ccv instanceof TestObjectCollection)
				{
					TestObjectCollection col = (TestObjectCollection) ccv;

					if (col.getElements().size() > 0)
					{
						transformCollection(xml, col, field);
					}
				}
				else if (ccv instanceof TestObjectComplex)
				{
					XmlElement xmlChild = new XmlElement(field.getField().getName());
					transform(xmlChild, ccv);
					xml.appendChild(xmlChild);
				}
				else if (ccv instanceof TestObjectSimple)
				{
					String value = toString(((TestObjectSimple<?>) ccv).getValue());
					if (value != null)
					{
						xml.appendChild(new XmlElement(field.getField().getName(), value));
					}
					else
					{
						Logger.logger.severe("TransformerXML.transform(): value is null");
					}
				}
				else
				{
					if (ccv != null)
					{
						throw new NotImplementedException(ccv.getClass());
					}
				}
			}
		}
		else if (testObject instanceof TestObjectSimple)
		{
			Logger.logger.severe("TransformerXML.transform(3)");
		}
		else
		{
			throw new NotImplementedException();
		}
	};

	private void transformCollection(XmlElement xml, TestObjectCollection col, TestObjectField field)
	{
		if (col.getElements().size() > 0)
		{
			for (Object object : col.getElements())
			{
				if (object == null)
				{
					continue;
				}

				if (object instanceof TestObjectCollection)
				{
					XmlElement xmlChild = new XmlElement(field.getField().getName());
					transformCollection(xmlChild, (TestObjectCollection) object, field);
					xml.appendChild(xmlChild);
				}
				else if (object instanceof TestObjectComplex)
				{
					XmlElement xmlChild = new XmlElement(field.getField().getName());
					transform(xmlChild, (TestObject) object);
					xml.appendChild(xmlChild);
				}
				else if (object instanceof TestObject)
				{
					transform(xml, (TestObject) object);
				}
				else if (object instanceof Number)
				{
					XmlElement xmlChild = new XmlElement(field.getField().getName(), toString(object));
					xml.appendChild(xmlChild);
				}
				else if (object.getClass().getCanonicalName().equals("java.lang.Object"))
				{
					XmlElement xmlChild = new XmlElement(field.getField().getName(), toString(object));
					xml.appendChild(xmlChild);
				}
				else
				{
					Logger.logger.severe("TransformerXML.transformCollection():" + object.getClass());
				}
			}
		}
	};

	private static String toString(Object object)
	{
		if (null == object)
		{
			return null;
		}

		if (object instanceof Double)
		{
			return String.format(Locale.US, "%.2f", object);
		}
		else if (object instanceof Float)
		{
			return String.format(Locale.US, "%.2f", object);
		}
		else if (object instanceof Byte)
		{
			return String.valueOf(object);
		}
		else if (object instanceof Long)
		{
			return String.format("%d", object);
		}
		else if (object instanceof Integer)
		{
			return String.format("%d", object);
		}
		else if (object instanceof Short)
		{
			return String.valueOf(object);
		}
		else if (object instanceof Character)
		{
			return String.format("'\\u%04x'", (int) 0 + (int) (char) object);
		}
		else if (object instanceof Boolean)
		{
			return object.toString();
		}
		else if (object instanceof String)
		{
			String result = "";

			for (int i = 0; i < ((String) object).length(); i++)
			{
				char c = ((String) object).charAt(i);

				if ((true == Character.isLetterOrDigit(c)) || (' ' == c))
				{
					result += c;
				}
				else
				{
					// result += String.format("\\u%04x", (int)0 + (int)c);
				}
			}

			return result;
		}
		else if (object instanceof TestObjectCollection)
		{
			return toString((TestObjectCollection) object);
		}
		else if (object instanceof ObjectConstruct)
		{
			return ((ObjectConstruct) object).getObjectName();
		}
		else if (object instanceof TestObjectSimple<?>)
		{
			return toString(((TestObjectSimple<?>) object).getValue());
		}
		else if (object instanceof TestObjectVariable)
		{
			return ((TestObjectVariable) object).getVariable().getObjectName();
		}
		else if (object instanceof BigDecimal)
		{
			return String.format(Locale.US, "%.2f", object);
		}
		else if (object instanceof BigInteger)
		{
			return ((BigInteger) object).toString();
		}
		else if (object instanceof Date)
		{
			Date date = (Date) object;
			return new SimpleDateFormat("yyyy-MM-DD").format(date);
		}
		else if (object instanceof XMLGregorianCalendar)
		{
			XMLGregorianCalendar date = (XMLGregorianCalendar) object;
			return date.toString();
		}
		else if (object.getClass().getCanonicalName().equals("java.lang.Object"))
		{
			return "";
		}

		throw new NotImplementedException("TransformerXML.toString():" + object.getClass() + " value:" + object);
	};

	public List<String> getAllClasses(TestObject testDataToValidate, String packageName)
	{
		List<String> result = new ArrayList<String>();

		if (testDataToValidate instanceof TestObjectCollection)
		{
			getCollectionClasses((TestObjectCollection) testDataToValidate, packageName, result);
		}
		else
		{
			getObjectClasses(testDataToValidate, packageName, result);
		}

		return result;
	};

	private void getCollectionClasses(TestObjectCollection testDataToValidate, String packageName, List<String> classes)
	{
		for (Object element : testDataToValidate.getElements())
		{
			if (element instanceof TestObjectCollection)
			{
				getCollectionClasses((TestObjectCollection) element, packageName, classes);
			}
			if (element instanceof TestObjectComplex)
			{
				getComplexClasses((TestObjectComplex) element, packageName, classes);
			}
			else if (!element.getClass().getCanonicalName().equals("java.lang.Object"))
			{
				Logger.logger.severe("TransformerXML.getCollectionClasses()TODO: " + element.getClass());
				// getObjectClasses((TestObjectComplex) element, packageName, classes);
			}
		}
	};

	private void getComplexClasses(TestObjectComplex testDataToValidate, String packageName, List<String> classes)
	{
		if (!classes.contains(testDataToValidate.getType().getName()))
		{
			classes.add(testDataToValidate.getType().getName());
		}

		for (TestObjectField field : testDataToValidate.getFields())
		{
			TestObject ccv = field.getValue();

			if (ccv instanceof TestObjectCollection)
			{
				getCollectionClasses((TestObjectCollection) ccv, packageName, classes);
			}
			else if (ccv instanceof TestObjectComplex)
			{
				getComplexClasses((TestObjectComplex) ccv, packageName, classes);
			}
			else if (ccv instanceof TestObjectSimple)
			{
				getObjectClasses(((TestObjectSimple<?>) ccv).getValue(), packageName, classes);
			}
			else
			{
				if (ccv != null)
				{
					throw new NotImplementedException(ccv.getClass());
				}
			}
		}
	};

	private void getObjectClasses(Object testDataToValidate, String packageName, List<String> classes)
	{
		if (testDataToValidate == null)
		{
			return;
		}

		if (testDataToValidate instanceof TestObjectCollection)
		{
			getCollectionClasses((TestObjectCollection) testDataToValidate, packageName, classes);
		}
		else if (testDataToValidate instanceof TestObjectComplex)
		{
			getComplexClasses((TestObjectComplex) testDataToValidate, packageName, classes);
		}
		else if (testDataToValidate instanceof Number || testDataToValidate instanceof Boolean || testDataToValidate instanceof String || testDataToValidate instanceof XMLGregorianCalendar)
		{
			// skip
			// -
		}
		else
		{
			Logger.logger.severe("TransformerXML.getObjectClasses() TODO:" + testDataToValidate.getClass());
		}
	};
};
