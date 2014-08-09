package lt.atgplugin.results;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IType;

/**
 * This class holds information about generator result - tested methods,
 * creation of class object and etc.
 * 
 * @author greta
 * 
 */
public class AnalyzedClass {
	/**
	 * Type which was tested.
	 */
	private IType testType = null;

	/**
	 * Constructor.
	 * 
	 * @param testType
	 *            - tested type
	 * @param obj
	 *            -
	 * @param generatedMethods
	 *            - generated test methods map
	 * @param generatedDeclarations
	 *            - generated declarations map
	 */
	public AnalyzedClass(IType testType, Param obj,
			Map<String, List<Param>> generatedMethods,
			Map<String, List<Param>> generatedDeclarations) {

	}

	/**
	 * Zero param constructor.
	 */
	public AnalyzedClass() {

	}

	/**
	 * This param is comprised of information how to create tested class object
	 * instance.
	 */
	protected Param testClassInvocationObject = null;
	/**
	 * Package name which holds analyzed type.
	 */
	protected String packageName = null;

	/**
	 * Getter for package name.
	 * 
	 * @return package name
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Setter for package name.
	 * 
	 * @param packageName
	 *            - name which will be set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * Getter for qualified tested class name
	 * 
	 * @return qualified name
	 */
	public String getQualifiedClassName() {
		return qClassName;
	}

	/**
	 * Setter for qualified tested class name
	 * 
	 * @param qClassName
	 */
	public void setQualifiedClassName(String qClassName) {
		this.qClassName = qClassName;
	}

	/**
	 * Holds qualified tested class name.
	 */
	protected String qClassName = null;
	/**
	 * Holds tested class name.
	 */
	protected String className = null;

	/**
	 * Getter for short class name.
	 * 
	 * @return short tested class name
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Setter for short class name.
	 * 
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Holds information about invoked methods in test.
	 */
	protected Map<String, List<Param>> methods = new HashMap<String, List<Param>>(
			0);

	public IType getTestType() {
		return testType;
	}

	public void setTestType(IType testType) {
		this.testType = testType;
	}

	public Param getTestedClassObjectForMethodsCalling() {
		return testClassInvocationObject;
	}

	public void setTestClassInvocationObject(Param testClassInvocationObject) {
		this.testClassInvocationObject = testClassInvocationObject;
	}

	public Map<String, List<Param>> getMethods() {
		return methods;
	}

	public void setMethods(Map<String, List<Param>> methods) {
		this.methods = methods;
	}

	public Map<String, List<Param>> getOtherDeclarations() {
		return otherDeclarations;
	}

	public void setOtherDeclarations(Map<String, List<Param>> otherDeclarations) {
		this.otherDeclarations = otherDeclarations;
	}

	/**
	 * Contains information about abstract classes or interfaces which are
	 * generated on the fly and used in test class.
	 */
	Map<String, List<Param>> otherDeclarations = new HashMap<String, List<Param>>(
			0);
}
