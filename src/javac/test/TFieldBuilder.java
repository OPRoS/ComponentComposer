package javac.test;
import javac.builder.*;
import java.io.PrintWriter;

public class TFieldBuilder implements FieldBuilder
{
	PrintWriter _out;

	public TFieldBuilder(PrintWriter out, Type type, String name)	{
		_out = out;

		_out.print("[field: " + type.getType() + " " + name + "]");
		for (int i=0; i<type.getArrayDimension(); i++)
			_out.print("[]");
		for (int i=0; i<type.getOptions().length; i++)
			_out.print("[" + type.getOptions()[i] + "]");
	}

	public void _array()	{
		_out.print("[]");
	}
	
	public void _initialize(String value)	{
		_out.print(" =" + value);
	}

	public void _begin(int line)	{
		_out.print("[begin: " + line + "]");
	}
	
	public void _end(int line)	{
		_out.print("[end: " + line + "]");
	}
}
