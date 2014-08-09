package lt.atgplugin.utils;

import static org.junit.Assert.*;

import org.junit.*;

public class LoggerComponentTest {

	protected transient lt.atgplugin.utils.LoggerComponent test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.utils.LoggerComponent();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testIsSuccess_Boolean_DT_1() {
		try {
			test.isSuccess();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetErrors_String_DT_1() {
		try {
			test.getErrors();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetErrorsList_ListString_DT_1() {
		try {
			test.getErrorsList();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testSetName_VoidString_DT_1() {
		try {
			test.setName("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testSetErrors_VoidString_DT_1() {
		try {
			test.setErrors("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testSetLog_VoidTYPESString_DT_1() {
		try {
			test.setLog(lt.atgplugin.utils.LoggerComponent.TYPES.ANALYZER,
					"foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testIsErrors_Boolean_DT_1() {
		try {
			test.isErrors();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}