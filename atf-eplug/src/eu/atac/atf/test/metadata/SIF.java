package eu.atac.atf.test.metadata;

import java.util.ArrayList;
import java.util.List;

import eu.atac.atf.main.ATF;

public class SIF extends SBase{
	private List<SBase> body = new ArrayList<SBase>();
	private SBase expresion;
	

	public SIF(SBase expresion) {
		super();
		this.expresion = expresion;
		setPostNewLine(true);
	}


	@Override
	protected void printElement(StringBuilder s) {
		printPreTab(s, 1);
		s.append("if(");
		expresion.printElement(s);
		s.append("){").append(ATF.NEW_LINE);
		
		for (SBase base : body) {
			base.setPreTab(4);
			base.print(s);
		}
		
		s.append(ATF.NEW_LINE);
		printPreTab(s, 3);
		s.append("}");
	}
	
	public void addBody(SBase sBase){
		this.body.add(sBase);
	}
	
}
