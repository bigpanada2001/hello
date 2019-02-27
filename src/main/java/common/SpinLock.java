package common;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLock {
	public static SpinLock sobj = new SpinLock();
  private AtomicReference<Thread> sign =new AtomicReference<>();
  public void lock(){
    Thread current = Thread.currentThread();
    while(!sign.compareAndSet(null, current)){
    }
  }
 
  public void unlock (){
    Thread current = Thread.currentThread();
    sign.compareAndSet(current, null);
  }
  

  
  public static void main(String[] param) {
	  MyThread2 myThread1=new MyThread2();
	  MyThread2 myThread2=new MyThread2();
	  new Thread(myThread1, "test1").start();
	  new Thread(myThread2, "test2").start();
  }
}