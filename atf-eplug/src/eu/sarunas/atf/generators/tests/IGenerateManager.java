package eu.sarunas.atf.generators.tests;

/**
 * Manages test generation procedure.
 * Main responsibility - stop generation.
 */
public interface IGenerateManager
{
	/**
	 * Generate another one test case/data.
	 * @return
	 */
	public boolean generateMore();
};
