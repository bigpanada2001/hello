package common;

public class MyThread2 implements Runnable {//ʵ��Runnable�ӿ�
	  public void run(){
		  SpinLock.sobj.lock();
		  System.out.println("----------------begin lock");
		  try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println("----------------finish lock");
		  SpinLock.sobj.unlock();
	  }
}