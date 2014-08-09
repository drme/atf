package eu.atac.atf.test.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ModelUtil;
import eu.sarunas.atf.generators.tests.RandomGenerator;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Method;

public class SForEach extends SBase{
	private List<SBase> body = new ArrayList<SBase>();
	private SVariable iterable;
	private String iterableField;
	private SVariable iterable_element;
	private Class iterableType;
	public SForEach(SVariable iterable,Class iterableType,String iterableField){
		this.iterable = iterable;
		this.iterableField = iterableField;
		this.iterableType = iterableType;
		int name = 123;//TODO
		iterable_element = new SVariable("ite"+name, this.iterableType, name);
		setPostNewLine(true);
		setPreTab(2);
	}

	@Override
	protected void printElement(StringBuilder s) {
//		for (iterable_type iterable_element : iterable) {}
		Method getter = ModelUtil.findGetter(iterable.getType(), iterableField);
		s.append(ATF.NEW_LINE);
		printPreTab(s, getPreTab());
		s.append("for(").append(iterable_element.getType().getFullName());
		s.append(' ').append(iterable_element.getName()).append(" : ");
		s.append(iterable.getName()).append(".").append(getter.getName()).append("()){").append(ATF.NEW_LINE);
		for (SBase base : body) {
			base.setPreTab(this.getPreTab());
			base.print(s);
		}
		printPreTab(s, getPreTab());
		s.append('}');
	}
	
	public void addBody(SBase sBase){
		this.body.add(sBase);
	}

	public String getIterableField() {
		return iterableField;
	}

	public void setIterableField(String iterableField) {
		this.iterableField = iterableField;
	}

	public SVariable getIterableElement() {
		return iterable_element;
	}

	public void setIterable_element(SVariable iterable_element) {
		this.iterable_element = iterable_element;
	}

	public Class getIterableType() {
		return iterableType;
	}

	public void setIterableType(Class iterableType) {
		this.iterableType = iterableType;
	}

}
