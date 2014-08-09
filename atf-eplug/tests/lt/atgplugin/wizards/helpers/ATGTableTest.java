package lt.atgplugin.wizards.helpers;

import static org.junit.Assert.*;

import org.junit.*;

public class ATGTableTest {

	protected transient lt.atgplugin.wizards.helpers.ATGTable test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.wizards.helpers.ATGTable(null, null, 1,
				new java.util.ArrayList(0), new java.util.ArrayList(0));
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testATGTable_VoidShellCompositeintListATGOptionListATGOption_DT_1() {
		try {
			new lt.atgplugin.wizards.helpers.ATGTable(null, null, 1,
					new java.util.ArrayList(0), new java.util.ArrayList(0));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testSetSelectedItems_VoidListATGOption_DT_1() {
		try {
			test.setSelectedItems(new java.util.ArrayList(0));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetTable_Table_DT_1() {
		try {
			test.getTable();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetSelectedItems_ListATGOption_DT_1() {
		try {
			test.getSelectedItems();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}