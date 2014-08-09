package eu.sarunas.atf.parsers;

import eu.sarunas.atf.meta.sut.Project;

/**
 * Interface for transformers.
 * Class implementing this interface has to be able extract meta data 
 * from source.
 * For example transform .jar package to meta data, or transform eclipse java
 * project to meta data. 
 */
public interface IModelParser
{
	public Project parseProject(Object source);
};
