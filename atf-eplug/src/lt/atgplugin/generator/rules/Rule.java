package lt.atgplugin.generator.rules;

import java.util.List;
import java.util.Map;

import lt.atgplugin.filters.ATGOption;
import lt.atgplugin.results.Param;

import org.eclipse.jdt.core.IJavaProject;

/**
 * Class describing how to generate test method invocation code.
 * 
 * @author greta
 * 
 */
public abstract class Rule implements ATGOption {

	/**
	 * Checks if this param is overridden
	 * 
	 * @param p
	 *            param to be checked
	 * @return true if overridden
	 */
	public abstract boolean isOverriden(Param p);

	/**
	 * Checks if this param can be filtered by rule.
	 * 
	 * @param p
	 *            param to be checked
	 * @return true if rule applies
	 */
	public abstract boolean isFiltered(Param p);

	/**
	 * Method used for invocation value generation for getting specific value
	 * for specific types of params.
	 * 
	 * @param p
	 *            param for value will be generated
	 * @return value which will be used for invocation
	 */
	protected abstract String getValue(Param p);

	/**
	 * Method which will actually generate testing code.
	 * 
	 * @param project
	 *            java project
	 * @param analyzed
	 *            param instance which will be analyzed
	 * @param elements
	 *            map containing all results generated by Generator
	 * @param main
	 *            this param contains information about class methods invocation
	 *            object
	 * @return list of method bodies containing generated test code
	 */
	public abstract List<String> getFilteredTestMethodBodies(
			IJavaProject project, Param analyzed,
			Map<String, List<Param>> elements, Param main);

	public List<String> getTestMethodsBodies(IJavaProject project,
			Param analyzed, Map<String, List<Param>> elements, Param main) {
		if (isFiltered(analyzed)) {
			return getFilteredTestMethodBodies(project, analyzed, elements,
					main);
		}
		return null;
	}

	/**
	 * Method generating actual parameter invocation code.
	 * 
	 * @param p
	 *            param for which code will be generated
	 * @param b
	 *            contains result of generated code
	 * 
	 */
	public void generateInvocationValue(Param p, StringBuilder b) {

		if (isOverriden(p)) {
			b.append(getValue(p));
		} else {
			if (p.getOtherValue() != null) {
				b.append(p.getOtherValue());
			} else {
				if (p.getOtherConstructor() != null) {

					b.append(p.getOtherConstructor());
					b.append("(");

					for (int j = 0; j < p.getParams().size() - 1; j++) {
						generateInvocationValue(p.getParams().get(j), b);
						b.append(",");
					}
					if (p.getParams().size() > 0) {
						generateInvocationValue(
								p.getParams().get(p.getParams().size() - 1), b);
					}

					b.append(")");
					b.append("\n");
				} else {
					if (p.isPrimitive()&&!p.isArray()) {
						b.append(getValue(p) + "\n");
					} else {
						if (p.isNull()) {
							b.append("null\n");
						} else {
							if (p.isArray()) {
								if (!p.getQualifiedName().startsWith("new")) {
									b.append("new " + p.getQualifiedName());
								}

								for (int i = 0; i < p.getDim(); i++) {
									b.append("[]");
								}

								for (int i = 0; i < p.getDim(); i++) {
									b.append("{");
								}
								for (int j = 0; j < p.getParams().size() - 1; j++) {
									generateInvocationValue(p.getParams()
											.get(j), b);
									b.append(",");
								}

								if (p.getParams().size() > 0) {
									generateInvocationValue(
											p.getParams().get(
													p.getParams().size() - 1),
											b);

								} else {
									if (p.getParams().isEmpty()) {
										b.append(getValue(p));
									}
								}

								for (int i = 0; i < p.getDim(); i++) {
									b.append("}");
								}
								b.append("\n");
							} else {
								if (p.isUseDefaultConstructor()) {
									b.append("new " + p.getQualifiedName()
											+ "()");
								} else {

									if (p.getParent() != null) {
										b.append("new " + p.getQualifiedName());
									} else {
										b.append(p.getQualifiedName());
									}
									b.append("(");

									for (int j = 0; j < p.getParams().size() - 1; j++) {
										generateInvocationValue(p.getParams()
												.get(j), b);

										b.append(",");
									}
									if (p.getParams().size() > 0) {
										generateInvocationValue(p.getParams()
												.get(p.getParams().size() - 1),
												b);

									}

									b.append(")");
									b.append("\n");
								}
							}
						}
					}
				}
			}
		}
	}
}
