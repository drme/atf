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
import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.atf.utils.Logger;
import eu.sarunas.projects.atf.generators.value.RVBigDecimal;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class RandomGenerator implements ITestGenerator
{
	public RandomGenerator(Randomizer randomizer)
	{
		this.randomizer = randomizer;
	};

	public TestSuite generate(Method method, ITestsGeneratorManager manager, TestProject project) throws Exception
	{
		assert (null != method);

		TestSuite tests = new TestSuite(project, "TestSuite" + method.getName());

		while (false == manager.isDone())
		{
			TestCase testCase = generateTestCase(method, tests, manager.getValidator());

			if (true == manager.acceptTest(testCase))
			{
				tests.addTestCase(testCase);
			}
		}

		return tests;
	};

	private TestCase generateTestCase(Method method, TestSuite testSuite, ITestDataValidator validator)
	{
		TestCase testCase = new TestCase(method, testSuite);
		List<Parameter> parameters = method.getParameters();
		TestInput testInput = new TestInput(testCase);

		for (int i = 0; i < parameters.size(); i++)
		{
			testInput.addParameter(new TestInputParameter(parameters.get(i), this.randomizer.getRandomValue(parameters.get(i).getType(), validator)));
		}

		testCase.addInput(testInput);

		return testCase;
	};

	private Randomizer randomizer = null;











































// so, who has removed interface?




	public static String STRING_SET_1 = "zxcvbnmasdfghjklqwertyuiop";
	private Random random;

	@Deprecated
	public RandomGenerator() {
		super();
		random = new Random(System.currentTimeMillis());
	}

	@Deprecated
	private static RandomGenerator INSTANCE;
	
	@Deprecated
	public static RandomGenerator getInstance(){
		if(INSTANCE == null){
			INSTANCE = new RandomGenerator();
		}
		
		return INSTANCE;
	}

	@Deprecated
	public String generate(Type type)
	{
		if (false == type.isReferenceType())
		{
			if (type.getFullName().equals(ATF.JAVA_TYPE_INT))
			{
				return Integer.toString(generateRandomInt());
			}
			else if (type.getFullName().equals(ATF.JAVA_TYPE_DOUBLE))
			{
				return String.format("%1$," + ATF.ATF_DOUBLE_FORMAT, generateRandomDouble());
			}
			else
			{
				Logger.logger.severe(type.getFullName());
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
			}

		}
		else
		{
			throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000, type.getFullName());
			// if(type.getFullName().equals("BigDecimal")){
			// return String.format("new BigDecimal(%1$," + ATF.ATF_DOUBLE_FORMAT + ")", generateRandomDouble());
			// }else {
			//
			// }
		}
	}
	
	@Deprecated
	public int generateRandomInt(){
		return random.nextInt();
	}
	
	@Deprecated
	public boolean generateRandomBoolean(){
		return random.nextBoolean();
	}
	
	@Deprecated
	public double generateRandomDouble(){
		return random.nextDouble();
	}
	
	@Deprecated
	public String randomString(int length){
		StringBuilder s = new StringBuilder(length);
 		for (int i = 0; i < length; i++){
 			s.append(STRING_SET_1.charAt(random.nextInt(STRING_SET_1.length())));
 		}
 		return s.toString();
	}
	
	@Deprecated
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
	
	@Deprecated
	public String randomRegexString(String regex){
		Xeger xeger = new Xeger(regex);
 		return xeger.generate();
	}
	
	@Deprecated
	public RVBigDecimal randomBigDecimal(){
		return new RVBigDecimal(new BigDecimal(random.nextDouble()));
	}
}
