package eu.sarunas.atf.generators.code.junit;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import eu.sarunas.atf.generators.code.ITestTransformer;
import eu.sarunas.atf.generators.code.InlineCode;
import eu.sarunas.atf.generators.code.TestObjectVariable;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Modifier;
import eu.sarunas.atf.meta.sut.Package;
import eu.sarunas.atf.meta.sut.Parameter;
import eu.sarunas.atf.meta.sut.ParameterizedClass;
import eu.sarunas.atf.meta.sut.basictypes.CollectionStyle;
import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.sut.basictypes.ObjectType;
import eu.sarunas.atf.meta.sut.basictypes.VoidType;
import eu.sarunas.atf.meta.sut.body.ArrayConstruct;
import eu.sarunas.atf.meta.sut.body.ArrayElementAssignment;
import eu.sarunas.atf.meta.sut.body.Assert;
import eu.sarunas.atf.meta.sut.body.FieldAsignment;
import eu.sarunas.atf.meta.sut.body.LineSeperator;
import eu.sarunas.atf.meta.sut.body.MethodCall;
import eu.sarunas.atf.meta.sut.body.MethodCallParameter;
import eu.sarunas.atf.meta.sut.body.ObjectConstruct;
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
	public Class transformTest(TestSuite testSuite, List<String> constraintsFiles)
	{
		Class cl = new Class(testSuite.getName(), EnumSet.of(Modifier.Public), testSuite.getTestCases().get(0).getPackage(), testSuite);

		int id = 0;

		for (TestCase tc : testSuite.getTestCases())
		{
			transformTestCase(tc, cl, ++id, constraintsFiles);
		}

		return cl;
	};

	private String getTestMethodName(Method methodToTest, int id)
	{
		String methodName = methodToTest.getName();
		
		methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		
		return "test" + methodName + id;
	};
	
	private void transformTestCase(TestCase testCase, Class testClass, int id, List<String> constraintsFiles)
	{
		Logger.logger.info("Transforming test to JUnit: " + testCase.toString());

		Method testMethod = new Method(testClass, getTestMethodName(testCase.getMethod(), id), EnumSet.of(Modifier.Public), new VoidType(), null);
		testMethod.getAnnotations().add("org.junit.Test");

		testClass.addMethod(testMethod);

		ObjectConstruct testObject = new ObjectConstruct(new Class(testCase.getClassName(), EnumSet.of(Modifier.Public), testCase.getPackage(), testCase), "testObject");

		testMethod.getImplementation().add(testObject);

		for (TestInput testInput : testCase.getInputs())
		{
			MethodCall call = new MethodCall(testCase.getMethod(), testObject.getObjectName(), testCase.getMethod().getReturnType() instanceof VoidType ? null : "res");

			HashMap<TestInputParameter, ObjectConstruct> preconstructedInputs = new HashMap<TestInputParameter, ObjectConstruct>();

			for (TestInputParameter p : testInput.getInputParameters())
			{
				if (p.getValue() instanceof TestObjectCollection)
				{
					TestObjectCollection collection = (TestObjectCollection) p.getValue();

					switch (collection.getStyle())
					{
						case Array:
							break;
						case List:
							ObjectConstruct construct = new ObjectConstruct(createList(collection), p.getName() + varId++);
							testMethod.getImplementation().add(construct);

							for (Object value : collection.getElements())
							{
								Object v = value;

								if (v instanceof TestObjectComplex)
								{
									v = unwapComplexCreation((TestObjectComplex) v, testMethod);
								}

								MethodCall addCall = new MethodCall(new Method(null, "add", EnumSet.of(Modifier.Public), null, null), construct.getObjectName(), null);

								MethodCallParameter parameter = new MethodCallParameter(null, v);

								addCall.addParameter(parameter);

								testMethod.getImplementation().add(addCall);
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

					testMethod.getImplementation().add(construct);

					TestObjectComplex value = (TestObjectComplex) p.getValue();

					for (TestObjectField field : value.getFields())
					{
						if (field.getValue() instanceof TestObjectCollection)
						{
							TestObjectCollection collection = (TestObjectCollection) field.getValue();

							switch (collection.getStyle())
							{
								case Array:
									ArrayConstruct arrayConstruct = new ArrayConstruct(collection.getType(), field.getField().getName() + varId++, collection.getElements().size());
									testMethod.getImplementation().add(arrayConstruct);

									int index = 0;

									for (Object value1 : collection.getElements())
									{
										Object v = value1;

										if (v instanceof TestObjectComplex)
										{
											v = unwapComplexCreation((TestObjectComplex) v, testMethod);
										}

										testMethod.getImplementation().add(new ArrayElementAssignment(arrayConstruct, index++, v));
									}

									break;
								case List:
									ObjectConstruct c1 = new ObjectConstruct(createList(collection), field.getField().getName() + varId++);
									testMethod.getImplementation().add(c1);

									for (Object value1 : collection.getElements())
									{
										Object v = value1;

										if (v instanceof TestObjectComplex)
										{
											v = unwapComplexCreation((TestObjectComplex) v, testMethod);
										}

										MethodCall addCall = new MethodCall(new Method(null, "add", EnumSet.of(Modifier.Public), null, null), c1.getObjectName(), null);

										MethodCallParameter parameter = new MethodCallParameter(null, v);

										addCall.addParameter(parameter);

										testMethod.getImplementation().add(addCall);
									}

									testMethod.getImplementation().add(new FieldAsignment(construct, field.getField(), new TestObjectVariable(c1.getObjectName(), c1.getClassToConstruct(), c1)));
									break;
								default:
									break;
							}
						}
						else if (field.getValue() instanceof TestObjectComplex)
						{
							testMethod.getImplementation().add(new FieldAsignment(construct, field.getField(), unwapComplexCreation((TestObjectComplex) field.getValue(), testMethod)));
						}
						else
						{
							testMethod.getImplementation().add(new FieldAsignment(construct, field.getField(), field.getValue()));
						}
					}

					preconstructedInputs.put(p, construct);
				}
			}

			for (TestInputParameter p : testInput.getInputParameters())
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

			testMethod.getImplementation().add(new LineSeperator());
			testMethod.getImplementation().add(call);
			
			
			
			

			
			
			
			ObjectConstruct constructPreArguments = new ObjectConstruct(createList(new TestObjectCollection("", new CollectionType(CollectionStyle.Array, new ObjectType()), 0, CollectionStyle.Array)), "preValues" + varId++);
			testMethod.getImplementation().add(constructPreArguments);

			for (TestInputParameter p : testInput.getInputParameters())
			{
				if (preconstructedInputs.containsKey(p))
				{
					ObjectConstruct construct = preconstructedInputs.get(p);

					MethodCall addCall = new MethodCall(new Method(null, "add", EnumSet.of(Modifier.Public), null, null), constructPreArguments.getObjectName(), null);

					MethodCallParameter parameter = new MethodCallParameter(null, construct);

					addCall.addParameter(parameter);

					testMethod.getImplementation().add(addCall);
				}
				else
				{
					MethodCall addCall = new MethodCall(new Method(null, "add", EnumSet.of(Modifier.Public), null, null), constructPreArguments.getObjectName(), null);

					MethodCallParameter parameter = new MethodCallParameter(null, p.getValue());

					addCall.addParameter(parameter);

					testMethod.getImplementation().add(addCall);
				}
			}
			
			
			
			
			
//		testMethod.getImplementation().add(new Assert());
			
			testMethod.getImplementation().add(new LineSeperator());
			
			Class assertClass = new Class("Assert", EnumSet.of(Modifier.Public), new Package(null, "eu.sarunas.atf.utils.junit", null), null);
			
			Method assertMethod = new Method(assertClass, "assertPostConditions", EnumSet.of(Modifier.Public, Modifier.Static), new VoidType(), null);
			
			MethodCall assertCall = new MethodCall(assertMethod, "", null);
			
			String testMethodName = "testObject.getClass().getMethod(\"" + testCase.getMethod().getName() + "\"";

			for (Parameter parameter : testCase.getMethod().getParameters())
			{
				testMethodName += ", " + parameter.getType().getFullName() + ".class";
			}
			
			testMethodName += ")";
			
			assertCall.addParameter(new MethodCallParameter("testObject", testObject));
			assertCall.addParameter(new MethodCallParameter("method", new InlineCode(testMethodName)));
			assertCall.addParameter(new MethodCallParameter("inputParameters", constructPreArguments));
			assertCall.addParameter(new MethodCallParameter("result", new TestObjectVariable("res", null, new ObjectConstruct(null, "res"))));
			
			for (String constraintsFile : constraintsFiles)
			{
				assertCall.addParameter(new MethodCallParameter(null, new InlineCode("\"" + constraintsFile + "\"")));
			}
			
			
			
			testMethod.getImplementation().add(assertCall);			
				
			
			
		}

		
		
				

	};
	
	private TestObjectVariable unwapComplexCreation(TestObjectComplex object, Method methodCode)
	{
		ObjectConstruct construct = new ObjectConstruct((Class) object.getType(), generateObjectName(object.getType()));

		methodCode.getImplementation().add(construct);

		for (TestObjectField field : object.getFields())
		{
			if (field.getValue() instanceof TestObjectCollection)
			{
				TestObjectCollection collection = (TestObjectCollection) field.getValue();

				switch (collection.getStyle())
				{
					case Array:
						break;
					case List:
						ObjectConstruct c1 = new ObjectConstruct(createList(collection), field.getField().getName() + varId++);
						methodCode.getImplementation().add(c1);

						for (Object value : collection.getElements())
						{
							Object v = value;

							if (v instanceof TestObjectComplex)
							{
								v = unwapComplexCreation((TestObjectComplex) v, methodCode);
							}

							MethodCall addCall = new MethodCall(new Method(null, "add", EnumSet.of(Modifier.Public), null, null), c1.getObjectName(), null);

							MethodCallParameter parameter = new MethodCallParameter(null, v);

							addCall.addParameter(parameter);

							methodCode.getImplementation().add(addCall);
						}

						methodCode.getImplementation().add(new FieldAsignment(construct, field.getField(), new TestObjectVariable(c1.getObjectName(), c1.getClassToConstruct(), c1)));

						break;
					default:
						break;
				}
			}
			else if (field.getValue() instanceof TestObjectComplex)
			{
				methodCode.getImplementation().add(new FieldAsignment(construct, field.getField(), unwapComplexCreation((TestObjectComplex) field.getValue(), methodCode)));
			}
			else
			{
				methodCode.getImplementation().add(new FieldAsignment(construct, field.getField(), field.getValue()));
			}
		}

		methodCode.getImplementation().add(new LineSeperator());

		return new TestObjectVariable(construct.getObjectName(), construct.getClassToConstruct(), construct);
	};

	private String generateObjectName(Type type)
	{
		String name = type.getName() + (varId++);

		return name.substring(0, 1).toLowerCase() + name.substring(1);
	};

	private ParameterizedClass createList(TestObjectCollection elementType)
	{
		ParameterizedClass cls = new ParameterizedClass(arrayListType, null, EnumSet.of(Modifier.Public), arrayListType.getPackage(), null);

		cls.addParameter(((CollectionType) elementType.getType()).getEnclosingType());

		return cls;
	};
	
	private int varId = 0;
	private static final Class arrayListType = new Class("ArrayList", EnumSet.of(Modifier.Public), new eu.sarunas.atf.meta.sut.Package(null, "java.util", null), null);
};
