package eu.atac.atf.test.metadata;

import eu.sarunas.atf.meta.sut.Class;

public class SInstanceOf  extends SBase{
	private SVariable object;
	private Class type;
	
	
	public SInstanceOf(SVariable object, Class type) {
		super();
		this.object = object;
		this.type = type;
	}


	@Override
	protected void printElement(StringBuilder s) {
		s.append(object.getName()).append("  instanceof  ").append(type.getFullName());
	}
}
