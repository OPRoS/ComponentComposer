package kr.co.n3soft.n3com.parser;

import java.io.File;

public class ParserError extends Exception{
	File _file;
	int _line;
	
	public ParserError(File file, int line, String message)	{
		super(message);
		_file = file;
		_line = line;
	}

	public String toString()	{
		return _file + ":" + _line + ":" + getMessage();
	}
}
