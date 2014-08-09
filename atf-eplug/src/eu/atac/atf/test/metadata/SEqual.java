package eu.atac.atf.test.metadata;

public class SEqual extends SBase{
	
	public SEqual(){
		setPreSpace(true);
		setPostSpace(true);
	}
	
	@Override
	protected void printElement(StringBuilder s) {
		s.append('=');
	}

	
}
