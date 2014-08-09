package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tudresden.ocl20.pivot.essentialocl.expressions.impl.ExpressionInOclImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.OperationCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.PropertyCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableImpl;
import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ATFException;
import eu.atac.atf.main.ATFTestModel;
import eu.atac.atf.main.OCLConstraint;
import eu.atac.atf.main.OCLUtil;
import eu.atac.atf.main.Tree;
import eu.atac.atf.test.metadata.SComment;
import eu.atac.atf.test.metadata.SVariable;
import eu.sarunas.atf.generators.tests.RandomGenerator;
import eu.sarunas.projects.atf.generators.value.RVBigDecimal;
import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * 
//0			ExpressionInOclImpl - inv invariant_TolAngle1: criterion.upperToleranceValue > criterion.lowerToleranceValue
//1			  OperationCallExpImpl - >
//2			    PropertyCallExpImpl - Integer upperToleranceValue
//3			      PropertyCallExpImpl - IntervalToleranceCriterion criterion
//4			        VariableExpImpl - self
//5			    PropertyCallExpImpl - Integer lowerToleranceValue
//6			      PropertyCallExpImpl - IntervalToleranceCriterion criterion
//7			        VariableExpImpl - self
//8			  VariableImpl - self
 *        
 *        
 * ExpressionInOclImpl - inv: self.useLeft <> self.useRight
 *   OperationCallExpImpl - <>
 *     PropertyCallExpImpl - Boolean useLeft
 *       VariableExpImpl - self
 *     PropertyCallExpImpl - Boolean useRight
 *       VariableExpImpl - self
  *  VariableImpl - self
 */
public class PPattern2 extends ConstrainPatternBase {
	private ConstrainPatternElementBase[] pattern;
	
	public PPattern2(){
		pattern = new ConstrainPatternElementBase[]{
				new PESimpleClass(ExpressionInOclImpl.class),
				new PESimpleClass(OperationCallExpImpl.class,1,2),//????? TODO FIX CHECK
				new PESimpleClass(PropertyCallExpImpl.class,1,3),
				new PESimpleClass(VariableExpImpl.class,1,1),
				new PESimpleClass(PropertyCallExpImpl.class,1,2),
				new PESimpleClass(VariableExpImpl.class,1,1),
				new PESimpleClass(VariableImpl.class,1,1),
		};		
	}
	
	@Override
	public ConstrainPatternElementBase[] getPattern() {
		return pattern;
	}


	@Override
	public void generatePre(OCLConstraint constraint,ATFTestModel atfTestModel) {
		try {
			List<EObject> list = constraint.getEobjectList();
			Tree<EObject> eobjectTree = constraint.getEobjectTree();
			
//			OCLUtil.printTreeToSysout(constraint.getEobjectTree());
			
			String comparator    = null;
			
			OperationCallExpImpl element1 = (OperationCallExpImpl) list.get(1);
			comparator = OCLUtil.getOperationCallOperator(element1);

		
			SVariable variable = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
			int indexes[] = getOperationChildIndex(getFirstElement(eobjectTree), list);

			int leftPropertyIndex  = indexes[0];
			int rightPropertyIndex = indexes[1];
			
//0			ExpressionInOclImpl - inv: self.useLeft <> self.useRight
//1			  OperationCallExpImpl - <>
//2			    PropertyCallExpImpl - Boolean useLeft
//3			      VariableExpImpl - self
//4			    PropertyCallExpImpl - Boolean useRight
//5			      VariableExpImpl - self
//6			  VariableImpl - self

//0			ExpressionInOclImpl - inv invariant_TolAngle1: criterion.upperToleranceValue > criterion.lowerToleranceValue
//1			  OperationCallExpImpl - >
//2			    PropertyCallExpImpl - Integer upperToleranceValue
//3			      PropertyCallExpImpl - IntervalToleranceCriterion criterion
//4			        VariableExpImpl - self
//5			    PropertyCallExpImpl - Integer lowerToleranceValue
//6			      PropertyCallExpImpl - IntervalToleranceCriterion criterion
//7			        VariableExpImpl - self
//8			  VariableImpl - self
			
			String leftExpr  = variable.getName() + parsePropertyCallChain(atfTestModel,list,leftPropertyIndex,false);
			String rightExpr = variable.getName() + parsePropertyCallChain(atfTestModel,list,rightPropertyIndex,false);
			
			Type leftType  = parsePropertyCallChainType(atfTestModel,getFirstElement(eobjectTree).getSubTrees().get(0));
			Type rightType = parsePropertyCallChainType(atfTestModel,getFirstElement(eobjectTree).getSubTrees().get(1));
			
			//TODO 
			if (comparator.equals(">")) {
				if(leftType.getFullName().equals(ATF.JAVA_TYPE_JAVA_MATH_BIGDECIMAL) && rightType.getFullName().equals(ATF.JAVA_TYPE_JAVA_MATH_BIGDECIMAL)){
					RVBigDecimal value1 = RandomGenerator.getInstance().randomBigDecimal();
					RVBigDecimal value2 = RandomGenerator.getInstance().randomBigDecimal();
					if (value1.compareTo(value2) > 0) {
						leftExpr += "(" + value1.asString() + ");";
						rightExpr += "(" + value2.asString() + ");";
					} else {
						leftExpr += "(" + value2.asString() + ");";
						rightExpr += "(" + value1.asString() + ");";
					}
				}else{
					throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
				}
			} else if (comparator.equals("<")) {
				if(leftType.getFullName().equals(ATF.JAVA_TYPE_JAVA_MATH_BIGDECIMAL) && rightType.getFullName().equals(ATF.JAVA_TYPE_JAVA_MATH_BIGDECIMAL)){
					RVBigDecimal value1 = RandomGenerator.getInstance().randomBigDecimal();
					RVBigDecimal value2 = RandomGenerator.getInstance().randomBigDecimal();
					if (value1.compareTo(value2) > 0) {
						leftExpr += "(" + value2.asString() + ");";
						rightExpr += "(" + value1.asString() + ");";
					} else {
						leftExpr += "(" + value1.asString() + ");";
						rightExpr += "(" + value2.asString() + ");";
					}
				}else{
					throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
				}

			} else if (comparator.equals("<>")){
				if(leftType.getFullName().equals(ATF.JAVA_TYPE_BOOLEAN) && rightType.getFullName().equals(ATF.JAVA_TYPE_BOOLEAN)){
					boolean value = RandomGenerator.getInstance().generateRandomBoolean();
					leftExpr  += "(" + Boolean.toString(value)+ ");";
					rightExpr += "(" + Boolean.toString(!value) + ");";
				}else{
					throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
				}
			}else {
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
			}		
			
			SComment com1 = new SComment(leftExpr);
			SComment com2 = new SComment(rightExpr);
			com1.setNotcomment(true);
			com2.setNotcomment(true);
			atfTestModel.getSmethod().addBodyElement(com1);
			atfTestModel.getSmethod().addBodyElement(com2);
			
			
		} catch (Exception e) {
			ATF.log(e);
		}
	}
	
	@Override
	public void generateInvariant(OCLConstraint constraint,ATFTestModel atfTestModel) {
		evaluate(constraint,atfTestModel);
	}
	
	private int[] getOperationChildIndex(Tree<EObject> operationTree, List<EObject> list){
		if(!(operationTree.getHead() instanceof OperationCallExpImpl)){
			return null;
		}
		
		EObject left = getFirstElement(operationTree).getHead();
		EObject right= getSecondElement(operationTree).getHead();
		
		
		return new int[]{list.indexOf(left), list.indexOf(right)};
	}
}
