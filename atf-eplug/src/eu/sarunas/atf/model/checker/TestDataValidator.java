package eu.sarunas.atf.model.checker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import antlr.SemanticException;
import eu.sarunas.atf.generators.code.xml.TransformerXML;
import eu.sarunas.atf.generators.code.xsd.TransformerXSD;
import eu.sarunas.atf.generators.model.dresden.ProjectModel;
import eu.sarunas.atf.generators.model.dresden.WrapperModelInstance;
import eu.sarunas.atf.generators.model.dresden.WrapperModelInstanceObject;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Package;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.testdata.TestObject;
import eu.sarunas.atf.meta.testdata.TestObjectCollection;
import eu.sarunas.atf.meta.testdata.TestObjectComplex;
import eu.sarunas.atf.meta.tests.TestInput;
import eu.sarunas.atf.meta.tests.TestInputParameter;
import eu.sarunas.atf.utils.Logger;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.ExpressionInOclImpl;
import tudresden.ocl20.pivot.essentialocl.standardlibrary.OclBoolean;
import tudresden.ocl20.pivot.interpreter.IInterpretationResult;
import tudresden.ocl20.pivot.interpreter.OclInterpreterPlugin;
import tudresden.ocl20.pivot.modelinstance.IModelInstance;
import tudresden.ocl20.pivot.modelinstancetype.types.IModelInstanceElement;
import tudresden.ocl20.pivot.modelinstancetype.types.base.JavaModelInstanceReal;
import tudresden.ocl20.pivot.parser.ParseException;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.pivotmodel.Operation;
import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;
import tudresden.ocl20.pivot.tools.template.exception.TemplateException;

/**
 * Validates if test data matches model and its' constraints.
 */
public class TestDataValidator implements ITestDataValidator
{
	public TestDataValidator(Project model, List<String> constraintsList) throws TemplateException, IOException, ParseException
	{
		this.model = model;
		
		StandaloneFacade.INSTANCE.initialize(null);
		
		this.projectModel = new ProjectModel(this.model);		
		
		for (String constraints : constraintsList)
		{
			try
			{
			this.constraints.addAll(StandaloneFacade.INSTANCE.parseOclConstraints(this.projectModel, constraints));
			}
			catch (tudresden.ocl20.pivot.parser.SemanticException ex)
			{
				ex.printStackTrace();;
			}
		}
	};
	
	@Override
	public TestDataValidationResult validate(TestObject testDataToValidate) throws Exception
	{
		return validate(this.model, new ArrayList<String>(), testDataToValidate);
	};
	
	@Override
	public TestDataValidationResult validate(Project model, String contraints, TestObject testDataToValidate) throws Exception
	{
		List<String> list = new ArrayList<String>();
		list.add(contraints);
		
		return validate(model, list, testDataToValidate);
	};
	
	@Override
	public TestDataValidationResult validate(Project model, List<String> contraints, TestObject testDataToValidate) throws Exception
	{
		TestDataValidationResult result = validateModel(model, testDataToValidate);

		if (true == result.isValid())
		{
			return validateConstraints(model, testDataToValidate);
		}
		else
		{
			return result;
		}
	};

	/**
	 * Validates if testDataToValidate objects matches model. The validation object is returned that states if data are valid or not. If data are not valid, validation object contains information about failed validation. For example: Invalid model type, etc.
	 * 
	 * @param model model to validate against to.
	 * @param contraints model constraints to validate against to.
	 * @param testDataToValidate test data to validate.
	 * @return validation result object.
	 */
	private TestDataValidationResult validateModel(Project model, TestObject testDataToValidate)
	{
		return new TestDataValidationResult(true);
	};

	/**
	 * Validates if testDataToValidate objects matches model constraints. The validation object is returned that states if data are valid or not. Id data is not valid, validation object contains information about failed validation. For example: failed OCL constraint, etc.
	 * 
	 * @param model model to validate against to.
	 * @param testDataToValidate test data to validate.
	 * @return validation result object.
	 */
	private TestDataValidationResult validateConstraints(Project project, TestObject testDataToValidate) throws Exception
	{
		if ((null == this.constraints) || (this.constraints.size() == 0))
		{
			return new TestDataValidationResult(true);
		}

		try
		{
			String packageName = null;
			String testObjectFullTypeName = null;

			if (testDataToValidate.getType() instanceof CollectionType)
			{
				CollectionType ct = (CollectionType) testDataToValidate.getType();
				while (ct.getEnclosingType() instanceof CollectionType)
				{
					ct = (CollectionType) ct.getEnclosingType();
				}
				testObjectFullTypeName = ct.getEnclosingType().getFullName();
			}
			else
			{
				testObjectFullTypeName = testDataToValidate.getType().getFullName();
			}

			if (isBasicType(testObjectFullTypeName))// What to do with these types?
			{
				return new TestDataValidationResult(true);
			}

			int indexLastDot = testObjectFullTypeName.lastIndexOf(".");

			if (indexLastDot > 0)
			{
				packageName = testObjectFullTypeName.substring(0, indexLastDot);
			}
			else
			{
				packageName = TransformerXSD.DEFAULT_PACKAGE_NAME;
			}

			Package pckg = null;

			for (Package p : project.getPackages())
			{
				if (false == packageName.equals(TransformerXSD.DEFAULT_PACKAGE_NAME))
				{
					if ((null != p.getName()) && (p.getName().equals(packageName)))
					{
						pckg = p;
						break;
					}
				}
				else
				{
					if ((null == p.getName()) || (p.getName().trim().length() == 0))
					{
						pckg = p;
						break;
					}
				}
			}

			if (null == pckg)
			{
				return new TestDataValidationResult(true);
			}

			TransformerXML transformerXML = new TransformerXML();
			List<Class> classes = transformerXML.getAllClasses(testDataToValidate, packageName);
			
			List<IModelInstance> modelInstances = new ArrayList<IModelInstance>();
			
			if (testDataToValidate instanceof TestObjectCollection)
			{
//				transformTestObject(testDataToValidate, packageName, result);
			}
			else
			{
			//	result.add(transform(testDataToValidate, packageName));
				
				modelInstances.add(new WrapperModelInstance(this.projectModel, testDataToValidate, this.projectModel));
			}			
			
			
			

			List<String> classesNames = new ArrayList<String>();
			
			for (Class clss : classes)
			{
				classesNames.add(TransformerXSD.getNameSpaceName(clss.getPackage()) + clss.getName());
			}

			return new TestDataValidationResult(validate(modelInstances, classesNames));
		}
		finally
		{
		}
	};

	/*
	
	private String patchConstraints(String constraints, Project project)
	{
		String[] lines = constraints.split("\n");
		
		String currentPackage = null;
		
		constraints = "";
		
		for (String line : lines)
		{
			line = line.trim();
			
			if (line.startsWith("package "))
			{
				currentPackage = line.substring(7).trim();
			}
			else if (line.startsWith("endpackage"))
			{
				currentPackage = null;
			}
			else if (line.startsWith("context "))
			{
				if (null != currentPackage)
				{
					constraints += "context " + currentPackage + "::" + line.substring(8) + "\n";
				}
				else
				{
					constraints += line + "\n";
				}
			}
			else
			{
				constraints += line + "\n";
			}
		}
		
		for (Package packge : project.getPackages())
		{
			for (Class clss : packge.getClasses())
			{
				constraints = constraints.replaceAll(getOclPackageName(packge) + "::" + clss.getName(), TransformerXSD.getNameSpaceName(packge) + clss.getName());
			}
		}

		for (Package packge : project.getPackages())
		{
			if (packge.getClasses().size() > 0)
			{
				constraints = constraints.replaceAll(getOclPackageName(packge), TransformerXSD.getNameSpaceName(packge));
			}
		}
		
		return constraints;
	};
	
	*/
	
	private String getOclPackageName(Package packge)
	{
		return packge.getName().replaceAll("\\.", "::");
	};
	
	private boolean validate(List<IModelInstance> modelInstances, List<String> types) throws InvalidOCLException
	{
		try
		{
			for (IModelInstance modelInstance : modelInstances)
			{
				for (IInterpretationResult result : StandaloneFacade.INSTANCE.interpretEverything(modelInstance, this.constraints))
				{
					Logger.logger.info("  " + result.getModelObject() + " (" + result.getConstraint().getKind() + ": " + result.getConstraint().getSpecification().getBody() + "): " + result.getResult());

					if (result.getResult() instanceof OclBoolean)
					{
						//OclBoolean oclBoolean = (OclBoolean) result.getResult();
						ExpressionInOclImpl expressionInOclImpl = (ExpressionInOclImpl) result.getConstraint().getSpecification();

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
						if (false == ((OclBoolean) result.getResult()).isTrue())
						{
							return false;
						}

					}
					else
					{
						return false;
					}
				}
			}
		}
		catch (Throwable ex)
		{
			Logger.log(ex);

			return false;
		}

		return true;
	};

	@Override
	public TestDataValidationResult validate(Method method, TestInput testInput) throws Exception
	{
		OclInterpreterPlugin p = new OclInterpreterPlugin();
		
		TestObject hhh = new TestObjectComplex("a1", method.getParent());
		
		IModelInstance modelInstance = new WrapperModelInstance(this.projectModel, hhh, this.projectModel);
		IModelInstanceElement modelInstanceElement = new WrapperModelInstanceObject(hhh, null, this.projectModel);
		Operation operation = this.projectModel.getOperation(method);
		List<IModelInstanceElement> parameters = new ArrayList<>();
		
		for (TestInputParameter parameter : testInput.getInputParameters())
		{
			parameters.add(this.projectModel.getInstance(parameter.getValue(), parameter.getParameter().getType()));
		}
		
		for (IInterpretationResult result : StandaloneFacade.interpretPreConditions(modelInstance, modelInstanceElement, operation, parameters, this.constraints))
		{
			Logger.logger.info("  " + result.getModelObject() + " (" + result.getConstraint().getKind() + ": " + result.getConstraint().getSpecification().getBody() + "): " + result.getResult());

			if (result.getResult() instanceof OclBoolean)
			{
				//OclBoolean oclBoolean = (OclBoolean) result.getResult();
				ExpressionInOclImpl expressionInOclImpl = (ExpressionInOclImpl) result.getConstraint().getSpecification();

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
				if (false == ((OclBoolean) result.getResult()).isTrue())
				{
					return new TestDataValidationResult(false);
				}

			}
			else
			{
				return new TestDataValidationResult(false);
			}
		}

		return new TestDataValidationResult(true);
	};
	
	
	
	
	private boolean isBasicType(String testObjectFullTypeName)
	{
		if (testObjectFullTypeName.equals("int") || testObjectFullTypeName.equals("double") || testObjectFullTypeName.equals("float") || testObjectFullTypeName.equals("byte") || testObjectFullTypeName.equals("short"))
		{
			return true;
		}
		return false;
	}


	private ProjectModel projectModel;
	private Project model;
	private List<Constraint> constraints = new ArrayList<>();
};
