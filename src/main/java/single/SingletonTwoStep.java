package single;

public class SingletonTwoStep {
	private static SingletonTwoStep single = null;
	
	private SingletonTwoStep() {
		
	}
	public static SingletonTwoStep getInstance() {
		if(single == null) {
			synchronized(SingletonTwoStep.class) {
				if(single == null) {
					single = new SingletonTwoStep();
				}
			}
		}
		return single;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SingletonTwoStep t = new SingletonTwoStep();
		
	}

}
