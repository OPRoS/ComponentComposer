package javac.parser;
import java.io.File;

public class CompileError extends Exception
{
	File _file;
	int _line;
	
	public CompileError(File file, int line, String message)	{
		super(message);
		_file = file;
		_line = line;
	}

	public String toString()	{
		return _file + ":" + _line + ":" + getMessage();
	}
}
