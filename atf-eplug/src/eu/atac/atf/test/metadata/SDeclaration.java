package eu.atac.atf.test.metadata;

import eu.sarunas.atf.meta.sut.Class;

public class SDeclaration extends SBase {
	private Class type;

	public SDeclaration(Class type) {
		super();
		this.type = type;
		setPostSpace(true);
		incrementPreTab();
		incrementPreTab();
	}

	@Override
	public void printElement(StringBuilder builder) {
		builder.append(type.getFullName());
	}
	
	public String getType(){
		return type.getFullName();
	}
}
