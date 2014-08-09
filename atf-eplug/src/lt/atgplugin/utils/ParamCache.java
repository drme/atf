package lt.atgplugin.utils;

import java.util.ArrayList;
import java.util.List;

import lt.atgplugin.results.Param;

public class ParamCache {
	static List<Element> e = new ArrayList<Element>(0);

	private ParamCache() {

	}

	private static Param last = null;

	public static Param getLast() {
		return last;
	}

	public static void addParam(String name, int depth, Param p) {
		e.add(new Element(name, depth, p));
	}

	public static boolean contains(String name, int depth) {
		boolean poz = false;
		for (Element x : e) {
			if (x.getName().equals(name) && x.getDepth() == depth) {
				last = x.getP();
				poz = true;
				break;
			}
		}
		return poz;
	}

	private static class Element {
		private String name = null;

		public String getName() {
			return name;
		}

		public int getDepth() {
			return depth;
		}

		public Param getP() {
			return p;
		}

		private int depth = 0;
		private Param p = null;

		public Element(String name, int depth, Param p) {
			this.name = name;
			this.depth = depth;
			this.p = p;
		}
	}
}
