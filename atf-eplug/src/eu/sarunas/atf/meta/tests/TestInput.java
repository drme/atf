package eu.sarunas.atf.meta.tests;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="testas_metodo")
public class TestInput
{
	public TestInput()
	{
	};
	
	public TestInput(TestCase testCase)
	{
		this.testCase = testCase;
	};
	
	public void addParameter(TestInputParameter parameter)
    {
		this.inputParameters.add(parameter);
    };

	public List<TestInputParameter> getInputParameters()
    {
    	return this.inputParameters;
    };
    
    public TestCase getTestCase()
    {
    	return this.testCase;
    };
    
    public Long getId()
    {
    	return this.id;
    };
    
	public String toString()
	{
		String result = "\tTestInput: " + "\n";
		
		for (TestInputParameter parameter : this.inputParameters)
		{
			result += parameter.toString();
		}
		
		return result;
	};    
    
    
    @Id()
	@Column(name = "id_testas_metodas")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;
    @ManyToOne
	@JoinColumn(name="id_testas", nullable=false)
    private TestCase testCase = null;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="testInput")
	private List<TestInputParameter> inputParameters = new ArrayList<TestInputParameter>();
};
