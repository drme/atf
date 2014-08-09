package eu.sarunas.atf.meta.sut;

import java.util.List;
import eu.sarunas.atf.meta.sut.body.ICodeElement;

/**
 * Represents .jar/.dll/.exe or software project under test.
 */
public interface ILibrary extends ICodeElement
{
	public List<Package> getPackages();
	public Package getDefaultPackage();
};
