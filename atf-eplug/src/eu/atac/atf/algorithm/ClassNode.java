package eu.atac.atf.algorithm;

import java.util.List;

import eu.atac.atf.main.OCLConstraint;
import eu.sarunas.atf.meta.sut.Class;

public class ClassNode extends BaseNode{
	private Class clazz;
	private List<OCLConstraint> contraints;

	public ClassNode(Class clazz,List<OCLConstraint> contraints) {
		super();
		this.clazz = clazz;
		this.contraints = contraints;
	}

	@Override
	public String toString() {
		return "ClassNode [clazz=" + clazz.getFullName() + "cons:" + contraints.size() +  "]";
	}
	
	
}
