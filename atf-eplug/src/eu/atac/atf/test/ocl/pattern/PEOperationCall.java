package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tudresden.ocl20.pivot.essentialocl.expressions.impl.OperationCallExpImpl;

import eu.atac.atf.main.ATF;
import eu.atac.atf.main.OCLUtil;

public class PEOperationCall extends ConstrainPatternElementBase{
	private String operation;

	public PEOperationCall(String operation) {
		super();
		this.operation = operation;
	}

	public int check(List<EObject> list, int index){
		try {
			EObject eObject = list.get(index);
			if(eObject instanceof OperationCallExpImpl){
				String operation = OCLUtil.getOperationCallOperator((OperationCallExpImpl) eObject);
				if(this.operation.equals(operation) ){
					return ++index;
				}
			}else{
				return -1;
			}
		} catch (Exception e) {
			ATF.log(e);
		}
		return -1;
	}

	public String getOperation() {
		return operation;
	}
	
}
