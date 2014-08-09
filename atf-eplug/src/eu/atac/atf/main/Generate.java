package eu.atac.atf.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tudresden.ocl20.pivot.model.IModel;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.standalone.facade.StandaloneFacade;
import eu.atac.atf.algorithm.BaseNode;
import eu.atac.atf.algorithm.ClassNode;
import eu.atac.atf.algorithm.RootNode;
import eu.atac.atf.test.metadata.SClass;
import eu.atac.atf.test.metadata.SCreateObject;
import eu.atac.atf.test.metadata.SDeclaration;
import eu.atac.atf.test.metadata.SEqual;
import eu.atac.atf.test.metadata.SImport;
import eu.atac.atf.test.metadata.SMethod;
import eu.atac.atf.test.metadata.SVariable;
import eu.atac.atf.test.metadata.Test;
import eu.atac.atf.test.metadata.TestCase;
import eu.atac.atf.test.metadata.TestSuite;
import eu.atac.atf.test.ocl.ConstraintParser;
import eu.atac.atf.test.ocl.pattern.ConstrainPatternBase;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Package;

public class Generate {
	
	public static TestSuite generate(ATFTestModel atfTestModel, File modelFile, File oclFile){
		List<Constraint> constraintList = null;
		try{
			if(modelFile == null || !modelFile.exists()){
				ATF.log("Model class file does not exist. Please build your project. ");
				return null;
			}
			
			if(oclFile == null || !oclFile.exists()){
				ATF.log("Ocl file does not exist");
				return null;
			}

			StandaloneFacade.INSTANCE.initialize(StandaloneFacade.class.getResource(ATF.ATF_LOG4JAVA_FILE));
			
			StandaloneFacade.INSTANCE.loadJavaModel(modelFile);
			
			IModel model = StandaloneFacade.INSTANCE.loadJavaModel(modelFile);
			try {
				constraintList = StandaloneFacade.INSTANCE.parseOclConstraints(model, oclFile);
				atfTestModel.setConstraintList(constraintList);
				
			} catch (Exception e) {
				ATF.log(e);
				return null;
			}
						
		} catch (Exception e) {
			ATF.log(e);
			return null;
		}
		
		Class clazz = atfTestModel.getClazz();

		if(clazz == null){
			return null;
		}
		
//		printDebugInfo(constraintList);
		//Testuosim modeli o ne metodus
		TestSuite testSuite = new TestSuite();
		testSuite.setDirectory(ATF.ATF_TEST_PACKAGE);
		
		TestCase testCase = new TestCase();
		testCase.setName(ATF.DEFAULT_TEST_FILE_NAME + ATF.firstToUpper(clazz.getName()));
		
		List<Class> parrents = ModelUtil.getAllParrents(atfTestModel, clazz);
		
		Tree<BaseNode> algoTree = new Tree<BaseNode>(new RootNode(clazz));
		
		for (int i = 0; i < parrents.size(); i++) {
			algoTree.addLeaf(new ClassNode(parrents.get(i),	
					getConstrainsForClass(atfTestModel.getConstraints(), atfTestModel, parrents.get(i))));
		}
		
		//System.out.println(algoTree);
		
		
		SClass sclass = new SClass();
		sclass.setTestClassModel(clazz);
		sclass.setPackage(testSuite.getDirectory());
		sclass.addImport(SImport.getStaticOrgJunitAssert());
		sclass.addImport(SImport.getOrgJunitTest());
		sclass.setName(testCase.getName());
		
		
		int testIndex = 0;
		
		for (int i = 0; i < parrents.size(); i++) {
			Class parrent = parrents.get(i);
			//algoTree.addLeaf(new ClassNode());
			
			List<OCLConstraint> consForClass = OCLUtil.getConstrainsForClass(atfTestModel.getConstraints(),clazz.getPackage().getName(), clazz.getName());
			
			atfTestModel.setPackage((Package) parrent.getPackage());
			atfTestModel.setClass(parrent);
			
			SMethod smethod = new SMethod();
			smethod.setName(ATF.DEFAULT_TEST_NAME + testIndex++);
			smethod.addAnotation(ATF.ANOTATION_TEST);
			
			//Create object
			smethod.addBodyElement(new SDeclaration(parrent));
			smethod.addBodyElement(new SVariable(generateObjectName(parrent,smethod), clazz,ATF.VARIAVLE_ID_TEST_OBJECT));
			smethod.addBodyElement(new SEqual());
			
			SCreateObject obj = new SCreateObject(parrent);
			obj.setSemicolon(true);
			obj.setPostNewLine(true);
			smethod.addBodyElement(obj);
			
			sclass.addMethod(smethod);
			
			atfTestModel.setSclass(sclass);
			atfTestModel.setSmethod(smethod);
			
			for (int j = 0; j < consForClass.size(); j++) {
				ConstrainPatternBase handler = ConstraintParser.getInstance().parse(consForClass.get(j));
				handler.generatePre(consForClass.get(j),atfTestModel);
				handler.generateInvariant(consForClass.get(j),atfTestModel);
			}
			
		}
		
		Test test = new Test();
		test.setBody(sclass.print());
		testCase.addTest(test);
		
		testSuite.addTestCase(testCase);
		
//		SClass sclass = new SClass();
//		sclass.setTestClassModel(clazz);
//		sclass.setPackage(testSuite.getDirectory());
//		sclass.addImport(SImport.getStaticOrgJunitAssert());
//		sclass.addImport(SImport.getOrgJunitTest());
//		sclass.setName(testCase.getName());
//		
//		SMethod smethod = new SMethod();
//		smethod.setName(ATF.DEFAULT_TEST_NAME + ATF.firstToUpper("test1"));
//		smethod.addAnotation(ATF.ANOTATION_TEST);
//		
//		
//		//Create object
//		smethod.addBodyElement(new SDeclaration(clazz));
//		smethod.addBodyElement(new SVariable(generateObjectName(clazz,smethod), clazz,ATF.VARIAVLE_ID_TEST_OBJECT));
//		smethod.addBodyElement(new SEqual());
//		
//		SCreateObject obj = new SCreateObject(clazz);
//		obj.setSemicolon(true);
//		obj.setPostNewLine(true);
//		smethod.addBodyElement(obj);
//		
//		sclass.addMethod(smethod);
//		
//		atfTestModel.setSclass(sclass);
//		atfTestModel.setSmethod(smethod);
//		
//		
//		Test test = new Test();
//		test.setBody(sclass.print());
//		testCase.addTest(test);
//		
//		testSuite.addTestCase(testCase);

		
		
		
		
//		for (IMethod method : clazz.getMethods()) {
//			if(ATF.isJavaBeanMethod(method)){
//				continue;
//			}
//			atfTestModel.setMethod((Method) method);
//			
//			TestCase testCase = new TestCase();
//			testCase.setName(ATF.DEFAULT_TEST_FILE_NAME + ATF.firstToUpper(method.getName()));
//			testSuite.addTestCase(testCase);
//			
//			SClass sclass = new SClass();
//			sclass.setTestClassModel(clazz);
//			sclass.setPackage(testSuite.getDirectory());
//			sclass.addImport(SImport.getStaticOrgJunitAssert());
//			sclass.addImport(SImport.getOrgJunitTest());
//			sclass.setName(testCase.getName());
//			
//			
//			SMethod smethod = new SMethod();
//			smethod.setName(ATF.DEFAULT_TEST_NAME + ATF.firstToUpper(method.getName()));
//			smethod.addAnotation(ATF.ANOTATION_TEST);
//			
//			
//			//Create object
//			smethod.addBodyElement(new SDeclaration(clazz));
//			smethod.addBodyElement(new SVariable(generateObjectName(clazz,smethod), clazz,ATF.VARIAVLE_ID_TEST_OBJECT));
//			smethod.addBodyElement(new SEqual());
//			
//			SCreateObject obj = new SCreateObject(clazz);
//			obj.setSemicolon(true);
//			obj.setPostNewLine(true);
//			smethod.addBodyElement(obj);
//			
//			sclass.addMethod(smethod);
//			
//			atfTestModel.setSclass(sclass);
//			atfTestModel.setSmethod(smethod);
//			
//			//Invoke method
//			SVariable svariable = smethod.findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
//			if(method.getParameters().isEmpty()){
//				SMethodInvocation invocation = new SMethodInvocation(svariable, method);
//				invocation.incrementPreTab2x();
//				invocation.setSemicolon(true);
//				invocation.setPostNewLine(true);
//				smethod.addBodyElement(invocation);
//			}else{
//				SMethodInvocation invocation = new SMethodInvocation(svariable, method);
//				for (int i = 0; i < method.getParameters().size(); i++) {
//					IParameter parameter = method.getParameters().get(i);
//					if(parameter.getType().isPrimitive()){
//						SDeclaration declaration = new SDeclaration( (Class) parameter.getType());
//						SVariable variable = new SVariable(generateParamName(i), (Class) parameter.getType(), generateParamIndex(i));
//						SValue value = new SValue((Class) parameter.getType(),RandomGenerator.getInstance().generate((Type) parameter.getType()));
//						
//						SParameter sparameter = new SParameter(declaration, variable,value);
//						sparameter.incrementPreTab2x();
//						sparameter.setSemicolon(true);
//						sparameter.setPostNewLine(true);
//						
//						smethod.addBodyElement(sparameter);
//						invocation.addParameter(sparameter);
//					}else{
//						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
//					}
//				}
//				
//				invocation.incrementPreTab2x();
//				invocation.setSemicolon(true);
//				invocation.setPostNewLine(true);
//				smethod.addBodyElement(invocation);
//			}
//			
//			//Pre
//			
//			//Post
//			
//			//inv
//			for (Constraint constraint : constraintList) {
//				if(constraint.getKind().getName().equals(ATF.OCL_KIND_INV) && isConstraintForClass(atfTestModel, constraint)){
//					ConstrainPatternBase handler = ConstraintParser.getInstance().parse(constraint);
//					handler.generateInvariant(constraint,atfTestModel);
//				}
//			}
//			
//			
//			Test test = new Test();
//			test.setBody(sclass.print());
//			
////			test.setBody(sclass.p)
////			test.setBody(smethod.print());
////			testCase.setPreTests(sclass.printPackageImport() + sclass.printClassDeclaration());
////			testCase.setPostTests(sclass.printEndClass());
//			
//			testCase.addTest(test);
//			
//		}
		
		
		return testSuite;
	}
		
	private static String generateObjectName(Class clazz, SMethod smethod){
		return "obj" + ATF.firstToUpper(clazz.getName());
	}
	
	@Deprecated
	private static String generateParamName(int index){
		return "param" + index;
	}
	
	@Deprecated
	private static int generateParamIndex(int index){
		return ATF.VARIAVLE_ID_PARAM + 1 + index;
	}
	
	public static List<OCLConstraint> getConstrainsForClass(List<OCLConstraint> constraintList,ATFTestModel atfTestModel, Class clazz){
		List<OCLConstraint> cons = new ArrayList<OCLConstraint>();
		
		for (OCLConstraint constraint : constraintList) {
			if(isConstraintForClass(constraint,atfTestModel, clazz)){
				cons.add(constraint);
			}
		}
		
		return cons;
	}
	
	public static boolean isConstraintForClass(OCLConstraint constraint,ATFTestModel atfTestModel,Class clazz){
		return clazz.instanceOf(ModelUtil.findClass(atfTestModel,constraint.getModelType()));
	}
	
	
}
