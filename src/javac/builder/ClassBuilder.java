package javac.builder;

/** Class Builder */

public interface ClassBuilder extends InterfaceBuilder, LineScope 
{
	
	public ClassBuilder buildSubclass(String name);
	public MethodBuilder buildMethod(Type returnType, String name);
	public FieldBuilder buildField(Type type, String name);

	public void _options(String[] options);
	public void _extends(String interface_);
	public void _implements(String implements_);
}
