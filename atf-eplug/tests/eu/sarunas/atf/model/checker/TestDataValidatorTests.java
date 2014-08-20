package eu.sarunas.atf.model.checker;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.junit.Assert;
import org.junit.Test;

import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;
import eu.atac.atf.main.Files;
import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.sut.Modifier;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.sut.basictypes.BooleanType;
import eu.sarunas.atf.meta.sut.basictypes.CollectionStyle;
import eu.sarunas.atf.meta.sut.basictypes.CollectionType;
import eu.sarunas.atf.meta.sut.basictypes.DateType;
import eu.sarunas.atf.meta.sut.basictypes.DoubleType;
import eu.sarunas.atf.meta.sut.basictypes.IntegerType;
import eu.sarunas.atf.meta.sut.basictypes.LargeNumberType;
import eu.sarunas.atf.meta.sut.basictypes.ObjectType;
import eu.sarunas.atf.meta.sut.basictypes.StringType;
import eu.sarunas.atf.meta.testdata.TestObjectCollection;
import eu.sarunas.atf.meta.testdata.TestObjectComplex;
import eu.sarunas.atf.meta.testdata.TestObjectField;
import eu.sarunas.atf.meta.testdata.TestObjectSimple;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class TestDataValidatorTests
{


	public static void main(String[] args) throws Exception {
		String pck = "org.inspection_plusplus";
		String jar = "./lib/IppWebService.jar";
		
		Class<?>[] classes = getClasses(pck,jar);
		 
		Project model = new Project("BasicTypeModel", null);
		eu.sarunas.atf.meta.sut.Package rootPackage = new eu.sarunas.atf.meta.sut.Package(model, pck, null);
		model.addPackage(rootPackage);
		
		eu.sarunas.atf.meta.sut.Class[] metaClassArray = new eu.sarunas.atf.meta.sut.Class[classes.length];
		
		for (int i = 0; i < classes.length; i++) {
			Class<?> cls = classes[i];
			String name = cls.getName().substring(cls.getName().lastIndexOf(".") + 1);
			eu.sarunas.atf.meta.sut.Class metaClass = new eu.sarunas.atf.meta.sut.Class(name, /* cls.getModifiers() */ EnumSet.of(Modifier.Public), rootPackage, null);
			rootPackage.addClass(metaClass);
			metaClassArray[i] = metaClass;
		}
		
		for (int i = 0; i < classes.length; i++) {
			Class<?> cls = classes[i];
			String name = cls.getName().substring(cls.getName().lastIndexOf(".") + 1);
			eu.sarunas.atf.meta.sut.Class metaClass = metaClassArray[i];
			for (java.lang.reflect.Field m : cls.getDeclaredFields()) {
				Field fieldBalance = null;
				if(m.getName().equals("operand")){
					fieldBalance = new eu.sarunas.atf.meta.sut.Field(m.getName(), Modifier.Public, new CollectionType(CollectionStyle.List,getType("Operand",rootPackage)) , metaClass, null);
				}else if(m.getName().equals("calculationNominalElement")){
					fieldBalance = new eu.sarunas.atf.meta.sut.Field(m.getName(), Modifier.Public, new CollectionType(CollectionStyle.List,getType("CalculationNominalElement",rootPackage)) , metaClass, null);
				}else{
					fieldBalance = new eu.sarunas.atf.meta.sut.Field(m.getName(), Modifier.Public, getType(m.getType().getName(),rootPackage), metaClass, null);
				}
				
				metaClass.addField(fieldBalance);
			}
			if(cls.getSuperclass() != null){
				metaClass.setSuperClass(getType(cls.getSuperclass().getName(), rootPackage));
			}else{
				System.out.println("Super is null:" + name);
			}
		}
		//----------------------------------------------------------------------------------------------------------//
		TestDataValidator validator = new TestDataValidator();
		StandaloneFacade.INSTANCE.initialize(TestDataValidatorTests.class.getResource("/log4j.properties"));
		URL oclURL   = TestDataValidatorTests.class.getResource("/eu/sarunas/atf/model/checker/IppWebservice.ocl");
		
		String constraints = Files.getFileContents(oclURL.getFile());
		TestObjectComplex testDataToValidate  = null;
		TestObjectComplex testDataToValidate2 = null;
		TestObjectComplex testDataToValidate3 = null;
		//1 
		//context IPE inv: name->size() < 3 and name->size() > 0 --Neveikia

		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("IPE"));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPE").getField("name"), new TestObjectSimple<String>("", new StringType(), "abc")));
		Assert.assertTrue(validator.validate(model,constraints,testDataToValidate).isValid());
		
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("IPE"));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPE").getField("name"), new TestObjectSimple<String>("", new StringType(), "abcde")));

		Assert.assertTrue(validator.validate(model,constraints,testDataToValidate).isValid());
		
		//2
		//context IPEGeometric 	inv: self.materialThickness > 0
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("IPEGeometric"));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPEGeometric").getField("materialThickness"), new TestObjectSimple<Double>("", new DoubleType(), 2.4)));
		Assert.assertTrue(validator.validate(model,constraints,testDataToValidate).isValid());
		
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("IPEGeometric"));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPEGeometric").getField("materialThickness"), new TestObjectSimple<Double>("", new DoubleType(), -2.4)));
		Assert.assertFalse(validator.validate(model,constraints,testDataToValidate).isValid());
		
		//3
		//context IPESingle    inv: self.useLeft <> self.useRight
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("IPESingle"));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPESingle").getField("useLeft"), new TestObjectSimple<Boolean>("", new BooleanType(), false)));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPESingle").getField("useRight"), new TestObjectSimple<Boolean>("", new BooleanType(), true)));
		Assert.assertTrue(validator.validate(model,constraints,testDataToValidate).isValid());
		
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("IPESingle"));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPESingle").getField("useLeft"), new TestObjectSimple<Boolean>("", new BooleanType(), false)));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPESingle").getField("useRight"), new TestObjectSimple<Boolean>("", new BooleanType(), false)));
		Assert.assertFalse(validator.validate(model,constraints,testDataToValidate).isValid());
		
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("IPESingle"));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPESingle").getField("useLeft"), new TestObjectSimple<Boolean>("", new BooleanType(), false)));
		Assert.assertTrue(validator.validate(model,constraints,testDataToValidate).isValid());
		
		//4
		//context TolAngle
		//inv: criterion.upperToleranceValue > criterion.lowerToleranceValue	
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("TolAngle"));
		Assert.assertFalse(validator.validate(model,constraints,testDataToValidate).isValid());
		
		testDataToValidate2 = new TestObjectComplex("test", rootPackage.getClass("IntervalToleranceCriterion"));
		testDataToValidate2.addField(new TestObjectField(rootPackage.getClass("IntervalToleranceCriterion").getField("upperToleranceValue"), new TestObjectSimple<Double>("", new DoubleType(), 3.5)));
		testDataToValidate2.addField(new TestObjectField(rootPackage.getClass("IntervalToleranceCriterion").getField("lowerToleranceValue"), new TestObjectSimple<Double>("", new DoubleType(), 4.1)));
		Assert.assertFalse(validator.validate(model,constraints,testDataToValidate2).isValid());
		
		testDataToValidate3 = new TestObjectComplex("test", rootPackage.getClass("IntervalToleranceCriterion"));
		testDataToValidate3.addField(new TestObjectField(rootPackage.getClass("IntervalToleranceCriterion").getField("upperToleranceValue"), new TestObjectSimple<Double>("", new DoubleType(), 3.5)));
		testDataToValidate3.addField(new TestObjectField(rootPackage.getClass("IntervalToleranceCriterion").getField("lowerToleranceValue"), new TestObjectSimple<Double>("", new DoubleType(), 2.1)));
		Assert.assertTrue(validator.validate(model,constraints,testDataToValidate3).isValid());
		
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("TolAngle"));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("TolAngle").getField("criterion"), testDataToValidate2));
		Assert.assertFalse(validator.validate(model,constraints,testDataToValidate).isValid());
		
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("TolAngle"));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("TolAngle").getField("criterion"), testDataToValidate3));
		Assert.assertTrue(validator.validate(model,constraints,testDataToValidate).isValid());
		
		//5	
		//	context IPESurfacePoint
		//		inv: self.calculationNominalElement -> size() = 1
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("IPESurfacePoint"));
		Assert.assertFalse(validator.validate(model,constraints,testDataToValidate).isValid());
		
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("IPESurfacePoint"));
		testDataToValidate2 = new TestObjectCollection("test2", rootPackage.getClass("CalculationNominalElement"),1,CollectionStyle.List);
		((TestObjectCollection)testDataToValidate2).addElement(new TestObjectComplex("test3", rootPackage.getClass("CalculationNominalElement")));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPESurfacePoint").getField("calculationNominalElement"), testDataToValidate2));
		Assert.assertFalse(validator.validate(model,constraints,testDataToValidate).isValid());
		
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("IPESurfacePoint"));
		testDataToValidate2 = new TestObjectCollection("test2", rootPackage.getClass("CalculationNominalElement"),2,CollectionStyle.List);
		((TestObjectCollection)testDataToValidate2).addElement(new TestObjectComplex("test3", rootPackage.getClass("CalculationNominalElement")));
		((TestObjectCollection)testDataToValidate2).addElement(new TestObjectComplex("test4", rootPackage.getClass("CalculationNominalElement")));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("IPESurfacePoint").getField("calculationNominalElement"), testDataToValidate2));
		Assert.assertFalse(validator.validate(model,constraints,testDataToValidate).isValid());
		
		//6 context CalculationNominalElement 	inv: operation='IPE_LiesOn'
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("CalculationNominalElement"));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("CalculationNominalElement").getField("operation"), new TestObjectSimple<String>("", new StringType(), "XX")));
		Assert.assertFalse(validator.validate(model,constraints,testDataToValidate).isValid());
		
		testDataToValidate = new TestObjectComplex("test", rootPackage.getClass("CalculationNominalElement"));
		testDataToValidate2 = new TestObjectCollection("test1", rootPackage.getClass("Operand"),2,CollectionStyle.List);
		TestObjectComplex testDataToValidateOp1 = new TestObjectComplex("test2", rootPackage.getClass("Operand"));
		testDataToValidateOp1.addField(new TestObjectField(rootPackage.getClass("Operand").getField("role"), new TestObjectSimple<String>("", new StringType(), "IPEIPE1")));
		TestObjectComplex testDataToValidateOp2 = new TestObjectComplex("test3", rootPackage.getClass("Operand"));
		testDataToValidateOp2.addField(new TestObjectField(rootPackage.getClass("Operand").getField("role"), new TestObjectSimple<String>("", new StringType(), "IPEIPE2")));
		
		
		((TestObjectCollection)testDataToValidate2).addElement(testDataToValidateOp1);
		((TestObjectCollection)testDataToValidate2).addElement(testDataToValidateOp2);
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("CalculationNominalElement").getField("operation"), new TestObjectSimple<String>("", new StringType(), "IPELiesOn")));
		testDataToValidate.addField(new TestObjectField(rootPackage.getClass("CalculationNominalElement").getField("operand"), testDataToValidate2));
		Assert.assertTrue(validator.validate(model,constraints,testDataToValidate).isValid());
		
		
		TestObjectComplex testDataToValidateSurf = new TestObjectComplex("test", rootPackage.getClass("IPESurfacePoint"));
		testDataToValidateSurf.addField(new TestObjectField(rootPackage.getClass("IPESurfacePoint").getField("calculationNominalElement"), testDataToValidate));
		Assert.assertTrue(validator.validate(model,constraints,testDataToValidateSurf).isValid());
		
		System.out.println(".");

	}
	
	private static eu.sarunas.projects.atf.metadata.generic.Type getType(String type,eu.sarunas.atf.meta.sut.Package pack){
		switch (type)
		{
			case "boolean":
				return new BooleanType();
			case "java.lang.Boolean":
				return new BooleanType();
			case "double":
				return new DoubleType();
			case "java.lang.Double":
				return new DoubleType();
//			case Signature.SIG_CHAR:
//				return typeChar;
//			case Signature.SIG_DOUBLE:
//			case "QDouble;": // not nice
//				return typeDouble;
//			case Signature.SIG_FLOAT:
//				return typeFloat;
			case "int":
				return new IntegerType();
//			case Signature.SIG_LONG:
//				return typeLong;
//			case Signature.SIG_SHORT:
//				return typeShort;
//			case Signature.SIG_VOID:
//				return typeVoid;
			case "java.lang.String": // not nice
				return new StringType();
//			case "QObject;":
//				return typeObject;
//			case "Ljava.math.BigDecimal;": // not nice
			case "java.math.BigDecimal": // not nice
				return new LargeNumberType();
			case "java.math.BigInteger": // not nice
				return new LargeNumberType();
			case "QBigInteger;": // not nice
				return new LargeNumberType("BigInteger");
			case "java.util.List": // not nice
				return new CollectionType(CollectionStyle.List,new ObjectType());
//			case "QDate;": // not nice
//				return typeDate;
			case "javax.xml.datatype.XMLGregorianCalendar":
				return new DateType("XMLGregorianCalendar");
			default:
				eu.sarunas.projects.atf.metadata.generic.Type tp = pack.getClass(type.substring(type.lastIndexOf(".") + 1 ));
				if(tp != null)
					return tp;
				if(type.indexOf("javax.") >= 0 || type.indexOf("java.net") >= 0 || type.equals("java.lang.Object") )
					return new ObjectType();
				System.err.println(type);
				return null;
		}
	}
	
	
	@Test
	public void testBasicTypeValid() throws Exception
	{
		StandaloneFacade.INSTANCE.initialize(TestDataValidatorTests.class.getResource("/log4j.properties"));
		URL oclURL   = TestDataValidatorTests.class.getResource("/eu/sarunas/atf/model/checker/Test1.ocl");
		
		TestDataValidator validator = new TestDataValidator();

		Project model = new Project("BasicTypeModel", null);
		eu.sarunas.atf.meta.sut.Package rootPackage = new eu.sarunas.atf.meta.sut.Package(model, "test.model", null);
		eu.sarunas.atf.meta.sut.Class accountClass = new eu.sarunas.atf.meta.sut.Class("Account", EnumSet.of(Modifier.Public), rootPackage, null);
		Field fieldBalance = new eu.sarunas.atf.meta.sut.Field("balance", Modifier.Public, new IntegerType(), accountClass, null);

		rootPackage.addClass(accountClass);
		model.addPackage(rootPackage);
		accountClass.addField(fieldBalance);

		//String constraints = "context Account; inv positiveBalance: self.balance >= 0;";
		String constraints = Files.getFileContents(oclURL.getFile());
		
		TestObjectComplex testDataToValidate = new TestObjectComplex("accountInstance", accountClass);
		testDataToValidate.addField(new TestObjectField(fieldBalance, new TestObjectSimple<Integer>("", new IntegerType(), 100)));

		Assert.assertTrue(validator.validate(model,constraints,testDataToValidate).isValid());
	};

	@Test
	public void testBasicTypeInValid() throws Exception
	{
		StandaloneFacade.INSTANCE.initialize(TestDataValidatorTests.class.getResource("/log4j.properties"));
		URL oclURL   = TestDataValidatorTests.class.getResource("/eu/sarunas/atf/model/checker/Test1.ocl");
		
		TestDataValidator validator = new TestDataValidator();

		Project model = new Project("BasicTypeModel", null);
		eu.sarunas.atf.meta.sut.Package rootPackage = new eu.sarunas.atf.meta.sut.Package(model, "test.model", null);
		eu.sarunas.atf.meta.sut.Class accountClass = new eu.sarunas.atf.meta.sut.Class("Account", EnumSet.of(Modifier.Public), rootPackage, null);
		Field fieldBalance = new eu.sarunas.atf.meta.sut.Field("balance", Modifier.Public, new IntegerType(), accountClass, null);

		rootPackage.addClass(accountClass);
		model.addPackage(rootPackage);
		accountClass.addField(fieldBalance);

		//String constraints = "context Account; inv positiveBalance: self.balance >= 0;";
		String constraints = Files.getFileContents(oclURL.getFile());

		TestObjectComplex testDataToValidate = new TestObjectComplex("accountInstance", accountClass);
		testDataToValidate.addField(new TestObjectField(fieldBalance, new TestObjectSimple<Integer>("", new IntegerType(), -100)));

		Assert.assertTrue(validator.validate(model, constraints, testDataToValidate).isValid());
	};

	@Test
	public void testComplexTypeValid() throws Exception
	{
		StandaloneFacade.INSTANCE.initialize(TestDataValidatorTests.class.getResource("/log4j.properties"));
		URL oclURL   = TestDataValidatorTests.class.getResource("/eu/sarunas/atf/model/checker/Test2.ocl");
		
		TestDataValidator validator = new TestDataValidator();

		Project model = new Project("ComplexTypeModel", null);
		eu.sarunas.atf.meta.sut.Package rootPackage = new eu.sarunas.atf.meta.sut.Package(model, "test.model", null);

		eu.sarunas.atf.meta.sut.Class personClass = new eu.sarunas.atf.meta.sut.Class("Person", EnumSet.of(Modifier.Public), rootPackage, null);
		Field fieldAge = new eu.sarunas.atf.meta.sut.Field("age", Modifier.Public, new IntegerType(), personClass, null);
		personClass.addField(fieldAge);

		eu.sarunas.atf.meta.sut.Class accountClass = new eu.sarunas.atf.meta.sut.Class("Account", EnumSet.of(Modifier.Public), rootPackage, null);
		Field fieldBalance = new eu.sarunas.atf.meta.sut.Field("balance", Modifier.Public, new IntegerType(), accountClass, null);
		Field fieldPerson = new eu.sarunas.atf.meta.sut.Field("holder", Modifier.Public, personClass, accountClass, null);

		rootPackage.addClass(accountClass);
		rootPackage.addClass(personClass);
		model.addPackage(rootPackage);
		accountClass.addField(fieldBalance);
		accountClass.addField(fieldPerson);

		//String constraints = "context Account; inv positiveBalance: self.balance >= 0; inv holderAge: self.holder.age >= 18";
		String constraints = Files.getFileContents(oclURL.getFile());
		
		TestObjectComplex personObject = new TestObjectComplex("personInstance", personClass);
		personObject.addField(new TestObjectField(fieldAge, new TestObjectSimple<Integer>("test.model", new IntegerType(), 21)));

		TestObjectComplex testDataToValidate = new TestObjectComplex("accountInstance", accountClass);
		testDataToValidate.addField(new TestObjectField(fieldBalance, new TestObjectSimple<Integer>("", new IntegerType(), 100)));
		testDataToValidate.addField(new TestObjectField(fieldPerson, personObject));

		Assert.assertTrue(validator.validate(model, constraints, testDataToValidate).isValid());
	};

	@Test
	public void testComplexTypeInValid() throws Exception
	{
		StandaloneFacade.INSTANCE.initialize(TestDataValidatorTests.class.getResource("/log4j.properties"));
		URL oclURL   = TestDataValidatorTests.class.getResource("/eu/sarunas/atf/model/checker/Test2.ocl");
		
		TestDataValidator validator = new TestDataValidator();

		Project model = new Project("ComplexTypeModel", null);
		eu.sarunas.atf.meta.sut.Package rootPackage = new eu.sarunas.atf.meta.sut.Package(model, "test.model", null);

		eu.sarunas.atf.meta.sut.Class personClass = new eu.sarunas.atf.meta.sut.Class("Person", EnumSet.of(Modifier.Public), rootPackage, null);
		Field fieldAge = new eu.sarunas.atf.meta.sut.Field("age", Modifier.Public, new IntegerType(), personClass, null);
		personClass.addField(fieldAge);

		eu.sarunas.atf.meta.sut.Class accountClass = new eu.sarunas.atf.meta.sut.Class("Account", EnumSet.of(Modifier.Public), rootPackage, null);
		Field fieldBalance = new eu.sarunas.atf.meta.sut.Field("balance", Modifier.Public, new IntegerType(), accountClass, null);
		Field fieldPerson = new eu.sarunas.atf.meta.sut.Field("holder", Modifier.Public, personClass, accountClass, null);

		rootPackage.addClass(accountClass);
		rootPackage.addClass(personClass);
		model.addPackage(rootPackage);
		accountClass.addField(fieldBalance);
		accountClass.addField(fieldPerson);

		//String constraints = "context Account; inv positiveBalance: self.balance >= 0; inv holderAge: self.holder.age >= 18";
		String constraints = Files.getFileContents(oclURL.getFile());

		TestObjectComplex personObject = new TestObjectComplex("personInstance", personClass);
		personObject.addField(new TestObjectField(fieldAge, new TestObjectSimple<Integer>("", new IntegerType(), 15)));

		TestObjectComplex testDataToValidate = new TestObjectComplex("accountInstance", accountClass);
		testDataToValidate.addField(new TestObjectField(fieldBalance, new TestObjectSimple<Integer>("", new IntegerType(), 100)));
		testDataToValidate.addField(new TestObjectField(fieldPerson, personObject));

		Assert.assertTrue(validator.validate(model, constraints, testDataToValidate).isValid());
	};
	
	public static Class<?>[] getClasses(String packageName, String jarName) throws ClassNotFoundException {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		packageName = packageName.replaceAll("\\.", "/");
		File f = new File(jarName);
		if (f.exists()) {
			try {
				JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
				JarEntry jarEntry;

				while (true) {
					jarEntry = jarFile.getNextJarEntry();
					if (jarEntry == null) {
						break;
					}
					if ((jarEntry.getName().startsWith(packageName)) && (jarEntry.getName().endsWith(".class"))) {
						classes.add(Class.forName(jarEntry.getName().replaceAll("/", "\\.").substring(0, jarEntry.getName().length() - 6)));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Class<?>[] classesA = new Class[classes.size()];
			classes.toArray(classesA);
			return classesA;
		} else
			return null;
	}

};
