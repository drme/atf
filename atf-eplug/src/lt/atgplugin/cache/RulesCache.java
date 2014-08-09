package lt.atgplugin.cache;

import java.util.HashMap;
import java.util.Map;

import lt.atgplugin.utils.Constants;

public class RulesCache {
	static Map<String, String> map = null;

	static {
		init();
	}

	private static void init() {
		map = new HashMap<String, String>();
		map.put("java.lang.String", "\"foreground\"");
		map.put("double", "0.1d");
		map.put("java.lang.Double", "0.1d");
		map.put("float", "0.1f");
		map.put("java.lang.Float", "0.1f");
		map.put("int", "1");
		map.put("java.lang.Integer", "1");
		map.put("long", "1l");
		map.put("java.lang.Long", "1l");
		map.put("boolean", "true");
		map.put("java.lang.Boolean", "true");
		map.put("byte", "4");
		map.put("java.lang.Byte", "4");
		map.put("char", "'a'");
		map.put("java.lang.Character", "'a'");
		map.put("short", "3");
		map.put("java.lang.Short", "3");
		map.put("java.io.File", "new java.io.File(\"test.txt\")");
		map.put("java.net.URL", "new java.net.URL(\"http://www.google.com\")");
		map.put("java.awt.Component", "new javax.swing.JLabel(\"test\")");
		map.put("javax.swing.JLabel", "new javax.swing.JLabel(\"test\")");
		map.put("java.math.BigDecimal", "new java.math.BigDecimal(3d)");
		map.put("java.math.BigInteger", "new java.math.BigInteger(\"33\")");
		map.put("java.io.Reader", "new java.io.FileReader(\"test.txt\")");
		map.put("java.io.FileReader", "new java.io.FileReader(\"test.txt\")");
		map.put("java.io.OutputStream",
				"new java.io.FileOutputStream(\"test.txt\")");
		map.put("java.io.PrintWriter", "new java.io.PrintWriter(\"test.txt\")");
		map.put("java.io.Writer", "new java.io.FileWriter(\"test.txt\")");
		map.put("java.io.FileWriter", "new java.io.FileWriter(\"test.txt\")");
		map.put("java.util.ArrayList", "new java.util.ArrayList(0)");
		map.put("com.eteks.sweethome3d.viewcontroller.HomeController",
				"new com.eteks.sweethome3d.viewcontroller.HomeController(new com.eteks.sweethome3d.model.Home(), new com.eteks.sweethome3d.io.DefaultUserPreferences(), new com.eteks.sweethome3d.swing.SwingViewFactory())");
		map.put("java.awt.image.WritableRaster", "null");

	}

	RulesCache() {

	}

	// ...
	public static boolean contains(String key) {
		if (Constants.useCache) {
			if (map == null) {
				init();
			}
			return (map.containsKey(key));
		} else {
			return false;
		}

	}

	public static String getValue(String key) {
		if (map == null) {
			init();
		}
		if (map.containsKey(key)) {
			return map.get(key);
		}
		return "null";
	}

	public static String getClass(String key) {
		if (map == null) {
			init();
		}
		if (map.containsKey(key)) {
			return map.get(key).split("\\(")[0].replace("new ", "");
		}
		return "null";
	}
}
