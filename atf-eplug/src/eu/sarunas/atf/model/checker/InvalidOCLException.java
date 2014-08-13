package eu.sarunas.atf.model.checker;

import tudresden.ocl20.pivot.parser.SemanticException;

public class InvalidOCLException extends Exception
{
	public InvalidOCLException(SemanticException ex)
	{
		super(ex.getMessage(), ex);
	};

	private static final long serialVersionUID = 4449895996225961808L;
};
