package lt.atgplugin.cache;

public class AbstractCCache {
	AbstractCache map = new AbstractCache();
	static AbstractCCache cache = new AbstractCCache();

	private AbstractCCache() {

	}

	public static AbstractCCache getInstance() {
		if (cache == null) {
			cache = new AbstractCCache();
		}
		return cache;
	}
	
	

}
