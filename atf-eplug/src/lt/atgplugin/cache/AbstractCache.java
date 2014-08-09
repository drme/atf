package lt.atgplugin.cache;

import java.util.HashMap;
import java.util.Map;

import lt.atgplugin.utils.Constants;

public class AbstractCache {
	static Map<String, String> map = new HashMap<String, String>(0);

	
	
	public boolean contains(String key) {
		if (Constants.useCache) {

			return (map.containsKey(key));
		} else {
			return false;
		}

	}

	public String getValue(String key) {
		if (Constants.useCache) {

			if (map.containsKey(key)) {
				return map.get(key);
			}
		}

		return "null";
	}

}
