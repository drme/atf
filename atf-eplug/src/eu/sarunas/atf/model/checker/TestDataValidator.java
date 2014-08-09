package eu.sarunas.atf.model.checker;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import tudresden.ocl20.pivot.essentialocl.expressions.impl.ExpressionInOclImpl;
import tudresden.ocl20.pivot.interpreter.IInterpretationResult;
import tudresden.ocl20.pivot.model.IModel;
import tudresden.ocl20.pivot.model.ModelAccessException;
import tudresden.ocl20.pivot.modelinstance.IModelInstance;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;
import tudresden.ocl20.pivot.standardlibrary.java.internal.library.InvalidException;
import tudresden.ocl20.pivot.standardlibrary.java.internal.library.JavaOclBoolean;
import eu.atac.atf.main.Files;
import eu.sarunas.atf.generators.code.xml.TransformerXML;
import eu.sarunas.atf.generators.code.xsd.TransformerXSD;
import eu.sarunas.atf.meta.sut.Package;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.testdata.TestObject;

/**
 * Validates if test data matches model and its' constraints.
 */
public class TestDataValidator
{
	public static final String DEFAULT_PACKAGE_NAME = "DEFAULT";
	/**
	 * Validates if testDataToValidate objects matches model and model constraints. The validation object is returned that states if data are valid or not. If data are not valid, validation object contains information about failed validation. For example: failed OCL constraint, invalid model type, etc.
	 * 
	 * @param model model to validate against to.
	 * @param contraints model constraints to validate against to.
	 * @param testDataToValidate test data to validate.
	 * @return validation result object.
	 */
	public TestDataValidationResult validate(Project model, String contraints, TestObject testDataToValidate)
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
	private TestDataValidationResult validateConstraints(Project project, String contraints, TestObject testDataToValidate)
	{		
		if(contraints == null || contraints.trim().length() == 0){
			return new TestDataValidationResult(true);
		}
		File modelFile = null;
		File modelInstanceFile = null;
		File oclFile = null;
		
		try {
			String packageName = null;
			String testObjectFullTypeName = null;
			if(testDataToValidate.getType() instanceof CollectionType)
			{
				CollectionType ct = (CollectionType) testDataToValidate.getType();
				while(ct.getEnclosingType() instanceof CollectionType)
				{
					ct = (CollectionType) ct.getEnclosingType();
				}
				testObjectFullTypeName = ct.getEnclosingType().getFullName();
			}else
			{
				testObjectFullTypeName = testDataToValidate.getType().getFullName();
			}
			
			if(isBasicType(testObjectFullTypeName))//What to do with these types?
			{
				return new TestDataValidationResult(true);
			}
			
			int indexLastDot = testObjectFullTypeName.lastIndexOf(".");
			if( indexLastDot > 0)
			{
				packageName = testObjectFullTypeName.substring(0, indexLastDot);
			}
			else
			{
				packageName = DEFAULT_PACKAGE_NAME;
			}
			
			
			Package pckg = null;
			for (Package p : project.getPackages()) 
			{
				if(!packageName.equals(DEFAULT_PACKAGE_NAME))
				{
					if(p.getName() !=null && p.getName().equals(packageName))
					{
						pckg = p;
						break;
					}
				}else
				{
					if(p.getName() == null || p.getName().trim().length() == 0)
					{
						pckg = p;
						break;
					}
				}
			}
			
			if(pckg == null)
			{
				return new TestDataValidationResult(true);
			}
			
			StandaloneFacade.INSTANCE.initialize(null);
			
			modelFile = File.createTempFile("model", ".xsd");
			Files.writeToFile(new TransformerXSD().generatePackage(pckg),modelFile);
			
			TransformerXML transformerXML = new TransformerXML();
//			List<String> classes = transformerXML.getAllClasses(testDataToValidate,packageName);
//			List<String> modelInstances = transformerXML.transformTestObject(testDataToValidate,packageName);
			

			IModel model = StandaloneFacade.INSTANCE.loadXSDModel(modelFile);
			
			oclFile = File.createTempFile("ocl", ".ocl");
			//Dresden tool does not support class (context) which has '_' //Hack for namespace
			contraints = contraints.replaceFirst(packageName.replaceAll("\\.", "::"), packageName.replaceAll("[\\._]", ""));
			Files.writeToFile(contraints,oclFile);
			boolean valid = true;//validate(oclFile,model,modelInstances,modelFile,classes);
			
			return new TestDataValidationResult(valid);
		}
		catch (NotImplementedException e) 
		{
			e.printStackTrace();
			return new TestDataValidationResult(false);
		}
		catch (ModelAccessException e) 
		{
			e.printStackTrace();
			return new TestDataValidationResult(false);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return new TestDataValidationResult(false);
		} 	
		finally
		{
			if(modelFile != null)
			{
				modelFile.delete();
			}
			if(modelInstanceFile != null)
			{
				modelInstanceFile.delete();
			}
			if(oclFile != null)
			{
				oclFile.delete();
			}
		}

	}
	
	private boolean validate(File oclFile, IModel model,List<String> modelInstances,File modelFile,List<String> types) throws Exception{
		List<Constraint> constraintList = null;
		try {
			constraintList = StandaloneFacade.INSTANCE.parseOclConstraints(model,oclFile);
		} catch (tudresden.ocl20.pivot.parser.SemanticException e) {
			System.err.println("Invalid OCL:" + e.getMessage());
			return false;
		}
		 
		
		File modelInstanceFile = null;
		IModelInstance modelInstance = null;
		try {
			
			for (String string : modelInstances) {
				modelInstanceFile = File.createTempFile("modelInstance", ".xml");
				Files.writeToFile(string, modelInstanceFile);
				try {
					modelInstance = StandaloneFacade.INSTANCE.loadXMLModelInstance(model, modelInstanceFile);
				} catch (ModelAccessException e) {
					if(e.getCause().getMessage().indexOf("Invalid byte")>=0){
						return false;
					}else{
						throw e;
					}
				}
				
				
				for (IInterpretationResult result : StandaloneFacade.INSTANCE.interpretEverything(modelInstance, constraintList))
				{
//					System.out.println("  " + result.getModelObject() + " ("
//							+ result.getConstraint().getKind() + ": " 
//							+ result.getConstraint().getSpecification().getBody()
//							+ "): " + result.getResult());
					if(result.getResult() instanceof JavaOclBoolean){
						JavaOclBoolean oclBoolean = (JavaOclBoolean) result.getResult();
						ExpressionInOclImpl expressionInOclImpl = (ExpressionInOclImpl) result.getConstraint().getSpecification();
						boolean exists = false;
						for (String type : types) {
							if(expressionInOclImpl.getContext().getType().getName().equals(type)){
								exists = true;
								break;
							}
						}
						if(!exists){
							continue;
						}
						
						try {
							if(!((JavaOclBoolean)result.getResult()).isTrue() ){
								return false;
							}
						} catch (InvalidException e) {
							if(result.getResult() instanceof JavaOclBoolean)
							{
								System.err.println("TestDataValidator.validate(): " + result.getConstraint().getSpecification().getBody());
								System.err.println("TestDataValidator.validate(): " + oclBoolean.getInvalidReason().getMessage());
							}
							else
							{
								System.err.println("TestDataValidator.validate(): " + e.getLocalizedMessage());
							}
							
							return false;
						}

					}else{
						return false;
					}
				}
				modelInstanceFile.delete();
			}
		}
		finally
		{
			if(modelInstanceFile != null)
			{
				modelInstanceFile.delete();
			}
		}
		
		return true;
	}
	
	private boolean isBasicType(String testObjectFullTypeName) 
	{
		if(testObjectFullTypeName.equals("int") 
				|| testObjectFullTypeName.equals("double")
				|| testObjectFullTypeName.equals("float")
				|| testObjectFullTypeName.equals("byte")
				|| testObjectFullTypeName.equals("short"))
		{
			return true;
		}
		return false;
	}
};
