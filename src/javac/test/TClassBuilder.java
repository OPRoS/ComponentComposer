package javac.test;
import javac.builder.*;
import java.io.PrintWriter;

public class TClassBuilder extends TInterfaceBuilder implements ClassBuilder 
{
	public TClassBuilder(PrintWriter out, String name)	{
		_out = out;

		_out.print("[class: " + name + "]");
	}
	
	public ClassBuilder buildSubclass(String name)	{
		return new TClassBuilder(_out, name);
	}
	
	public void _implements(String _implements)	{
		_out.print("[implements: " + _implements + "]");
	}
}
