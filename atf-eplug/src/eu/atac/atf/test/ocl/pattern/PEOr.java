package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class PEOr extends ConstrainPatternElementBase{
	private ConstrainPatternElementBase or1;
	private ConstrainPatternElementBase or2;

	
	public PEOr(ConstrainPatternElementBase or1,ConstrainPatternElementBase or2) {
		super();
		this.or1 = or1;
		this.or2 = or2;
	}
	
	public int check(List<EObject> list, int index){
		int index1 = or1.check(list, index);
		
		if(index1 > 0){
			return index1;
		}
		
		int index2 = or2.check(list, index);
		
		if(index2 > 0){
			return index2;
		}
		System.out.println("PEOr.check(3)");
		return -1;
	}


	
}
