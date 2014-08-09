package lt.atgplugin.wizards;

import static org.junit.Assert.*;

import org.junit.*;

public class GenerateTestWizardTest {

	protected transient lt.atgplugin.wizards.GenerateTestWizard test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.wizards.GenerateTestWizard(null);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testAddPages_Void_DT_1() {
		try {
			test.addPages();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGenerateTestWizard_VoidShell_DT_1() {
		try {
			new lt.atgplugin.wizards.GenerateTestWizard(null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testInit_VoidIWorkbenchIStructuredSelection_DT_1() {
		try {
			test.init(null, null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testPerformFinish_Boolean_DT_1() {
		try {
			test.performFinish();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}