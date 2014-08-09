package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import eu.atac.atf.main.ATF;

public class PESimpleClass extends ConstrainPatternElementBase{
	private Class<?> clazz;
	private int min = -1;
	private int max = -1;
	
	public PESimpleClass(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}

	
	public PESimpleClass(Class<?> clazz, int min, int max) {
		super();
		this.clazz = clazz;
		this.min = min;
		this.max = max;
	}

	public int check(List<EObject> list, int index){
		try {
			if(min > 0 && max >= min){
				int size = index + min;
				for (int i = index; i < size; i++) {
					EObject object = list.get(index);
					if(object.getClass().equals(clazz)){
						++index;
					}else{
						return -1;
					}
				}
				size = index + (max - min);
				for (int i = index; i < size; i++) {
					EObject object = list.get(index);
					if(object.getClass().equals(clazz)){
						++index;
					}else{
						return index;
					}
				}				
				return index;
			}
			if(min < 0 && max < 0){
				EObject object = list.get(index);
				if(object.getClass().equals(clazz)){
					return ++index;
				}
			}
		} catch (Exception e) {
			ATF.log(e);
		}
		return -1;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
	
}
