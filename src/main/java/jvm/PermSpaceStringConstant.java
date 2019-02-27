package jvm;

import java.util.ArrayList;
import java.util.List;

public class PermSpaceStringConstant {

	public static void main(String[] args) {
		 List<String> strs = new ArrayList<String>();
		 int i = 0;
		 
		 while(true) {
			 strs.add(String.valueOf(i++).intern()); 
			 System.out.println("We have created " + i + " constant String.");
			 try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
}