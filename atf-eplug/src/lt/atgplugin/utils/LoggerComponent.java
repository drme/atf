package lt.atgplugin.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for logging errors.
 * 
 * @author greta
 * 
 */
public class LoggerComponent {
	public static enum TYPES {
		ANALYZER("[ANALYZER]"), GENERATOR("[GENERATOR]"), GENERIC("[GENERIC]"), LOG(
				"[LOG]");

		String what;

		public String getType() {
			return what;
		}

		TYPES(String s) {
			what = s;
		}
	};

	protected boolean errors = Boolean.FALSE;
	protected List<String> errorsList = new ArrayList<String>(0);
	protected List<String> logList = new ArrayList<String>(0);

	public List<String> getErrorsList() {
		return errorsList;
	}

	public String className = "";

	public void setName(String name) {
		className = name;
	}

	public boolean isSuccess() {
		return errorsList.size() == 0;
	}

	public String getErrors() {

		StringBuilder b = new StringBuilder();
		if (errorsList.size() > 0) {
			b.append(className + " error log\n");
			for (String s : errorsList) {
				b.append(s);
			}
		} else {
			b.append(className + " OK\n");
		}
		return b.toString();
	}

	public void setErrors(String msg) {
		errors = true;
		if (msg.endsWith("\n")) {
			msg = msg + "\n";
		}
		errorsList.add(msg);
	}

	public void setLog(TYPES type, String msg) {
		if (!msg.endsWith("\n")) {
			msg = msg + "\n";
		}
		if (type != TYPES.LOG) {
			errors = true;
			errorsList.add(type.getType() + " " + msg);
		} else {
			logList.add(type.getType() + " " + msg);
		}
	}

	public boolean isErrors() {
		return errors;
	}
}
