package eu.sarunas.atf.generators.code.xml;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.NotImplementedException;

import eu.atac.atf.main.XMLElement;
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

public class TransformerXML implements ITestTransformer
{
    public Class transformTest(TestSuite testSuite)
    {
    	return null;
    };

	public List<String> transformTestObject(TestObject testDataToValidate, String packageName)
	{
		List<String> ret = Collections.emptyList();
		if(testDataToValidate instanceof TestObjectCollection)
		{
			ret = new ArrayList<String>();
			transformTestObject(testDataToValidate, packageName,ret);
		}
		else
		{
			ret = new ArrayList<String>(1);
			ret.add(transform(testDataToValidate,packageName));
			
		}
		
		return ret;
	}
	
	private void transformTestObject(TestObject testDataToValidate, String packageName,List<String> xml)
	{
		if(testDataToValidate instanceof TestObjectCollection){
			TestObjectCollection toc = (TestObjectCollection) testDataToValidate;
			for (Object element : toc.getElements())
			{
				if(element instanceof TestObjectCollection)
				{
					transformTestObject((TestObject)element, packageName,xml);
				}
				else if(element instanceof TestObject)
				{
					xml.add(transform((TestObject)element,packageName));
				}
				else
				{	
					System.err.println("TransformerXML.transformTestObject(): " + element.getClass().getName());
				}
			}
		}
		else
		{
			xml.add(transform(testDataToValidate,packageName));
		}
	}
	
	public String transform(TestObject testDataToValidate, String packageName)
	{
		String name;
		if(testDataToValidate.getType().getName().indexOf("<") > 0)
		{
			name = testDataToValidate.getType().getName().substring(0, testDataToValidate.getType().getName().indexOf("<"));
		}
		else
		{
			name = testDataToValidate.getType().getName();
		}
		
		
		XMLElement xml = new XMLElement("HEAD");
		xml.addParam("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		
		if(testDataToValidate.getType() instanceof eu.sarunas.atf.meta.sut.Class)
		{
			xml.addParam("xmlns=\"urn:"+packageName.replaceAll("[\\._]", "") + "\"");	//Hack for namespace
		}
		else
		{
			System.err.println("TransformerXML.transform(): " + testDataToValidate.getType().getClass().getName());
			return "";
		}
		
		XMLElement child = new XMLElement(name);
		xml.addChild(child);
		
		if(testDataToValidate instanceof TestObjectCollection){
			throw new NotImplementedException(testDataToValidate.getClass().getName());
		}
		else if(testDataToValidate instanceof TestObjectComplex)
		{
			transform(child,testDataToValidate);
		}
		else
		{
			throw new NotImplementedException(testDataToValidate.getClass().getName());
		}

		StringBuilder s = new StringBuilder(500);
		s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.toString(s, "");
		
		return s.toString();
	}
	
	private void transform(XMLElement xml,TestObject testObject){
		if(testObject instanceof TestObjectCollection)
		{
			TestObjectCollection toc = (TestObjectCollection) testObject;
			for (Object element : toc.getElements())
			{
				
				if(element instanceof TestObject)
				{
					transform(xml,(TestObject) element);
				}
				else
				{
					System.err.println("TransformerXML.transform(): collections:  " + element.getClass().getName());
				}
			}
		}
		else if(testObject instanceof TestObjectComplex)
		{
			TestObjectComplex toc = (TestObjectComplex) testObject;
			for (TestObjectField field : toc.getFields())
			{
				TestObject ccv = field.getValue();
				if(ccv instanceof TestObjectCollection)
				{
					TestObjectCollection col = (TestObjectCollection) ccv;
					
					if(col.getElements().size() > 0)
					{
						transformCollection(xml, col, field );
					}
				}
				else if(ccv instanceof TestObjectComplex)
				{
					XMLElement xmlChild = new XMLElement(field.getField().getName());
					transform(xmlChild,ccv);
					xml.addChild(xmlChild);
				}
				else if(ccv instanceof TestObjectSimple)
				{
					String value = toString(((TestObjectSimple<?>) ccv).getValue());
					if(value != null)
					{
						xml.addChild(new XMLElement(field.getField().getName(),value));
					}
					else
					{
						System.err.println("TransformerXML.transform(): value is null");
					}
				}
				else
				{
					if(ccv != null)
					{
						throw new NotImplementedException(ccv.getClass());
					}
				}
			}
		}
		else if(testObject instanceof TestObjectSimple)
		{
			System.out.println("TransformerXML.transform(3)");
		}
		else
		{
			throw new NotImplementedException();
		}
	}
	private void transformCollection(XMLElement xml,TestObjectCollection col,TestObjectField field ){
		if(col.getElements().size() > 0)
		{
			for (Object object : col.getElements()) {
				if(object == null){
					continue;
				}
				
				if(object instanceof TestObjectCollection)
				{
					XMLElement xmlChild = new XMLElement(field.getField().getName());
					transformCollection( xmlChild,(TestObjectCollection) object,field );
					xml.addChild(xmlChild);					
				}
				else if(object instanceof TestObjectComplex)
				{
					XMLElement xmlChild = new XMLElement(field.getField().getName());
					transform(xmlChild,(TestObject) object );
					xml.addChild(xmlChild);	
				}
				else if(object instanceof TestObject)
				{
					transform(xml, (TestObject)object);
				}else if(object instanceof Number)
				{
					XMLElement xmlChild = new XMLElement(field.getField().getName(),toString(object));
					xml.addChild(xmlChild);
				}
				else if(object.getClass().getCanonicalName().equals("java.lang.Object"))
				{
					XMLElement xmlChild = new XMLElement(field.getField().getName(),toString(object));
					xml.addChild(xmlChild);
				}
				else
				{
					System.err.println("TransformerXML.transformCollection():" + object.getClass());
				}
			}
		}		
	}
	    
	private static String toString(Object object){
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
    		return String.format("'\\u%04x'", (int)0 + (int)(char)object);
    	}
    	else if (object instanceof Boolean)
    	{
    		return object.toString();
    	}
    	else if (object instanceof String)
    	{
    		String result = "";
    		
    		for (int i = 0; i < ((String)object).length(); i++)
    		{
    			char c = ((String)object).charAt(i);
    			
    			if ((true == Character.isLetterOrDigit(c)) || (' ' == c))
    			{
    				result += c;
    			}
    			else
    			{
    	    		//result += String.format("\\u%04x", (int)0 + (int)c);
    			}
    		}
    		
    		return result;
    	}
    	else if (object instanceof TestObjectCollection)
    	{
    		return toString((TestObjectCollection)object);
    	}
    	else if (object instanceof ObjectConstruct)
    	{
    		return ((ObjectConstruct)object).getObjectName();
    	}
    	else if (object instanceof TestObjectSimple<?>)
    	{
    		return toString(((TestObjectSimple<?>)object).getValue());
    	}
    	else if (object instanceof TestObjectVariable)
    	{
    		return ((TestObjectVariable)object).getVariable().getObjectName();
    	}
    	else if (object instanceof BigDecimal)
    	{
    		return String.format(Locale.US, "%.2f", object);
    	}
    	else if (object instanceof BigInteger)
    	{
    		return ((BigInteger)object).toString();
    	}
    	else if (object instanceof Date)
    	{
    		Date date = (Date)object;
    		return new SimpleDateFormat("yyyy-MM-DD").format(date);
    	}
    	else if (object instanceof XMLGregorianCalendar)
    	{
    		XMLGregorianCalendar date = (XMLGregorianCalendar)object;
    		return date.toString();
    	}
    	else if (object.getClass().getCanonicalName().equals("java.lang.Object"))
    	{
    		return "";
    	}
    	
    	throw new NotImplementedException("TransformerXML.toString():" + object.getClass() + " value:" + object);
	}
	
	
	public List<String> getAllClasses(TestObject testDataToValidate, String packageName)
	{
		List<String> ret = Collections.emptyList();
		if(testDataToValidate instanceof TestObjectCollection)
		{
			ret = new ArrayList<String>();
			getCollectionClasses((TestObjectCollection)testDataToValidate, packageName,ret);
		}
		else
		{
			ret = new ArrayList<String>(1);
			getObjectClasses(testDataToValidate, packageName,ret);
		}
		
		return ret;
	}
	
	private void getCollectionClasses(TestObjectCollection testDataToValidate, String packageName,List<String> classes)
	{
		for (Object element : testDataToValidate.getElements()) {
			if (element instanceof TestObjectCollection) 
			{
				getCollectionClasses((TestObjectCollection) element, packageName, classes);
			}
			if (element instanceof TestObjectComplex) 
			{
				getComplexClasses((TestObjectComplex) element, packageName, classes);
			}
			else if(!element.getClass().getCanonicalName().equals("java.lang.Object"))
			{
				System.err.println("TransformerXML.getCollectionClasses()TODO: " +element.getClass());
				//getObjectClasses((TestObjectComplex) element, packageName, classes);
			}
		}
	}
	
	private void getComplexClasses(TestObjectComplex testDataToValidate, String packageName,List<String> classes)
	{
		
		if(!classes.contains(testDataToValidate.getType().getName()))
		{
			classes.add(testDataToValidate.getType().getName());
		}
		
		for (TestObjectField field : testDataToValidate.getFields())
		{
			TestObject ccv = field.getValue();
			if(ccv instanceof TestObjectCollection)
			{
				getCollectionClasses((TestObjectCollection) ccv, packageName, classes);
			}
			else if(ccv instanceof TestObjectComplex)
			{
				getComplexClasses((TestObjectComplex) ccv, packageName, classes);
			}
			else if(ccv instanceof TestObjectSimple)
			{
				getObjectClasses(((TestObjectSimple<?>) ccv).getValue(), packageName, classes);
			}
			else
			{
				if(ccv != null)
				{
					throw new NotImplementedException(ccv.getClass());
				}
			}
		}

	}
	
	private void getObjectClasses(Object testDataToValidate, String packageName,List<String> classes)
	{
		if(testDataToValidate == null){
			return;
		}
		
		if(testDataToValidate instanceof TestObjectCollection)
		{
			getCollectionClasses((TestObjectCollection) testDataToValidate, packageName,classes);
		}
		else if(testDataToValidate instanceof TestObjectComplex)
		{
			getComplexClasses((TestObjectComplex) testDataToValidate, packageName,classes);
		}
		else if(testDataToValidate instanceof Number || testDataToValidate instanceof Boolean 
				|| testDataToValidate instanceof String || testDataToValidate instanceof XMLGregorianCalendar) //skip
		{
			//-
		}
		else
		{
			System.err.println("TransformerXML.getObjectClasses() TODO:" + testDataToValidate.getClass());
		}
	}
	
	
};
