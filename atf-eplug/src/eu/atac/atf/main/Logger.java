package eu.atac.atf.main;

@Deprecated
public class Logger {
	@Deprecated
	public static void log(Exception ex){
		System.err.println(ex.getMessage());
		ex.printStackTrace(System.err);
	}
}
