package javac.parser;

import java.util.Stack;
import java.util.StringTokenizer;
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

public class CplusEmitter implements TokenTypeCplus, CplusParsingTable
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
	boolean isPublicMember = false;
	boolean isClassBegin = false;
	boolean isOperation = false;
	boolean isClassEndBegin = false;
	
	boolean isEqual = false;
	boolean isBungi = false;
	boolean isPoint = false;
	boolean isParamBegin = false;
	
	
	public String name = null;
	public String type = null;
	public String scope = null;
	StringBuffer sb = new StringBuffer();
//	java.util.ArrayList<E>
	/*
	 * 2:private
	 * 1:protected
	 * 0:public
	 */
//	public int scopeType = 1;
	
//	ProjectManager.getInstance();

	public CplusEmitter(JavaBuilder builder)	{
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
				this.isClassEndBegin = false;
				isClassBegin = true;
			
			rfm = new ReverseFromJavaToModel();
			rfm.isClassType = true;
			break; 
			case e_interface_begin:
			rfm.isClassType = false;
			break;
			case e_put_id:
				if(!this.isEqual)
				sb.append(sval+",");
				isEqual = false;
			break;
			case e_scope:
			String[] arr=sb.toString().split(",");
			if(arr!=null){
				if("public".equals(arr[0])){
					scope = "public";
				}
				else if("private".equals(arr[0])){
					scope = "private";
				}
				sb.delete(0, sb.toString().length());
			}
			break;
			case e_end:
				arr=sb.toString().split(",");
				if(arr!=null){
					if(arr.length>1){
						attr = new ReverseAttribute();
						
					    
						if(scope==null){
							attr.scope = "";
						}
						else
						attr.scope = scope;
						if(this.isPoint){
							attr.name = "*"+arr[1];
						}
						else{
							attr.name = arr[1];
						}
						attr.type = arr[0];
						rfm.attributes.add(attr.getModel());
					}
					else if(arr.length==1 && !arr[0].trim().equals("")){
						attr.name = arr[0];
						attr.type = type;
						if(scope==null){
							attr.scope= "";
						}
						else{
							attr.scope = scope;
						}
						rfm.attributes.add(attr.getModel());
					}
					
					this.isPoint = false;
				}
				sb.delete(0, sb.toString().length());
				oper = null;
				attr = null;
				isParamBegin = false;
			break;
			case e_param_begin:
				arr=sb.toString().split(",");
				oper = new ReverseOperation();
				isParamBegin = true;
				if(scope==null){
					oper.scope= "";
				}
				else{
					oper.scope = scope;
				}
				if(arr!=null){
					if(arr.length>1){
						oper.type = arr[0];
						oper.name = arr[1];
					}
					else if(!"".equals(arr[0])){
						oper.type = "";
						oper.name = arr[0];
					}
				}
				sb.delete(0, sb.toString().length());
			break;
			case e_param_add_equal:
				isEqual = true;
				break;
			case e_param_add:
				if(this.isParamBegin){
				param = new ReverseParameter();
				arr=sb.toString().split(",");
				if(arr!=null){
					if(arr.length>1){
						param.type = arr[0];
						param.name = arr[1];
						this.oper.params.add(param.getModel());
					}
					else if(!"".equals(arr[0])){
						if(this.isBungi){
							param.type = "&"+arr[0];
						}
						else{
							param.type = arr[0];
						}
						param.name ="";
						this.oper.params.add(param.getModel());
					}
					
				}
			
				}
				else{
					attr = new ReverseAttribute();
					
						
						arr=sb.toString().split(",");
						if(arr!=null){
							if(arr.length==2){
								attr.name = arr[1];
								attr.type = arr[0];
								this.type = attr.type;
								if(scope==null){
									attr.scope= "";
								}
								else{
									attr.scope = scope;
								}
								rfm.attributes.add(attr.getModel());
							}
							else if(arr.length==1){
								attr.name = arr[0];
								attr.type = type;
								if(scope==null){
									attr.scope= "";
								}
								else{
									attr.scope = scope;
								}
								rfm.attributes.add(attr.getModel());
							}
						}
					
					
				}
				this.isBungi = false;
				sb.delete(0, sb.toString().length());
				break;
			case e_param_add_end:
				param = new ReverseParameter();
				arr=sb.toString().split(",");
				if(arr!=null){
					if(arr.length>1){
						param.type = arr[0];
						param.name = arr[1];
						this.oper.params.add(param.getModel());
					}
					else if(!"".equals(arr[0])){
						if(this.isBungi){
							param.type = "&"+arr[0];
						}
						else{
							param.type = arr[0];
						}
						param.name ="";
						this.oper.params.add(param.getModel());
					}
					
				}
				
				rfm.operations.add(oper.getModel());
				this.isBungi = false;
				sb.delete(0, sb.toString().length());
				isParamBegin = false;

				break;
			case e_bungi_put:
				this.isBungi = true;

				break;
			case e_point_put:
				this.isPoint = true;

				break;
			case e_class_id:
				rfm.name = sval;

				break;
			
			case e_class_end:
//				if(this.isClassEndBegin){
				java.util.ArrayList models = (java.util.ArrayList)ProjectManager.getInstance().getReverseModelMap().get(rfm.pkg);
				if(models==null){
					models = new java.util.ArrayList();
					models.add(rfm);
					ProjectManager.getInstance().getReverseModelMap().put(rfm.pkg, models);
				}
				else{
					models.add(rfm);
				}
				this.isClassBegin = false;
//				isClassEndBegin = false;
//				}

				break;
			case e_public_begin:
				if(isClassBegin)
				scope = "public";
				
				break;
			case e_private_begin:
				if(isClassBegin)
				scope = "private";
				
				break;
			case e_member_data_put:
				if(isClassBegin){
			System.out.println("data:"+sval);
			if(!this.isOperation){
				attr = new ReverseAttribute();
				attr.name = this.name;
				if(scope==null){
					attr.scope = "";
				}
				else
				attr.scope = scope;
				attr.type = type;
				rfm.attributes.add(attr.getModel());
				this.name = null;
				this.scope = null;
				this.type = null;
				
			}//oper
			else{
				oper = new ReverseOperation();
				oper.name = this.name;
				if(scope==null){
					oper.scope= "";
				}
				else{
					oper.scope = scope;
				}
				oper.type =type;
				int begin = sval.lastIndexOf("(");
				int end = sval.lastIndexOf(")");
//				oper.name = sval.substring(0,begin);
				if(end!=0){
				String parameters = sval.substring(0, end);
				String[] params = parameters.split(",");
				if(params!=null && params.length>0){
				for(int i=0;i<params.length;i++){
					
					String p = params[i];
					StringTokenizer st = new StringTokenizer(p);
					String type = "";
					String name = "";
					if(st.hasMoreElements())
					 type = st.nextToken();
					if(st.hasMoreElements())
					 name = st.nextToken();
					param = new ReverseParameter();
					param.name = name;
					param.type = type;
					this.oper.params.add(param.getModel());
					this.name = null;
	//				this.scope = null;
					this.type = null;
				
				}
				}
				}
				rfm.operations.add(oper.getModel());
				this.name = null;
				this.scope = null;
				this.type = null;
			
			}
			this.isOperation = false;
				}
				break;
			case e_type_put:
				if(isClassBegin){
				System.out.println("type:"+sval);
				type = sval;
				}
					break;
			case e_cName_put:
				System.out.println("name:"+sval);
				if(isClassBegin)
				name = sval;
					break;
					
			case e_Parent_put:
				if(isClassBegin){
					scope = "";
					rfm.extendsClass.add(sval);
				}
					
					break;
			case e_operation:
				if(isClassBegin)
				isOperation = true;
					
					break;
		
			case e_ClassEnd_begin:
				if(isClassBegin)
				this.isClassEndBegin = true;
					
					break;				
					
			case e_push:
				_strBuffer.append(sval); 
				break;

			case e_extends: 
				value = popBuffer();
				if(isClassBegin)
				rfm.extendsClass.add(value);
				
				break;
//			case e_implements: 
//				value = popBuffer();
//				rfm.interfaces.add(value);
//				break;

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
