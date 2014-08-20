package eu.sarunas.atf.generators.tests.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.xml.datatype.XMLGregorianCalendar;
import com.mysql.jdbc.log.Log;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Modifier;
import eu.sarunas.atf.meta.testdata.TestObject;
import eu.sarunas.atf.meta.testdata.TestObjectComplex;
import eu.sarunas.atf.meta.testdata.TestObjectField;
import eu.sarunas.atf.meta.testdata.TestObjectSimple;
import eu.sarunas.atf.utils.Logger;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class CompositeGenerator extends ITypeGenerator
{
	public CompositeGenerator(Randomizer randomizer)
	{
		super(randomizer);
	};

	public Object generate(Type type)
	{
		Class c = (Class) type;
		
		List<Class> childClassess = c.getPackage().getProject().findChilds(c);
		
		List<Class> classes = new ArrayList<Class>();
		
		for (Class clazz : childClassess)
		{
			if (false == clazz.getModifiers().contains(Modifier.Abstract))
			{
				classes.add(clazz);
			}
		}

		if (classes.size() > 0)
		{
			childClassess = classes;
		}
		
		if (childClassess.size() > 0)
		{
			c = childClassess.get(new Random().nextInt(childClassess.size())); 
		}
		
		
		TestObjectComplex result = null;
		if(c.isInterface() || c.getModifiers().contains(Modifier.Abstract)){
			//TODO FIX
			result =  new TestObjectComplex(null, c);
		}else{
			result =  new TestObjectComplex(null, c);
		}

		generateFieldsValues(c, result, new ArrayList<Class>());
		
		
		/*
		
		
		List<Constructor> constructors = c.getConstructors();

		if (constructors.size() > 0)
		{
			Constructor m = constructors.get(0);

			Object[] args = new Object[m.getParameters().size()];

			for (int i = 0; i < args.length; i++)
			{
				args[i] = this.randomizer.getRandomValue(m.getParameters().get(i).getType());
			}

			try
			{
				// TODO: return m.newInstance(args);
			}
			catch (Exception ex)
			{
				// Logger.log(ex);
			}
		}

		return null;
*/
		return result;
	}

	private void generateFieldsValues(Class c, TestObjectComplex result, List<Class> generatedTypes)
    {
	//	Logger.logger.info("Generating for: "+ c.getFullName());
		
	    for (eu.sarunas.atf.meta.sut.Field field : c.getFields())
        {
	    	Object value = null;
	    	Type type = field.getType();
	    	
	    	if (type instanceof Class)
	    	{
		    	// break the cycle
		    	if (false == generatedTypes.contains((Class)type))
		    	{
		    		generatedTypes.add((Class)field.getType());
		    		value = this.randomizer.getRandomValue(field.getType());
		    	}
	    	}
	    	else
	    	{
	    		value = this.randomizer.getRandomValue(field.getType());
	    	}
	    	
			if (value instanceof TestObject)
			{
				result.addField(new TestObjectField(field, (TestObject)value));
			}
			else
			{
				TestObject v = null;
				
			   	if (value instanceof Double)
		    	{
		    		v = new TestObjectSimple<Double>(null, field.getType(), (Double)value);
		    	}
		    	else if (value instanceof Float)
		    	{
		    		v = new TestObjectSimple<Float>(null, field.getType(), (Float)value);
		    	}
		    	else if (value instanceof Byte)
		    	{
		    		v = new TestObjectSimple<Byte>(null, field.getType(), (Byte)value);
		    	}
		    	else if (value instanceof Long)
		    	{
		    		v = new TestObjectSimple<Long>(null, field.getType(), (Long)value);
		    	}
		    	else if (value instanceof Integer)
		    	{
		    		v = new TestObjectSimple<Integer>(null, field.getType(), (Integer)value);
		    	}
		    	else if (value instanceof Short)
		    	{
		    		v = new TestObjectSimple<Short>(null, field.getType(), (Short)value);
		    	}
		    	else if (value instanceof Character)
		    	{
		    		v = new TestObjectSimple<Character>(null, field.getType(), (Character)value);
		    	}
		    	else if (value instanceof String)
		    	{
		    		v = new TestObjectSimple<String>(null, field.getType(), (String)value);
		    	}
		    	else if (value instanceof Boolean)
		    	{
		    		v = new TestObjectSimple<Boolean>(null, field.getType(), (Boolean)value);
		    	}
		    	else if (value instanceof BigDecimal)
		    	{
		    		v = new TestObjectSimple<BigDecimal>(null, field.getType(), (BigDecimal)value);
		    	}
		    	else if (value instanceof BigInteger)
		    	{
		    		v = new TestObjectSimple<BigInteger>(null, field.getType(), (BigInteger)value);
		    	}
		    	else if (value instanceof Date)
		    	{
		    		v = new TestObjectSimple<Date>(null, field.getType(), (Date)value);
		    	}
		    	else if (value instanceof XMLGregorianCalendar)
		    	{
		    		v = new TestObjectSimple<XMLGregorianCalendar>(null, field.getType(), (XMLGregorianCalendar)value);
		    	}
				
				result.addField(new TestObjectField(field, v));
			}
        }
	    
	    if (null != c.getSuperClass())
	    {
	    	generateFieldsValues((Class) c.getSuperClass(), result, generatedTypes);
	    }
    };
};
