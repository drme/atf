package eu.sarunas.atf.generators.code.java;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import eu.sarunas.atf.generators.code.ICodeGenerator;
import eu.sarunas.atf.generators.code.TestObjectVariable;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.ParameterizedClass;
import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.sut.body.ArrayConstruct;
import eu.sarunas.atf.meta.sut.body.ArrayElementAssignment;
import eu.sarunas.atf.meta.sut.body.Assert;
import eu.sarunas.atf.meta.sut.body.FieldAsignment;
import eu.sarunas.atf.meta.sut.body.ICodeBodyElement;
import eu.sarunas.atf.meta.sut.body.LineSeperator;
import eu.sarunas.atf.meta.sut.body.MethodCall;
import eu.sarunas.atf.meta.sut.body.MethodCallParameter;
import eu.sarunas.atf.meta.sut.body.ObjectConstruct;
import eu.sarunas.atf.meta.testdata.TestObjectCollection;
import eu.sarunas.atf.meta.testdata.TestObjectEnumValue;
import eu.sarunas.atf.meta.testdata.TestObjectSimple;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class CodeGeneratorJava implements ICodeGenerator
{
	public String generateClass(Class clss)
	{
		String result = "";

		if (clss.getPackage().getName().length() > 0)
		{
			result += "package " + formatPackageName(clss.getPackage()) + ";\n";
		}

		for (String annotation : clss.getAnnotations())
		{
			result += formatAnnotation(annotation, 0);
		}

		result += "public class " + clss.getName() + "\n";
		result += "{\n";

		for (Method method : clss.getMethods())
		{
			result += generateMethod(method);
		}

		result += "};\n";

		return result;
	};

	private String generateMethod(Method method)
	{
		ICodeBodyElement lastElement = null;

		String result = "";

		for (String annotation : method.getAnnotations())
		{
			result += formatAnnotation(annotation, 1);
		}

		result += "	public void " + method.getName() + "() throws Throwable\n";
		result += "	{\n";

		for (ICodeBodyElement codeElement : method.getImplementation())
		{
			if (codeElement instanceof Assert)
			{
				result += formatAssert((Assert) codeElement, 2);
			}
			else if (codeElement instanceof ArrayConstruct)
			{
				result += formatArrayConstruct((ArrayConstruct) codeElement, 2);
			}
			else if (codeElement instanceof ObjectConstruct)
			{
				result += formatObjectConstruct((ObjectConstruct) codeElement, 2);
			}
			else if (codeElement instanceof MethodCall)
			{
				result += formatMethodCall((MethodCall) codeElement, 2);
			}
			else if (codeElement instanceof FieldAsignment)
			{
				result += formatFieldAssignment((FieldAsignment) codeElement, 2);
			}
			else if (codeElement instanceof ArrayElementAssignment)
			{
				result += formatArrayElementAssignment((ArrayElementAssignment) codeElement, 2);
			}
			else if (codeElement instanceof LineSeperator)
			{
				if ((null == lastElement) || (false == (lastElement instanceof LineSeperator)))
				{
					result += "\n";
				}
			}
			else
			{
				result += formatUnhandledCode(codeElement, 2);
			}

			lastElement = codeElement;
		}

		result += "	};\n";
		result += "\n";
		return result;
	};
    
	private String formatAssert(Assert assrt, int tabs)
	{
		return getTabs(tabs) + "junit.framework.Assert.fail();\n";
	};
    
    private String formatObjectConstruct(ObjectConstruct objectConstruct, int tabs)
    {
    	String result = getTabs(tabs);
    	
   		result += formatClassName(objectConstruct.getClassToConstruct()) + " " + objectConstruct.getObjectName() + " = new " + formatClassName(objectConstruct.getClassToConstruct()) + "(" + ");\n";
    	
    	return result;
    };
    
    private String formatArrayConstruct(ArrayConstruct objectConstruct, int tabs)
    {
    	String result = getTabs(tabs);
    	
   		result += formatTypeName(objectConstruct.getTypeToConstruct()) + "[] " + objectConstruct.getObjectName() + " = new " + formatTypeName(objectConstruct.getTypeToConstruct()) + "[" + objectConstruct.getSize() + "];\n";
    	
    	return result;
    };    
    
    private String formatMethodCall(MethodCall methodCall, int tabs)
    {
    	return formatMethodCall( methodCall, tabs,true);
    }
    
    private String formatMethodCall(MethodCall methodCall, int tabs, boolean end)
    {
    	String result = getTabs(tabs);
    	
		if (null != methodCall.getReturnObjectName())
		{
			result += methodCall.getMethod().getReturnType().getFullName() + " " + methodCall.getReturnObjectName() + " = " + methodCall.getObjectName() + "." + methodCall.getMethod().getName() + "(";
		}
		else
		{
			result += methodCall.getObjectName() + "." + methodCall.getMethod().getName() + "(";
		}
		
		if (methodCall.getPrameters().size() > 0)
		{
			result += toString(methodCall.getPrameters().get(0).getParameterValue());
			
			for (int i = 1; i < methodCall.getPrameters().size(); i++)
			{
				result += ", " + toString(methodCall.getPrameters().get(i).getParameterValue());
			}
		}					
		
		if(end){
			result += ");\n"; 	
		}else{
			result += ")";
		}
		
    	
		return result;
    };

	private String formatArrayElementAssignment(ArrayElementAssignment elementAssignment, int tabs)
	{
		return getTabs(tabs) + elementAssignment.getArray().getObjectName() + "[" + elementAssignment.getIndex() + "] = " + toString(elementAssignment.getValue()) + ";\n";
	};
    
    private String formatFieldAssignment(FieldAsignment fieldAsignment, int tabs)
    {
    	String pre = getTabs(tabs) + fieldAsignment.getObject().getObjectName() + ".";
    	
    	eu.sarunas.projects.atf.metadata.generic.Type fieldType = fieldAsignment.getField().getType();

    	// das ist POS
//    	if(fieldType instanceof eu.sarunas.atf.meta.sut.Collection){
//			Method getter = findGetter (fieldAsignment.getField());
//			MethodCall getCall = new MethodCall(getter, fieldAsignment.getObject().getObjectName(), null);
//			return getTabs(tabs) + formatMethodCall(getCall, 0,false) + ".add(" + toString(fieldAsignment.getValue()) +");\n" ;
//    	}else 
    	if (true == fieldAsignment.getField().getModifier().isPublic())
    	{
    		return pre + fieldAsignment.getField().getName() + " = " + toString(fieldAsignment.getValue()) + ";\n";
    	}
    	else
    	{
    		Method setter = findSetter(fieldAsignment.getField());
    		
    		if (null != setter)
    		{
    			MethodCall call = new MethodCall(setter, fieldAsignment.getObject().getObjectName(), null);
    			
    			call.addParameter(new MethodCallParameter("p", fieldAsignment.getValue()));
    			
    			return getTabs(tabs) + formatMethodCall(call, 0);
    		}
    		else
    		{
    			return getTabs(tabs) + "eu.sarunas.junit.TestsHelper.set(" + fieldAsignment.getObject().getObjectName() + ", \"" + fieldAsignment.getField().getName() + "\", " + toString(fieldAsignment.getValue()) + ");\n";
    		}
    	}
    };
    
	private String formatUnhandledCode(ICodeBodyElement codeBodyElement, int tabs)
	{
		return getTabs(tabs) + "// unknown code " + codeBodyElement.getClass().toString();
	};
    
    // TODO make private
    public static String toString(Object object)
    {
    	if (null == object)
    	{
    		return "null";
    	}
    	
    	if (object instanceof Double)
    	{
    		return String.format(Locale.US, "%.2f", object);
    	}
    	else if (object instanceof Float)
    	{
    		return String.format(Locale.US, "%.2f", object) + "f";
    	}
    	else if (object instanceof Byte)
    	{
    		return String.format("(byte)0x%x", object);
    	}
    	else if (object instanceof Long)
    	{
    		return String.format("(long)%d", object);
    	}
    	else if (object instanceof Integer)
    	{
    		return String.format("(int)%d", object);
    	}
    	else if (object instanceof Short)
    	{
    		return String.format("(short)%d", object);
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
    		String result = "\"";
    		
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
    		
    		return result + "\"";
    	}
    	else if (object instanceof TestObjectCollection)
    	{
    		return toString((TestObjectCollection)object);
    	}
    	else if (object instanceof ObjectConstruct)
    	{
    		return ((ObjectConstruct)object).getObjectName();
    	}
    	else if (object instanceof TestObjectEnumValue)
    	{
    		return formatClassName((Class)((TestObjectEnumValue)object).getType()) + "." + ((TestObjectEnumValue)object).getValue().toString();
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
    		return "new java.math.BigDecimal(" + String.format(Locale.US, "%.2f", object) + ")";
    	}
    	else if (object instanceof BigInteger)
    	{
    		return "new java.math.BigInteger(\"" + ((BigInteger)object).toString() + "\")";
    	}
    	else if (object instanceof Date)
    	{
    		Date date = (Date)object;

    		return "new java.util.Date(" + (1900 + date.getYear()) + ", " + date.getMonth() + ", " + date.getDate() + ", " + date.getHours() + ", " + date.getMinutes() + ", " + date.getSeconds() + ")";
    	}
    	else if (object instanceof XMLGregorianCalendar)
    	{
    		XMLGregorianCalendar date = (XMLGregorianCalendar)object;

    		return "javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(new java.util.GregorianCalendar(" + date.getYear() + ", " + date.getMonth() + ", " + date.getDay() + ", " + date.getHour() + ", " + date.getMinute() + ", " + date.getSecond() + "))";
    	}
    	else if (object instanceof Object)
    	{
    		return "new Object()";
    	}
    	
    	return " // err " + object.toString();
    };
    
    private static String toString(TestObjectCollection collection)
    {
    	switch (collection.getStyle())
    	{
    		case Array:
    			{
    				CollectionType type = (CollectionType)collection.getType();
    				
    				String result = "new " + type.getFullName();
    				
    				for (int i = 0; i < type.getDimensions(); i++)
    				{
    					result += "[]";
    				}

    				result += " { ";
    				
    				if (collection.getSize() > 0)
    				{
    					result += toString(collection.getElements().get(0));
    					
    					for (int i = 1; i < collection.getSize(); i++) 
    					{
    		    			result += ", " + toString(collection.getElements().get(i));
    					}
    				}
    				
    				result += " }";
    				
    				return result;    	
    			}
    		default:
    			return "// NotImplemented by layzy ass developers";
    	}
    };
    
    private static String formatTypeName(Type type)
    {
    	if (type instanceof Class)
    	{
    		return formatClassName((Class)type);
    	}
    	else
    	{
    		return type.getFullName();
    	}
    };
    
    
	private static String formatClassName(Class clss)
	{
		String packageName = formatPackageName(clss.getPackage());
		String name = null;

		if (clss instanceof ParameterizedClass)
		{
			ParameterizedClass pc = (ParameterizedClass) clss;

			name = clss.getName() + "<";

			if (pc.getParameters().size() > 0)
			{
				Type p0 = pc.getParameters().get(0);

				if (p0 instanceof Class)
				{
					name += formatClassName((Class) p0);
				}
				else
				{
					name += p0.getFullName();
				}

				for (int i = 1; i < pc.getParameters().size(); i++)
				{
					Type p = pc.getParameters().get(i);

					if (p instanceof Class)
					{
						name += formatClassName((Class) p);
					}
					else
					{
						name += ", " + p.getFullName();
					}
				}
			}

			name += ">";
		}
		else
		{
			name = clss.getName();
		}

		if (packageName.length() > 0)
		{
			return packageName + "." + name;
		}
		else
		{
			return name;
		}
	};

	private static String formatPackageName(eu.sarunas.atf.meta.sut.Package packge)
	{
		return packge.getName();
	};

    private String formatAnnotation(String annotation, int tabs)
    {
    	return getTabs(tabs) + "@" + annotation + "\n";
    };
    
    private String getTabs(int count)
    {
    	String result = "";
    	
    	for (int i = 0; i < count; i++)
    	{
    		result += "\t";
    	}
    	
    	return result;
    };

    private Method findSetter(Field field)
    {
    	String methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    	
    	for (Method method : field.getParent().getMethods())
    	{
    		if ((true == method.getModifier().isPublic()) && (1 == method.getParameters().size()) && (true == methodName.equals(method.getName())))
    		{
    			return method;
    		}
    	}
    	
    	return null;
    };
    
    private Method findGetter(Field field)
    {
    	String methodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    	
    	for (Method method : field.getParent().getMethods())
    	{
    		if ((true == method.getModifier().isPublic()) && (true == methodName.equals(method.getName())))
    		{
    			return method;
    		}
    	}
    	
    	return null;
    };
};
