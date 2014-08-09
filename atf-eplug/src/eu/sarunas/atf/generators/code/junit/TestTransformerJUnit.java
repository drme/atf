package eu.sarunas.atf.generators.code.junit;

import java.util.HashMap;
import eu.sarunas.atf.generators.code.ITestTransformer;
import eu.sarunas.atf.generators.code.TestObjectVariable;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Modifier;
import eu.sarunas.atf.meta.sut.ParameterizedClass;
import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.sut.basictypes.VoidType;
import eu.sarunas.atf.meta.sut.body.Assert;
import eu.sarunas.atf.meta.sut.body.FieldAsignment;
import eu.sarunas.atf.meta.sut.body.MethodCall;
import eu.sarunas.atf.meta.sut.body.MethodCallParameter;
import eu.sarunas.atf.meta.sut.body.ObjectConstruct;
import eu.sarunas.atf.meta.testdata.TestObject;
import eu.sarunas.atf.meta.testdata.TestObjectCollection;
import eu.sarunas.atf.meta.testdata.TestObjectComplex;
import eu.sarunas.atf.meta.testdata.TestObjectField;
import eu.sarunas.atf.meta.tests.TestCase;
import eu.sarunas.atf.meta.tests.TestInput;
import eu.sarunas.atf.meta.tests.TestInputParameter;
import eu.sarunas.atf.meta.tests.TestSuite;
import eu.sarunas.atf.utils.Logger;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class TestTransformerJUnit implements ITestTransformer
{
	public Class transformTest(TestSuite testSuite)
	{
		Class cl = new Class("Test" + testSuite.getName(), Modifier.PUBLIC, testSuite.getTestCases().get(0).getPackage(), testSuite);

		int id = 0;

		for (TestCase tc : testSuite.getTestCases())
		{
			transformTestCase(tc, cl, ++id);
		}

		return cl;
	};

	private void transformTestCase(TestCase testCase, Class cl, int id)
	{
		Logger.logger.info("Transforming test to JUnit: " + testCase.toString());
		
		Method m = new Method(cl, "test" + testCase.getMethod().getName() + id, Modifier.Public, new VoidType(), null);
		m.getAnnotations().add("org.junit.Test");

		cl.addMethod(m);

		ObjectConstruct testObject = new ObjectConstruct(new Class(testCase.getClassName(), Modifier.PUBLIC, testCase.getPackage(), testCase), "testObject");
		
		m.getImplementation().add(testObject);

		for (TestInput i : testCase.getInputs())
		{
			MethodCall call = new MethodCall(testCase.getMethod(), testObject.getObjectName(), testCase.getMethod().getReturnType() instanceof VoidType ? null : "res");

			HashMap<TestInputParameter, ObjectConstruct> preconstructedInputs = new HashMap<TestInputParameter, ObjectConstruct>();

			for (TestInputParameter p : i.getInputParameters())
			{
				if (p.getValue() instanceof TestObjectCollection)
				{
					TestObjectCollection collection = (TestObjectCollection)p.getValue();
					
					switch (collection.getStyle())
					{
						case Array:
							break;
						case List:
							ObjectConstruct construct = new ObjectConstruct(createList(collection), p.getName() + varId++);
							m.getImplementation().add(construct);
							
							for (Object value : collection.getElements())
							{
								Object v = value;
								
								if (v instanceof TestObjectComplex)
								{
									v = unwapComplexCreation((TestObjectComplex) v, m);
								}								
								
								MethodCall addCall = new MethodCall(new Method(null, "add", Modifier.Public, null, null), construct.getObjectName(), null);

								MethodCallParameter parameter = new MethodCallParameter(null, v);
								
								addCall.addParameter(parameter);
								
								m.getImplementation().add(addCall);
							}
							
							preconstructedInputs.put(p, construct);
							break;
						default:
							break;
					}
				}
				else if (p.getValue() instanceof TestObjectComplex)
				{
					ObjectConstruct construct = new ObjectConstruct((Class) p.getParameter().getType(), generateObjectName(p.getParameter().getType()));

					m.getImplementation().add(construct);

					TestObjectComplex value = (TestObjectComplex) p.getValue();

					for (TestObjectField field : value.getFields())
					{
						
						if (field.getValue() instanceof TestObjectCollection)
						{
							
							
							TestObjectCollection collection = (TestObjectCollection)field.getValue();
							
							switch (collection.getStyle())
							{
								case Array:
									break;
								case List:
									ObjectConstruct c1 = new ObjectConstruct(createList(collection), field.getField().getName() + varId++);
									m.getImplementation().add(c1);
									
									for (Object value1 : collection.getElements())
									{
										Object v = value1;
										
										if (v instanceof TestObjectComplex)
										{
											v = unwapComplexCreation((TestObjectComplex) v, m);
										}
										
										MethodCall addCall = new MethodCall(new Method(null, "add", Modifier.Public, null, null), c1.getObjectName(), null);

										MethodCallParameter parameter = new MethodCallParameter(null, v);
										
										addCall.addParameter(parameter);
										
										m.getImplementation().add(addCall);
									}
									
									m.getImplementation().add(new FieldAsignment(construct, field.getField(), new TestObjectVariable(c1.getObjectName(), c1.getClassToConstruct(), c1)));
									
									break;
								default:
									break;
							}				
							
							
							
						}						
						
						
						else if (field.getValue() instanceof TestObjectComplex)
						{
							m.getImplementation().add(new FieldAsignment(construct, field.getField(), unwapComplexCreation((TestObjectComplex)field.getValue(), m)));
						}
						else
						{
							m.getImplementation().add(new FieldAsignment(construct, field.getField(), field.getValue()));
						}
					}

					preconstructedInputs.put(p, construct);
				}
			}

			for (TestInputParameter p : i.getInputParameters())
			{
				if (preconstructedInputs.containsKey(p))
				{
					ObjectConstruct construct = preconstructedInputs.get(p);

					call.addParameter(new MethodCallParameter(p.getName(), construct));
				}
				else
				{
					call.addParameter(new MethodCallParameter(p.getName(), p.getValue()));
				}
			}

			m.getImplementation().add(call);
		}

		m.getImplementation().add(new Assert());
	};
	
	private TestObjectVariable unwapComplexCreation(TestObjectComplex object, Method m)
	{
		ObjectConstruct construct = new ObjectConstruct((Class)object.getType(), generateObjectName(object.getType()));
		
		m.getImplementation().add(construct);

		for (TestObjectField field : object.getFields())
		{
			if (field.getValue() instanceof TestObjectCollection)
			{
				
				
				TestObjectCollection collection = (TestObjectCollection)field.getValue();
				
				switch (collection.getStyle())
				{
					case Array:
						break;
					case List:
						ObjectConstruct c1 = new ObjectConstruct(createList(collection), field.getField().getName() + varId++);
						m.getImplementation().add(c1);
						
						for (Object value : collection.getElements())
						{
							Object v = value;
							
							if (v instanceof TestObjectComplex)
							{
								v = unwapComplexCreation((TestObjectComplex) v, m);
							}							
							
							
							MethodCall addCall = new MethodCall(new Method(null, "add", Modifier.Public, null, null), c1.getObjectName(), null);

							MethodCallParameter parameter = new MethodCallParameter(null, v);
							
							addCall.addParameter(parameter);
							
							m.getImplementation().add(addCall);
						}
						
						m.getImplementation().add(new FieldAsignment(construct, field.getField(), new TestObjectVariable(c1.getObjectName(), c1.getClassToConstruct(), c1)));
						
						break;
					default:
						break;
				}				
				
				
				
			}
			else if (field.getValue() instanceof TestObjectComplex)
			{
				m.getImplementation().add(new FieldAsignment(construct, field.getField(), unwapComplexCreation((TestObjectComplex)field.getValue(), m)));
			}
			else
			{
				m.getImplementation().add(new FieldAsignment(construct, field.getField(), field.getValue()));
			}
		}
		
		return new TestObjectVariable(construct.getObjectName(), construct.getClassToConstruct(), construct);
	};

	private String generateObjectName(Type type)
	{
		String name = type.getName() + (varId++);
		
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	};
	
	private ParameterizedClass createList(TestObjectCollection elementType)
	{
		ParameterizedClass cls = new ParameterizedClass(arrayListType, null, 0, arrayListType.getPackage(), null);
		
		cls.addParameter(((CollectionType)elementType.getType()).getEnclosingType());
		
		return cls;
	};
	
	private int varId = 0;
	private static final Class arrayListType = new Class("ArrayList", 0, new eu.sarunas.atf.meta.sut.Package(null, "java.util", null), null);
};
