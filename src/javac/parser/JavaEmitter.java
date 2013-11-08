package javac.parser;
import java.util.Stack;
import java.util.Vector;

import javac.builder.FieldBuilder;
import javac.builder.InterfaceBuilder;
import javac.builder.JavaBuilder;
import javac.builder.MethodBuilder;
import javac.builder.Type;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class JavaEmitter implements TokenType, ParsingTable
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
	String pkg = "";
	String imports = "";
	String value = "";
	

	java.util.ArrayList operations = new java.util.ArrayList();
	java.util.ArrayList attributes= new java.util.ArrayList();
	FinalClassModel fcm = null;
	InterfaceModel im = null;
	AttributeEditableTableItem newAttr  = null;
	OperationEditableTableItem newop = null;
	ReverseFromJavaToModel rfm = null; 
	ReverseAttribute attr = null;
	ReverseOperation oper = null;
	ReverseParameter param = null;
	
	public String name = null;
	public String type = null;
	public String scope = null;
	
//	ProjectManager.getInstance();

	public JavaEmitter(JavaBuilder builder)	{
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
		switch (command)	{
			case e_nothing:
				break;
				
			case e_class_begin: 
			rfm.isClassType = true;
			 
			break; 
			case e_interface_begin:
			rfm.isClassType = false;
			break;
			case e_class_id:
				rfm.name = sval;
				break;
			case e_class_end:
				java.util.ArrayList models = (java.util.ArrayList)ProjectManager.getInstance().getReverseModelMap().get(rfm.pkg);
				if(models==null){
					models = new java.util.ArrayList();
					models.add(rfm);
					ProjectManager.getInstance().getReverseModelMap().put(rfm.pkg, models);
				}
				else{
					models.add(rfm);
				}

				break;
				
			case e_push:
				_strBuffer.append(sval); 
				break;
				
			case e_package: 
//				_javaBuilder._package(popBuffer()); break;
				rfm = new ReverseFromJavaToModel();
				pkg = popBuffer();
				rfm.setPkg(pkg);
				break;
//				ProjectManager.getInstance().getReverseModelMap().put(pkg, pkg);
//				java.util.ArrayList list = (java.util.ArrayList)ProjectManager.getInstance().getReverseModelMap().get(pkg);
			case e_import: 
//				_javaBuilder._import(popBuffer());
				value = popBuffer();
				rfm.imports.add(value);
				
				break;
			case e_extends: 
				value = popBuffer();
				rfm.extendsClass.add(value);
				
				break;
			case e_implements: 
				value = popBuffer();
				rfm.interfaces.add(value);
				break;

			case e_option:
				_optionBuffer.add(sval);
				break;

			case e_array:
				_arrayDimension++;
				break;

			case e_array_after_field: 
				_fieldBuilder._array(); 
				break;
			case e_array_after_method: _methodBuilder._array(); break;

			case e_name:
				System.out.println("--------------------");

				this.name = sval;
				break;

			case e_field:
				if (name != null)	{
					
					attr = new ReverseAttribute();
					attr.name = this.name;
					String[] scope = popOptions();
//					this.scope = scope[0];
					if(scope!=null && scope.length>0){
						attr.scope = scope[0];
					}
					else{
						attr.scope= "";
					}
					attr.type = popBuffer();
					rfm.attributes.add(attr.getModel());
					this.name = null;
					this.scope = null;
					this.type = null;
					}
				
				break;

			case e_more_field:
				System.out.println("dfdfdfd");
				break;

			case e_method:
				if (name != null)	{
					
					
					oper = new ReverseOperation();
					oper.name = this.name;
					String[] scope = popOptions();
					if(scope!=null && scope.length>0)
						oper.scope = scope[0];
					else{
						oper.scope = "";
					}
					oper.type =popBuffer();
					rfm.operations.add(oper.getModel());
					this.name = null;
					this.scope = null;
					this.type = null;
				}
				break;

			case e_parameter:
				_arrayDimension = 0;
				if(oper!=null){
					param = new ReverseParameter();
					param.name = name;
					param.type = popBuffer();
					this.oper.params.add(param.getModel());
					this.name = null;
	//				this.scope = null;
					this.type = null;
				}
				break;
				
			case e_parameter_name:
				name = sval;
				break;

			case e_throws:
//				_methodBuilder._throws(popBuffer());
				break;

			case e_constructor:
//				_methodBuilder = _classBuilder.buildMethod(MethodBuilder.CONSTRUCTOR, popBuffer());
				break;

			case e_static_block:
//				_methodBuilder = 
//					_classBuilder.buildMethod(MethodBuilder.STATIC_BLOCK, null);
				break;

			case e_expr: 
//				attributes.add(newAttr);
//				_fieldBuilder._initialize(sval); 
				break;

			case e_block:
//				_methodBuilder._body(sval);
				break;
		}

		return -1;
	}

//	public int processCommand(int command, String sval) throws Exception {
//		switch (command)	{
//			case e_nothing:
//				break;
//				
//			case e_class_begin: _isClass = true; break; 
//			case e_interface_begin: _isClass = false; break;
//
//			case e_class_id:
//				if (_classBuilder != null)
//					_classBuilderStack.push(_classBuilder);
//
//				_classBuilder = _isClass ? 
//					_javaBuilder.buildClass(sval) : 
//					_javaBuilder.buildInterface(sval);
//					
//				_classBuilder._options(popOptions());
//				_optionBuffer.removeAllElements();
//				break;
//			
//			case e_class_end:
//				if (_classBuilderStack.isEmpty())
//					_classBuilder = null;
//				else	{
//					_classBuilder = (InterfaceBuilder)_classBuilderStack.pop();
//					return STATE_CLASS_BODY;
//				}
//				break;
//				
//			case e_push:
//				_strBuffer.append(sval); 
//				break;
//				
//			case e_package: 
//				_javaBuilder._package(popBuffer()); break;
//			case e_import: 
//				_javaBuilder._import(popBuffer()); break;
//			case e_extends: 
//				_classBuilder._extends(popBuffer()); break;
//			case e_implements: 
//				if (_classBuilder instanceof ClassBuilder)
//					((ClassBuilder)_classBuilder)._implements(popBuffer());
//				break;
//
//			case e_option:
//				_optionBuffer.add(sval);
//				break;
//
//			case e_array:
//				_arrayDimension++;
//				break;
//
//			case e_array_after_field: _fieldBuilder._array(); break;
//			case e_array_after_method: _methodBuilder._array(); break;
//
//			case e_name:
//				_type = new Type(popBuffer(), popOptions(), _arrayDimension);
//				_name = sval;
//
//				_arrayDimension = 0;
//				break;
//
//			case e_field:
//				if (_name != null)	{
//					_fieldBuilder = _classBuilder.buildField(_type, _name);
//					_name = null;
//				}
//				break;
//
//			case e_more_field:
//				_fieldBuilder = _classBuilder.buildField(_type, sval);
//				break;
//
//			case e_method:
//				_methodBuilder = _classBuilder.buildMethod(_type, _name);
//				break;
//
//			case e_parameter:
//				_methodBuilder._parameter(
//					new Type(popBuffer(), popOptions(), _arrayDimension), _name);
//				_name = null;
//				_arrayDimension = 0;
//				break;
//				
//			case e_parameter_name:
//				_name = sval;
//				break;
//
//			case e_throws:
//				_methodBuilder._throws(popBuffer());
//				break;
//
//			case e_constructor:
//				_methodBuilder = _classBuilder.buildMethod(MethodBuilder.CONSTRUCTOR, popBuffer());
//				break;
//
//			case e_static_block:
//				_methodBuilder = 
//					_classBuilder.buildMethod(MethodBuilder.STATIC_BLOCK, null);
//				break;
//
//			case e_expr: 
//				_fieldBuilder._initialize(sval); break;
//
//			case e_block:
//				_methodBuilder._body(sval); break;
//		}
//
//		return -1;
//	}
}
