package javac.test;
import javac.builder.*;
import java.io.PrintWriter;

public class TMethodBuilder implements MethodBuilder
{
	PrintWriter _out;
	
	public TMethodBuilder(PrintWriter out, Type returnType, String name)	{
		_out = out;
		
		if (returnType == MethodBuilder.CONSTRUCTOR)
			_out.print("[constructor]");
		else if (returnType == MethodBuilder.STATIC_BLOCK)
			_out.print("[static]");
		else	{
			_out.print("[method: " + returnType.getType() + " " + name + "]");
			for (int i=0; i<returnType.getArrayDimension(); i++)
				_out.print("[]");
			for (int i=0; i<returnType.getOptions().length; i++)
				_out.print("[" + returnType.getOptions()[i] + "]");
		}
	}

	public void _array()	{
		_out.print("[]");
	}

	public void _parameter(Type type, String name)	{
		_out.print("[param: " + type.getType() + " " + name + "]");
		for (int i=0; i<type.getArrayDimension(); i++)
			_out.print("[]");
		for (int i=0; i<type.getOptions().length; i++)
			_out.print("[" + type.getOptions()[i] + "]");
	}
	
	public void _throws(String exception)	{
		_out.print("[throws: " + exception + "]");
	}
	
	public void _body(String source)	{
		_out.print("{" + source + "}");
	}

	public void _begin(int line)	{
		_out.print("[begin: " + line + "]");
	}
	
	public void _end(int line)	{
		_out.print("[end: " + line + "]");
	}
}
