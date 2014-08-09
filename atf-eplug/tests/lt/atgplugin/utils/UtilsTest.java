package lt.atgplugin.utils;

import static org.junit.Assert.*;

import org.junit.*;

public class UtilsTest {

	protected transient lt.atgplugin.utils.Utils test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.utils.Utils();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testIsArray_BooleanString_DT_1() {
		try {
			lt.atgplugin.utils.Utils.isArray("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testHasPrivateConstructor_BooleanITypeTypeDeclaration_DT_1() {
		try {
			lt.atgplugin.utils.Utils.hasPrivateConstructor(null, null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testFormatCode_StringString_DT_1() {
		try {
			lt.atgplugin.utils.Utils.formatCode("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetAllDeclaredConstructors_ListMethodDeclarationITypeTypeDeclaration_DT_1() {
		try {
			lt.atgplugin.utils.Utils.getAllDeclaredConstructors(null, null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testIsPrimitive2_BooleanString_DT_1() {
		try {
			lt.atgplugin.utils.Utils.isPrimitive2("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testParse_CompilationUnitICompilationUnit_DT_1() {
		try {
			lt.atgplugin.utils.Utils.parse(null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetTypeDeclarationForType_TypeDeclarationIType_DT_1() {
		try {
			lt.atgplugin.utils.Utils.getTypeDeclarationForType(null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetAllDeclaredConstructorsNonPrivate_ListMethodDeclarationITypeTypeDeclaration_DT_1() {
		try {
			lt.atgplugin.utils.Utils.getAllDeclaredConstructorsNonPrivate(null,
					null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testLog_VoidString_DT_1() {
		try {
			lt.atgplugin.utils.Utils.log("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetAllConstructors_ListIMethodITypeStringbooleanbooleanboolean_DT_1() {
		try {
			lt.atgplugin.utils.Utils.getAllConstructors(null, "foreground",
					true, true, true);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testError_VoidString_DT_1() {
		try {
			lt.atgplugin.utils.Utils.error("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testIsPrimitive_BooleanString_DT_1() {
		try {
			lt.atgplugin.utils.Utils.isPrimitive("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}