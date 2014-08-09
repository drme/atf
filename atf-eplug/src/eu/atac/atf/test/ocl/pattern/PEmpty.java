package eu.atac.atf.test.ocl.pattern;

import tudresden.ocl20.pivot.pivotmodel.Expression;
import eu.atac.atf.main.ATF;
import eu.atac.atf.main.OCLConstraint;

public class PEmpty extends ConstrainPatternBase{

	@Override
	public PESimpleClass[] getPattern() {
		return null;
	}

	@Override
	public boolean check(OCLConstraint constraint) {
		if(constraint == null){
			return true;
		}
		Expression expression = constraint.getConstraint().getSpecification();
		if(expression == null){
			return true;
		}
		if(!ATF.hasLenght(expression.getBody())){
			return true;
		}
		return false;
	}

}
