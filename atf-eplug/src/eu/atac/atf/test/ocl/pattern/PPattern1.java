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
import eu.sarunas.atf.generators.tests.RandomGenerator;
import eu.sarunas.atf.meta.sut.Method;

 /**
  *ExpressionInOclImpl - inv invariant_size:name.size() < 4
  *OperationCallExpImpl - <
  *  OperationCallExpImpl - size
  *    PropertyCallExpImpl - String name
  *      VariableExpImpl - self
  *  IntegerLiteralExpImpl - 4
  *    VariableImpl - self
  */
public class PPattern1 extends ConstrainPatternBase {
	private ConstrainPatternElementBase[] pattern;
	
	public PPattern1(){
		pattern = new ConstrainPatternElementBase[]{
				new PESimpleClass(ExpressionInOclImpl.class),
				new PESimpleClass(OperationCallExpImpl.class,2,2),
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
			
			
			String comparator    = null;
			String variableName  = null;
			String variableProp  = null;
			String propertyType  = null;
			String value = null;
			
			OperationCallExpImpl element1 = (OperationCallExpImpl) list.get(1);
			comparator = OCLUtil.getOperationCallOperator(element1);
			
			OperationCallExpImpl element2 = (OperationCallExpImpl) list.get(2);
			variableProp = OCLUtil.getOperationCallOperator(element2);
			
			PropertyCallExpImpl element3 = (PropertyCallExpImpl) list.get(3);
			propertyType = OCLUtil.getPropertyCallType(element3);
			
			VariableExpImpl element4 = (VariableExpImpl) list.get(4);
			variableName = OCLUtil.getVariableExpType(element4);
			
			IntegerLiteralExpImpl element5 = (IntegerLiteralExpImpl) list.get(5);
			value = Integer.toString(element5.getIntegerSymbol());
			
			if(variableProp.equals("size") && propertyType.equals("String")){
				int size = Integer.parseInt(value);
				
				Method method = ModelUtil.findSetter(atfTestModel.getClazz(), variableName);
				if(method == null){
					throw new ATFException(ATFException.ATF_ERROR_CODE_1000,"Field (" + variableName + ") does not have setter");
				}
				
				SVariable svariable = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
				if(svariable == null){
					throw new ATFException(ATFException.ATF_ERROR_CODE_1001);
				}
				
				
				if(comparator.equals("<")){
					SComment comment = new SComment(svariable.getName() + "." + method.getName() 
							+ "(\"" + RandomGenerator.getInstance().randomString(0,size, false) +"\")");
					atfTestModel.getSmethod().addBodyElement(comment);
				}else{
					throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
				}
			}else{
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
			}
				
		} catch (Exception e) {
			ATF.log(e);
		}
	}
	
	@Override
	public void generateInvariant(OCLConstraint constraint,ATFTestModel atfTestModel) {
		evaluate(constraint,atfTestModel);
	}
	
}

