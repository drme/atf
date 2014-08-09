package lt.atgplugin.cache;

import static org.junit.Assert.*;

import org.junit.*;

public class AbstractCCacheTest {

	protected transient lt.atgplugin.cache.AbstractCCache test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = lt.atgplugin.cache.AbstractCCache.getInstance();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testGetInstance_AbstractCCache_DT_1() {
		try {
			lt.atgplugin.cache.AbstractCCache.getInstance();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}