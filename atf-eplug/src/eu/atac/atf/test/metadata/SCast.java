package eu.atac.atf.test.metadata;

import eu.sarunas.atf.meta.sut.Class;

public class SCast extends SBase{
	private Class type;
	private SVariable variable;
	

	public SCast(Class type, SVariable variable) {
		super();
		this.type = type;
		this.variable = variable;
		setIgnoreFormating(true);
	}

	@Override
	protected void printElement(StringBuilder s) {
		s.append(" = (").append(type.getFullName()).append(')').append(variable.getName()).append(";");
	}
	
	
}
