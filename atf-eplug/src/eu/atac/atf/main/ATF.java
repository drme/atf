package eu.atac.atf.main;


public class ATF {
	public static final String ATF_LOG4JAVA_FILE = "log4j.properties";
	public static final String ATF_OCL_FILE_EXTENSION = "ocl";
	public static final String ATF_DEFAULT_OCL_FILE = "ocl.ocl";
	public static final String ATF_TEST_PACKAGE = "atac";
	
	public static final String NEW_LINE = System.getProperty("line.separator"); 
	public static final String TAB      = "\t"; 
	
	@Deprecated
	public static final String ATF_DOUBLE_FORMAT = ".2f";

	
	public static final String DEFAULT_TEST_FILE_NAME = "Test";
	public static final String DEFAULT_TEST_NAME      = "test";
	public static final String ANOTATION_TEST         = "@Test"; 
	
	public static final String OCL_KIND_INV  = "invariant"; 
	public static final String OCL_KIND_PRE  = "precondition"; 
	public static final String OCL_KIND_POST = "postcondition"; 
	public static final String OCL_TYPE_STRING  = "String"; 
	public static final String OCL_TYPE_INTEGER = "Integer"; 
	
	public static final int VARIAVLE_ID_UNKNOW      = -1; 
	public static final int VARIAVLE_ID_TEST_OBJECT = 10; 
	public static final int VARIAVLE_ID_PARAM       = 100;
	public static final int VARIAVLE_ID_PARAM_1     = 101; 
	public static final int VARIAVLE_ID_PARAM_2     = 102;
	public static final int VARIAVLE_ID_PARAM_3     = 102;
	public static final int VARIAVLE_ID_LOOP_OBJECT = 201; 
	public static final int VARIAVLE_ID_OBJECT_1	   = 1001; 
	public static final int VARIAVLE_ID_OBJECT_2 	   = 1002; 
	public static final int VARIAVLE_ID_OBJECT_3    = 1003; 
	public static final int VARIAVLE_ID_OBJECT_4    = 1004; 
	
	public static final String JAVA_TYPE_INT           = "int"; 
	public static final String JAVA_TYPE_DOUBLE        = "double"; 
	public static final String JAVA_TYPE_BOOLEAN       = "boolean"; 
	public static final String JAVA_TYPE_VOID          = "void"; 
	
	
	public static final String JAVA_TYPE_JAVA_LANG_OBJECT        = "java.lang.Object"; 
	public static final String JAVA_TYPE_JAVA_MATH_BIGDECIMAL    = "java.math.BigDecimal"; 
	public static final String JAVA_TYPE_JAVA_MATH_BIGINTEGER    = "java.math.BigInteger"; 	
	public static final String JAVA_TYPE_JAVA_UTIL_LIST          = "java.util.List"; 
	public static final String JAVA_TYPE_JAVA_LANG_STRING        = "java.lang.String"; 
	public static final String JAVA_TYPE_JAVA_LANG_DOUBLE        = "java.lang.Double"; 
	public static final String JAVA_TYPE_JAVA_LANG_BOOLEAN       = "java.lang.Boolean"; 
	
	//LOG
	public static void log(Exception ex){
		System.err.println(ex.getMessage());
		ex.printStackTrace(System.err);
	}
	public static void log(String message){
		System.out.println(message);
	}
	
	//UTIL
	public static String firstToUpper(String s){
		if(hasLenght(s)){
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		}
		return s;
	}
	
	public static String removeNewLineSymbols(String s){
		if(s == null || s.length() == 0){
			return s;
		}
		return s.replace('\n', ' ').replace('\r', ' ').trim();
	}
	
	public static boolean hasLenght(String s){
		if(s == null || s.length() == 0){
			return false;
		}
		
		return true;
	}
	
}
