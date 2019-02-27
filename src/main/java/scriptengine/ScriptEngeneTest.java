package scriptengine;

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngeneTest {

	public static void main(String[] args) {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("groovy");
		Bindings bindings = engine.createBindings();
		Map<String, String> context = new HashMap<String, String>();
		context.put("id", "zhangsan");
		context.put("name", "张三");
		bindings.put("context", context);
		bindings.put("dataType", "学生");
		String groovyScript = 
			"	def id = context.get(\"id\");"+
			"	def name = context.get(\"name\");"+
			"	def rst=dataType + id + name;"+
			"	System.out.println(\"---finish\"+rst);"+
			"	context.put(\"rst\",rst);";
		try {
			engine.eval(new StringReader(groovyScript), bindings);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---finish");
		
//		try {
//	        ScriptEngineManager factory = new ScriptEngineManager();
//	        ScriptEngine engine = factory.getEngineByName("groovy");
//	        String fact = "def factorial(n, msg) { println(msg);return n == 1 ? 1 : n * factorial(n - 1, msg);}";
//	        engine.eval(fact);
//	        Invocable inv = (Invocable) engine;
//	        Object[] params = { new Integer(5),"ssss"};
//	        Object result;
//	        result = inv.invokeFunction("factorial", params);
//	        System.out.println(result);
//	
//	        Bindings binding = engine.createBindings();  
//	        binding.put("date", new Date());  
//	        engine.eval("def getTime(){return date.getTime();}",binding);  
//	        engine.eval("def sayHello(name,age){return 'Hello,I am ' + name + ',age ' + age;}");  
//	        Object time = inv.invokeFunction("getTime", null);  
//	        System.out.println((Long) time);  
//	        String message = (String)inv.invokeFunction("sayHello", "zhangsan",new Integer(12));  
//	        System.out.println(message);  
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ScriptException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
