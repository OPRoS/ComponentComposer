package javac.test;
import javac.builder.*;
import java.io.PrintWriter;

public class TInterfaceBuilder implements InterfaceBuilder
{
	PrintWriter _out;

	public TInterfaceBuilder()	{ }
	
	public TInterfaceBuilder(PrintWriter out, String name)	{
		_out = out;
		_out.print("[interface: " + name + "]");
	}
	
	public MethodBuilder buildMethod(Type returnType, String name)	{
		return new TMethodBuilder(_out, returnType, name);
	}

	public FieldBuilder buildField(Type type, String name)	{
		return new TFieldBuilder(_out, type, name);
	}

	public void _name(String name)	{
		_out.print("[name: " + name + "]");
	}

	public void _options(String[] options)	{
		StringBuffer output = new StringBuffer();
		for (int i=0; i<options.length; i++)
			output.append("[" + options[i] + "]");
			
		_out.print(output);
	}
	
	public void _extends(String interface_)	{
		_out.print("[extends: " + interface_ + "]");
	}

	public void _begin(int line)	{
		_out.print("[begin: " + line + "]");
	}
	
	public void _end(int line)	{
		_out.print("[end: " + line + "]");
	}
}
