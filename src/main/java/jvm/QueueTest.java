package jvm;

import java.util.concurrent.PriorityBlockingQueue;

public class QueueTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<Integer>();
		q.add(9);
		q.add(1);
		q.add(5);
		q.add(4);
		q.add(7);
		q.add(8);
		System.out.print(q.poll());
		System.out.print(q.poll());
		System.out.print(q.poll());
		System.out.print(q.poll());
		System.out.print(q.poll());
		System.out.print(q.poll());
//		String a="a".intern();
//		String b="a".intern();
//		
//		System.out.print(a==b);
		
		String a1="a";
		String b1="a";
		System.out.print(a1==b1);
		
		String a2=new String("a");
		String b2=new String("a");
		System.out.print(a2==b2);
	}

}
