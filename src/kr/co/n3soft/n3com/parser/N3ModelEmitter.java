package kr.co.n3soft.n3com.parser;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

import javac.builder.FieldBuilder;
import javac.builder.InterfaceBuilder;
import javac.builder.JavaBuilder;
import javac.builder.MethodBuilder;
import javac.builder.Type;

public class N3ModelEmitter implements IN3ModelTokenType, IN3ModelParsingTable
{
	JavaBuilder _javaBuilder;
	Stack _classBuilderStack;
	InterfaceBuilder _classBuilder; boolean _isClass;
	MethodBuilder _methodBuilder;
	FieldBuilder _fieldBuilder;

	StringBuffer _strBuffer;
	Vector _optionBuffer;

	String _name;
	int _arrayDimension;

	Type _type;
	public HashMap model = new HashMap();
	public java.util.ArrayList beginList = new java.util.ArrayList(); 
	

	public N3ModelEmitter(JavaBuilder builder)	{
		_javaBuilder = builder;
		_classBuilderStack = new Stack();

		_strBuffer = new StringBuffer();
		_optionBuffer = new Vector();
		_arrayDimension = 0;
	}


	private String popBuffer()	{
		try	{ return _strBuffer.toString(); }
		finally	{
			_strBuffer = new StringBuffer();
		}
	}

	private String[] popOptions()	{
		try	{ return (String[])_optionBuffer.toArray(new String[0]); }
		finally	{
			_optionBuffer.removeAllElements();
		}
	}

	public int processCommand(int command, String sval) throws Exception {
		ModelManager.getInstance().execute(command, sval);
//		switch (command)	{
//			case e_nothing:
//				break;
//			case e_push:
//			break;
//			case e_model_create:
//				break;
//			case e_model_set_property:
//				System.out.println("e_model_set_property:"+sval);
//				break;
//				
////			case e_class_begin: _isClass = true; break; 
////			case e_interface_begin: _isClass = false; break;
////
////			case e_class_id:
////				if (_classBuilder != null)
////					_classBuilderStack.push(_classBuilder);
////
////				_classBuilder = _isClass ? 
////					_javaBuilder.buildClass(sval) : 
////					_javaBuilder.buildInterface(sval);
////					
////				_classBuilder._options(popOptions());
////				_optionBuffer.removeAllElements();
////				break;
////			
////			case e_class_end:
////				if (_classBuilderStack.isEmpty())
////					_classBuilder = null;
////				else	{
////					_classBuilder = (InterfaceBuilder)_classBuilderStack.pop();
////					return STATE_CLASS_BODY;
////				}
////				break;
////				
////			case e_push:
////				_strBuffer.append(sval); 
////				break;
////				
////			case e_package: 
////				_javaBuilder._package(popBuffer()); break;
////			case e_import: 
////				_javaBuilder._import(popBuffer()); break;
////			case e_extends: 
////				_classBuilder._extends(popBuffer()); break;
////			case e_implements: 
////				if (_classBuilder instanceof ClassBuilder)
////					((ClassBuilder)_classBuilder)._implements(popBuffer());
////				break;
////
////			case e_option:
////				_optionBuffer.add(sval);
////				break;
////
////			case e_array:
////				_arrayDimension++;
////				break;
////
////			case e_array_after_field: _fieldBuilder._array(); break;
////			case e_array_after_method: _methodBuilder._array(); break;
////
////			case e_name:
////				_type = new Type(popBuffer(), popOptions(), _arrayDimension);
////				_name = sval;
////
////				_arrayDimension = 0;
////				break;
////
////			case e_field:
////				if (_name != null)	{
////					_fieldBuilder = _classBuilder.buildField(_type, _name);
////					_name = null;
////				}
////				break;
////
////			case e_more_field:
////				_fieldBuilder = _classBuilder.buildField(_type, sval);
////				break;
////
////			case e_method:
////				_methodBuilder = _classBuilder.buildMethod(_type, _name);
////				break;
////
////			case e_parameter:
////				_methodBuilder._parameter(
////					new Type(popBuffer(), popOptions(), _arrayDimension), _name);
////				_name = null;
////				_arrayDimension = 0;
////				break;
////				
////			case e_parameter_name:
////				_name = sval;
////				break;
////
////			case e_throws:
////				_methodBuilder._throws(popBuffer());
////				break;
////
////			case e_constructor:
////				_methodBuilder = _classBuilder.buildMethod(MethodBuilder.CONSTRUCTOR, popBuffer());
////				break;
////
////			case e_static_block:
////				_methodBuilder = 
////					_classBuilder.buildMethod(MethodBuilder.STATIC_BLOCK, null);
////				break;
////
////			case e_expr: 
////				_fieldBuilder._initialize(sval); break;
////
////			case e_block:
////				_methodBuilder._body(sval); break;
//		}

		return -1;
	}
}
