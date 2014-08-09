package lt.atgplugin.results;

import java.util.ArrayList;
import java.util.List;

public class Param {

	/**
	 * Parameter in method name.
	 */
	protected String paramName = null;

	/**
	 * Getter for parameter name in analyzed method.
	 * 
	 * @return parameter name in method
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * Setter for parameter name in analyzed method.
	 * 
	 * @param paramName
	 *            name which will be set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * Parameters of this param
	 */
	protected List<Param> params = new ArrayList<Param>(0);
	/**
	 * ELement name, used if value is not null
	 */
	protected String qualifiedName = null;
	/**
	 * Flag if value should be null.
	 */
	protected boolean isNull = Boolean.FALSE;
	/**
	 * Flag if param is array
	 */
	protected boolean isArray = Boolean.FALSE;

	/**
	 * Flag if this param is parent of all children
	 */
	protected boolean isRoot = Boolean.FALSE;

	/**
	 * Holds return value of this param
	 */
	protected String returnValue = null;

	/**
	 * Sets return value.
	 * 
	 * @param value
	 * @return
	 */
	public void setReturnValue(String value) {
		returnValue = value;
	}

	public String getReturnValue() {
		return returnValue;
	}

	/**
	 * Dimension of array
	 */
	protected int dim = 0;
	/**
	 * Flag if param is primitive
	 */
	protected boolean isPrimitive = Boolean.FALSE;

	/**
	 * Getter for param name.
	 * 
	 * @return fully qualified name of this param
	 */
	public String getQualifiedName() {
		return qualifiedName.replace("$", "."); 
	}

	/**
	 * Setter for qualified name.
	 * 
	 * @param qualifiedName
	 *            name which will be set for this param
	 */
	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	/**
	 * Checks if this param is array.
	 * 
	 * @return true if array
	 */
	public boolean isArray() {
		return isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}

	public void setPrimitive(boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public Param getParent() {
		return parent;
	}

	public void setParent(Param parent) {
		this.parent = parent;
	}

	public boolean isUseDefaultConstructor() {
		return isUseDefaultConstructor;
	}

	public void setUseDefaultConstructor(boolean isUseDefaultConstructor) {
		this.isUseDefaultConstructor = isUseDefaultConstructor;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}

	/**
	 * Param's parent
	 */
	protected Param parent = null;
	/**
	 * Value which might be set if there is a need for special value. Might be
	 * used for enums.
	 */
	protected String otherValue = null;

	/**
	 * Used if there is a need of special value for invoking this param
	 */
	protected String otherConstructor = null;
	/**
	 * Flag if use silent constructor
	 */
	boolean isUseDefaultConstructor = false;

	/**
	 * Sets flag that this param is array.
	 * 
	 * @param d
	 *            - dimension of array
	 */
	public void setArray(int d) {
		isArray = Boolean.TRUE;
		dim = d;
	}

	/**
	 * Sets flag that this param is primitive - is char, int, double and so on.
	 */
	public void setPrimitivie() {
		isPrimitive = true;
	}

	/**
	 * Sets other than generated invocation value, if it is needed.
	 * 
	 * @param value
	 *            which will be set as other than generated invocation value
	 */
	public void setOtherValue(String value) {
		otherValue = value;
	}

	/**
	 * Getter for invocation value.
	 * 
	 * @return value set as invocation value
	 */
	public String getOtherValue() {
		return otherValue;
	}

	/**
	 * Checks if param invocation value should be null.
	 * 
	 * @return true if param invocation value should be null
	 */
	public boolean isNull() {
		return isNull;
	}

	/**
	 * Sets flag if test containing this param should fail, because somethibng
	 * is wrong - no valid constructors and so on...
	 */
	boolean fail = Boolean.FALSE;

	public void setFail(String msg) {
		fail = Boolean.TRUE;
		failMsg = msg;
	}

	public boolean isFailed() {
		return fail;
	}

	String failMsg = null;

	public String getFailMsg() {
		return failMsg;
	}

	public Param(String name, Param parent, boolean isNull) {
		qualifiedName = name;
		this.isNull = isNull;
		this.parent = parent;

	}

	public Param(String name, Param parent) {
		qualifiedName = name;
		this.parent = parent;
	}

	/**
	 * Setter if there is need to invoke other constructor.
	 * 
	 * @param value
	 *            other constructor method name
	 */
	public void setInvokeOtherConstructor(String value) {
		otherConstructor = value;
	}

	public String getOtherConstructor() {
		return otherConstructor;
	}

	public void setOtherConstructor(String otherConstructor) {
		this.otherConstructor = otherConstructor;
	}

	/**
	 * Sets param invocation value to null.
	 */
	public void setNull() {
		isNull = true;
	}

	/**
	 * Sets to use default empty constructor.
	 */
	public void setUseDefaultConstructor() {
		isUseDefaultConstructor = true;
	}

	public Param(String name, List<Param> elements) {
		qualifiedName = name;
		this.params.addAll(elements);
	}

	public Param(String name, boolean isNull, List<Param> elements) {
		qualifiedName = name;
		this.isNull = isNull;
		this.params.addAll(elements);
	}

	/**
	 * Checks if there are no children parameters.
	 * 
	 * @return true if there are no children parameters.
	 */
	public boolean isEmpty() {
		return params.size() == 0;
	}

	/**
	 * Getter for children parameters.
	 * 
	 * @return children parameters
	 */
	public List<Param> getParams() {
		return params;
	}

	/**
	 * Adds child param to tree.
	 * 
	 * @param e
	 *            param to add
	 */
	public void addParam(Param e) {
		if (e != null) {
			params.add(e);
		}
	}

}
