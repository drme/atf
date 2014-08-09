package eu.atac.atf.main;

public class ATFException extends RuntimeException{
	public static int ATF_ERROR_CODE_1000 = 1000;
	public static int ATF_ERROR_CODE_1001 = 1001;
	public static int ATF_ERROR_CODE_1002 = 1002;
	public static int ATF_ERROR_CODE_1003 = 1003;
	public static int ATF_ERROR_CODE_1004 = 1004;
	
	public static int ATF_UNIMPLEMENTED_CODE_1000 = 1000;
	public static int ATF_UNIMPLEMENTED_CODE_1001 = 1001;
	public static int ATF_UNIMPLEMENTED_CODE_1002 = 1002;
	public static int ATF_UNIMPLEMENTED_CODE_1003 = 1003;
	public static int ATF_UNIMPLEMENTED_CODE_1004 = 1004;
	
	public ATFException(String message) {
		super(message);
	}
	
	public ATFException(int id) {
		super(Integer.toString(id));
	}
	
	public ATFException(int id,String message) {
		super(Integer.toString(id) + ": " + message );
	}
	

	private static final long serialVersionUID = -3965542823349778463L;

}
