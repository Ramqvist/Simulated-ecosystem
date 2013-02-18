package chalmers.dax021308.ecosystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for saving and printing various data for testing and development.
 * 
 * TODO: Put inputs to the historyList.
 * @author Erik
 *
 */
public class Log {
	
	private static List<String> printHistory;
	
	private static boolean enableLog = true;
	
	/* Log level constants */
	public static final int LEVEL_VERBOSE = 1;
	public static final int LEVEL_INFO    = 2;
	public static final int LEVEL_ERROR   = 3;
	
	static {
		printHistory = new ArrayList<String>(256);
	}
	
	
	/**
	 * Informational printout.
	 * @param s
	 */
	public static void i(String s) {
		if(enableLog) 
			System.out.println(s);		
	}
	
	/**
	 * Verbose printout.
	 * @param s
	 */
	public static void v(String s) {
		if(enableLog)
			System.out.println(s);
	}
	
	/**
	 * Error printout.
	 * @param s
	 */
	public static void e(String s) {
		if(enableLog)
			System.out.println(s);
	}
	
	
	/**
	 * Prints the String with the {@link System#currentTimeMillis()} and {@link Thread#currentThread()}.
	 * 
	 * @param s
	 */
	public static void printWithThreadAndTime(String s) {
		if(enableLog)
			System.out.println(System.currentTimeMillis() + " - "+ Thread.currentThread().toString() + " - " + s);
	}
	
	public static void clearLogHistory() {
		printHistory.clear();
	}
	
	public static void dumpLogToTextfile(String fileName, int level) {
		//TODO: Implement
	}
}
