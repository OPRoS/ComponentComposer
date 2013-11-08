package javac.builder;

public interface InterfaceBuilder extends LineScope
{
	public MethodBuilder buildMethod(Type returnType, String name);
	public FieldBuilder buildField(Type type, String name);

	public void _options(String[] options);
	public void _extends(String interface_);
}
