package eu.atac.atf.main;

import java.util.ArrayList;
import java.util.List;

import tudresden.ocl20.pivot.pivotmodel.Constraint;

import eu.atac.atf.test.metadata.SClass;
import eu.atac.atf.test.metadata.SMethod;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Package;
import eu.sarunas.atf.meta.sut.Project;

public class ATFTestModel {
	private Project project;
	
	private Package _package;
	private Class   _class;
	private Method _method;
	
	private SClass sclass;
	private SMethod smethod;
	
	private List<OCLConstraint> constraints;
	
	public ATFTestModel(Project project, Package _package, Class _class, Method _method) {
		super();
		this.project  = project;
		this._package = _package;
		this._class   = _class;
		this._method  = _method;
	}
	
	public void setConstraintList(List<Constraint> constraintList){
		constraints = new ArrayList<OCLConstraint>(constraintList.size());
		for (int i = 0; i < constraintList.size(); i++) {
			constraints.add(new OCLConstraint(constraintList.get(i)));
//			OCLUtil.printTreeToSysout(constraints.get(i).getEobjectTree());
		}
		
	}
	
//	public Package getCurrentPackage(){
//		return _package;
//	}
//	
//	public Class getCurrentClass(){
//		return _class;
//	}
//	
//	public Method getCurrentMethod(){
//		return _method;
//	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Package getPackage() {
		return _package;
	}

	public void setPackage(Package _package) {
		this._package = _package;
	}

	public Class getClazz() {
		return _class;
	}

	public void setClass(Class _class) {
		this._class = _class;
	}

	public Method getMethod() {
		return _method;
	}

	public void setMethod(Method _method) {
		this._method = _method;
	}

	public SClass getSclass() {
		return sclass;
	}

	public void setSclass(SClass sclass) {
		this.sclass = sclass;
	}

	public SMethod getSmethod() {
		return smethod;
	}

	public void setSmethod(SMethod smethod) {
		this.smethod = smethod;
	}

	public List<OCLConstraint> getConstraints() {
		return constraints;
	}
	
}
