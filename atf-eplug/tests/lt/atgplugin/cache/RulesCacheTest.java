package lt.atgplugin.cache;

import static org.junit.Assert.*;

import org.junit.*;

public class RulesCacheTest {

	protected transient lt.atgplugin.cache.RulesCache test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.cache.RulesCache();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testRulesCache_Void_DT_1() {
		try {
			new lt.atgplugin.cache.RulesCache();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetClass_StringString_DT_1() {
		try {
			lt.atgplugin.cache.RulesCache.getClass("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testGetValue_StringString_DT_1() {
		try {
			lt.atgplugin.cache.RulesCache.getValue("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 60000)
	public void testContains_BooleanString_DT_1() {
		try {
			lt.atgplugin.cache.RulesCache.contains("foreground");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}