package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tudresden.ocl20.pivot.pivotmodel.impl.TypedElementImpl;
import eu.atac.atf.main.ATF;
import eu.atac.atf.main.OCLUtil;

public class PEPropertyCall extends ConstrainPatternElementBase{
	private String type;

	public PEPropertyCall( String type) {
		super();
		this.type = type;
	}

	public int check(List<EObject> list, int index){
		try {
			EObject eObject = list.get(index);
			if(eObject instanceof TypedElementImpl){
				String type = OCLUtil.getTypedElementType(eObject);
				if(this.type.equals(type)){
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

	public String getType() {
		return type;
	}
	
}
