package kr.co.n3soft.n3com.parser;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;

import javac.builder.JavaBuilder;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.dialogs.ListSelectionDialog;

public class N3ModelParser implements IRunnableWithProgress, Parser, IN3ModelTokenType, IN3ModelParsingTable
{
	N3ModelLexer _lexer;
	N3ModelEmitter _emitter;
	private static final int TOTAL_TIME = 10000;
	LoadProgressRun p = null;

	private static final int INCREMENT = 500;
	private int total = 0;
	int _state;
	int go = 0;
	boolean beginParser = false;

	public N3ModelParser(File file, JavaBuilder builder,LoadProgressRun lr ) throws IOException {
		_lexer = new N3ModelLexer(file);
		_emitter = new N3ModelEmitter(builder);
		p= lr;
	}
	public N3ModelParser(File file, JavaBuilder builder) throws IOException {
		_lexer = new N3ModelLexer(file);
		_emitter = new N3ModelEmitter(builder);
		
	}
	public N3ModelParser(Reader reader, JavaBuilder builder)	{
		_lexer = new N3ModelLexer(reader);
		_emitter = new N3ModelEmitter(builder);
	}
	
	 public void run(IProgressMonitor monitor) throws InvocationTargetException,
     InterruptedException {
		 int token;

			_state = STATE_BEGIN;
			ModelManager.getInstance().init();
			try	{
				monitor.beginTask("Loading Project",
				        true ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
	next_state:
		
		      while (_state != STATE_END)	{
					if(!this.beginParser)
						continue;
					if(monitor.isCanceled()){
						monitor.done();
						return;
					}
					int ddd = _table[_state][0][0];
					
					if (processStmtToken(_table[_state][0][0]))	{
						monitor.worked(INCREMENT);
						nextState(0);
//						monitor.worked(INCREMENT);
						continue next_state;
					}

					token = _lexer.next();

//					monitor.setTaskName("Load "+_lexer.sval);
					monitor.worked(INCREMENT);
//					Thread.sleep(INCREMENT);
					for (int i=0; i<_table[_state].length; i++)
						if (token == _table[_state][i][0])	{
//							monitor.worked(INCREMENT);
							nextState(i);
						
							continue next_state;
						}
					

					_lexer.error("Parsing Failed: State " + _state + ", Token " + token);
				}
				
//				this.refStore.clear();
//			for(;total==10000;total=total+INCREMENT){
//				Thread.sleep(INCREMENT);
//				monitor.worked(total);
//			}
				
			monitor.done();
			
			
			
			
			_lexer.get_lineNumberCounter().close();
			
			
		
			}
			catch (ParserError e)	{
				e.printStackTrace();
				
//				throw e;
			}
			catch (Exception e)	{
				e.printStackTrace();
				ModelManager.getInstance().init();
//				_lexer.error("Exception: " + e);
			}
			finally{
				try{
				_lexer.get_lineNumberCounter().close();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			
	 }

	public void parse() throws ParserError	{
		beginParser = true;
//		int token;
//
//		_state = STATE_BEGIN;
//		ModelManager.getInstance().init();
//		try	{
//next_state:
//			while (_state != STATE_END)	{
//				
//				int ddd = _table[_state][0][0];
//				
//				if (processStmtToken(_table[_state][0][0]))	{
//					nextState(0);
//					continue next_state;
//				}
//
//				token = _lexer.next();
//				
//				for (int i=0; i<_table[_state].length; i++)
//					if (token == _table[_state][i][0])	{
//						nextState(i);
//						continue next_state;
//					}
//				if(token==104){
//					p.total = 5000;
//					go = 1;
//				}
//				else if(go==0 && total==5000){
//				 p.total = 5000;
//				}
//				else if(go==1 && total==8000){
//					 p.total = 8000;
//				}
//				else{
//					 p.total = total+INCREMENT;
//				}
//
//				_lexer.error("Parsing Failed: State " + _state + ", Token " + token);
//			}
//		for(;p.total==10000;p.total=total+INCREMENT){
//			
//		}
//		
//		ModelManager.getInstance().init();
//		_lexer.get_lineNumberCounter().close();
//	
//		}
//		catch (ParserError e)	{
//			
//			
//			throw e;
//		}
//		catch (Exception e)	{
//			e.printStackTrace();
//			ModelManager.getInstance().init();
//			_lexer.error("Exception: " + e);
//		}
//		finally{
//			try{
//			_lexer.get_lineNumberCounter().close();
//			}
//			catch(Exception e){
//				e.printStackTrace();
//			}
//		}
	}

	protected void nextState(int testing_state) throws Exception	{
		int result = _emitter.processCommand(_table[_state][testing_state][2], _lexer.sval);
		
		_state = (result != -1) ? result : _table[_state][testing_state][1];
	}

	protected boolean processStmtToken(int token) throws Exception	{
		switch (token)	{
		case t_until_key_value:
			_lexer.readUntil("PROPERTY_N3EOF");
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}