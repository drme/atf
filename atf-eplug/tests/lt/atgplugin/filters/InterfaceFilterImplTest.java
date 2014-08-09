package lt.atgplugin.filters;

import static org.junit.Assert.*;

import org.junit.*;

public class InterfaceFilterImplTest {

	protected transient lt.atgplugin.filters.InterfaceFilterImpl test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.filters.InterfaceFilterImpl();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testGetDescription_String_DT_1() {
		try {
			test.getDescription();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetRealizedInterface_ITypeIJavaProjectITypeListIType_DT_1() {
		try {
			test.getRealizedInterface(null, null, new java.util.ArrayList(0));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testIsConfigurable_Boolean_DT_1() {
		try {
			test.isConfigurable();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetName_String_DT_1() {
		try {
			test.getName();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetShortName_String_DT_1() {
		try {
			test.getShortName();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetDialog_ATGDialogShell_DT_1() {
		try {
			test.getDialog(null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}