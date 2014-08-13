package eu.sarunas.atf.model.checker;

import java.io.File;
import java.net.URL;
import java.util.List;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.ExpressionInOclImpl;
import tudresden.ocl20.pivot.essentialocl.standardlibrary.OclBoolean;
import tudresden.ocl20.pivot.interpreter.IInterpretationResult;
import tudresden.ocl20.pivot.model.IModel;
import tudresden.ocl20.pivot.modelinstance.IModelInstance;
import tudresden.ocl20.pivot.parser.SemanticException;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;
import eu.atac.atf.main.Files;
import eu.sarunas.atf.generators.code.xml.TransformerXML;
import eu.sarunas.atf.generators.code.xsd.TransformerXSD;
import eu.sarunas.atf.meta.sut.Package;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.testdata.TestObject;
import eu.sarunas.atf.utils.FileUtils;
import eu.sarunas.atf.utils.Logger;

/**
 * Validates if test data matches model and its' constraints.
 */
public class TestDataValidator
{
	/**
	 * Validates if testDataToValidate objects matches model and model constraints. The validation object is returned that states if data are valid or not. If data are not valid, validation object contains information about failed validation. For example: failed OCL constraint, invalid model type, etc.
	 * 
	 * @param model model to validate against to.
	 * @param contraints model constraints to validate against to.
	 * @param testDataToValidate test data to validate.
	 * @return validation result object.
	 */
	public TestDataValidationResult validate(Project model, String contraints, TestObject testDataToValidate) throws Exception
	{
		TestDataValidationResult result = validateModel(model, testDataToValidate);

		if (true == result.isValid())
		{
			return validateConstraints(model, contraints, testDataToValidate);
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
	 * @param contraints model constraints to validate against to.
	 * @param testDataToValidate test data to validate.
	 * @return validation result object.
	 */
	private TestDataValidationResult validateConstraints(Project project, String contraints, TestObject testDataToValidate) throws Exception
	{
		if ((null == contraints) || (contraints.trim().length() == 0))
		{
			return new TestDataValidationResult(true);
		}

		File modelFile = null;
		File modelInstanceFile = null;
		File oclFile = null;

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

			StandaloneFacade.INSTANCE.initialize(null);

			modelFile = File.createTempFile("model", ".xsd");
//			Files.writeToFile(new TransformerXSD().generatePackage(pckg), modelFile);
			Files.writeToFile(new TransformerXSD().generateProject(project), modelFile);

			
			TransformerXML transformerXML = new TransformerXML();
			List<String> classes = transformerXML.getAllClasses(testDataToValidate, packageName);
			List<String> modelInstances = transformerXML.transformTestObject(testDataToValidate, packageName);

			IModel model = StandaloneFacade.INSTANCE.loadXSDModel(modelFile);
			
			oclFile = File.createTempFile("ocl", ".ocl");

			// Dresden tool does not support class (context) which has '_' //Hack for namespace

		//	contraints = contraints.replaceFirst(packageName.replaceAll("\\.", "::"), packageName.replaceAll("[\\._]", ""));
			Files.writeToFile(contraints, oclFile);

			return new TestDataValidationResult(validate(oclFile, model, modelInstances, modelFile, classes));
		}
		finally
		{
			FileUtils.deleteFile(modelFile);
			FileUtils.deleteFile(modelInstanceFile);
			FileUtils.deleteFile(oclFile);
		}
	};

	private boolean validate(File oclFile, IModel model, List<String> modelInstances, File modelFile, List<String> types) throws InvalidOCLException
	{
		File modelInstanceFile = null;

		try
		{
			List<Constraint> constraintList = StandaloneFacade.INSTANCE.parseOclConstraints(model, oclFile);

			for (String string : modelInstances)
			{
				modelInstanceFile = File.createTempFile("modelInstance", ".xml");

				Files.writeToFile(string, modelInstanceFile);

				IModelInstance modelInstance = StandaloneFacade.INSTANCE.loadXMLModelInstance(model, modelInstanceFile);

				for (IInterpretationResult result : StandaloneFacade.INSTANCE.interpretEverything(modelInstance, constraintList))
				{
					Logger.logger.info("  " + result.getModelObject() + " (" + result.getConstraint().getKind() + ": " + result.getConstraint().getSpecification().getBody() + "): " + result.getResult());

					if (result.getResult() instanceof OclBoolean)
					{
						//OclBoolean oclBoolean = (OclBoolean) result.getResult();
						ExpressionInOclImpl expressionInOclImpl = (ExpressionInOclImpl) result.getConstraint().getSpecification();

						//Logger.logger.info("TestDataValidator.validate(): " + result.getConstraint().getSpecification().getBody());
						//Logger.logger.info("TestDataValidator.validate(): " + oclBoolean.getInvalidReason().getMessage());

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

				FileUtils.deleteFile(modelInstanceFile);
			}
		}
		catch (SemanticException ex)
		{
			Logger.log(ex);

			throw new InvalidOCLException(ex);
		}
		catch (Throwable ex)
		{
			Logger.log(ex);

			return false;
		}
		finally
		{
			if (modelInstanceFile != null)
			{
				modelInstanceFile.delete();
			}
		}

		return true;
	};

	private boolean isBasicType(String testObjectFullTypeName)
	{
		if (testObjectFullTypeName.equals("int") || testObjectFullTypeName.equals("double") || testObjectFullTypeName.equals("float") || testObjectFullTypeName.equals("byte") || testObjectFullTypeName.equals("short"))
		{
			return true;
		}
		return false;
	};
};
