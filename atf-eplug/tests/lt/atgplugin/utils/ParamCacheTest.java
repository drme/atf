package lt.atgplugin.utils;

import static org.junit.Assert.*;

import org.junit.*;

public class ParamCacheTest {

	protected transient lt.atgplugin.utils.ParamCache test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = null;
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testContains_BooleanStringint_DT_1() {
		try {
			lt.atgplugin.utils.ParamCache.contains("foreground", 1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testAddParam_VoidStringintParam_DT_1() {
		try {
			lt.atgplugin.utils.ParamCache
					.addParam("foreground", 1, new lt.atgplugin.results.Param(
							"foreground", new lt.atgplugin.results.Param(
									"foreground", null,false)));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetLast_Param_DT_1() {
		try {
			lt.atgplugin.utils.ParamCache.getLast();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}