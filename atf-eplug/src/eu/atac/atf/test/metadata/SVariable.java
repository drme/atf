package eu.atac.atf.test.metadata;

import eu.sarunas.atf.meta.sut.Class;

public class SVariable extends SBase{
	private String name;
	private Class  type;
	private int id;
	
	public SVariable(String name, Class type, int id) {
		super();
		this.name = name;
		this.type = type;
		this.id   = id;
		setPreSpace(true);
		setPostSpace(true);
	}

	@Override
	public void printElement(StringBuilder builder) {
		builder.append(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
