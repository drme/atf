package lt.atgplugin.popup.actions;

import static org.junit.Assert.*;

import org.junit.*;

public class GenerateTestCUDefaultsActionTest {

	protected transient lt.atgplugin.popup.actions.GenerateTestCUDefaultsAction test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.popup.actions.GenerateTestCUDefaultsAction();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testGenerateTestCUDefaultsAction_Void_DT_1() {
		try {
			new lt.atgplugin.popup.actions.GenerateTestCUDefaultsAction();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testSetActivePart_VoidIActionIWorkbenchPart_DT_1() {
		try {
			test.setActivePart(null, null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testRun_VoidIAction_DT_1() {
		try {
			test.run(null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testSelectionChanged_VoidIActionISelection_DT_1() {
		try {
			test.selectionChanged(null, null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}