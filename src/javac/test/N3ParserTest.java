package javac.test;

import java.io.File;
import java.io.PrintWriter;

import javac.parser.CompileError;
import kr.co.n3soft.n3com.parser.N3ModelParser;
import kr.co.n3soft.n3com.parser.Parser;

public class N3ParserTest{ 
long _start_time;

public N3ParserTest()	{
	_start_time = System.currentTimeMillis();
}

public void test() throws Exception	{
	parse(new File("C:\\TStack.h"));

//	parse(new File("/home/fish0/java"));
//	parse(new File("/mnt/windows/project"));

	System.out.println("Parsing succeed. Time:" + getTime() + "sec");
}

public void parse(File file) throws Exception	{
	if (file.isDirectory())	{
		File[] list = file.listFiles();
		
		for (int i=0; i<list.length; i++)
			parse(list[i]);
	}
	else if (file.getName().endsWith(".h"))	{
		Parser parser = new N3ModelParser(
			file, new TJavaBuilder(new PrintWriter(System.out)));
		
		parser.parse();
	}
}

public double getTime()	{
	return (System.currentTimeMillis() - _start_time) / 1000.;
}

public static void main(String[] args)	{
	N3ParserTest t = new N3ParserTest();
	
	try	{
		t.test();
	}
	catch (CompileError e)	{
		System.out.println(e);
	}
	catch (Exception e)	{
		e.printStackTrace();
	}
}
}
