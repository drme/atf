package eu.atac.atf.test.ocl.pattern;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import tudresden.ocl20.pivot.essentialocl.expressions.impl.IntegerLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.OperationCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.PropertyCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.StringLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.TypeLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableImpl;
import tudresden.ocl20.pivot.pivotmodel.impl.TypedElementImpl;
import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ATFException;
import eu.atac.atf.main.ATFTestModel;
import eu.atac.atf.main.ModelUtil;
import eu.atac.atf.main.OCLConstraint;
import eu.atac.atf.main.OCLUtil;
import eu.atac.atf.main.Tree;
import eu.atac.atf.test.metadata.SAssertion;
import eu.atac.atf.test.metadata.SVariable;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Field;

import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.utils.Logger;
import eu.sarunas.projects.atf.metadata.generic.Type;

public abstract class ConstrainPatternBase {
	public abstract ConstrainPatternElementBase [] getPattern();
	
	public void generatePre(OCLConstraint constrain,ATFTestModel atfTestModel) {
		
	}
	
	public void generateInvariant(OCLConstraint constrain,ATFTestModel atfTestModel) {
		
	}
	
	public boolean check(OCLConstraint constraint){
		return check(constraint.getEobjectList(), getPattern());
	}
	
	protected boolean check(List<EObject> list, ConstrainPatternElementBase [] pattern){
		try {
			int index = 0;
			for (int i = 0; i < pattern.length; i++) {
				index = pattern[i].check(list, index);
				if(index < 0){
					return false;
				}
			}
			if(index == list.size()){
				return true;
			}
			
			return false;
		} catch (Exception e) {
			ATF.log(e);
		}
		return false;		
	}
	
	protected SAssertion evaluate(ATFTestModel atfTestModel,Tree<EObject> tree,SVariable variable ){

		if(tree.getHead() instanceof OperationCallExpImpl){
			OperationCallExpImpl head = (OperationCallExpImpl) tree.getHead();
			List<Tree<EObject>> subList = tree.getSubTrees();
			String operation = OCLUtil.getOperationCallOperator(head);
			
			if(subList.size() == 2){
				
				String leftString  = parsePropertyCallChain2(atfTestModel,subList.get(0), true);
				String rightString = parsePropertyCallChain2(atfTestModel,subList.get(1), true);
				
				String leftType = OCLUtil.getTypedElementType(subList.get(0).getHead()) ;
				String rightType= OCLUtil.getTypedElementType(subList.get(1).getHead()) ;
				
				if(ModelUtil.isString(leftType) && ModelUtil.isString(rightType)){
					return new SAssertion(variable.getName() + " " + leftString + ".equals(" + rightString + ")");
				}else if(ModelUtil.isInteger(leftType) && ModelUtil.isInteger(rightType)){
					return new SAssertion(variable.getName() + " " + leftString + " == " + rightString);
				}else {
					throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000,leftType + " " + rightType);
				}

			}else if(subList.size() == 1){
//				PropertyCallExpImpl propertyCallExpImpl = (PropertyCallExpImpl) getFirstElement(tree).getHead();
				
				//Hack Begin
//				if(OCLUtil.getTypedElementType(propertyCallExpImpl).equals(ATF.JAVA_TYPE_JAVA_LANG_STRING)){
//					if(operation.equals("size")){
//						s.append('.').append("length").append("()");
//					}else{
//						s.append('.').append(OCLUtil.getOperationCallOperator(head)).append("()");
//					}
//					evaluateOperationLeaf(atfTestModel,subList.get(0), s);
//				}else{
//					s.append(operation).append("()");
//					evaluateOperationLeaf(atfTestModel,subList.get(0), s);
//				}
				//Hack End
			}			
			
		}else {
			throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000,tree.getHead().getClass().getName());
		}
		
		return null;
	}
		
	//Pergalvoti
	@Deprecated
	protected void evaluate(OCLConstraint constraint,ATFTestModel atfTestModel){
		try {
			
			StringBuilder s = new StringBuilder();
			
			List<Tree<EObject>> subTree = constraint.getEobjectTree().getSubTrees();
			for (int i = 0; i < subTree.size(); i++) {
				Tree<EObject> tree2 = subTree.get(i);
				if(tree2.getHead() instanceof OperationCallExpImpl){
					evaluateOperationLeaf(atfTestModel,tree2,s);				
				}else if(tree2.getHead() instanceof VariableImpl){
					
				}else{
					OCLUtil.printTreeToSysout(tree2);
					throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000,tree2.getHead().getClass().getName());
				}
			}
			
			SAssertion invAssertion = new SAssertion(s.toString());
			atfTestModel.getSmethod().addAssertBodyElement(invAssertion);
			
		} catch (Exception e) {
			ATF.log(e);
		}
	}
	
	//Pergalvoti
	@Deprecated
	protected void evaluateOperationLeaf(ATFTestModel atfTestModel,Tree<EObject> tree, StringBuilder s){
		if(tree == null){
			return;
		}
		
		if(tree.getHead() instanceof OperationCallExpImpl){
			OperationCallExpImpl head = (OperationCallExpImpl) tree.getHead();
			List<Tree<EObject>> subList = tree.getSubTrees();
			String operation = OCLUtil.getOperationCallOperator(head);
			
			if(subList.size() == 2){
				
				StringBuilder left  = new StringBuilder();
				StringBuilder right = new StringBuilder();
				
				evaluateOperationLeaf(atfTestModel,subList.get(0), left);
				evaluateOperationLeaf(atfTestModel,subList.get(1), right);
				if(operation.equals("<>")){
					operation = "!=";
				}
				
				s.append(left).append(' ').append(operation).append(' ').append(right);
			}else if(subList.size() == 1){
				PropertyCallExpImpl propertyCallExpImpl = (PropertyCallExpImpl) getFirstElement(tree).getHead();
				
				//Hack Begin
				if(OCLUtil.getTypedElementType(propertyCallExpImpl).equals(ATF.JAVA_TYPE_JAVA_LANG_STRING)){
					if(operation.equals("size")){
						s.append('.').append("length").append("()");
					}else{
						s.append('.').append(OCLUtil.getOperationCallOperator(head)).append("()");
					}
					evaluateOperationLeaf(atfTestModel,subList.get(0), s);
				}else{
					s.append(operation).append("()");
					evaluateOperationLeaf(atfTestModel,subList.get(0), s);
				}
				//Hack End
			}
		}else if(tree.getHead() instanceof PropertyCallExpImpl){
			SVariable svariable = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
			String prop = svariable.getName() + parsePropertyCallChain(atfTestModel, tree, true) + "()";
			
			//Hack Begin
			PropertyCallExpImpl head = (PropertyCallExpImpl) tree.getHead();
			EObject leaf = getFirstElement(tree).getHead();
			if(leaf instanceof TypedElementImpl){
				String type   = OCLUtil.getTypedElementType(leaf);
				Field field = ModelUtil.findField(atfTestModel, type, OCLUtil.getPropertyCallName(head));
				if(field != null && field.getType().getName().equals(ATF.JAVA_TYPE_JAVA_MATH_BIGDECIMAL)){
					prop += ".doubleValue()";
				}
			}
			
			//Hack End
			
			
			s.append(prop);
			
		}else if(tree.getHead() instanceof IntegerLiteralExpImpl){
			IntegerLiteralExpImpl head = (IntegerLiteralExpImpl) tree.getHead();
			s.append(head.getIntegerSymbol());
			
		}else if(tree.getHead() instanceof StringLiteralExpImpl){
			StringLiteralExpImpl head = (StringLiteralExpImpl) tree.getHead();
			s.append('"').append(head.getQualifiedName()).append('"');
			
		}else if(tree.getHead() instanceof VariableExpImpl){
			VariableExpImpl head = (VariableExpImpl) tree.getHead();
			
			String variable = OCLUtil.getVariableExpElement(head);
			
			Method method = ModelUtil.findGetter(atfTestModel.getClazz(), variable);
			if(method == null){
				throw new ATFException(ATFException.ATF_ERROR_CODE_1000,
						atfTestModel.getClazz().getName() + "field (" + variable + ") does not have getter");
			}
			
			SVariable svariable = atfTestModel.getSmethod().findVariable(ATF.VARIAVLE_ID_TEST_OBJECT);
			if(svariable == null){
				throw new ATFException(ATFException.ATF_ERROR_CODE_1001);
			}
			
			s.insert(0, svariable.getName() );
		}else
		{
			throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
		}
	}
	
	protected String parsePropertyCallChain2(ATFTestModel atfTestModel,Tree<EObject> tree,boolean getter){
		Tree<EObject> level1 = tree;
		Tree<EObject> level2 = getFirstElement(tree);
		
		
		StringBuilder s = new StringBuilder();
		int index = 0;
		while(level1 != null){
			
			if(level1.getHead() instanceof StringLiteralExpImpl){
				s.append('"').append(((StringLiteralExpImpl)level1.getHead()).getStringSymbol()).append('"');
				break;
			}
			
			if(level1.getHead() instanceof IntegerLiteralExpImpl){
				s.append(((IntegerLiteralExpImpl)level1.getHead()).getIntegerSymbol());
				break;
			}
			
//			if(!(level1.getHead() instanceof PropertyCallExpImpl)){
//				return s.toString();
//			}
			if(level2 == null){
				break;
			}
			
			if(level1.getHead() instanceof PropertyCallExpImpl && level2.getHead() instanceof OperationCallExpImpl){
				String propName = OCLUtil.getPropertyCallName(level1.getHead());
				String operationName = OCLUtil.getOperationCallOperator(level2.getHead()); 
				
				if(operationName.equals("at")){
					try{
						OperationCallExpImpl operation = (OperationCallExpImpl) level2.getHead();
						PropertyCallExpImpl  source    = (PropertyCallExpImpl) operation.getSource();
						IntegerLiteralExpImpl indexAt  =  (IntegerLiteralExpImpl) getSecondElement(level2).getHead();
						
						if(index == 0 && !getter){
							String type = OCLUtil.getSequenceType(source);
							if(type.equals(ATF.JAVA_TYPE_JAVA_UTIL_LIST)){
								throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
							}else{
								String insert = ".get(" + indexAt.getIntegerSymbol() +")" + "." ;
								if(OCLUtil.isSeqProperty(level1.getHead())){
									Method method = ModelUtil.findGetter(atfTestModel,OCLUtil.getSequenceType(source), propName);
									insert += method.getName() + "()";
								}else{
									Method method = ModelUtil.findSetter(atfTestModel, OCLUtil.getSequenceType(source) , propName);
									insert += method.getName();
								}
								s.insert(0, insert);
							}
						}else{
							Method method = ModelUtil.findGetter(atfTestModel, OCLUtil.getSequenceType(source) , propName);
							s.insert(0, ".get(" + indexAt.getIntegerSymbol() +")" + "." + method.getName() +"()" );
						}						
					}catch (Exception e){
						Logger.logger.severe(e.getMessage());
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
					}
				}else if(operationName.equals("oclAsType")){ 
					// PropertyCallExpImpl - Sequence(CalculationNominalElement) calculationNominalElement
					//	OperationCallExpImpl - oclAsType
					//		VariableExpImpl - org.inspection_plusplus.IPE
					//		TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
					Class cast = parseOclIsTypeOfType(atfTestModel, level2);
					
					Method method = ModelUtil.findGetter( cast , propName);
					s.insert(0,"." + method.getName() + "()");

					break;
				}else{
					throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000,operationName);
				}
				level1 = level2;
				level2 = getFirstElement(level1);
				
				level1 = level2;
				level2 = getFirstElement(level1);
				index += 2; 
			}else if(level1.getHead() instanceof OperationCallExpImpl && level2.getHead() instanceof PropertyCallExpImpl){
				String operationLevel1 = OCLUtil.getOperationCallOperator(level1.getHead()); 
				
				if(OCLUtil.isSeqProperty(level2.getHead())){
					String sourceType = OCLUtil.getPropertyCallSourceType(level2.getHead());
					String propertyName = OCLUtil.getPropertyCallName(level2.getHead());
					
					if(operationLevel1.equals("size")){
						Method methodGetter = ModelUtil.findGetter(atfTestModel, sourceType, propertyName);
						
						if(methodGetter.getReturnType().getName().equals(ATF.JAVA_TYPE_JAVA_UTIL_LIST)){
							s.insert(0, ".size()" );
						}else{
							throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000, methodGetter.getReturnType().getName());
						}
					}else{
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
					}
				}else{
					throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
				}
				level1 = level2;
				level2 = getFirstElement(level1);
				++index;
			}else if(level1.getHead() instanceof PropertyCallExpImpl && level2.getHead() instanceof TypedElementImpl){
				if(!OCLUtil.isSeqProperty(level1.getHead())){
					Method method = null;
					if(index == 0 && !getter){
						if(getter){
							method = ModelUtil.findGetter(atfTestModel, OCLUtil.getTypedElementType(level2.getHead()), OCLUtil.getPropertyCallName(level1.getHead()));
						}else{
							method = ModelUtil.findSetter(atfTestModel, OCLUtil.getTypedElementType(level2.getHead()), OCLUtil.getPropertyCallName(level1.getHead()));
						}
						s.append("." + method.getName());
					}else{
						method = ModelUtil.findGetter(atfTestModel, OCLUtil.getTypedElementType(level2.getHead()), OCLUtil.getPropertyCallName(level1.getHead()));
						s.insert(0, "." +method.getName() +"()" );
					}					
				}else{
					String seqType = OCLUtil.getSequenceType(level1.getHead());
					if(index == 0 && !getter){
						Method methodGetter = ModelUtil.findGetter(atfTestModel, OCLUtil.getTypedElementType(level2.getHead()), OCLUtil.getPropertyCallName(level1.getHead())) ;
						
						if(methodGetter.getReturnType().getName().equals(ATF.JAVA_TYPE_JAVA_UTIL_LIST)){
							s.insert(0, "." + methodGetter.getName() + "().add");
						}else{
							throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
						}
						
						
//						org.inspection_plusplus.ID4Referencing
//						org.inspection_plusplus.QC
//						ipeRef
						
						System.out.println(seqType);
						System.out.println(OCLUtil.getTypedElementType(level2.getHead()));
						System.out.println(OCLUtil.getPropertyCallName(level1.getHead()));
						
					}else{
						throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
					}

//					if(method == null){
//						System.out.println(level1.getHead());
//						System.out.println(level2.getHead());
//					}
				}

				
				level1 = level2;
				level2 = getFirstElement(level1);
				++index;
			}else {
				String type = level1.getHead().getClass().getName();// + " " + level2.getHead().getClass().getName() ;
				OCLUtil.printTreeToSysout(level1);
				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000,type);
			} //IF
		}//WHILE
		
		return s.toString();
	}
				
	//Pergalvoti
	@Deprecated
	protected String parsePropertyCallChain(ATFTestModel atfTestModel,Tree<EObject> tree,boolean getter){
		List<EObject> list  = new ArrayList<EObject>();
		while(tree != null){
			if(!(tree.getHead() instanceof PropertyCallExpImpl || tree.getHead() instanceof VariableExpImpl)){
				break;
			}
			list.add(tree.getHead());
			tree = getFirstElement(tree);
		}
		
		return parsePropertyCallChain (atfTestModel,list,0, getter);
	}
	
	//Pergalvoti
	@Deprecated
	protected String parsePropertyCallChain(ATFTestModel atfTestModel,List<EObject> list, int startIndex,boolean getter){
		String s = null;
		for (int i = startIndex; i < list.size() - 1; i++) {
			EObject present = list.get(i);
			EObject next    = list.get(i + 1);
			
			if(!(present instanceof PropertyCallExpImpl)){
				return s;
			}
			if(present instanceof PropertyCallExpImpl && next instanceof TypedElementImpl){
				Method method = null;
				if(i == startIndex){
					if(getter){
						method = ModelUtil.findGetter(atfTestModel, OCLUtil.getTypedElementType(next), OCLUtil.getPropertyCallName(present));
					}else{
						method = ModelUtil.findSetter(atfTestModel, OCLUtil.getTypedElementType(next), OCLUtil.getPropertyCallName(present));
					}
					s = "." + method.getName();
				}else{
					method = ModelUtil.findGetter(atfTestModel, OCLUtil.getTypedElementType(next), OCLUtil.getPropertyCallName(present));
					s = "." +method.getName() +"()" + s;
				}
			}
		}
		
		return s;
	}
	
	//Pergalvoti
	@Deprecated
	protected Type parsePropertyCallChainType(ATFTestModel atfTestModel,Tree<EObject> tree){
		List<EObject> list  = new ArrayList<EObject>();
		while(tree != null){
			if(!(tree.getHead() instanceof PropertyCallExpImpl || tree.getHead() instanceof VariableExpImpl)){
				break;
			}
			list.add(0,tree.getHead());
			tree = getFirstElement(tree);
		}
		
		Type type = null;
		for (int i = 0; i < list.size(); i++) {
			if(type == null){
				type = (Type) ModelUtil.findClass(atfTestModel, OCLUtil.getTypedElementType(list.get(i)));
			}else{
				type = (Type) ModelUtil.findField((Class) type, OCLUtil.getPropertyCallName(list.get(i))).getType();
			}
		}
		
		return type;
	}
	
	
	/**
	 * OperationCallExpImpl - oclIsTypeOf
	 *   VariableExpImpl - org.inspection_plusplus.IPE
	 *   TypeLiteralExpImpl - org.inspection_plusplus.IPESurfacePoint
	 */
	protected Class parseOclIsTypeOfType(ATFTestModel atfTestModel, Tree<EObject> tree){
		if(!(tree.getHead() instanceof OperationCallExpImpl)){
			throw new ATFException(ATFException.ATF_ERROR_CODE_1001);
		}
		TypeLiteralExpImpl t1 = (TypeLiteralExpImpl) getSecondElement(tree).getHead();
		
		return ModelUtil.findClass(atfTestModel, OCLUtil.getType(t1.getReferredType()));
	}
	
	protected List<OCLConstraint> findConstraints(ATFTestModel atfTestModel, Class clazz){
		List<OCLConstraint> cons = new ArrayList<OCLConstraint>(3);
		for (int i = 0; i < atfTestModel.getConstraints().size(); i++) {
			Class consClass = ModelUtil.findClass(atfTestModel, atfTestModel.getConstraints().get(i).getModelType());
			System.out.println(consClass.getFullName() + " " + clazz.getFullName() + " " +clazz.instanceOf(consClass));
			if( clazz.instanceOf(consClass)){
				cons.add(atfTestModel.getConstraints().get(i));
			}
		}
		return cons;
	}
	//Iskelti
	protected Tree<EObject> getFirstElement(Tree<EObject> tree){
		if(tree == null){
			return null;
		}
		List<Tree<EObject>> subList = tree.getSubTrees();
		if(subList != null && subList.size() >= 1){
			return subList.get(0);
		}

		return null;
	}
	
	//Iskelti
	protected Tree<EObject> getSecondElement(Tree<EObject> tree){
		if(tree == null){
			return null;
		}
		List<Tree<EObject>> subList = tree.getSubTrees();
		if(subList != null && subList.size() >= 2){
			return subList.get(1);
		}

		return null;
	}

}


//String propType = OCLUtil.getPropertyCallType(level1.getHead());
//String propName = OCLUtil.getPropertyCallName(level1.getHead());
//
//level1 = level2;
//level2 = getFirstElement(level1);
//
//String operationName  = OCLUtil.getOperationCallOperator(level1.getHead()); 
//if(operationName.equals("at")){
//	int    operationIndex = ((IntegerLiteralExpImpl) getSecondElement(level1).getHead()).getIntegerSymbol();
//	if(level2.getHead() instanceof PropertyCallExpImpl){
//		System.out.println(level2.getHead());
//		String propSeqType = OCLUtil.getSequenceType(level2.getHead());
//		String propSeqName = OCLUtil.getPropertyCallName(level2.getHead());
//		Tree<EObject> seqParentTree = getFirstElement(level2);
//		if(seqParentTree.getHead() instanceof OperationCallExpImpl){
//			String operationParent = OCLUtil.getOperationCallOperator(seqParentTree.getHead());
//			if(operationParent.equals("oclAsType")){
//				
//				Class oclAsTypeClass =  parseOclIsTypeOfType( atfTestModel,seqParentTree);
//				Field field = ModelUtil.findField(oclAsTypeClass, propSeqName);
//				Method getterMethod = ModelUtil.findGetter(oclAsTypeClass, propSeqName);
//				
//				if(field.getType().getName().equals(ATF.JAVA_TYPE_JAVA_UTIL_LIST)){
//					Method setterMethod  = ModelUtil.findSetter(atfTestModel, propSeqType , propName);
//
//					s.append(getterMethod.getName() + "()" + ".get(" + operationIndex + ")" + "." + setterMethod.getName());
//					break;//TODO
//				}else {
//					throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
//				}
//			} else{
//				throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
//			}
//		}else{
//			throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
//		}
//	}else{
//		throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
//	}
//}else{
//	throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000,operationName);
//}