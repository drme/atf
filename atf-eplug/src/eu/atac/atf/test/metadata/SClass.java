package eu.atac.atf.test.metadata;

import java.util.ArrayList;
import java.util.List;

import eu.sarunas.atf.meta.sut.Class;
import static eu.atac.atf.main.ATF.NEW_LINE;

public class SClass extends SBase {
	private Class testClassModel;
	private String _package;
	private String name;
	private List<SImport> _imports = new ArrayList<SImport>();
	private List<SMethod> methods = new ArrayList<SMethod>();

	public String print() {
		StringBuilder s = new StringBuilder(1000);
		printElement(s);
		return s.toString();
	}
	
	@Override
	protected void printElement(StringBuilder s) {
		if (_package != null) {
			s.append("package ").append(_package).append(";");
			s.append(NEW_LINE);
		}

		s.append(NEW_LINE);

		for (SImport imp : _imports) {
			imp.print(s);
		}
		if (_imports.size() > 0) {
			s.append(NEW_LINE);
		}

		s.append(NEW_LINE).append("public class ").append(name).append(" {").append(NEW_LINE);

		for (SMethod method : methods) {
			method.printElement(s);
		}

		s.append(NEW_LINE).append('}');

	}

	@Deprecated
	public void print(StringBuilder s) {

		s.append("public ").append(name).append(" {").append(NEW_LINE);
		for (SMethod method : methods) {
			method.print(s);
		}

		s.append(NEW_LINE).append('}');
	}

	@Deprecated
	public String printPackageImport() {
		StringBuilder s = new StringBuilder();
		if (_package != null) {
			s.append("package ").append(_package).append(";");
			s.append(NEW_LINE);
		}

		s.append(NEW_LINE);

		for (SImport imp : _imports) {
			s.append("import ").append(imp).append(';').append(NEW_LINE);
		}
		if (_imports.size() > 0) {
			s.append(NEW_LINE);
		}

		return s.toString();
	}

	@Deprecated
	public String printClassDeclaration() {
		StringBuilder s = new StringBuilder();

		s.append(NEW_LINE).append("public class ").append(name).append(" {").append(NEW_LINE);

		return s.toString();
	}

	@Deprecated
	public String printEndClass() {
		return "}";
	}

	public String getPackage() {
		return _package;
	}

	public void setPackage(String _package) {
		this._package = _package;
	}

	public List<SImport> getImports() {
		return _imports;
	}

	public void setImports(List<SImport> _imports) {
		this._imports = _imports;
	}

	public List<SMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<SMethod> methods) {
		this.methods = methods;
	}

	public void addMethod(SMethod method) {
		this.methods.add(method);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addImport(SImport _import) {
		this._imports.add(_import);
	}

	public Class getTestClassModel() {
		return testClassModel;
	}

	public void setTestClassModel(Class testClassModel) {
		this.testClassModel = testClassModel;
	}

}
