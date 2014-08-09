package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tudresden.ocl20.pivot.essentialocl.expressions.impl.ExpressionInOclImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.PropertyCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableImpl;
import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ATFTestModel;
import eu.atac.atf.main.OCLConstraint;
import eu.atac.atf.test.metadata.SAssertion;
import eu.atac.atf.test.metadata.SComment;
import eu.atac.atf.test.metadata.SVariable;


/**
 * 
 *ExpressionInOclImpl - inv: orientation.oclIsUndefined()
 *  OperationCallExpImpl - oclIsUndefined
 *    PropertyCallExpImpl - Vector3D orientation
 *      VariableExpImpl - self
 *    VariableImpl - self
 *
 */
public class PPatternIsNull extends ConstrainPatternBase {
	private ConstrainPatternElementBase[] pattern;
	
	public PPatternIsNull(){
		pattern = new ConstrainPatternElementBase[]{
				new PESimpleClass(ExpressionInOclImpl.class),
				new PEOperationCall("oclIsUndefined"), 
				new PESimpleClass(PropertyCallExpImpl.class,1,1),
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
//			 *0ExpressionInOclImpl - inv: orientation.oclIsUndefined()
//			 *1  OperationCallExpImpl - oclIsUndefined
//			 *2    PropertyCallExpImpl - Vector3D orientation
//			 *3      VariableExpImpl - self
//			 *4    VariableImpl - self

			List<EObject> list = constraint.getEobjectList();
			
            SVariable variable = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
			
			String commment  = variable.getName() + parsePropertyCallChain(atfTestModel,list,2,false) + "(null);";
			
					
			SComment com1 = new SComment(commment);
			atfTestModel.getSmethod().addBodyElement(com1);
			
		} catch (Exception e) {
			ATF.log(e);
		}
	}
	
	@Override
	public void generateInvariant(OCLConstraint constraint,ATFTestModel atfTestModel) {
		
		List<EObject> list = constraint.getEobjectList();
		
        SVariable variable = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
		
		String assertion  = variable.getName() + parsePropertyCallChain(atfTestModel,list,2,true) + "()";
		
		SAssertion invAssertion = new SAssertion(assertion);
		invAssertion.setType(SAssertion.TYPE_ASSERT_NULL);
		atfTestModel.getSmethod().addBodyElement(invAssertion);
	}	

}


