package eu.atac.atf.test.metadata;

public class SParameter extends SBase {
	private SDeclaration declaration;
	private SVariable    variable;
	private SValue       value;
	
	
	public SParameter(SDeclaration declaration, SVariable variable,SValue value) {
		super();
		this.declaration = declaration;
		this.variable = variable;
		this.value = value;
	}


	@Override
	protected void printElement(StringBuilder s) {
		s.append(declaration.getType()).append(' ').append(variable.getName()).append(" = ").append(value.getValue());
	}
	
	public String getName(){
		return variable.getName();
	}

}
