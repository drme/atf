package eu.atac.atf.test.metadata;

import java.util.ArrayList;
import java.util.List;

import eu.atac.atf.main.ATF;


public class TestCase {
	private String name;
    @Deprecated
	private String preTests;
    @Deprecated
	private String postTests;
	private List<Test> tests = new ArrayList<Test>();
	
	public String getContent(){
//		StringBuilder s = new StringBuilder(300);
//		if(preTests != null)
//			s.append(preTests);
//		for (Test test : tests) {
//			s.append(test.getBody()).append(ATF.NEW_LINE);
//		}
//		if(postTests != null)
//			s.append(postTests);
//		return s.toString();
		if(tests.size() > 0){
			return tests.get(0).getBody();
		}
		return "//";
	}

	public void addTest(Test test) {
		this.tests.add(test);
	}

	public Test getLast(){
		if(tests.isEmpty()){
			return null;
		}
		return tests.get(tests.size() - 1);
	}
	
	public String getName() {
		return name;
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public String getPreTests() {
		return preTests;
	}

	public void setPreTests(String preTests) {
		this.preTests = preTests;
	}

	public String getPostTests() {
		return postTests;
	}

	public void setPostTests(String postTests) {
		this.postTests = postTests;
	}

}
