package eu.atac.atf.test.metadata;

import eu.atac.atf.main.ATF;

public class SComment extends SBase{
	private String   comment;
	private boolean multiple;
	private boolean notcomment;
	

	public SComment(String comment) {
		super();
		this.comment = comment;
		incrementPreTab2x();
		setPostNewLine(true);
	}
	
	public SComment(String comment, boolean notcomment) {
		this(comment);
		this.notcomment = notcomment;
	}


	@Override
	protected void printElement(StringBuilder s) {
		if(!notcomment){
			if(multiple){
				s.append("/*").append(ATF.NEW_LINE).
				append(comment).append(ATF.NEW_LINE).append("*/");
			}else{
				s.append("//").append(comment);
			}
		}else{
			s.append(comment);
		}
		
	}


	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public boolean isNotcomment() {
		return notcomment;
	}

	public void setNotcomment(boolean notcomment) {
		this.notcomment = notcomment;
	}
	
	
}
