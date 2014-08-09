package eu.atac.atf.test.metadata;


public class SImport extends SBase{
	private boolean _static;
	private boolean _star;
	private String[] _names;
	
	
	public SImport(boolean _static, boolean _star, String ... _names) {
		super();
		this._static = _static;
		this._star = _star;
		this._names = _names;
		
		setPostNewLine(true);
	}


	@Override
	protected void printElement(StringBuilder s) {
		s.append("import ");
		if(_static){
			s.append("static ");
		}
		for (int i = 0; i < _names.length; i++) {
			s.append(_names[i]);
			if(i < _names.length -1){
				s.append('.');
				
			}
		}
		if(_star){
			s.append(".*");
		}
		s.append(';');
	}


	public boolean isStatic() {
		return _static;
	}


	public void setStatic(boolean _static) {
		this._static = _static;
	}


	public boolean isStar() {
		return _star;
	}


	public void setStar(boolean _star) {
		this._star = _star;
	}


	public String[] getNames() {
		return _names;
	}


	public void setNames(String[] _names) {
		this._names = _names;
	}

	

	
	public static SImport getStaticOrgJunitAssert(){
		return new SImport(true, true, "org","junit","Assert");
	}
	
	public static SImport getOrgJunitTest(){
		return new SImport(false, false, "org","junit","Test");
	}
}
