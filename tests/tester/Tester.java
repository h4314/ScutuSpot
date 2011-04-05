package tester;

public abstract class Tester {
	public static Tester t;
	
	protected int nbTests = 0;
	protected int success = 0;
	protected int failure = 0;
	
	/**
	 * Execute a serie of tests.
	 */
	protected abstract void _run();
	
	public static void test() {
		if(t == null) {
			System.out.println("No test to run.");
			return;
		}
		
		System.out.print("E: ");
		t._run();
		System.out.println();
		
		System.out.println("Summary : ");
		System.out.println("\texecuted : " + t.nbTests);
		System.out.println("\tsuccess  : " + t.success);
		System.out.println("\tfailure  : " + t.failure);
	}
	
	public void assertTrue(boolean val) {
		++nbTests;
		if(val) {
			System.out.print(".");
			++success;
		}
		else {
			System.out.println("F");
			++failure;
		}
	}
}
