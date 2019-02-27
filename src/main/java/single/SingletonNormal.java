package single;

public class SingletonNormal {
	private static SingletonNormal single = null;
	
	private SingletonNormal() {
		
	}
	public static SingletonNormal getInstance() {
		if(single == null) {
			single = new SingletonNormal();
		}
		return single;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingletonNormal t = new SingletonNormal();
		
	}

}
