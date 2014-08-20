package eu.atac.atf.test.ocl.pattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.ExpressionInOclImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.IntegerLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.IteratorExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.OperationCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.PropertyCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.StringLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.TypeLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableImpl;
import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ATFException;
import eu.atac.atf.main.ATFTestModel;
import eu.atac.atf.main.ModelUtil;
import eu.atac.atf.main.OCLConstraint;
import eu.atac.atf.main.OCLUtil;
import eu.atac.atf.main.Tree;
import eu.atac.atf.test.metadata.SCast;
import eu.atac.atf.test.metadata.SComment;
import eu.atac.atf.test.metadata.SCreateObject;
import eu.atac.atf.test.metadata.SDeclaration;
import eu.atac.atf.test.metadata.SEqual;
import eu.atac.atf.test.metadata.SForEach;
import eu.atac.atf.test.metadata.SIF;
import eu.atac.atf.test.metadata.SInstanceOf;
import eu.atac.atf.test.metadata.SVariable;
import eu.sarunas.atf.generators.tests.RandomGenerator;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Modifier;

import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * 
 * 
0 ExpressionInOclImpl
1  IteratorExpImpl - forAll
2    PropertyCallExpImpl - Sequence(IPE) ipe
3      VariableExpImpl - org.inspection_plusplus.InspectionPlan
4    OperationCallExpImpl - implies
5      OperationCallExpImpl - oclIsTypeOf
6        VariableExpImpl - org.inspection_plusplus.IPE
7        TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
8      OperationCallExpImpl - and
............
 */
public class PPattern4 extends ConstrainPatternBase {
	private ConstrainPatternElementBase[] pattern;
	
	public PPattern4(){
		pattern = new ConstrainPatternElementBase[]{
				new PESimpleClass(ExpressionInOclImpl.class),
				new PESimpleClass(IteratorExpImpl.class,1,1),
				new PESimpleClass(PropertyCallExpImpl.class,1,1),
				new PESimpleClass(VariableExpImpl.class,1,1),
				new PEOperationCall("implies"), 
				new PEOperationCall("oclIsTypeOf"), 
				new PEAll(),
		};
	}
	
	@Override
	public ConstrainPatternElementBase[] getPattern() {
		return pattern;
	}


	@Override
	public void generatePre(OCLConstraint constraint,ATFTestModel atfTestModel) {
		try {
			Tree<EObject> tree =  getFirstElement(constraint.getEobjectTree());
//			0 ExpressionInOclImpl
//			1  IteratorExpImpl - forAll
//			2    PropertyCallExpImpl - Sequence(IPE) ipe
//			3      VariableExpImpl - org.inspection_plusplus.InspectionPlan
//			4    OperationCallExpImpl - implies
//		      		OperationCallExpImpl - oclIsTypeOf
//		        		VariableExpImpl - org.inspection_plusplus.IPE
//		        			TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
		        
			String loop = OCLUtil.getIteratorName(tree.getHead());
			if(!loop.equals("forAll")){
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000 + " " + loop);
			}
			
			String property = OCLUtil.getPropertyCallName(getFirstElement(tree).getHead()) ;
			
			if(ModelUtil.findSetter(atfTestModel.getClazz(), property) != null){
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001);
			}
			
			Method method = ModelUtil.findGetter(atfTestModel.getClazz(), property);
			
			if(!method.getReturnType().getName().equals(ATF.JAVA_TYPE_JAVA_UTIL_LIST)){
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001);
			}

			Tree<EObject> impliesTree =  getSecondElement(tree);
			
			String implies = OCLUtil.getOperationCallOperator(impliesTree.getHead()) ;
			
			Class impliesType = parseOclIsTypeOfType(atfTestModel, getFirstElement(impliesTree));
			
			//Create object implies
			atfTestModel.getSmethod().addBodyElement(new SDeclaration(impliesType));
			atfTestModel.getSmethod().addBodyElement(new SVariable("loopObj" + impliesType.getName(), impliesType,ATF.VARIAVLE_ID_LOOP_OBJECT));
			atfTestModel.getSmethod().addBodyElement(new SEqual());
			atfTestModel.getSmethod().addBodyElement(new SCreateObject(impliesType, true,true));
			
			SVariable testObject = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
			SVariable loopObject = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_LOOP_OBJECT);
			
			SComment addForObject = new SComment(testObject.getName() +"."+ method.getName() + "().add(" +loopObject.getName()+");",true);
			atfTestModel.getSmethod().addBodyElement(addForObject);
			
			//And Chain
			List<Tree<EObject>>  andChain = parseAndChain(getSecondElement(impliesTree));

			for (int i = 0; i < andChain.size(); i++) {
				List<EObject> andEObjectList = OCLUtil.getEObjectList(andChain.get(i));
				
//				System.out.println(OCLUtil.print(andEObjectList));
				
				if(check(andEObjectList, patternSize)){					
					Class castType = ModelUtil.findClass(atfTestModel, OCLUtil.getTypeLiteralExpType((TypeLiteralExpImpl) andEObjectList.get(5))) ;
					if(!castType.instanceOf(impliesType)){
						throw new ATFException(ATFException.ATF_ERROR_CODE_1000);
					}
					int size = ((IntegerLiteralExpImpl) andEObjectList.get(andEObjectList.size() -1)).getIntegerSymbol();
					
					String propertyNameForSize = OCLUtil.getPropertyCallName(andEObjectList.get(2)) ;
					String propertyTypeForSize = OCLUtil.getSequenceType(andEObjectList.get(2)) ;
					
					
					if(ModelUtil.findSetter(castType, propertyNameForSize) != null){
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001);
					}
					
					Method methodForSize = ModelUtil.findGetter(castType, propertyNameForSize);
					
					if(!method.getReturnType().getName().equals(ATF.JAVA_TYPE_JAVA_UTIL_LIST)){
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001);
					}
					Type  propertyType =  ModelUtil.findField(castType, propertyNameForSize).getGenericType();
					
					if(size == 1 ){
						atfTestModel.getSmethod().addBodyElement(new SDeclaration(ModelUtil.findClass(atfTestModel, propertyTypeForSize)));
						atfTestModel.getSmethod().addBodyElement(new SVariable("loopObjSize" + 1, impliesType,ATF.VARIAVLE_ID_OBJECT_1));
						atfTestModel.getSmethod().addBodyElement(new SEqual());
						atfTestModel.getSmethod().addBodyElement(new SCreateObject(ModelUtil.findClass(atfTestModel, propertyTypeForSize),true,true));
						
						SVariable objectSize1 = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_OBJECT_1);
						SComment  addObject = new SComment(loopObject.getName() + "." + methodForSize.getName() + "().add("+objectSize1.getName() +");" ,true);
						atfTestModel.getSmethod().addBodyElement(addObject);
						SComment com1 = new SComment(loopObject.getName() + methodForSize.getName() + "().add(new " +propertyType.getName() + "));" );
						atfTestModel.getSmethod().addBodyElement(com1);
					}else{
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001, Integer.toString(size));
					}
				}else if(check(andEObjectList, patternEqual)){
					
					String operation = OCLUtil.getOperationCallOperator(andChain.get(i).getHead());
					
					//left
					String leftString = parsePropertyCallChain2(atfTestModel, getFirstElement(andChain.get(i)), false);
					
					//right
					String rightString = parsePropertyCallChain2(atfTestModel, getSecondElement(andChain.get(i)), false);
					
					if(operation.equals("=")){
						String comment = loopObject.getName()  + leftString + "(" + rightString + ");";
						SComment  addObject = new SComment(comment ,true);
						atfTestModel.getSmethod().addBodyElement(addObject);
					}else{
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001, operation);
					}
					
				}
				else if(check(andEObjectList, patternSize2)){		
				
					Class castType = ModelUtil.findClass(atfTestModel, OCLUtil.getTypeLiteralExpType((TypeLiteralExpImpl) andEObjectList.get(7))) ;
					if(!castType.instanceOf(impliesType)){
						throw new ATFException(ATFException.ATF_ERROR_CODE_1000);
					}
					int size = ((IntegerLiteralExpImpl) andEObjectList.get(andEObjectList.size() -1)).getIntegerSymbol();
					int at   = ((IntegerLiteralExpImpl) andEObjectList.get(andEObjectList.size() -2)).getIntegerSymbol();
					
					String propertyForSize = OCLUtil.getPropertyCallName(andEObjectList.get(2)) ;
			
					if(ModelUtil.findSetter(castType, propertyForSize) != null){
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001);
					}
					
					Method methodForSize = ModelUtil.findGetter(castType, propertyForSize);
					
					if(!method.getReturnType().getName().equals(ATF.JAVA_TYPE_JAVA_UTIL_LIST)){
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001);
					}
					
					String propertyType2 = OCLUtil.getSequenceType(andEObjectList.get(2));
					String propertyType4 = OCLUtil.getSequenceType(andEObjectList.get(4));
					
					String propertyName2 = OCLUtil.getPropertyCallName(andEObjectList.get(2));
					String propertyName4 = OCLUtil.getPropertyCallName(andEObjectList.get(4));
					
					if(size == 1 ){
						Class type = ModelUtil.findClass(atfTestModel, propertyType2);
						atfTestModel.getSmethod().addBodyElement(new SDeclaration(type));
						atfTestModel.getSmethod().addBodyElement(new SVariable("loopObjSize" + 2, type,ATF.VARIAVLE_ID_OBJECT_2));
						atfTestModel.getSmethod().addBodyElement(new SEqual());
						SCreateObject obj1 = new SCreateObject(type,true,true);
						atfTestModel.getSmethod().addBodyElement(obj1);
						
						SVariable objectSize1 = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_OBJECT_2);
						String comment = loopObject.getName() + "." +
								ModelUtil.findGetter(castType, propertyName4).getName() + "().get(" + at + ")." +
								ModelUtil.findGetter(atfTestModel, propertyType4, propertyName2).getName() + "().add(" +
								objectSize1.getName() +");";
						
						SComment  addObject = new SComment(comment ,true);
						atfTestModel.getSmethod().addBodyElement(addObject);
					}else{
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001, Integer.toString(size));
					}
				}else if(check(andEObjectList, patternEqual2)){
										
					String operation = OCLUtil.getOperationCallOperator(andChain.get(i).getHead());
					
					//left
					String leftString = parsePropertyCallChain2(atfTestModel, getFirstElement(andChain.get(i)), false);
					
					//right
					String rightString = parsePropertyCallChain2(atfTestModel, getSecondElement(andChain.get(i)), false);
					
					if(operation.equals("=")){
						SComment com1 = new SComment(loopObject.getName() + leftString + "(" + rightString + ");",true);
						atfTestModel.getSmethod().addBodyElement(com1);	
					}else{
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001, operation);
					}
				}else if(check(andEObjectList, patternNotEqual)){
					//TODO
				}else if(check(andEObjectList, patternSize3)){
					String type = OCLUtil.getSequenceType(andEObjectList.get(3));
					
					String typeRef = OCLUtil.getTypedElementType(andEObjectList.get(10));
					
					int size = ((IntegerLiteralExpImpl)andEObjectList.get(andEObjectList.size() - 1)).getIntegerSymbol();
					
					Class typeClass = ModelUtil.findClass(atfTestModel, type);
					Class typeRefClass = ModelUtil.findClass(atfTestModel, typeRef);
					if(typeClass.getModifiers().contains(Modifier.Abstract)){
						List<Class> parrents = ModelUtil.getAllParrents(atfTestModel, typeClass);
						Class parrent = parrents.get(1);
						
						Tree<EObject> propTreeLeft1  = andChain.get(i).getTree(andEObjectList.get(7));
						Tree<EObject> propTreeLeft2  = andChain.get(i).getTree(andEObjectList.get(9));
						Tree<EObject> propTreeRight = andChain.get(i).getTree(andEObjectList.get(12));	
						
						String leftSet1  = parsePropertyCallChain2(atfTestModel, propTreeLeft1, false);
						String leftSet2  = parsePropertyCallChain2(atfTestModel, propTreeLeft2, false);
						String rightSet = parsePropertyCallChain2(atfTestModel, propTreeRight, false);
						String rightGet = parsePropertyCallChain2(atfTestModel, propTreeRight, true);
						
						SComment comSet = new SComment(testObject.getName() + rightSet + "(\"" + RandomGenerator.getInstance().randomString(10) + "\");",true);
						atfTestModel.getSmethod().addBodyElement(comSet);	
						
						for (int j = 0; j < size; j++) {
							int idVariable = (1300 + j);
							SVariable var = new SVariable("qc" + j, parrent,idVariable);
							atfTestModel.getSmethod().addBodyElement(new SDeclaration(parrent));
							atfTestModel.getSmethod().addBodyElement(var);
							atfTestModel.getSmethod().addBodyElement(new SEqual());
							atfTestModel.getSmethod().addBodyElement(new SCreateObject(parrent,true,true));
							
							
							int idRefVariable = (1400 + j);
							SVariable varRef = new SVariable("qcRef" + j, typeRefClass,idRefVariable);
							atfTestModel.getSmethod().addBodyElement(new SDeclaration(typeRefClass));
							atfTestModel.getSmethod().addBodyElement(varRef);
							atfTestModel.getSmethod().addBodyElement(new SEqual());
							atfTestModel.getSmethod().addBodyElement(new SCreateObject(typeRefClass,true,true));
							
							SComment comRefSet = new SComment(varRef.getName() + leftSet2 +"("  + testObject.getName() + rightGet +");",true);
							atfTestModel.getSmethod().addBodyElement(comRefSet);	
							
							SComment comLoopSet = new SComment(var.getName() + leftSet1 + "(" + varRef.getName() + ");",true);
							atfTestModel.getSmethod().addBodyElement(comLoopSet);	
						}
					}else{
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001);
					}
					
				}else if(check(andEObjectList, patternSize4)){
//tolerance->select(tolAxisPosX: Tolerance| tolAxisPosX.oclIsTypeOf(TolAxisPosX) 
//and tolAxisPosX.oclAsType(TolAxisPosX).criterion.upperToleranceValue > ipe.oclAsType(IPESurfacePoint).origin.x
//and tolAxisPosX.oclAsType(TolAxisPosX).criterion.lowerToleranceValue < ipe.oclAsType(IPESurfacePoint).origin.x
//) -> size() = 1
					
//					0OperationCallExpImpl - =
//							1  OperationCallExpImpl - size
//							2    IteratorExpImpl - select
//							3      PropertyCallExpImpl - Sequence(Tolerance) tolerance
//							4        VariableExpImpl - org.inspection_plusplus.InspectionPlan
//							5      OperationCallExpImpl - and
//							6        OperationCallExpImpl - and
//							7          OperationCallExpImpl - oclIsTypeOf
//							8            VariableExpImpl - org.inspection_plusplus.Tolerance
//							9            TypeLiteralExpImpl - org.inspection_plusplus.TolAxisPosX
//							32      VariableImpl - tolAxisPosX
//							33  IntegerLiteralExpImpl - 1
					
					String selectPropertyName = OCLUtil.getPropertyCallName(andEObjectList.get(3));
					String selectPropertyType = OCLUtil.getSequenceType(andEObjectList.get(3));
					String selectSouceType = OCLUtil.getTypedElementType(andEObjectList.get(4));
					System.out.println(selectPropertyName + " " + selectPropertyType + " " +selectSouceType);
					
					int size = ((IntegerLiteralExpImpl)andEObjectList.get(andEObjectList.size() - 1)).getIntegerSymbol();
					
					List<Tree<EObject>>  andSubChain = parseAndChain(andChain.get(i).getTree(andEObjectList.get(5)));
					
//					for (int k = 0; k < andSubChain.size(); k++) {
//						OCLUtil.printTreeToSysout(andSubChain.get(k));
//						System.out.println();
//					}
					
					for (int k = 0; k < size; k++) {
						Tree<EObject> oclIsTypeOf = andSubChain.get(0);
						if(OCLUtil.getOperationCallOperator(oclIsTypeOf.getHead()).equals("oclIsTypeOf")){
							Class oclIsTypeOfClass = parseOclIsTypeOfType(atfTestModel, oclIsTypeOf);
							
							int idVariable = atfTestModel.getSmethod().getObjectRepository().generateNextId();
							SVariable var = new SVariable(selectPropertyName + idVariable, oclIsTypeOfClass,idVariable);
							atfTestModel.getSmethod().addBodyElement(new SDeclaration(oclIsTypeOfClass));
							atfTestModel.getSmethod().addBodyElement(var);
							atfTestModel.getSmethod().addBodyElement(new SEqual());
							atfTestModel.getSmethod().addBodyElement(new SCreateObject(oclIsTypeOfClass,true,true));
							
							List<OCLConstraint> cons = findConstraints(atfTestModel, oclIsTypeOfClass);
							
							System.out.println(cons.size());
							
						}else{
							throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001);
						}
						
						Tree<EObject> andSub1 = andSubChain.get(1);
						
//						and tolAxisPosX.oclAsType(TolAxisPosX).criterion.upperToleranceValue > ipe.oclAsType(IPESurfacePoint).origin.x
//						OperationCallExpImpl - >
//						  PropertyCallExpImpl - Integer upperToleranceValue
//						    PropertyCallExpImpl - IntervalToleranceCriterion criterion
//						      OperationCallExpImpl - oclAsType
//						        VariableExpImpl - org.inspection_plusplus.Tolerance
//						        TypeLiteralExpImpl - org.inspection_plusplus.TolAxisPosX
//						  PropertyCallExpImpl - Real x
//						    PropertyCallExpImpl - Vector3D origin
//						      OperationCallExpImpl - oclAsType
//						        VariableExpImpl - org.inspection_plusplus.IPE
//						        TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
						
//						Tree<EObject> andSub2 = andSubChain.get(2);
//						OCLUtil.printTreeToSysout(andSub2);
						
//						OperationCallExpImpl - <
//						  PropertyCallExpImpl - Integer lowerToleranceValue
//						    PropertyCallExpImpl - IntervalToleranceCriterion criterion
//						      OperationCallExpImpl - oclAsType
//						        VariableExpImpl - org.inspection_plusplus.Tolerance
//						        TypeLiteralExpImpl - org.inspection_plusplus.TolAxisPosX
//						  PropertyCallExpImpl - Real x
//						    PropertyCallExpImpl - Vector3D origin
//						      OperationCallExpImpl - oclAsType
//						        VariableExpImpl - org.inspection_plusplus.IPE
//						        TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint						
						
						
						
						
						
						

						
						
						
						
						
						
					}
					
							  
					//OCLUtil.printTreeToSysout(andChain.get(i));
					//TODO
				}else if(check(andEObjectList, patternSelect)){
					//TODO
				}else{
					for (int j = 0; j < andEObjectList.size(); j++) {
						System.out.println("new PESimpleClass(" +andEObjectList.get(j).getClass().getSimpleName() +".class,1,1),");
					}
					OCLUtil.printTreeToSysout(andChain.get(i));
					throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001);
				}
			}
		} catch (Exception e) {
			ATF.log(e);
		}
	}
	

	
	@Override
	public void generateInvariant(OCLConstraint constraint,ATFTestModel atfTestModel) {
		SVariable variable = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
		SForEach foreach = new SForEach(variable, 
				ModelUtil.findClass(atfTestModel, OCLUtil.getSequenceType(constraint.getEobjectList().get(2))),
				OCLUtil.getPropertyCallName(constraint.getEobjectList().get(2)));
		
		Class iteType = ModelUtil.findClass(atfTestModel, OCLUtil.getTypedLiteralType (constraint.getEobjectList().get(7) ));
		
		atfTestModel.getSmethod().addBodyElement(foreach);
		SInstanceOf sinstance = new SInstanceOf(foreach.getIterableElement(),iteType);
		SIF sif = new SIF(sinstance);
		
		SDeclaration dec = new SDeclaration(iteType);
		SVariable ite = new SVariable("casted",iteType,1021);
		ite.setIgnoreFormating(true);
		SCast scast= new  SCast(iteType, foreach.getIterableElement());
		scast.setIgnoreFormating(true);
		sif.addBody(dec);
		sif.addBody(ite);
		sif.addBody(scast);
		
		
		foreach.addBody(sif);
		
		List<Tree<EObject>>  andChain = parseAndChain(getSecondElement(getSecondElement(getFirstElement(constraint.getEobjectTree()))));
		
		
		for (int i = 0; i < andChain.size(); i++) {
			List<EObject> andEObjectList = OCLUtil.getEObjectList(andChain.get(i));
			
			//System.out.println(OCLUtil.print(andEObjectList));
			
			if(check(andEObjectList, patternSize) || check(andEObjectList, patternSize2) || check(andEObjectList, patternEqual) 
					|| check(andEObjectList, patternEqual2) ){	
				sif.addBody(evaluate(atfTestModel,andChain.get(i),ite ));
				
			}else if(check(andEObjectList, patternNotEqual)){

			}else{
				//throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1001);
			}
		}
		
		//evaluate(constraint,atfTestModel);
	}
	
	private List<Tree<EObject>> parseAndChain(Tree<EObject> tree){
		List<Tree<EObject>> chain = new ArrayList<Tree<EObject>>();

		while(tree.getHead() instanceof OperationCallExpImpl){
			if(OCLUtil.getOperationCallOperator(tree.getHead()).equals("and") ){
				chain.add(getSecondElement(tree));
			}else{
				chain.add(tree);
				break;
			}
			
			tree = getFirstElement(tree);
		}
		
		Collections.reverse(chain);
		
		return chain;
	}
	
	/**
	 *OperationCallExpImpl - =
	 * OperationCallExpImpl - size
	 *  PropertyCallExpImpl - Sequence(CalculationNominalElement) calculationNominalElement
	 *    OperationCallExpImpl - oclAsType
	 *      VariableExpImpl - org.inspection_plusplus.IPE
	 *      TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
	 * IntegerLiteralExpImpl - 102
	 */
	private ConstrainPatternElementBase[] patternSize  = new ConstrainPatternElementBase[]{
			    new PESimpleClass(OperationCallExpImpl.class,1,1),
				new PEOperationCall("size"),
				new PESimpleClass(PropertyCallExpImpl.class,1,1),
				new PEOperationCall("oclAsType"),
				new PESimpleClass(VariableExpImpl.class,1,1),
				new PESimpleClass(TypeLiteralExpImpl.class,1,1),
				new PESimpleClass(IntegerLiteralExpImpl.class,1,1),
		};
	
	/**
	 *OperationCallExpImpl - =
	 * OperationCallExpImpl - size
	 *  PropertyCallExpImpl - Sequence(Operand) operand
	 *    OperationCallExpImpl - at
	 *      PropertyCallExpImpl - Sequence(CalculationNominalElement) calculationNominalElement
	 *        OperationCallExpImpl - oclAsType
	 *          VariableExpImpl - org.inspection_plusplus.IPE
	 *          TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
	 *      IntegerLiteralExpImpl - 0
	 * IntegerLiteralExpImpl - 201
	 */
	private ConstrainPatternElementBase[] patternSize2  = new ConstrainPatternElementBase[]{
			    new PESimpleClass(OperationCallExpImpl.class,1,1),
				new PEOperationCall("size"),
				new PESimpleClass(PropertyCallExpImpl.class,1,1),
				new PESimpleClass(OperationCallExpImpl.class,1,1),//at
				new PESimpleClass(PropertyCallExpImpl.class,1,1),
				new PEOperationCall("oclAsType"),
				new PESimpleClass(VariableExpImpl.class,1,1),
				new PESimpleClass(TypeLiteralExpImpl.class,1,1),
				new PESimpleClass(IntegerLiteralExpImpl.class,1,1),//(0)
				new PESimpleClass(IntegerLiteralExpImpl.class,1,1),
		};	

	/**
	 *
	 *OperationCallExpImpl - =
	 * PropertyCallExpImpl - String operation
	 *  OperationCallExpImpl - at
	 *    PropertyCallExpImpl - Sequence(CalculationNominalElement) calculationNominalElement
	 *      OperationCallExpImpl - oclAsType
	 *        VariableExpImpl - org.inspection_plusplus.IPE
	 *        TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
	 *    IntegerLiteralExpImpl - 0
	 * StringLiteralExpImpl - IPE_LiesOn
	 */
	private ConstrainPatternElementBase[] patternEqual  = new ConstrainPatternElementBase[]{
			    new PESimpleClass(OperationCallExpImpl.class,1,1),
			    new PESimpleClass(PropertyCallExpImpl.class,1,1),
			    new PEOperationCall("at"),
				new PESimpleClass(PropertyCallExpImpl.class,1,1),
				new PEOperationCall("oclAsType"),
				new PESimpleClass(VariableExpImpl.class,1,1),
				new PESimpleClass(TypeLiteralExpImpl.class,1,1),
				new PESimpleClass(IntegerLiteralExpImpl.class,1,1),
				new PESimpleClass(StringLiteralExpImpl.class,1,1),
		};
	
	/**
	 *
	 *OperationCallExpImpl - =
	 * PropertyCallExpImpl - String role
	 *  OperationCallExpImpl - at
	 *    PropertyCallExpImpl - Sequence(Operand) operand
	 *      OperationCallExpImpl - at
	 *        PropertyCallExpImpl - Sequence(CalculationNominalElement) calculationNominalElement
	 *          OperationCallExpImpl - oclAsType
	 *            VariableExpImpl - org.inspection_plusplus.IPE
	 *            TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
	 *        IntegerLiteralExpImpl - 0
	 *    IntegerLiteralExpImpl - 1
	 * StringLiteralExpImpl - IPE_IPE_1_XX
	 */
	private ConstrainPatternElementBase[] patternEqual2  = new ConstrainPatternElementBase[]{
			    new PESimpleClass(OperationCallExpImpl.class,1,1),
			    new PESimpleClass(PropertyCallExpImpl.class,1,1),//
			    new PEOperationCall("at"),						//
			    new PESimpleClass(PropertyCallExpImpl.class,1,1),
			    new PEOperationCall("at"),
				new PESimpleClass(PropertyCallExpImpl.class,1,1),
				new PEOperationCall("oclAsType"),
				new PESimpleClass(VariableExpImpl.class,1,1),
				new PESimpleClass(TypeLiteralExpImpl.class,1,1),
				new PESimpleClass(IntegerLiteralExpImpl.class,1,1),
				new PESimpleClass(IntegerLiteralExpImpl.class,1,1),//
				new PESimpleClass(StringLiteralExpImpl.class,1,1),
		};	

//	OperationCallExpImpl - <>
//	  IteratorExpImpl - collect
//	    IteratorExpImpl - collect
//	      PropertyCallExpImpl - Sequence(Operand) operand
//	        OperationCallExpImpl - at
//	          PropertyCallExpImpl - Sequence(CalculationNominalElement) calculationNominalElement
//	            OperationCallExpImpl - oclAsType
//	              VariableExpImpl - org.inspection_plusplus.IPE
//	              TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
//	          IntegerLiteralExpImpl - 0
//	      PropertyCallExpImpl - Sequence(ID4Referencing) ipeGeometricRef
//	        VariableExpImpl - org.inspection_plusplus.Operand
//	      VariableImpl - $implicitCollect0$
//	    PropertyCallExpImpl - String uuid
//	      VariableExpImpl - org.inspection_plusplus.ID4Referencing
//	    VariableImpl - $implicitCollect0$
//	  PropertyCallExpImpl - String uuid
//	    PropertyCallExpImpl - ID4Objects systemID
//	      VariableExpImpl - org.inspection_plusplus.IPE

	/**
	 *

	 */
	private ConstrainPatternElementBase[] patternNotEqual  = new ConstrainPatternElementBase[]{
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(IteratorExpImpl.class,1,1),
			new PESimpleClass(IteratorExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),//
		    new PEOperationCall("at"),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PEOperationCall("oclAsType"),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(IntegerLiteralExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),//
			new PESimpleClass(VariableExpImpl.class,1,1),//
			new PESimpleClass(VariableImpl.class,1,1),//
			new PESimpleClass(PropertyCallExpImpl.class,1,1),//
			new PESimpleClass(VariableExpImpl.class,1,1),//
			new PESimpleClass(VariableImpl.class,1,1),//
			new PESimpleClass(PropertyCallExpImpl.class,1,1),//
			new PESimpleClass(PropertyCallExpImpl.class,1,1),//
			new PESimpleClass(VariableExpImpl.class,1,1),//    
		};		
	
	
    /**
     * qc->select(singleQC : QC | singleQC.ipeRef.uuid = ipe.systemID.uuid)->size() = 4
     * 	OperationCallExpImpl - =
     *			  OperationCallExpImpl - size
     *			    IteratorExpImpl - select
     *			      PropertyCallExpImpl - Sequence(QC) qc
     *			        VariableExpImpl - org.inspection_plusplus.InspectionPlan
     *			      OperationCallExpImpl - =
     *			        IteratorExpImpl - collect
     *			          PropertyCallExpImpl - Sequence(ID4Referencing) ipeRef
     *			            VariableExpImpl - org.inspection_plusplus.QC
     *			          PropertyCallExpImpl - String uuid
     *			            VariableExpImpl - org.inspection_plusplus.ID4Referencing
     *			          VariableImpl - $implicitCollect0$
     *			        PropertyCallExpImpl - String uuid
     *			          PropertyCallExpImpl - ID4Objects systemID
     *			            VariableExpImpl - org.inspection_plusplus.IPE
     *			      VariableImpl - singleQC
     *			  IntegerLiteralExpImpl - 4
     * 
     */
	private ConstrainPatternElementBase[] patternSize3  = new ConstrainPatternElementBase[]{
	    new PESimpleClass(OperationCallExpImpl.class,1,1),
		new PEOperationCall("size"),
		new PESimpleClass(IteratorExpImpl.class,1,1),
		new PESimpleClass(PropertyCallExpImpl.class,1,1),
		new PESimpleClass(VariableExpImpl.class,1,1),
		new PESimpleClass(OperationCallExpImpl.class,1,1),
		new PESimpleClass(IteratorExpImpl.class,1,1),
		new PESimpleClass(PropertyCallExpImpl.class,1,1),
		new PESimpleClass(VariableExpImpl.class,1,1),
		new PESimpleClass(PropertyCallExpImpl.class,1,1),
		new PESimpleClass(VariableExpImpl.class,1,1),
		new PESimpleClass(VariableImpl.class,1,1),
		new PESimpleClass(PropertyCallExpImpl.class,1,1),
		new PESimpleClass(PropertyCallExpImpl.class,1,1),
		new PESimpleClass(VariableExpImpl.class,1,1),
		new PESimpleClass(VariableImpl.class,1,1),
		new PESimpleClass(IntegerLiteralExpImpl.class,1,1),
	};
	
	/**
	 * 
0OperationCallExpImpl - =
1  OperationCallExpImpl - size
2    IteratorExpImpl - select
3      PropertyCallExpImpl - Sequence(Tolerance) tolerance
4        VariableExpImpl - org.inspection_plusplus.InspectionPlan
5      OperationCallExpImpl - and
6        OperationCallExpImpl - and
7          OperationCallExpImpl - oclIsTypeOf
8            VariableExpImpl - org.inspection_plusplus.Tolerance
9            TypeLiteralExpImpl - org.inspection_plusplus.TolAxisPosX
10          OperationCallExpImpl - >
11           PropertyCallExpImpl - Integer upperToleranceValue
12              PropertyCallExpImpl - IntervalToleranceCriterion criterion
13                OperationCallExpImpl - oclAsType
14                  VariableExpImpl - org.inspection_plusplus.Tolerance
15                  TypeLiteralExpImpl - org.inspection_plusplus.TolAxisPosX
16            PropertyCallExpImpl - Real x
17              PropertyCallExpImpl - Vector3D origin
18                OperationCallExpImpl - oclAsType
19                  VariableExpImpl - org.inspection_plusplus.IPE
20                  TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
21        OperationCallExpImpl - <
22          PropertyCallExpImpl - Integer lowerToleranceValue
23            PropertyCallExpImpl - IntervalToleranceCriterion criterion
24              OperationCallExpImpl - oclAsType
25                VariableExpImpl - org.inspection_plusplus.Tolerance
26                TypeLiteralExpImpl - org.inspection_plusplus.TolAxisPosX
27          PropertyCallExpImpl - Real x
28            PropertyCallExpImpl - Vector3D origin
29              OperationCallExpImpl - oclAsType
30                VariableExpImpl - org.inspection_plusplus.IPE
31                TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
32      VariableImpl - tolAxisPosX
33  IntegerLiteralExpImpl - 1
	 */
	private ConstrainPatternElementBase[] patternSize4  = new ConstrainPatternElementBase[]{
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(IteratorExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(VariableImpl.class,1,1),
			new PESimpleClass(IntegerLiteralExpImpl.class,1,1),
		};	

/**
OperationCallExpImpl - includesAll
  IteratorExpImpl - collectNested
    IteratorExpImpl - collect
      IteratorExpImpl - select
        PropertyCallExpImpl - Sequence(QC) qc
          VariableExpImpl - org.inspection_plusplus.InspectionPlan
        OperationCallExpImpl - =
          IteratorExpImpl - collect
            PropertyCallExpImpl - Sequence(ID4Referencing) ipeRef
              VariableExpImpl - org.inspection_plusplus.QC
            PropertyCallExpImpl - String uuid
              VariableExpImpl - org.inspection_plusplus.ID4Referencing
            VariableImpl - $implicitCollect0$
          PropertyCallExpImpl - String uuid
            PropertyCallExpImpl - ID4Objects systemID
              VariableExpImpl - org.inspection_plusplus.IPE
        VariableImpl - singleQC
      OperationCallExpImpl - oclAsType
        VariableExpImpl - org.inspection_plusplus.QC
        TypeLiteralExpImpl - org.inspection_plusplus.QCSingle
      VariableImpl - $implicitCollect0$
    PropertyCallExpImpl - String uuid
      PropertyCallExpImpl - ID4Referencing toleranceRef
        VariableExpImpl - org.inspection_plusplus.QCSingle
    VariableImpl - $implicitVariable0$
  IteratorExpImpl - collect
    IteratorExpImpl - collect
      IteratorExpImpl - select
        PropertyCallExpImpl - Sequence(Tolerance) tolerance
          VariableExpImpl - org.inspection_plusplus.InspectionPlan
        OperationCallExpImpl - and
          OperationCallExpImpl - and
            OperationCallExpImpl - oclIsTypeOf
              VariableExpImpl - org.inspection_plusplus.Tolerance
              TypeLiteralExpImpl - org.inspection_plusplus.TolAxisPosX
            OperationCallExpImpl - >
              PropertyCallExpImpl - Integer upperToleranceValue
                PropertyCallExpImpl - IntervalToleranceCriterion criterion
                  OperationCallExpImpl - oclAsType
                    VariableExpImpl - org.inspection_plusplus.Tolerance
                    TypeLiteralExpImpl - org.inspection_plusplus.TolAxisPosX
              PropertyCallExpImpl - Real x
                PropertyCallExpImpl - Vector3D origin
                  OperationCallExpImpl - oclAsType
                    VariableExpImpl - org.inspection_plusplus.IPE
                    TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
          OperationCallExpImpl - <
            PropertyCallExpImpl - Integer lowerToleranceValue
              PropertyCallExpImpl - IntervalToleranceCriterion criterion
                OperationCallExpImpl - oclAsType
                  VariableExpImpl - org.inspection_plusplus.Tolerance
                  TypeLiteralExpImpl - org.inspection_plusplus.TolAxisPosX
            PropertyCallExpImpl - Real x
              PropertyCallExpImpl - Vector3D origin
                OperationCallExpImpl - oclAsType
                  VariableExpImpl - org.inspection_plusplus.IPE
                  TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
        VariableImpl - tolAxisPosX
      PropertyCallExpImpl - ID4Objects systemID
        VariableExpImpl - org.inspection_plusplus.Tolerance
      VariableImpl - $implicitCollect0$
    PropertyCallExpImpl - String uuid
      VariableExpImpl - org.inspection_plusplus.ID4Objects
    VariableImpl - $implicitCollect0$	
 */
	private ConstrainPatternElementBase[] patternSelect  = new ConstrainPatternElementBase[]{
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(IteratorExpImpl.class,1,1),
			new PESimpleClass(IteratorExpImpl.class,1,1),
			new PESimpleClass(IteratorExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(IteratorExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(VariableImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(VariableImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(VariableImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(VariableImpl.class,1,1),
			new PESimpleClass(IteratorExpImpl.class,1,1),
			new PESimpleClass(IteratorExpImpl.class,1,1),
			new PESimpleClass(IteratorExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(OperationCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(TypeLiteralExpImpl.class,1,1),
			new PESimpleClass(VariableImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(VariableImpl.class,1,1),
			new PESimpleClass(PropertyCallExpImpl.class,1,1),
			new PESimpleClass(VariableExpImpl.class,1,1),
			new PESimpleClass(VariableImpl.class,1,1),
		};	

	
}


















