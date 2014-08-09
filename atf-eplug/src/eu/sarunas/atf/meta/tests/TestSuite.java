package eu.sarunas.atf.meta.tests;

import java.util.*;
import javax.persistence.*;

@Entity(name="testu_rinkinys")
public class TestSuite
{
	public TestSuite()
	{
	};
	
	public TestSuite(TestProject project, String name)
	{
		this.testProject = project;
		this.name = name;
	};
	
	public void addTestCase(TestCase testCase)
	{
		this.testCases.add(testCase);
	};

	public List<TestCase> getTestCases()
    {
    	return testCases;
    };

    public Long getId()
    {
		return this.id;
	};

	public void setId(Long id)
	{
		this.id = id;
	};
	
	public String getName()
	{
		return name;
	};

	public void setName(String name)
	{
		this.name = name;
	};
	
	public TestProject getTestProject()
	{
		return this.testProject;
	};

	public void setTestProject(TestProject testProject)
	{
		this.testProject = testProject;
	};
	
	public String toString()
	{
		String result = "TestSuite: " + this.name + "\n";
		
		for (TestCase testCase : this.testCases)
		{
			result += testCase.toString();
		}
		
		return result;
	};
    
	@Id()
	@Column(name = "id")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;
	@Column(name = "pavadinimas")	
	private String name = null;
	//@Transient
	@OneToMany(cascade=CascadeType.ALL, mappedBy="testSuite")
	private List<TestCase> testCases = new ArrayList<TestCase>();
    @ManyToOne
	@JoinColumn(name="id_test_projektas", nullable=false)
	private TestProject testProject = null;
};
