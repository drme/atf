package eu.atac.atf.test.metadata;

import java.util.ArrayList;
import java.util.List;

import eu.atac.atf.main.ATF;

import static eu.atac.atf.main.ATF.NEW_LINE;
import static eu.atac.atf.main.ATF.TAB;

public class SMethod extends SBase {
	private List<String> anotations = new ArrayList<String>(2);
	private List<SBase> body = new ArrayList<SBase>();
	private String name;
	private String type = "void"; // FIX
	private String flag = "public"; // FIX
	private List<SBase> iniBody = new ArrayList<SBase>();
	private List<SBase> assertBody = new ArrayList<SBase>();
	private MethodObjectRepository objectRepository;
	
	public SMethod(){
		objectRepository = new MethodObjectRepository();
	}
	
	@Override
	protected void printElement(StringBuilder s) {
		for (String anotation : anotations) {
			s.append(NEW_LINE).append(TAB).append(anotation);
		}

		s.append(NEW_LINE).append(TAB).append(flag).append(' ').append(type).append(' ').append(name).append(' ');

		s.append('(').append(' ').append(')');

		s.append('{').append(NEW_LINE);

		for (SBase element : body) {
			element.print(s);
		}
		
		for (SBase element : iniBody) {
			element.print(s);
		}
		
		for (SBase element : assertBody) {
			element.print(s);
		}

		s.append(NEW_LINE).append(TAB).append('}');

	}

	public SVariable findVariable(int id) {
		for (int i = 0; i < body.size(); i++) {
			if (body.get(i) instanceof SVariable) {
				if (((SVariable) body.get(i)).getId() == id) {
					return (SVariable) body.get(i);
				}
			}
		}

		return null;
	}

	@Deprecated
	public void print(StringBuilder s) {

		for (String anotation : anotations) {
			s.append(NEW_LINE).append(TAB).append(anotation);
		}

		s.append(NEW_LINE).append(TAB).append(flag).append(' ').append(type).append(' ').append(name).append(' ');

		s.append('(').append(' ').append(')');

		s.append('{').append(NEW_LINE);

		for (SBase element : body) {
			element.print(s);
		}

		s.append(NEW_LINE).append(TAB).append('}');
	}

	@Deprecated
	public String print() {
		StringBuilder s = new StringBuilder();

		print(s);

		return s.toString();
	}

	public void addAnotation(String anotation) {
		this.anotations.add(anotation);
	}

	public void addBodyElement(SBase print) {
		this.body.add(print);
	}
	public void addIniBodyElement(SBase print) {
		this.iniBody.add(print);
	}
	public void addAssertBodyElement(SBase print) {
		this.assertBody.add(print);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getAnotations() {
		return anotations;
	}

	public String getFlag() {
		return flag;
	}

	public void setAnotations(List<String> anotations) {
		this.anotations = anotations;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public MethodObjectRepository getObjectRepository() {
		return objectRepository;
	}
	
	
}
