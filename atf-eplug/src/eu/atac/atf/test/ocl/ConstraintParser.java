package eu.atac.atf.test.ocl;

import eu.atac.atf.main.ATF;
import eu.atac.atf.main.OCLConstraint;
import eu.atac.atf.test.ocl.pattern.ConstrainPatternBase;
import eu.atac.atf.test.ocl.pattern.PEmpty;
import eu.atac.atf.test.ocl.pattern.PPattern1;
import eu.atac.atf.test.ocl.pattern.PPattern2;
import eu.atac.atf.test.ocl.pattern.PPattern3;
import eu.atac.atf.test.ocl.pattern.PPattern4;
import eu.atac.atf.test.ocl.pattern.PPatternIsNull;
import eu.atac.atf.test.ocl.pattern.PPatternRegex;
import eu.atac.atf.test.ocl.pattern.PUnknow;

public class ConstraintParser {
	private ConstrainPatternBase PATTERNS [] = {
			new PPattern1(),
			new PPattern2(),
			new PPattern3(),
			new PPattern4(),
			new PPatternRegex(),
			new PPatternIsNull(),
			new PEmpty(),
	};
	
	private static ConstraintParser INSTANCE;
	
	public static ConstraintParser getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ConstraintParser();
		}
		
		return INSTANCE;
	}
	
	public ConstrainPatternBase parse(OCLConstraint constraint){
		ConstrainPatternBase pattern = new PUnknow();
		
		try {
			for (int i = 0; i < PATTERNS.length; i++) {
				if(PATTERNS[i].check(constraint)){
					pattern = PATTERNS[i];
					break;
				}
			}			
		} catch (Exception e) {
			ATF.log(e);
		}
		return pattern;
	}
}
