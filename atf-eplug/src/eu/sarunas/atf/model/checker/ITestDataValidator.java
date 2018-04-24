package eu.sarunas.atf.model.checker;

import java.util.List;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.testdata.TestObject;
import eu.sarunas.atf.meta.tests.TestInput;

/**
 * Validates if test data matches model and its' constraints.
 */
public interface ITestDataValidator
{
	/**
	 * Validates if testDataToValidate objects matches model and model constraints. The validation object is returned that states if data are valid or not. If data are not valid, validation object contains information about failed validation. For example: failed OCL constraint, invalid model type, etc.
	 *
	 * @param model model to validate against to.
	 * @param contraints model constraints to validate against to.
	 * @param testDataToValidate test data to validate.
	 * @return validation result object.
	 */
	public TestDataValidationResult validate(Project model, List<String> contraints, TestObject testDataToValidate) throws Exception;

	public TestDataValidationResult validate(Project model, String contraints, TestObject testDataToValidate) throws Exception;

	public TestDataValidationResult validate(TestObject testDataToValidate) throws Exception;

	public TestDataValidationResult validate(Method method, TestInput testInput) throws Exception;
};
