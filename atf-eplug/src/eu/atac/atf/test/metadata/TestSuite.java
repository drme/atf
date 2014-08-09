package eu.atac.atf.test.metadata;

import java.util.ArrayList;
import java.util.List;

public class TestSuite {
	private String directory;
	private List<TestCase> testCases = new ArrayList<TestCase>();

	public void addTestCase(TestCase testCase) {
		this.testCases.add(testCase);
	}

	public TestCase getLast(){
		if(testCases.isEmpty()){
			return null;
		}
		return testCases.get(testCases.size() - 1);
	}
	public String getDirectory() {
		return directory;
	}

	public List<TestCase> getTestCases() {
		return testCases;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	
}
