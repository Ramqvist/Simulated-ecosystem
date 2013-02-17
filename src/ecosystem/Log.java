package ecosystem;

public class Log {
	static void print(String s) {
		System.out.println(System.currentTimeMillis() + " - "+ Thread.currentThread().toString() + " - " + s);
	}
}
