package javac.parser;
import java.io.*;

public class JavaLexer implements TokenType
{
	File _file;
	LineNumberReader _lineNumberCounter;
	StreamTokenizer _tokenizer;

	String sval;

	public JavaLexer(File file) throws IOException {
		this(new BufferedReader(new FileReader(file)));
		_file = file;
	}

	public JavaLexer(Reader reader)	{
		_file = null;
		_tokenizer = new StreamTokenizer(
			_lineNumberCounter = new LineNumberReader(reader));

		setTokenalization(true);
	}

	public void setTokenalization(boolean b)	{
		if (b)	{
			_tokenizer.resetSyntax();
			_tokenizer.wordChars('a', 'z');
			_tokenizer.wordChars('A', 'Z');
			_tokenizer.wordChars('_', '_');
			_tokenizer.wordChars('$', '$');
			_tokenizer.parseNumbers();
			_tokenizer.ordinaryChar('.');
			_tokenizer.whitespaceChars(0, ' ');

			_tokenizer.quoteChar('"');
			_tokenizer.quoteChar('\'');
			_tokenizer.slashSlashComments(true);
			_tokenizer.slashStarComments(true);
		}
		else	{
			_tokenizer.resetSyntax();
			_tokenizer.quoteChar('"');
			_tokenizer.quoteChar('\'');
			_tokenizer.slashSlashComments(true);
			_tokenizer.slashStarComments(true);
		}
	}

	public int next() throws CompileError, IOException	{
		int token = _tokenizer.nextToken();
		sval = _tokenizer.sval;

		if (token == TT_WORD)	{
			for (int i=0; i<string_tokens.length; i++)
				if (string_tokens[i].equals(_tokenizer.sval))
					return _str_token + i;

			return t_id;
		}
		else if (token == TT_EOF)	{
			return t_eof;
		}
		else	{
			for (int i=0; i<mark_tokens.length; i++)
				if (mark_tokens[i] == token)	{
					sval = "" + (char)token;
					return _mark_token + i;
				}

			error("Invalid character: " + (char)token);
			return t_error;
		}
	}

	public String readUntil(String until) throws CompileError, IOException {
		setTokenalization(false);
		
		char ch;
		int r, scope_m = (until == null) ? 1 : 0, scope_s = 0;
		StringBuffer read = new StringBuffer();
		
		while ((r = r()) != -1)	{
			ch = (char)r;
			
			switch (ch)	{
				case '{': scope_m++; break;
				
				case '}': scope_m--; break;
				case '(': scope_s++; break;
				case ')': scope_s--; break;
			}

			if (until == null && scope_m == 0) 
				break;
			else if (scope_m == 0 && scope_s == 0 && until.indexOf((char)ch) != -1) 
				break;
			
			if (ch == '\"' || ch == '\'')
				read.append(ch + _tokenizer.sval + ch);
			else
				read.append(ch);
		}

		if (r == -1)
			error("\"" + until + "\" expected.");

		_tokenizer.pushBack();
		setTokenalization(true);

		return (sval = read.toString());
	}

	public int r() throws IOException {
		return _tokenizer.nextToken();
	}

	public void error(String errorMsg) throws CompileError	{
		throw new CompileError(
			_file, _lineNumberCounter.getLineNumber() + 1, errorMsg);
	}
}
