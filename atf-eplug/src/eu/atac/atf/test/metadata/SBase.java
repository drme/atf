package eu.atac.atf.test.metadata;

import eu.atac.atf.main.ATF;

public abstract class SBase {
	private int preTab;
	private boolean preSpace;
	private boolean postSpace;
	private boolean semicolon;
	private boolean preNewLine;
	private boolean postNewLine;
	private boolean ignoreFormating;

	
	protected abstract void printElement(StringBuilder s);
	
	public void print(StringBuilder s){
		if(!ignoreFormating){
			pre(s);
			printElement(s);
			post(s);
		}else{
			printElement(s);
		}

	}
	
	protected void pre(StringBuilder s){
		for (int i = 0; i < preTab; i++) {
			s.append(ATF.TAB);
		}
		if(preNewLine){
			s.append(ATF.NEW_LINE);
		}
		if(preSpace){
			s.append(' ');
		}

	}
	
	protected void post(StringBuilder s){
		if(postSpace){
			s.append(' ');
		}
		if(semicolon){
			s.append(';');
		}
		if(postNewLine){
			s.append(ATF.NEW_LINE);
		}
	}
	
	protected void printPreTab(StringBuilder s, int size){
		for (int i = 0; i < size; i++) {
			s.append(ATF.TAB);
		}
		
	}

	public void incrementPreTab(){
		++preTab;
	}
	public void incrementPreTab2x(){
		++preTab;
		++preTab;
	}
	public boolean isPreSpace() {
		return preSpace;
	}

	public void setPreSpace(boolean preSpace) {
		this.preSpace = preSpace;
	}

	public boolean isPostSpace() {
		return postSpace;
	}

	public void setPostSpace(boolean postSpace) {
		this.postSpace = postSpace;
	}

	public boolean isSemicolon() {
		return semicolon;
	}

	public void setSemicolon(boolean semicolon) {
		this.semicolon = semicolon;
	}

	public boolean isPreNewLine() {
		return preNewLine;
	}

	public void setPreNewLine(boolean preNewLine) {
		this.preNewLine = preNewLine;
	}

	public boolean isPostNewLine() {
		return postNewLine;
	}

	public void setPostNewLine(boolean postNewLine) {
		this.postNewLine = postNewLine;
	}
	
	public void setSemicolonPostLine(boolean semicolon,boolean postNewLine) {
		this.semicolon = semicolon;
		this.postNewLine = postNewLine;
	}

	public int getPreTab() {
		return preTab;
	}

	public void setPreTab(int preTab) {
		this.preTab = preTab;
	}

	public boolean isIgnoreFormating() {
		return ignoreFormating;
	}

	public void setIgnoreFormating(boolean ignoreFormating) {
		this.ignoreFormating = ignoreFormating;
	}
	
	
}
