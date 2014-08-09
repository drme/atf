package eu.atac.atf.test.ocl.pattern;

import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ATFTestModel;
import eu.atac.atf.main.OCLConstraint;
import eu.atac.atf.test.metadata.SComment;

public class PUnknow extends ConstrainPatternBase{

	@Override
	public ConstrainPatternElementBase[] getPattern() {
		return null;
	}

	@Override
	public boolean check(OCLConstraint constraint) {
		return false;
	}
	
	@Override
	public void generateInvariant(OCLConstraint constraint,ATFTestModel atfTestModel) {
		try {
			atfTestModel.getSmethod().addBodyElement(new SComment(ATF.removeNewLineSymbols(constraint.getConstraint().getSpecification().getBody())));
		} catch (Exception e) {
			ATF.log(e);
		}
	}

}
