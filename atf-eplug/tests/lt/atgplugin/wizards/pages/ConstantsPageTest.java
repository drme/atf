package lt.atgplugin.wizards.pages;

import static org.junit.Assert.*;

import org.junit.*;

public class ConstantsPageTest {

	protected transient lt.atgplugin.wizards.pages.ConstantsPage test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.wizards.pages.ConstantsPage();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testConstantsPage_Void_DT_1() {
		try {
			new lt.atgplugin.wizards.pages.ConstantsPage();
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
	public void testConstantsPage_VoidISelection_DT_1() {
		try {
			new lt.atgplugin.wizards.pages.ConstantsPage(null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}