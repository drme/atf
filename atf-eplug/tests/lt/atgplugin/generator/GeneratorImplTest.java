package lt.atgplugin.generator;

import static org.junit.Assert.*;

import org.junit.*;

public class GeneratorImplTest {

	protected transient lt.atgplugin.generator.GeneratorImpl test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.generator.GeneratorImpl(
				new java.util.ArrayList(0),
				new lt.atgplugin.generator.rules.DefaultInvocationRule());
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testGeneratorImpl_VoidListRuleRule_DT_1() {
		try {
			new lt.atgplugin.generator.GeneratorImpl(
					new java.util.ArrayList(0),
					new lt.atgplugin.generator.rules.DefaultInvocationRule());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGeneratorImpl_VoidRuleRule_DT_1() {
		try {
			new lt.atgplugin.generator.GeneratorImpl(
					new lt.atgplugin.generator.rules.DefaultInvocationRule(),
					new lt.atgplugin.generator.rules.DefaultInvocationRule());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}