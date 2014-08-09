package eu.sarunas.atf.generators.code;

import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.tests.TestSuite;

public interface ITestTransformer
{
	public Class transformTest(TestSuite testSuite);
};
