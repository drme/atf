package eu.atac.atf.main;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tudresden.ocl20.pivot.pivotmodel.Constraint;

public class OCLConstraint {
	private String _class;
	private String _package;
	private Constraint constraint;
	private Tree<EObject> eobjectTree;
	private List<EObject> eobjectList;
	
	public OCLConstraint(Constraint constraint) {
		super();
		this.constraint = constraint;
		this.eobjectList = OCLUtil.getEObjectList(constraint);
		this.eobjectTree = OCLUtil.constructTree(constraint);
		this._class = OCLUtil.getConstraintClass(constraint);
		this._package = OCLUtil.getConstraintPackage(constraint);
	}
	
	public String getModelType(){
		return _package + "." + _class;
	}
	
	public String getClazz() {
		return _class;
	}

	public void setClazz(String _class) {
		this._class = _class;
	}

	public String getPackage() {
		return _package;
	}

	public void setPackage(String _package) {
		this._package = _package;
	}

	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	public Tree<EObject> getEobjectTree() {
		return eobjectTree;
	}

	public void setEobjectTree(Tree<EObject> eobjectTree) {
		this.eobjectTree = eobjectTree;
	}

	public List<EObject> getEobjectList() {
		return eobjectList;
	}

	public void setEobjectList(List<EObject> eobjectList) {
		this.eobjectList = eobjectList;
	}
	
}
