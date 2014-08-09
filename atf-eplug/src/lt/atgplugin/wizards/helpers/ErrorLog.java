package lt.atgplugin.wizards.helpers;


public class ErrorLog {

	public static enum TYPES {
		ANALYZER("[ANALYZER]"), GENERATOR("[GENERATOR]"), GENERIC("[GENERIC]");

		String what;

		public String getType() {
			return what;
		}

		TYPES(String s) {
			what = s;
		}
	};

}
