package javac.builder;

public interface MethodBuilder extends LineScope
{	
	public static final Type 
		CONSTRUCTOR = new Type("#constructor", null, 0), 
		STATIC_BLOCK = new Type("#static_block", null, 0);
	
	public void _array();
	public void _parameter(Type type, String name);
	public void _throws(String exception);
	public void _body(String source);
}
