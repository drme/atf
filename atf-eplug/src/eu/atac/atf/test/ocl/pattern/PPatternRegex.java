package eu.atac.atf.test.ocl.pattern;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tudresden.ocl20.pivot.essentialocl.expressions.impl.ExpressionInOclImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.StringLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableImpl;
import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ATFTestModel;
import eu.atac.atf.main.OCLConstraint;
import eu.atac.atf.test.metadata.SAssertion;
import eu.atac.atf.test.metadata.SComment;
import eu.atac.atf.test.metadata.SVariable;
import eu.sarunas.atf.generators.tests.RandomGenerator;

/**
 * 
 * ExpressionInOclImpl - inv invariant_IPE:name.RegExpMatch('[1-9][0-9]{3}')
 *  OperationCallExpImpl - RegExpMatch
 *    PropertyCallExpImpl - String name
 *      VariableExpImpl - self
 *    StringLiteralExpImpl - [1-9][0-9]{3}
 *      VariableImpl - self
 */
public class PPatternRegex extends ConstrainPatternBase {
	private ConstrainPatternElementBase[] pattern;
	
	public PPatternRegex(){
		pattern = new ConstrainPatternElementBase[]{
				new PESimpleClass(ExpressionInOclImpl.class),
				new PEOperationCall("RegExpMatch"), 
				new PEPropertyCall("String"),
				new PESimpleClass(VariableExpImpl.class,1,1),
				new PESimpleClass(StringLiteralExpImpl.class,1,1),
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
			
//			 *0 ExpressionInOclImpl - inv invariant_IPE:name.RegExpMatch('[1-9][0-9]{3}')
//			 *1  OperationCallExpImpl - RegExpMatch
//			 *2    PropertyCallExpImpl - String name
//			 *3      VariableExpImpl - self
//			 *4    StringLiteralExpImpl - [1-9][0-9]{3}
//			 *5      VariableImpl - self
			List<EObject> list = constraint.getEobjectList();
			String regex = ((StringLiteralExpImpl)list.get(4)).getStringSymbol();
			
			SVariable variable = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
			
			String expr  = variable.getName() + parsePropertyCallChain(atfTestModel,list,2,false);
			
			String commment = expr + "(\"" + RandomGenerator.getInstance().randomRegexString(regex) + "\");";
			
			SComment com1 = new SComment(commment);
			com1.setNotcomment(true);
			atfTestModel.getSmethod().addBodyElement(com1);
			
		} catch (Exception e) {
			ATF.log(e);
		}
	}
	
	@Override
	public void generateInvariant(OCLConstraint constraint,ATFTestModel atfTestModel) {
		
		List<EObject> list = constraint.getEobjectList();
		String regex = ((StringLiteralExpImpl)list.get(4)).getStringSymbol();
		
		SVariable variable = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
		
		String expr  = variable.getName() + parsePropertyCallChain(atfTestModel,list,2,true);
		
		String assertion = expr + "().matches(\"" + regex +"\")";
		
		SAssertion invAssertion = new SAssertion(assertion);
		atfTestModel.getSmethod().addBodyElement(invAssertion);
	}	

}


