package eu.atac.atf.test.metadata;


public class SAssertion extends SBase{
	public static final int TYPE_ASSERT_TRUE = 1;
	public static final int TYPE_ASSERT_NULL = 2;
	private int type;
	private String assertion;
	
	public SAssertion(String assertion){
		this.assertion = assertion;
		type = TYPE_ASSERT_TRUE;
		incrementPreTab2x();
		setPostNewLine(true);
		
	}

	@Override
	protected void printElement(StringBuilder s) {
		switch (type) {
		case TYPE_ASSERT_TRUE:
			s.append("assertTrue(").append(assertion).append(");");
			break;
		case TYPE_ASSERT_NULL:
			s.append("assertNull(").append(assertion).append(");");
			break;
		default:
			break;
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
