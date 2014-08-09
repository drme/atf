package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tudresden.ocl20.pivot.essentialocl.expressions.impl.ExpressionInOclImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.IntegerLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.OperationCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.PropertyCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableImpl;
import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ATFException;
import eu.atac.atf.main.ATFTestModel;
import eu.atac.atf.main.ModelUtil;
import eu.atac.atf.main.OCLConstraint;
import eu.atac.atf.main.OCLUtil;
import eu.atac.atf.test.metadata.SComment;
import eu.atac.atf.test.metadata.SVariable;
import eu.sarunas.atf.generators.code.java.CodeGeneratorJava;
import eu.sarunas.atf.generators.tests.RandomGenerator;

import eu.sarunas.projects.atf.metadata.generic.Type;

/**
 * 
 *ExpressionInOclImpl - inv: self.materialThickness > 0
 *  OperationCallExpImpl - >
 *    PropertyCallExpImpl - Real materialThickness
 *      VariableExpImpl - self
 *    IntegerLiteralExpImpl - 0
 *      VariableImpl - self
 */
public class PPattern3 extends ConstrainPatternBase {
	private ConstrainPatternElementBase[] pattern;
	
	public PPattern3(){
		pattern = new ConstrainPatternElementBase[]{
				new PESimpleClass(ExpressionInOclImpl.class),
				new PESimpleClass(OperationCallExpImpl.class,1,1),
				new PESimpleClass(PropertyCallExpImpl.class,1,1),
				new PESimpleClass(VariableExpImpl.class,1,1),
				new PESimpleClass(IntegerLiteralExpImpl.class,1,1),
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
			OCLUtil.printTreeToSysout(constraint.getEobjectTree());
			
			String comparator    = null;
			
			OperationCallExpImpl element1 = (OperationCallExpImpl) list.get(1);
			comparator = OCLUtil.getOperationCallOperator(element1);


//0			ExpressionInOclImpl - inv: self.materialThickness > 0
//1			  OperationCallExpImpl - >
//2			    PropertyCallExpImpl - Real materialThickness
//3			      VariableExpImpl - self
//4			    IntegerLiteralExpImpl - 0
//5			      VariableImpl - self

			
			SVariable variable = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
			
			String leftExpr  = variable.getName() + parsePropertyCallChain(atfTestModel,list,2,false);
			int value = ((IntegerLiteralExpImpl)list.get(4)).getIntegerSymbol();
			
			Type typeL = ModelUtil.findField(atfTestModel, OCLUtil.getTypedElementType(list.get(3)), OCLUtil.getPropertyCallName(list.get(2))).getType();
			
			if(!typeL.getName().equals(ATF.JAVA_TYPE_JAVA_LANG_DOUBLE) ){
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000,typeL.getName());
			}

			if(comparator.equals(">")){
				if(typeL.getName().equals(ATF.JAVA_TYPE_JAVA_LANG_DOUBLE) ){
					double randValue = RandomGenerator.getInstance().generateRandomDouble() + value;
					leftExpr   += "(new Double(" + CodeGeneratorJava.toString(randValue) + "));"; 
				}
				
			}else{
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
			}
			
			SComment com1 = new SComment(leftExpr);
			com1.setNotcomment(true);
			atfTestModel.getSmethod().addBodyElement(com1);
		} catch (Exception e) {
			ATF.log(e);
		}
	}
	
	@Override
	public void generateInvariant(OCLConstraint constraint,ATFTestModel atfTestModel) {
		evaluate(constraint,atfTestModel);
	}
}
