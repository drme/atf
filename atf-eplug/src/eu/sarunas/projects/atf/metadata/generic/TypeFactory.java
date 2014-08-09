package eu.sarunas.projects.atf.metadata.generic;

import eu.atac.atf.main.ATF;
import eu.atac.atf.main.ATFException;

@Deprecated
public class TypeFactory{
	protected TypeFactory(){
	}
	
	public Type create(String name){
		if(name.equals(ATF.JAVA_TYPE_INT)){ // TODO: eu.sarunas.projects.atf.metadata.generic.types hello hello
			return new Type(name, null);
		}
		if(name.equals(ATF.JAVA_TYPE_BOOLEAN)){
			return new Type(name, null);
		}	
		if(name.equals(ATF.JAVA_TYPE_DOUBLE)){
			return new Type(name, null);
		}
		
		if(name.equals(ATF.JAVA_TYPE_VOID)){
			return new Type(name, null);
		}
		if(name.equals(ATF.JAVA_TYPE_JAVA_LANG_OBJECT)){
			return new Type(name, null);
		}
		
		
		if(isJavaType(name)){
			if(name.equals(ATF.JAVA_TYPE_JAVA_MATH_BIGDECIMAL)){
				return new Type(name, null);
			}
			if(name.equals(ATF.JAVA_TYPE_JAVA_MATH_BIGINTEGER)){
				return new Type(name, null);
			}

			if(name.equals(ATF.JAVA_TYPE_JAVA_UTIL_LIST)){
				return new Type(name, null);
			}
			if(name.equals(ATF.JAVA_TYPE_JAVA_LANG_DOUBLE)){
				return new Type(name, null);
			}
			if(name.equals(ATF.JAVA_TYPE_JAVA_LANG_STRING)){
				return new Type(name, null);
			}
			if(name.equals(ATF.JAVA_TYPE_JAVA_LANG_BOOLEAN)){
				return new Type(name, null);
			}
			if(name.startsWith("javax")){//
				return new Type(name, null);
			}
		}
		

		
		throw new ATFException(ATFException.ATF_UNIMPLEMENTED_CODE_1000, "Unknow type: " + name);
	}
	
	public boolean isJavaType(String name){
		return name.startsWith("java.") || name.startsWith("javax.") ;
	}
	
	public static TypeFactory getInstance(){
		if (null == instance)
		{
			instance = new TypeFactory();
		}
		
		return instance;
	}
	
	private static TypeFactory instance = null;
};
