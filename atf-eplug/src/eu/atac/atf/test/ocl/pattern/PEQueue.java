package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class PEQueue extends ConstrainPatternElementBase{
	private ConstrainPatternElementBase elements[];
	
	public PEQueue(ConstrainPatternElementBase ... elements) {
		super();
		this.elements = elements;
	}
	
	@Override
	public int check(List<EObject> list, int index){
		for (int i = 0; i < elements.length; i++) {
			index = elements[i].check(list, index);
			if(index < 0){
				return -1;
			}
		}
		return index;
	}
}
