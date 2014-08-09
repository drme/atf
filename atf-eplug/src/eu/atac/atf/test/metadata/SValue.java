package eu.atac.atf.test.metadata;

import eu.sarunas.atf.meta.sut.Class;

public class SValue extends SBase {
	protected Class type; 
	protected String value;
	protected boolean reference;
	
	public SValue(Class type) {
		this.type = type;
	}
	
	public SValue(Class type,String value) {
		this.type  = type;
		this.value = value;
	}
	
	@Override
	protected void printElement(StringBuilder s) {
		s.append(value);

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
