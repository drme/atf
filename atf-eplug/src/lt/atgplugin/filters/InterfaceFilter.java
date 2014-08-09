package lt.atgplugin.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;

/**
 * Class used for interfaces analyzing.
 * 
 * @author greta
 * 
 */
public abstract class InterfaceFilter implements ATGOption {
	static Map<String, String> cache = new HashMap<String, String>(0);
	static {
		cache.put("java.util.List", "java.util.ArrayList");
		cache.put("java.util.Map", "java.util.HashMap");
	}

	protected abstract IType getRealizedInterface(IJavaProject project,
			IType main, List<IType> list);

	public IType getInterface(IJavaProject project, IType name) {

		List<IType> intSsar = searchForInterfaces(name);
		return getRealizedInterface(project, name, intSsar);

	}

	protected List<IType> searchForInterfaces(IType tipas) {
		final List<IType> types = new ArrayList<IType>();
		try {
			if (tipas != null && tipas.getFullyQualifiedName() != null) {
				SearchPattern pattern = SearchPattern.createPattern(
						tipas.getFullyQualifiedName(),
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
