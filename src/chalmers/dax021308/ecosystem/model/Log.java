package chalmers.dax021308.ecosystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for saving and printing various data for testing and development.
 * @author Erik
 *
 */
public class Log {
	
	private static List<String> printHistory;
	
	public static final int LEVEL_VERBOSE = 1;
	public static final int LEVEL_INFO = 2;
	public static final int LEVEL_ERROR = 3;
	
	static {
		printHistory = new ArrayList<String>();
	}
	
	
	/**
	 * Informational printout.
	 * @param s
	 */
	public static void i(String s) {
		System.out.println(System.currentTimeMillis() + " - "+ Thread.currentThread().toString() + " - " + s);		
	}
	
	/**
	 * Verbose printout.
	 * @param s
	 */
	public static void v(String s) {
		System.out.println(System.currentTimeMillis() + " - "+ Thread.currentThread().toString() + " - " + s);
	}
	
	public static void printWithThreadAndTime(String s) {
		System.out.println(System.currentTimeMillis() + " - "+ Thread.currentThread().toString() + " - " + s);
	}
	
	public static void clearLogHistory() {
		printHistory.clear();
	}
	
	public static void dumpLogToTextfile(String fileName, int level) {
		//TODO: Implement
	}
}
