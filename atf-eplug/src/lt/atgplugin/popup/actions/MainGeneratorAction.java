package lt.atgplugin.popup.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lt.atgplugin.analyzer.Analyzer;
import lt.atgplugin.analyzer.AnalyzerImpl;
import lt.atgplugin.filters.ATGOption;
import lt.atgplugin.filters.AbstractClassFilter;
import lt.atgplugin.filters.ConstructorFilter;
import lt.atgplugin.filters.EnumFilter;
import lt.atgplugin.filters.InterfaceFilter;
import lt.atgplugin.generator.Generator;
import lt.atgplugin.generator.GeneratorImpl;
import lt.atgplugin.generator.rules.Rule;
import lt.atgplugin.utils.Constants;
import lt.atgplugin.utils.LoggerComponent;
import lt.atgplugin.wizards.helpers.DefaultOptions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

public class MainGeneratorAction extends lt.atgplugin.utils.LoggerComponent {
	long generationTime = 0L;
	final Shell shell;
	String msg;

	public String getPerformanceTimes() {
		return msg;
	}

	List<String> failedClasses = new ArrayList<String>(0);

	public MainGeneratorAction(final List<ICompilationUnit> units,
			final Shell shell) {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
		this.shell = shell;
		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) {
					long t1 = System.nanoTime();
					try {
						System.gc();
						generate(units, monitor);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						long t2 = System.nanoTime();
						System.gc();
						generationTime = t2 - t1;

						if (Constants.showTimes) {
							String tmp = "Generation took\t"
									+ ((generationTime / 1000000000.0))
									+ " s\nfor total units " + units.size()
									+ "\nfailed " + failedClasses.size() + "\n";
							msg = tmp;
						}
					}

				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	StringBuilder log = new StringBuilder();

	public boolean isAnyError() {
		return failedClasses.size() > 0;
	}

	public String getFailedClasses() {
		StringBuilder b = new StringBuilder("Failed classes:\n");
		for (String s : failedClasses) {
			b.append("\t" + s + "\n");
		}
		return b.toString();
	}

	public String getMessage() {
		return log.toString();
	}

	private void generate(List<ICompilationUnit> units, IProgressMonitor monitor) {
		errorsList.clear();
		Rule defaultRule = (Rule) DefaultOptions.getDefaultRule();
		List<Rule> rules = new ArrayList<Rule>();
		for (ATGOption t : DefaultOptions.getDefaultRules()) {
			rules.add((Rule) t);
		}
		List<ConstructorFilter> defaultConstructors = new ArrayList<ConstructorFilter>();
		for (ATGOption t : DefaultOptions.getDefaultConstructors()) {
			defaultConstructors.add((ConstructorFilter) t);
		}
		List<InterfaceFilter> defaultInterfaces = new ArrayList<InterfaceFilter>();
		for (ATGOption t : DefaultOptions.getDefaultInterfaces()) {
			defaultInterfaces.add((InterfaceFilter) t);
		}
		List<AbstractClassFilter> abstractClasses = new ArrayList<AbstractClassFilter>();
		for (ATGOption t : DefaultOptions.getDefaultAclasses()) {
			abstractClasses.add((AbstractClassFilter) t);
		}
		List<EnumFilter> enums = new ArrayList<EnumFilter>();
		for (ATGOption t : DefaultOptions.getDefaultEnums()) {
			enums.add((EnumFilter) t);
		}

		ConstructorFilter c = (ConstructorFilter) DefaultOptions
				.getDefaultConstructor();

		AbstractClassFilter a = (AbstractClassFilter) DefaultOptions
				.getDefaultAbtractClassFilter();

		InterfaceFilter di = (InterfaceFilter) DefaultOptions
				.getDefaultInterface();

		EnumFilter eF = (EnumFilter) DefaultOptions.getDefaultEnumFilter();

		monitor.beginTask("Starting generation", units.size());
		int i = 0;

		for (ICompilationUnit t : units) {
			LoggerComponent fullLog = new LoggerComponent();
			fullLog.setName(t.getElementName());
			fullLog.setLog(TYPES.LOG,
					"Generating test for " + t.getElementName());

			try {
				if (!Flags.isAbstract(t.getAllTypes()[0].getFlags())
						&& !Flags.isInterface(t.getAllTypes()[0].getFlags())) {
					Analyzer aa = new AnalyzerImpl(defaultConstructors,
							abstractClasses, defaultInterfaces, enums, c, a,
							di, eF);
					boolean poz = aa.analyze(t);
					if (!aa.isErrors() && poz) {
						Generator g = new GeneratorImpl(rules, defaultRule);
						g.generate(t, aa.getGeneratedResult());
						monitor.worked(i);
						i++;
						if (!g.isErrors()) {
							fullLog.getErrorsList().addAll(g.getErrorsList());
						}
					} else {
						fullLog.getErrorsList().addAll(aa.getErrorsList());
					}

				} else {

					fullLog.setLog(
							TYPES.GENERIC,
							"Test will not be generated for "
									+ t.getElementName()
									+ " because it is interface or abstract class");

				}
			} catch (JavaModelException e) {
				fullLog.setLog(TYPES.GENERIC, "Test will not be generated for "
						+ t.getElementName() + " ");

			}
			log.append(fullLog.getErrors());
			if (!fullLog.isSuccess()) {
				failedClasses.add(t.getElementName());
			}
		}

		monitor.done();
	}

}
