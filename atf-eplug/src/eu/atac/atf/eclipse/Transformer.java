package eu.atac.atf.eclipse;

import org.eclipse.jdt.internal.core.CompilationUnit;

import eu.sarunas.atf.eclipse.parsers.JavaProjectParser;
import eu.sarunas.atf.meta.sut.Project;

public class Transformer extends JavaProjectParser
{
	public Project transformEclipseModelToATF(CompilationUnit unit)
	{
		return super.parseProject(unit.getJavaProject());
	}
}
