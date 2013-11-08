package javac.test;
import java.io.File;
import java.io.PrintWriter;

import javac.parser.CplusParser;
import javac.parser.JavaParser;
import javac.parser.Parser;

public class ReleaseTest
{
	long _start_time;
	boolean isJava = true;

	public ReleaseTest()	{
		_start_time = System.currentTimeMillis();
	}

	public void test(String path) throws Exception	{
		if(this.isJava){
			parseJava(new File(path));
		}
		else{
			parseCplus(new File(path));
		}
		
//		parse(new File("/home/fish0/java"));
//		parse(new File("/mnt/windows/project"));

		System.out.println("Parsing succeed. Time:" + getTime() + "sec");
	}
	
	public void parseCplus(File file) throws Exception	{
		if (file.isDirectory())	{
			File[] list = file.listFiles();
			
			for (int i=0; i<list.length; i++)
				parseCplus(list[i]);
		}
		else if (file.getName().endsWith(".h"))	{
			Parser parser = new CplusParser(
				file, new TJavaBuilder(new PrintWriter(System.out)));
			
			parser.parse();
		}
	}
	public void parseJava(File file) throws Exception	{
		if (file.isDirectory())	{
			File[] list = file.listFiles();
			
			for (int i=0; i<list.length; i++)
				parseJava(list[i]);
		}
		else if (file.getName().endsWith(".java"))	{
			Parser parser = new JavaParser(
				file, new TJavaBuilder(new PrintWriter(System.out)));
			
			parser.parse();
		}
	}

	public double getTime()	{
		return (System.currentTimeMillis() - _start_time) / 1000.;
	}

	public static void main(String[] args)	{
		ReleaseTest t = new ReleaseTest();
		
		try	{
			t.test("C:\\s.h");
//			StringTokenizer st = new StringTokenizer("int i");
//			String type = st.nextToken();
//			String name = st.nextToken();
			System.out.println("dddd");
		}
		
		catch (Exception e)	{
			e.printStackTrace();
		}
	}

	public boolean isJava() {
		return isJava;
	}

	public void setJava(boolean isJava) {
		this.isJava = isJava;
	}
}
