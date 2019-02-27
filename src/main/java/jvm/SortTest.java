package jvm;

import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;
public class SortTest {

	public static void main(String[] args) {
		List<String> a = new ArrayList<String>(){{add("a");add("g");add("d");add("18");add("1");}};
		ArrayList<String> lists2 = new ArrayList<String>(){{
            add("test1");
            add("test2");
        }};
        Collections.sort(a);
        System.out.println(a.toString());
//        new TreeMap(a);
	}

}
