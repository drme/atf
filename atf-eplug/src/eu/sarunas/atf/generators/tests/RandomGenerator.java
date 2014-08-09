package eu.sarunas.atf.generators.tests;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import nl.flotsam.xeger.Xeger;
import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ATFException;
import eu.sarunas.atf.generators.tests.data.Randomizer;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Parameter;
import eu.sarunas.atf.meta.tests.TestCase;
import eu.sarunas.atf.meta.tests.TestInput;
import eu.sarunas.atf.meta.tests.TestInputParameter;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.meta.tests.TestSuite;
import eu.sarunas.projects.atf.generators.value.RVBigDecimal;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class RandomGenerator implements ITestGenerator 
{
public RandomGenerator(Randomizer randomizer)
{
	this.randomizer = randomizer;
};	

public TestSuite generate(Method method, ITestsGeneratorManager manager, TestProject project)
{
	assert (null != method);
	
	TestSuite tests = new TestSuite(project, "TestSuite" + method.getName());
	
	while (false == manager.isDone())
	{
		TestCase testCase = generateTestCase(method, tests);
		
		if (true == manager.acceptTest(testCase))
		{
			tests.addTestCase(testCase);
		}
	}
	
	return tests;
};

private static int cpt = 0;

private TestCase generateTestCase(Method method, TestSuite testSuite)
{
	TestCase testCase = new TestCase(method/*, cpt++*/, testSuite);
	
	List<Parameter> parameters = method.getParameters();
	
	TestInput testInput = new TestInput(testCase);
	
	for (int i = 0; i < parameters.size(); i++)
	{
		testInput.addParameter(new TestInputParameter(parameters.get(i), this.randomizer.getRandomValue(parameters.get(i).getType())));
	}
	
	testCase.addInput(testInput);
	
	return testCase;
};


private Randomizer randomizer = null;












// so, who has removed interface?




	public static String STRING_SET_1 = "zxcvbnmasdfghjklqwertyuiop";
	private Random random;
	
	public RandomGenerator() {
		super();
		random = new Random(System.currentTimeMillis());
	}

	private static RandomGenerator INSTANCE;
	
	public static RandomGenerator getInstance(){
		if(INSTANCE == null){
			INSTANCE = new RandomGenerator();
		}
		
		return INSTANCE;
	}

	public String generate(Type type){
		if(!type.isReferenceType()){
			if(type.getFullName().equals(ATF.JAVA_TYPE_INT)){
				return Integer.toString(generateRandomInt());
			}else if(type.getFullName().equals(ATF.JAVA_TYPE_DOUBLE)){
				return String.format("%1$," + ATF.ATF_DOUBLE_FORMAT, generateRandomDouble());
			}else {
				ATF.log(type.getFullName());
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
			}
			
		}else {
			throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000, type.getFullName());
//			if(type.getFullName().equals("BigDecimal")){
//				return String.format("new BigDecimal(%1$," + ATF.ATF_DOUBLE_FORMAT + ")", generateRandomDouble());
//			}else {
//				
//			}
		}
	}
	
	public int generateRandomInt(){
		return random.nextInt();
	}
	
	public boolean generateRandomBoolean(){
		return random.nextBoolean();
	}
	
	public double generateRandomDouble(){
		return random.nextDouble();
	}
	
	public String randomString(int length){
		StringBuilder s = new StringBuilder(length);
 		for (int i = 0; i < length; i++){
 			s.append(STRING_SET_1.charAt(random.nextInt(STRING_SET_1.length())));
 		}
 		return s.toString();
	}
	
	public String randomString(int minLenght, int maxLenght, boolean nullable){
		int length = random.nextInt(maxLenght - minLenght);
		if(length == 0){
			if(nullable){
				return random.nextBoolean() ? null : "";
			}else{
				return "";
			}
		}
		StringBuilder s = new StringBuilder(length);
 		for (int i = 0; i < length; i++){
 			s.append(STRING_SET_1.charAt(random.nextInt(STRING_SET_1.length())));
 		}
 		return s.toString();
	}
	
	public String randomRegexString(String regex){
		Xeger xeger = new Xeger(regex);
 		return xeger.generate();
	}
	
	public RVBigDecimal randomBigDecimal(){
		return new RVBigDecimal(new BigDecimal(random.nextDouble()));
	}
}
