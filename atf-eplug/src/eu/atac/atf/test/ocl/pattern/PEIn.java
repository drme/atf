package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import eu.atac.atf.main.ATF;

public class PEIn extends ConstrainPatternElementBase{
	private Class<?> clazz[];

	
	public PEIn(Class<?> ... clazz) {
		super();
		this.clazz = clazz;
	}
	
	public int check(List<EObject> list, int index){
		try {
			for (int i = 0; i < clazz.length; i++) {
				if(clazz[i].equals(list.get(index).getClass())){
					return ++index;
				}
			}
			return -1;
		} catch (Exception e) {
			ATF.log(e);
		}
		return -1;
	}

	public Class<?>[] getClazz() {
		return clazz;
	}

	public void setClazz(Class<?>[] clazz) {
		this.clazz = clazz;
	}
	
}
