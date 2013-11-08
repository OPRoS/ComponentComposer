package javac.parser;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import javac.builder.JavaBuilder;

public class CplusParser implements Parser,CplusParsingTable, TokenTypeCplus
{
	CplusLexer _lexer;
	CplusEmitter _emitter;

	int _state;

	public CplusParser(File file, JavaBuilder builder) throws IOException {
		_lexer = new CplusLexer(file);
		_emitter = new CplusEmitter(builder);
	}

	public CplusParser(Reader reader, JavaBuilder builder)	{
		_lexer = new CplusLexer(reader);
		_emitter = new CplusEmitter(builder);
	}

	public void parse() throws CompileError	{
		int token;

		_state = STATE_BEGIN;
			
		try	{
next_state:
			while (_state != STATE_END)	{
				int ddd = _table[_state][0][0];
				if (processStmtToken(_table[_state][0][0]))	{
					nextState(0);
					continue next_state;
				}

				token = _lexer.next();
				
				for (int i=0; i<_table[_state].length; i++)
					if (token == _table[_state][i][0])	{
						nextState(i);
						continue next_state;
					}

				_lexer.error("Parsing Failed: State " +_lexer.sval+ _state + ", Token " + token);
			}
		}
		catch (CompileError e)	{
			throw e;
		}
		catch (Exception e)	{
			e.printStackTrace();
			_lexer.error("Exception: " + e);
		}
	}

	protected void nextState(int testing_state) throws Exception	{
		int result = _emitter.processCommand(_table[_state][testing_state][2], _lexer.sval);
		
		_state = (result != -1) ? result : _table[_state][testing_state][1];
	}

	protected boolean processStmtToken(int token) throws Exception	{
		switch (token)	{
		case t_until_line:
			_lexer.readUntil(null);
			return true;
		case t_until_s_end:
			_lexer.readUntil(")");
			return true;
		case t_until_m_end:
			_lexer.readUntil("}");
			return true;
		case t_until_p_end:
			_lexer.readUntil(">");
			return true;
		case t_until_key_value:
			_lexer.readUntil(";");
			return true;
			case t_block:
				_lexer.readUntil(null);
				return true;
			case t_expr:
				_lexer.readUntil(",;");
				return true;
		}

		return false;
	}
}
