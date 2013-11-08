package javac.builder;

public class Type
{
	String _type;
	String[] _options;
	int _arrayDimension;

	public Type(String type, String[] options, int arrayDimension)	{
		_type = type;
		_options = options;
		_arrayDimension = arrayDimension;
	}

	public String getType()	{
		return _type;
	}

	public String[] getOptions()	{
		return _options;
	}
	
	public int getArrayDimension()	{
		return _arrayDimension;
	}
}
