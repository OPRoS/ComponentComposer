package javac.test;
import javac.builder.*;
import java.io.PrintWriter;

public class TJavaBuilder implements JavaBuilder
{
	PrintWriter _out;
	
	public TJavaBuilder(PrintWriter out)	{
		_out = out;
	}
	
	public ClassBuilder buildClass(String name)	{
		return new TClassBuilder(_out, name);
	}
	
	public InterfaceBuilder buildInterface(String name)	{
		return new TInterfaceBuilder(_out, name);
	}

	public void _package(String _package)	{
		_out.print("[package: " + _package + "]");
	}
	
	public void _import(String _import)	{
		_out.print("[import: " + _import + "]");
	}

	public void _begin(int line)	{
		_out.print("[begin: " + line + "]");
	}
	
	public void _end(int line)	{
		_out.print("[end: " + line + "]");
	}
}
