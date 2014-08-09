package lt.atgplugin.wizards.pages;

import static org.junit.Assert.*;

import org.junit.*;

public class EnumsPageTest {

	protected transient lt.atgplugin.wizards.pages.EnumsPage test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.wizards.pages.EnumsPage();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testEnumsPage_Void_DT_1() {
		try {
			new lt.atgplugin.wizards.pages.EnumsPage();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testCreateControl_VoidComposite_DT_1() {
		try {
			test.createControl(null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testEnumsPage_VoidISelectionShell_DT_1() {
		try {
			new lt.atgplugin.wizards.pages.EnumsPage(null, null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetSelectedOptions_ListEnumFilter_DT_1() {
		try {
			test.getSelectedOptions();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetDefault_EnumFilter_DT_1() {
		try {
			test.getDefault();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}