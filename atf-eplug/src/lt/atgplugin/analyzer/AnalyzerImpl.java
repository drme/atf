package lt.atgplugin.analyzer;

import java.util.List;

import lt.atgplugin.filters.AbstractClassFilter;
import lt.atgplugin.filters.ConstructorFilter;
import lt.atgplugin.filters.EnumFilter;
import lt.atgplugin.filters.InterfaceFilter;

/**
 * Default implementation for ICompilationUnit Analyzer.
 * 
 * @author greta
 * 
 */
public class AnalyzerImpl extends Analyzer {

	public AnalyzerImpl(List<ConstructorFilter> c, List<AbstractClassFilter> a,
			List<InterfaceFilter> i, List<EnumFilter> e,
			ConstructorFilter defaultConstructor,
			AbstractClassFilter defaultAbstractClassFilter,
			InterfaceFilter defaultInterfaceFilter, EnumFilter defaultEnumFilter

	) {
		super(c, a, i, e, defaultConstructor, defaultAbstractClassFilter,
				defaultInterfaceFilter, defaultEnumFilter);
	}

}
