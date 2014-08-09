package eu.atac.atf.test.metadata;

import eu.atac.atf.main.ATFException;
import eu.atac.atf.main.ModelUtil;
import eu.sarunas.atf.meta.sut.Class;

public class SCreateObject extends SValue{
	
	public SCreateObject(Class type,boolean semicolon,boolean postNewLine) {
		this(type);
		setSemicolonPostLine(semicolon, postNewLine);
	}
	
	public SCreateObject(Class type) {
		super(type);
		this.type = type;
	}

	@Override
	protected void printElement(StringBuilder s) {
		if(false == type.isReferenceType()){
			super.printElement(s);
		}else{
			if(ModelUtil.hasDefaultConstructor(type)){
				s.append(" new ").append(type.getFullName()).append("()");
				if(type.isAbstract()){
					s.append("{}");
				}
			}else{
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000, type.getFullName());
			}
		}
	}
	
}
