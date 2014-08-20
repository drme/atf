package eu.sarunas.projects.atf.metadata.transformers;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import eu.sarunas.projects.atf.metadata.IClass;
import eu.sarunas.projects.atf.metadata.IPackage;
import eu.sarunas.projects.atf.metadata.generic.Package;
import eu.sarunas.projects.atf.metadata.transformers.test_data.EmptyClass;

public class JavaTransformerTests
{
	private JavaTransformer transformer = null;

	@Before
	public void setUp()
	{
		this.transformer = new JavaTransformer();
	};

	@Test
	public void testPublicClass()
	{
		IPackage pckg = new Package("whatever");

		IClass clazz = this.transformer.transformClass(EmptyClass.class.getCanonicalName(), (JavaArchiveLoader) Thread.currentThread().getContextClassLoader(), pckg);

		Assert.assertEquals("whatever", clazz.getPackage().getName());
		Assert.assertEquals(EmptyClass.class.getSimpleName(), clazz.getName());
	};

};
