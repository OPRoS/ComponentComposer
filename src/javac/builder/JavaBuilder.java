package javac.builder;

public interface JavaBuilder extends LineScope 
{
	public ClassBuilder buildClass(String name);
	public InterfaceBuilder buildInterface(String name);

	public void _package(String _package);
	public void _import(String _import);
}
