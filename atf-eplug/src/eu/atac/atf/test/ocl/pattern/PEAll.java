package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import eu.atac.atf.main.ATF;

public class PEAll extends ConstrainPatternElementBase{
	public int check(List<EObject> list, int index){
		try {
			return list.size();
		} catch (Exception e) {
			ATF.log(e);
		}
		return -1;
	}
}
