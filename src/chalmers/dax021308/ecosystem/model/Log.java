package chalmers.dax021308.ecosystem.model;

public class Log {
	static void print(String s) {
		System.out.println(System.currentTimeMillis() + " - "+ Thread.currentThread().toString() + " - " + s);
	}
}
