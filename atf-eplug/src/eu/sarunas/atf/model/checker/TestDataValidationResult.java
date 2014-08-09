package eu.sarunas.atf.model.checker;

/**
 * Test data validation result. Contains pass or fail mark stating if data are valid. If data is not valid specifies additional information that describes what data are not valid.
 */
public class TestDataValidationResult
{
	/**
	 * Constructs test data validation result object.
	 * 
	 * @param isValid are test data valid.
	 */
	public TestDataValidationResult(boolean isValid)
	{
		this.isValid = isValid;
	};

	/**
	 * Are test data valid.
	 * 
	 * @return validation general result.
	 */
	public boolean isValid()
	{
		return this.isValid;
	};

	private boolean isValid = false;
};
