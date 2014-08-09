package eu.atac.atf.eclipse;

public final class ATACPlug {
	private ATACPlug(){}
	
	public static final String ATF_TEST_DIRECTORY  = "test";
	public static final String ATF_MODEL_DIRECTORY = "model";
	public static final String ATF_MODEL_NAME      = "ATACTestModel";
	public static final String ATF_DEFAULT_CLASS_FILE_DIR = "bin";
	public static final String ATF_CONSOLE = "ATF_CONSOLE";
	
	
	
	
	@Deprecated
	public static void log(Exception ex){
		System.err.println(ex.getMessage());
		ex.printStackTrace(System.err);
		//ExceptionHandler.handle(e, getShell(), JUnitMessages.JUnitLaunchShortcut_dialog_title, JUnitMessages.JUnitLaunchShortcut_message_launchfailed);
	}
	@Deprecated
	public static void log(String message){
		System.out.println(message);
	}
}
