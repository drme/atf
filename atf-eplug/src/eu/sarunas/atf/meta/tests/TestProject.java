package eu.sarunas.atf.meta.tests;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity(name="test_projektas")
public class TestProject
{
	public TestProject()
	{
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
	
	public List<TestSuite> getTestSuites()
	{
		return this.testSuites;
	};
    
	@Id()
	@Column(name = "id")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;
	@Column(name = "pavadinimas")	
	private String name = null;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="testProject")
	private List<TestSuite> testSuites = new ArrayList<TestSuite>();
};
