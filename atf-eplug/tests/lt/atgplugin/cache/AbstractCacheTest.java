package lt.atgplugin.cache;

import static org.junit.Assert.*;

import org.junit.*;

public class AbstractCacheTest {

	protected transient lt.atgplugin.cache.AbstractCache test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.cache.AbstractCache();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testContains_BooleanString_DT_1() {
		try {
			test.contains("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetValue_StringString_DT_1() {
		try {
			test.getValue("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}