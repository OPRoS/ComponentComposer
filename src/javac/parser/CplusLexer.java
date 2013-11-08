package javac.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StreamTokenizer;

import kr.co.n3soft.n3com.parser.N3StreamTokenizer;
import kr.co.n3soft.n3com.parser.ParserError;

public class CplusLexer implements TokenTypeCplus
{
	File _file;
	LineNumberReader _lineNumberCounter;
	N3StreamTokenizer _tokenizer;

	String sval;

	public CplusLexer(File file) throws IOException {
		this(new BufferedReader(new FileReader(file)));
		_file = file;
	}

	public CplusLexer(Reader reader)	{
		_file = null;
		_tokenizer = new N3StreamTokenizer(
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

	public int next() throws ParserError, IOException	{
		int token = _tokenizer.nextToken();
		sval = _tokenizer.sval;
		System.out.println("sval==>"+sval);
		

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
	
	public String readUntil(String until) throws ParserError, IOException {
		setTokenalization(false);
		
		char ch;
		int r, scope_m = (until == null) ? 1 : 0, scope_s = 0;
		StringBuffer read = new StringBuffer();
		char buf[] = new char[20];
		int i= 0;
//		String s;
		boolean isBreak = false;
		while (!isBreak)	{
			String s  = this.readLine();
			if(until==null){
				isBreak = true;
			}
			else if(s==null || s.lastIndexOf(until)>=0){
				isBreak = true;
			}
			
			if(i>0){
				read.append("/n");	
			}
			read.append(s);
			i++;
		}
			
			

//		_tokenizer.pushBack();
		setTokenalization(true);
       
		return (sval = read.toString());
	}

//	public String readUntil(String until) throws CompileError, IOException {
//		setTokenalization(false);
//		
//		char ch;
//		int r, scope_m = (until == null) ? 1 : 0, scope_s = 0;
//		StringBuffer read = new StringBuffer();
//		
//		while ((r = r()) != -1)	{
//			ch = (char)r;
//			
//			switch (ch)	{
//				case '{': scope_m++; break;
//				
//				case '}': scope_m--; break;
//				case '(': scope_s++; break;
//				case ')': scope_s--; break;
//			}
//
//			if (until == null && scope_m == 0) 
//				break;
//			else if (scope_m == 0 && scope_s == 0 && until.indexOf((char)ch) != -1) 
//				break;
//			
//			if (ch == '\"' || ch == '\'')
//				read.append(ch + _tokenizer.sval + ch);
//			else
//				read.append(ch);
//		}
//
//		if (r == -1)
//			error("\"" + until + "\" expected.");
//
//		_tokenizer.pushBack();
//		setTokenalization(true);
//
//		return (sval = read.toString());
//	}

	public String readLine(){
		return _tokenizer.getReadLine();
	}

	public int r() throws IOException {
		
		return _tokenizer.nextToken();
	}

	public void error(String errorMsg) 	{
		System.out.println("errorMsg:"+errorMsg);
	}

	public LineNumberReader get_lineNumberCounter() {
		return _lineNumberCounter;
	}

	public void set_lineNumberCounter(LineNumberReader numberCounter) {
		_lineNumberCounter = numberCounter;
	}

	
}
