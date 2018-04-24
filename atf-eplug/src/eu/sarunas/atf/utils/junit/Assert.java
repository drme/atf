package eu.sarunas.atf.utils.junit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import eu.sarunas.atf.utils.Logger;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.ExpressionInOclImpl;
import tudresden.ocl20.pivot.essentialocl.standardlibrary.OclBoolean;
import tudresden.ocl20.pivot.interpreter.IInterpretationResult;
import tudresden.ocl20.pivot.interpreter.OclInterpreterPlugin;
import tudresden.ocl20.pivot.metamodels.java.internal.model.JavaModel;
import tudresden.ocl20.pivot.modelinstancetype.java.internal.modelinstance.JavaModelInstance;
import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceElement;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.pivotmodel.Operation;
import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;
import tudresden.ocl20.pivot.standalone.metamodel.JavaMetamodel;
import tudresden.ocl20.pivot.tools.template.exception.TemplateException;

public class Assert
{

	public static void assertPostConditions(Object testObject, Method method, List<Object> input, Object result, String... constraints) throws Throwable
	{
		
		StandaloneFacade.INSTANCE.initialize(null);
		
		OclInterpreterPlugin p = new OclInterpreterPlugin();

		
		JavaModel model = new JavaModel(testObject.getClass(), new JavaMetamodel());
		
		JavaModelInstance modelInstance1 = new JavaModelInstance(model); 
		IModelInstanceElement modelInstanceElement = modelInstance1.addModelInstanceElement(testObject);
		Operation operation = ((JavaModel)modelInstance1.getModel()).getAdapterFactory().createOperation(method);
		List<IModelInstanceElement> parameters = new ArrayList<>();
		
		IModelInstanceElement resultValue = modelInstance1.addModelInstanceElement(result);
		
		for (Object inputParameter : input)
		{
			parameters.add(modelInstance1.addModelInstanceElement(inputParameter));
		}
		
		parameters.add(resultValue);
		
		for (IInterpretationResult validationResult : StandaloneFacade.interpretPostConditions(modelInstance1, modelInstanceElement, operation, parameters, resultValue, getConstraints(model, constraints)))
		{
			Logger.logger.info("  " + validationResult.getModelObject() + " (" + validationResult.getConstraint().getKind() + ": " + validationResult.getConstraint().getSpecification().getBody() + "): " + validationResult.getResult());

			if (validationResult.getResult() instanceof OclBoolean)
			{
				//OclBoolean oclBoolean = (OclBoolean) result.getResult();
				ExpressionInOclImpl expressionInOclImpl = (ExpressionInOclImpl) validationResult.getConstraint().getSpecification();

				//Logger.logger.info("TestDataValidator.validate(): " + result.getConstraint().getSpecification().getBody());
				//Logger.logger.info("TestDataValidator.validate(): " + oclBoolean.getInvalidReason().getMessage());
/* utter rubbish 
				boolean exists = false;

				for (String type : types)
				{
					if (expressionInOclImpl.getContext().getType().getName().equals(type))
					{
						exists = true;
						break;
					}
				}

				if (false == exists)
				{
					continue;
				}
*/
				if (false == ((OclBoolean) validationResult.getResult()).isTrue())
				{
					org.junit.Assert.fail(validationResult.toString());
				}
			}
			else
			{
				org.junit.Assert.fail(validationResult.toString());
			}
		}
	};
	
	private static List<Constraint> getConstraints(JavaModel model, String... fileNames) throws IOException, ParseException, tudresden.ocl20.pivot.parser.ParseException
	{
		List<Constraint> result = new ArrayList<>();

		
		for (String constraintsFile : fileNames)
		{
			result.addAll(StandaloneFacade.INSTANCE.parseOclConstraints(model, readToString(constraintsFile)));
		}		
		
		
		
		return result;
	};
	
	private static String fromStream(InputStream stream)
	{
		BufferedReader reader = null;

		try
		{
			StringBuilder result = new StringBuilder();

			reader = new BufferedReader(new InputStreamReader(stream));

			String line = reader.readLine();

			while (line != null)
			{
				result.append(line);
				result.append("\n");

				line = reader.readLine();
			}

			return result.toString();
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();

			return "";
		}
		finally
		{
			//ProjectManager.close(reader);
			
			if (null != reader)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	};
	
	
	
	private static String readToString(String file)
	{
		InputStream ss = Assert.class.getResourceAsStream(file);

//		if (null == ss)
		{
//			String alt = file.substring(3);
			
	//		System.err.println(alt);
			
		//	ss = Assert.class.getResourceAsStream(alt);
		}
		
			return fromStream(ss);
	};	
};
