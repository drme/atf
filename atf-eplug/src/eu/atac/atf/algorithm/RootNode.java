package eu.atac.atf.algorithm;

import eu.sarunas.atf.meta.sut.Class;

public class RootNode extends BaseNode{
	private Class clazz;

	public RootNode(Class clazz) {
		super();
		this.clazz = clazz;
	}

	@Override
	public String toString() {
		return "RootNode [clazz=" + clazz.getFullName() + "]";
	}
	
	
	
}
