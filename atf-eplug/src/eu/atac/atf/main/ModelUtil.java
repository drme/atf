package eu.atac.atf.main;

import java.util.ArrayList;
import java.util.List;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Constructor;
import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Package;
import eu.sarunas.atf.meta.sut.Project;

public class ModelUtil {

	public static Class findClass(Project project, String pckg, String clazz){
		Package pack = findPackage(project,pckg);
		if(pack == null){
			return null;
		}
		
		return findClass(pack, clazz);
	}
	public static Class findClass(Project project, String type){
		int index = type.lastIndexOf(".");
		if(index < 0){
			throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000);
		}
		
		Package pckg = findPackage(project,type.substring(0, index));
		if(pckg == null){
			return null;
		}
		
		return findClass(pckg, type.substring(++index));
	}
	
	public static Class findClass(ATFTestModel atfTestModel, String type){
		int index = type.lastIndexOf(".");
		if(index < 0){
			throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000, type);
		}
		
		Package pckg = findPackage(atfTestModel,type.substring(0, index));
		if(pckg == null){
			return null;
		}
		
		return findClass(pckg, type.substring(++index));
	}
	
	public static Package findPackage(ATFTestModel atfTestModel, String pckg){
		return findPackage(atfTestModel.getProject(),pckg);
	}
	
	public static Package findPackage(Project project, String pckg){
		for (Package p : project.getPackages()) {
			if(p.getName().equals(pckg)){
				return p;
			}
		}
		
		return null;
	}
	
	public static Class findClass(Package pckg,String name){
		for (Class cls : pckg.getClasses()) {
			if(cls.getName().equals(name)){
				return (Class) cls;
			}
		}
		return null;
	}
	
	public static Class findClassFullName(Package pckg,String fullName){
		for (Class cls : pckg.getClasses()) {
			if(((Class)cls).getFullName().equals(fullName)){
				return (Class) cls;
			}
		}
		return null;
	}
	
    public static Field findField(ATFTestModel atfTestModel,String type, String fieldName){
    	return findField(findClass(atfTestModel, type), fieldName);
    }
    
    public static Field findField(Class cls,String name){
    	for (Field field : cls.getFields()) {
			if(field.getName().equals(name)){
				return (Field) field;
			}
		}
    	if(cls.getSuperClass() != null){
    		return findField((Class) cls.getSuperClass(), name);
    	}
    	
    	return null;
    }
	
    public static Method findGetter(ATFTestModel atfTestModel,String type, String fieldName){
    	return findGetter(findClass(atfTestModel, type), fieldName);
    }
    
    public static Method findGetter(Class cls, String fieldName){
    	String isGetter  = "is"  + fieldName;
    	String getGetter = "get" + fieldName;
    	
    	for (Method method : cls.getMethods()) {
    		if(method.getName().equalsIgnoreCase(getGetter) || method.getName().equalsIgnoreCase(isGetter)){
    			return (Method) method;
    		}
		}
    	if(cls.getSuperClass() != null){
    		return findGetter((Class) cls.getSuperClass(),fieldName);
    	}
    	
    	return null;
    }
    
    public static Method findSetter(ATFTestModel atfTestModel,String type, String fieldName){
    	return findSetter(findClass(atfTestModel, type), fieldName);
    }
    
    public static Method findSetter(Class cls,String fieldName){
    	String setSetter  = "set"  + ATF.firstToUpper(fieldName);
    	for (Method method : cls.getMethods()) {
    		if(method.getName().equals(setSetter) ){
    			return (Method) method;
    		}
		}
    	
    	if(cls.getSuperClass() != null){
    		return findSetter((Class) cls.getSuperClass(),fieldName);
    	}
    	
    	return null;
    }
	
	public static boolean isJavaBeanMethod(Method method){
		String name = method.getName();
		if(name.startsWith("is") || name.startsWith("get") || name.startsWith("set")){
			return true;
		}
		return false;
	}
	
	public static boolean isJavaBean(Class clazz){
		List<Method> list = clazz.getMethods();
		for (Method iMethod : list) {
		
			if(!isJavaBeanMethod(iMethod)){
				return false;
			}
		}
		return true;
	}
	
	public static List<Class> getAllParrents(ATFTestModel atfTestModel, Class clazz){
		List<Class> ret = new ArrayList<Class>();
		Project project  = atfTestModel.getProject();
		for (Package pcg : project.getPackages()) {
			for (Class iClass : pcg.getClasses()) {
				if(((Class)iClass).instanceOf(clazz)){
					ret.add((Class) iClass);
				}
			}
		}
		return ret;
	}
	
    public static boolean hasDefaultConstructor(Class clazz){
    	if(clazz.getConstructors().isEmpty()){
    		return true;
    	}
    	for (Constructor cons : clazz.getConstructors()) {
			if(cons.isDefaultConstructor()){
				return true;
			}
		}
    	return false;
    }

	public static boolean isString(String type) {
		return type.equals(ATF.JAVA_TYPE_JAVA_LANG_STRING) || type.equals(ATF.OCL_TYPE_STRING);
	}
	public static boolean isInteger(String type) {
		return type.equals(ATF.JAVA_TYPE_INT) || type.equals(ATF.OCL_TYPE_INTEGER);
	}

}
