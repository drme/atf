package lt.atgplugin.popup.actions;

import static org.junit.Assert.*;

import org.junit.*;

public class MainGeneratorActionTest {

	protected transient lt.atgplugin.popup.actions.MainGeneratorAction test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.popup.actions.MainGeneratorAction(
				new java.util.ArrayList(0), null);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testGetMessage_String_DT_1() {
		try {
			test.getMessage();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetPerformanceTimes_String_DT_1() {
		try {
			test.getPerformanceTimes();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testIsAnyError_Boolean_DT_1() {
		try {
			test.isAnyError();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testMainGeneratorAction_VoidListICompilationUnitShell_DT_1() {
		try {
			new lt.atgplugin.popup.actions.MainGeneratorAction(
					new java.util.ArrayList(0), null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetFailedClasses_String_DT_1() {
		try {
			test.getFailedClasses();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}