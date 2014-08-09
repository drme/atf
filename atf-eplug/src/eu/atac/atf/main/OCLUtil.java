package eu.atac.atf.main;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import tudresden.ocl20.pivot.essentialocl.expressions.Variable;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.ExpressionInOclImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.IntegerLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.IteratorExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.OperationCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.PropertyCallExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.StringLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.TypeLiteralExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableExpImpl;
import tudresden.ocl20.pivot.essentialocl.expressions.impl.VariableImpl;
import tudresden.ocl20.pivot.essentialocl.types.impl.SequenceTypeImpl;
import tudresden.ocl20.pivot.metamodels.java.internal.model.JavaClass;
import tudresden.ocl20.pivot.metamodels.java.internal.model.JavaField;
import tudresden.ocl20.pivot.metamodels.java.internal.model.JavaPackage;
import tudresden.ocl20.pivot.pivotmodel.Constraint;
import tudresden.ocl20.pivot.pivotmodel.Namespace;
import tudresden.ocl20.pivot.pivotmodel.Type;
import tudresden.ocl20.pivot.pivotmodel.impl.TypedElementImpl;

public class OCLUtil {

	public static String getPropertyCallName(EObject eObject){
		return getPropertyCallName((PropertyCallExpImpl) eObject);
	}
	
	public static String getPropertyCallType(EObject eObject){
		return getPropertyCallType((PropertyCallExpImpl) eObject);
	}
	
	public static String getVariableExpType(EObject eObject){
		return getVariableExpType((VariableExpImpl) eObject);
	}
	
	public static String getTypedElementType(EObject eObject){
		return getTypedElementType((TypedElementImpl)eObject);
	}
	
	public static String getOperationCallOperator(EObject eObject){
		return getOperationCallOperator((OperationCallExpImpl)eObject);
	}
	
	public static String getIteratorName(EObject eObject){
		return getIteratorName((IteratorExpImpl)eObject);
	}
	
	public static String getIteratorName(IteratorExpImpl iteratorExpImpl){
		return iteratorExpImpl.getName();
	}
	
	public static String getTypeLiteralExpType(TypeLiteralExpImpl typeLiteralExpImpl){
		return getType(typeLiteralExpImpl.getReferredType());
	}
	
	public static String getPropertyCallName(PropertyCallExpImpl propertyCallExpImpl){
		JavaField javaField = (JavaField) propertyCallExpImpl.getReferredProperty();
		return javaField.getName();
	}
	
	public static String getPropertyCallPackage(PropertyCallExpImpl propertyCallExpImpl){
		Type type = propertyCallExpImpl.getType();
		return getPackage(type.getNamespace());
	}
	
	public static String getPropertyCallClass(PropertyCallExpImpl propertyCallExpImpl){
		Type type = propertyCallExpImpl.getType();
		return type.getName();
	}
	
	public static String getPropertyCallType(PropertyCallExpImpl propertyCallExpImpl){
		Type type = propertyCallExpImpl.getType();
		Namespace namespace = type.getNamespace();
		if(namespace == null){
			return type.getName();
		}
		return getPackage(type.getNamespace()) + "." + type.getName();
	}
	
	public static String getVariableExpPackage(VariableExpImpl variableExpImpl){
		Type type = variableExpImpl.getType();
		return getPackage(type.getNamespace());
	}
	
	public static String getVariableExpType(VariableExpImpl variableExpImpl){
		Type type = variableExpImpl.getType();
		return getPackage(type.getNamespace()) + "." + type.getName();
	}

	public static String getTypedElementType(TypedElementImpl typedElementImpl){
		Type type = typedElementImpl.getType();
		return getType(type);
	}
	
	public static String getVariableExpElement(VariableExpImpl variableExpImpl){
		Variable variable = variableExpImpl.getReferredVariable();
		return variable.getName();
	}
	
	public static String getOperationCallOperator(OperationCallExpImpl operationCallExpImpl){
		return operationCallExpImpl.getReferredOperation().getName();
	}
	
	public static String getExpressionInOclBody(ExpressionInOclImpl expressionInOclImpl){
		return ATF.removeNewLineSymbols(expressionInOclImpl.getBody());
	}
	
	public static String getType(Type type){
		String pckg = getPackage(type.getNamespace());
		return pckg != null ? pckg + "." + type.getName() : type.getName();
	}
	public static String getSequenceType(EObject eObject){
		SequenceTypeImpl type = (SequenceTypeImpl) ((PropertyCallExpImpl)eObject).getReferredProperty().getType();
		return getType(type.getElementType());
	}
	
	public static String getTypeIfSeq(EObject eObject){
		if(((PropertyCallExpImpl)eObject).getReferredProperty().getType() instanceof SequenceTypeImpl){
			return getSequenceType(eObject);
		}
		return getPropertyCallType(eObject);
	}
	
	public static boolean isSeqProperty(EObject eObject){
		if(((PropertyCallExpImpl)eObject).getReferredProperty().getType() instanceof SequenceTypeImpl){
			return true;
		}
		return false;
	}
	
	@Deprecated
	public static String getType(JavaClass javaClass){
		return javaClass.getName();
	}

	public static String getPackage(JavaClass javaClass){
		return getPackage((JavaPackage)javaClass.getNamespace());
	}
	
	public static String getPackage(Namespace namespace){
		return getPackage((JavaPackage)namespace);
	}
	
	public static String getPackage(JavaPackage javaPackage){
		if(javaPackage == null){
			return null;
		}
		
		String pcg = javaPackage.getName();
		JavaPackage nested = (JavaPackage) javaPackage.getNestingNamespace();
		while(nested != null && !nested.getName().equals("root")){
			pcg = nested.getName() + "." + pcg;
			nested = (JavaPackage) nested.getNestingNamespace();
		}
		return pcg;
	}
	
	
	private static final int indent = 2;
	
	public static void printTreeToSysout(Tree<EObject> tree){
		System.out.println(printTree(tree));
	}
	
	public static String printTree(Tree<EObject> tree) {
		return printTree(tree,0);
	}

	private static String printTree(Tree<EObject> tree,int increment) {
		String s   = "";
		String inc = "";
		for (int i = 0; i < increment; ++i) {
			inc = inc + " ";
		}
		
		EObject head = tree.getHead();
		
		s = inc + toStringEObject(head);
		
		for (Tree<EObject> child : tree.getSubTrees()) {
			s += "\n" + printTree(child,increment + indent);
		}
		return s;
	}
	
	public static String toString(Constraint constraint){
		StringBuilder s = new StringBuilder();
		org.eclipse.emf.common.util.TreeIterator<EObject> treeIterator = constraint.eAllContents();
		
		for (TreeIterator<EObject> iterator = treeIterator; iterator.hasNext();){
			EObject head = iterator.next();
			
			s.append(toStringEObject(head));

			s.append(ATF.NEW_LINE);
		}
		
		return s.toString();
	}
	
	public static String print(List<EObject> list){
		StringBuilder s = new StringBuilder();
		
		for (EObject head: list){
			s.append(toStringEObject(head));

			s.append(ATF.NEW_LINE);
		}
		
		return s.toString();
	}
	
	public static String toStringEObject(EObject eObject){
		String cls = eObject.getClass().getSimpleName();
		
		if(eObject instanceof PropertyCallExpImpl){
			PropertyCallExpImpl propertyCallExpImpl = (PropertyCallExpImpl) eObject;
			return cls + " - " + getPropertyCallClass(propertyCallExpImpl) + " " + getPropertyCallName(propertyCallExpImpl);
		}else if(eObject instanceof VariableExpImpl){
			VariableExpImpl variableExpImpl = (VariableExpImpl) eObject;
			return cls + " - " +  getTypedElementType(variableExpImpl) + " " + variableExpImpl.getReferredVariable().getName();//getVariableExpElement(variableExpImpl) + "-" +
		}else if(eObject instanceof OperationCallExpImpl){
			OperationCallExpImpl operationCallExpImpl = (OperationCallExpImpl) eObject;
			return cls + " - " + getOperationCallOperator(operationCallExpImpl);
		}else if(eObject instanceof ExpressionInOclImpl){
			//ExpressionInOclImpl expressionInOclImpl = (ExpressionInOclImpl) eObject;
			//returncls + " - " + getExpressionInOclBody(expressionInOclImpl));
			return cls;
		}else if(eObject instanceof VariableImpl){
			VariableImpl variableImpl = (VariableImpl) eObject;
			return cls + " - " + variableImpl.getName();
		}else if(eObject instanceof IntegerLiteralExpImpl){
			IntegerLiteralExpImpl integerLiteralExpImpl = (IntegerLiteralExpImpl) eObject;
			return cls + " - " + integerLiteralExpImpl.getIntegerSymbol();
		}else if(eObject instanceof StringLiteralExpImpl){
			StringLiteralExpImpl stringLiteralExpImpl = (StringLiteralExpImpl) eObject;
			return cls + " - " + stringLiteralExpImpl.getStringSymbol();
		}else if(eObject instanceof IteratorExpImpl){
			IteratorExpImpl iteratorExpImpl = (IteratorExpImpl) eObject;
			return cls + " - " + iteratorExpImpl.getName();
		}else if(eObject instanceof TypeLiteralExpImpl){
			TypeLiteralExpImpl typeLiteralExpImpl = (TypeLiteralExpImpl) eObject;
			return cls + " - "  + getType(typeLiteralExpImpl.getReferredType()) ;
		}
		else{
			return cls + " - TODO";
		}
	}
	
	public static List<EObject> getEObjectList(Constraint constraint){
		List<EObject> list = new ArrayList<EObject>();
		org.eclipse.emf.common.util.TreeIterator<EObject> treeIterator = constraint.eAllContents();
		for (TreeIterator<EObject> iterator = treeIterator; iterator.hasNext();){
			list.add(iterator.next());
		}
		
		return list;
	}
	
	public static List<EObject> getEObjectList(Tree<EObject> tree){
		List<EObject> list = new ArrayList<EObject>();
		list.add(tree.getHead());
		
		org.eclipse.emf.common.util.TreeIterator<EObject> treeIterator = tree.getHead().eAllContents();
		for (TreeIterator<EObject> iterator = treeIterator; iterator.hasNext();){
			list.add(iterator.next());
		}
		
		return list;
	}
	
	public static String getConstraintClass(Constraint constraint){
		if(constraint.getConstrainedElement().size() == 1){
			JavaClass javaClass = (JavaClass) constraint.getConstrainedElement().get(0);
			return getType(javaClass);
		}
		
		throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);		
	}
	
	public static String getConstraintPackage(Constraint constraint){
		if(constraint.getConstrainedElement().size() == 1){
			JavaClass javaClass = (JavaClass) constraint.getConstrainedElement().get(0);
			return getPackage(javaClass);
		}
		
		throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);		
	}
	
	public static List<OCLConstraint> getConstrainsForClass(List<OCLConstraint> constraintList, String _package, String _class){
		List<OCLConstraint> cons = new ArrayList<OCLConstraint>();
		
		for (OCLConstraint constraint : constraintList) {
			if(isConstraintForClass(constraint,_package, _class)){
				cons.add(constraint);
			}
		}
		
		return cons;
	}
	
	public static boolean isConstraintForClass(OCLConstraint constraint,String _package, String _class){
		return constraint.getPackage().equals(_package) && constraint.getClazz().equals(_class);
	}
	
	public static void printTree(Constraint constraint){
		printTree(constraint.getSpecification(), 0);
	}
	
	private static void printTree(EObject eObject, int level){
		String inc = "";
		for (int i = 0; i < level; ++i) {
			inc = inc + "  ";
		}
		for (EObject child : eObject.eContents()) {
			System.out.println(level + inc + " "  + toStringEObject(child));
			if(child.eContents().size() > 0){
				printTree(child, (level + 1));
			}
			
		}		
	}
	
	public static Tree<EObject> constructTree(Constraint constraint){
		try {
			Tree<EObject> tree = new Tree<EObject>(constraint.eAllContents().next());
			constructTree(constraint.getSpecification(), tree);
			return tree;
		} catch (Exception e) {
			ATF.log(e);
			return null;
		}
	}
	
	private static void constructTree(EObject eObject, Tree<EObject> tree){
		for (EObject child : eObject.eContents()) {
			Tree<EObject> childTree = tree.addLeaf(child);
			if(child.eContents().size() > 0){
				constructTree(child, childTree);
			}
		}		
	}

	public static String getTypedLiteralType(EObject eObject){
		return getType(((TypeLiteralExpImpl)eObject).getReferredType());
	}
	
	public static String getPropertyCallSourceType(EObject eObject){
		PropertyCallExpImpl property = (PropertyCallExpImpl) eObject;
		return getType(property.getSourceType()) ;
	}
	
}
