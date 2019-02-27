package single;

public class Singleton7 {
	private Singleton7() {
		
	}
	
	private static class Singleton7Holder {
		private static Singleton7 single = new Singleton7();
	}
	
	public static Singleton7 getInstance() {
		return Singleton7Holder.single;
	}

}
