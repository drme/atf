package eu.atac.atf.main;

import java.util.ArrayList;
import java.util.List;



public class XMLElement {
	private String value;
	private String tag;
	private List<XMLElement> child;
	private List<String> parameters;
	
	public XMLElement(String tag) {
		super();
		this.tag = tag;
		child = new ArrayList<XMLElement>(3);
	}
	
	public XMLElement(String tag,String value) {
		super();
		this.value = value;
		this.tag = tag;
		child = new ArrayList<XMLElement>(3);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void addChild(XMLElement xmlElement){
		child.add(xmlElement);
	}
	
	public void addParam(String param){
		if(parameters == null ){
			parameters = new ArrayList<String>(3);
		}
		parameters.add(param);
	}
	
	public int getParamCount(){
		if(parameters == null ){
			return 0;
		}
		return parameters.size();
	}
	
	public void toString(StringBuilder s, String tab) {
		tab += " ";
		if(value != null){
			s.append("\n").append(tab).append('<').append(tag).append(">");
			if(!value.equals(""))
			{
				s.append("<![CDATA[").append(value).append("]]>");
			}
			s.append("</").append(tag).append(">");
		}else if(child.size() > 0){
			s.append("\n").append(tab).append("<").append(tag);
			if(parameters!=null){
				for (String param : parameters) {
					s.append(' ').append(param).append(' ');
				}
			}
			s.append(">");
			for (int i = 0; i < child.size(); i++) {
				child.get(i).toString(s, tab);
			}
			s.append("\n").append(tab).append("</").append(tag).append(">");
		}else{
			if(parameters!=null){
				s.append("\n").append(tab).append("<").append(tag);
				for (String param : parameters) {
					s.append(' ').append(param).append(' ');
				}
				s.append("/>");
			}else{
				s.append("\n").append(tab).append('<').append(tag).append("/>");
			}
		}
	}
	
	private String stripAll(String s){
		if(s == null || s.length() == 0){
			return s;
		}
	    char[] oldChars = new char[s.length()];
	    s.getChars(0, s.length(), oldChars, 0);
	    char[] newChars = new char[s.length()];
	    int newLen = 0;
	    for (int j = 0; j < s.length(); j++) {
	        char ch = oldChars[j];
	        if (ch >= ' ') {
	            newChars[newLen] = ch;
	            newLen++;
	        }
	    }
	    s = new String(newChars, 0, newLen);
	    return s;
	}

}
