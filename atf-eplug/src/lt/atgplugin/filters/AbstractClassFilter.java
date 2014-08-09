package lt.atgplugin.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lt.atgplugin.utils.Utils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;

/**
 * Class used for abstract classes analysis.
 * 
 * @author greta
 * 
 */
public abstract class AbstractClassFilter implements ATGOption {

	static Map<String, String> cache = new HashMap<String, String>(0);
	static {
		cache.put("java.util.List", "java.util.ArrayList");
		cache.put("java.util.Map", "java.util.HashMap");
	}

	/**
	 * Returns realized class which will be used.
	 * 
	 * @param list
	 * @return
	 */
	protected abstract IType getRealizedClass(IJavaProject project, IType main, List<IType> list);

	/**
	 * Getter for implementation class type which matches search criterion
	 * 
	 * @param project
	 *            where abstract class implementations will be searched
	 * @param name
	 *            name of searched abstract class
	 * @return type which matches search criterion, null if not found
	 */
	public IType getClass(IJavaProject project, String name) {

		IType tipas = null;
		try {
			tipas = project.findType(name);

		} catch (JavaModelException e) {

		}
		List<IType> intSsar = searchForTypes(tipas);

		return getRealizedClass(project, tipas, intSsar);

	}

	/**
	 * Getter for implementation class type which matches search criterion
	 * 
	 * @param project
	 *            where abstract class implementations will be searched
	 * @param name
	 *            name of searched abstract class
	 * @return type which matches search criterion, null if not found
	 */
	public IType getClass(IJavaProject project, IType name) {
		List<IType> intSsar = searchForTypes(name);
		Utils.log("[SEARCH] Found classes for " + name.getFullyQualifiedName());
		for (IType t : intSsar) {
			Utils.log("[FOUND] " + t.getFullyQualifiedName());
		}
		return getRealizedClass(project, name, intSsar);

	}

	/**
	 * Searched for all classes which implement type.
	 * 
	 * @param type
	 *            abstract class
	 * @return list of classes implementing type
	 */
	protected List<IType> searchForTypes(IType type) {
		final List<IType> types = new ArrayList<IType>();
		try {
			if (type != null && type.getFullyQualifiedName() != null) {
				SearchPattern pattern = SearchPattern.createPattern(
						type.getFullyQualifiedName(),
						IJavaSearchConstants.CLASS,
						IJavaSearchConstants.IMPLEMENTORS,
						SearchPattern.R_PATTERN_MATCH);
				IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
				SearchEngine searchEngine = new SearchEngine();

				SearchRequestor r = new SearchRequestor() {

					@Override
					public void acceptSearchMatch(SearchMatch match)
							throws CoreException {
						types.add((IType) match.getElement());

					}
				};

				searchEngine.search(pattern,
						new SearchParticipant[] { SearchEngine
								.getDefaultSearchParticipant() }, scope, r,
						null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return types;
	}

}
