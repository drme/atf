package lt.atgplugin.analyzer;

import static org.junit.Assert.*;

import org.junit.*;

public class AnalyzerImplTest {

	protected transient lt.atgplugin.analyzer.AnalyzerImpl test = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		test = new lt.atgplugin.analyzer.AnalyzerImpl(
				new java.util.ArrayList(0), new java.util.ArrayList(0),
				new java.util.ArrayList(0), new java.util.ArrayList(0),
				new lt.atgplugin.filters.LongestPathConstructorFilterImpl(),
				new lt.atgplugin.filters.AbstractClassFilterImpl(), null,
				new lt.atgplugin.filters.EnumFilterImpl());
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(timeout = 60000)
	public void testAnalyzerImpl_VoidListConstructorFilterListAbstractClassFilterListInterfaceFilterListEnumFilterConstructorFilterAbstractClassFilterInterfaceFilterEnumFilter_DT_1() {
		try {
			new lt.atgplugin.analyzer.AnalyzerImpl(
					new java.util.ArrayList(0),
					new java.util.ArrayList(0),
					new java.util.ArrayList(0),
					new java.util.ArrayList(0),
					new lt.atgplugin.filters.LongestPathConstructorFilterImpl(),
					new lt.atgplugin.filters.AbstractClassFilterImpl(), null,
					new lt.atgplugin.filters.EnumFilterImpl());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}