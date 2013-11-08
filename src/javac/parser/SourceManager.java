package javac.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.ParameterEditableTableItem;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class SourceManager {
	private static SourceManager instance;
	private static String PACKAGE_KEY_WORLD = "package";
	private static String PUBLIC_KEY_WORLD = "public";
	private static String PRIVATE_KEY_WORLD = "private";
	private static String PROTECTED_KEY_WORLD = "protected";
	private static String CLASS_KEY_WORLD = "class";
	private static String INTERFACE_KEY_WORLD = "interface";
	private static String BLOCK_START_TOKEN = "{";
	private static String BLOCK_END_TOKEN = "}";
	private static String FUNCTION_START_TOKEN = "(";
	private static String FUNCTION_END_TOKEN = ")";
	private static String EXTENDS__KEY_WORLD = "extends";
	private static String IMPLEMENTS_KEY_WORLD = "implements";
	private static String IMPORT_KEY_WORLD = "import";
	private static String END_MARK_TOKEN = ";";
	private static String SPACE_TOKEN = " ";
	private static String ENTER_TOKEN = "\n";
	/*
	 * 1:java
	 * 2:C++ header
	 */
	int type = 1;


	private String path = "";
//	private static String IMPLEMENTS_KEY_WORLD = "implements";

	public static SourceManager getInstance() {
		if (instance == null) {
			instance = new SourceManager();
			return instance;
		}
		else {
			return instance;
		}
	}


	public void writeJavas(UMLTreeParentModel p){

	}

	public String writePackage(UMLModel um){
		String str = this.PACKAGE_KEY_WORLD+this.SPACE_TOKEN+um.getPackage()+this.END_MARK_TOKEN+ENTER_TOKEN;
		return str;
	}
	public String getScope(String p){
		String scope = "";
		if("0".equals(p)){
			scope = this.PUBLIC_KEY_WORLD;
		}
		else if("2".equals(p)){
			scope = this.PRIVATE_KEY_WORLD;
		}
		else{
			scope = this.PROTECTED_KEY_WORLD;
		}
		return  scope;
	}

	public String writePublicHeader(UMLModel um){
		
		return "";


	}

	public String writeAttributes(java.util.ArrayList attrs){
		StringBuffer sb = new StringBuffer();
		if(this.type==1){
			for(int i=0;i<attrs.size();i++){
				AttributeEditableTableItem p =(AttributeEditableTableItem)attrs.get(i);
				sb.append(this.writeAttribute(p));

			}
		}
		else if(this.type==2){
//			sb.append(this.PUBLIC_KEY_WORLD+":"+this.ENTER_TOKEN);
			StringBuffer sb2 = new StringBuffer();
			for(int i=0;i<attrs.size();i++){
				AttributeEditableTableItem p =(AttributeEditableTableItem)attrs.get(i);
				if(p.scope==0){
					sb2.append(this.writeAttribute(p));
				}

			}
			if(sb2.length()>0){
				sb.append(this.PUBLIC_KEY_WORLD+":"+this.ENTER_TOKEN);
				sb.append(sb2.toString());
			}
			sb2.delete(0, sb2.length());
			
			for(int i=0;i<attrs.size();i++){
				AttributeEditableTableItem p =(AttributeEditableTableItem)attrs.get(i);
				if(p.scope==2){
					sb2.append(this.writeAttribute(p));
				}

			}
			if(sb2.length()>0){
				sb.append(this.PRIVATE_KEY_WORLD+":"+this.ENTER_TOKEN);
				sb.append(sb2.toString());
			}
			sb2.delete(0, sb2.length());
//			sb.append(this.PROTECTED_KEY_WORLD+":"+this.ENTER_TOKEN);
			for(int i=0;i<attrs.size();i++){
				AttributeEditableTableItem p =(AttributeEditableTableItem)attrs.get(i);
				if(p.scope==1){
					sb2.append(this.writeAttribute(p));
				}

			}
			if(sb2.length()>0){
				sb.append(this.PROTECTED_KEY_WORLD+":"+this.ENTER_TOKEN);
				sb.append(sb2.toString());
			}
		}
		return sb.toString();
	}

	public String writeAttribute(AttributeEditableTableItem p){
		String str = "";
		if(this.type==1){
		str = getScope(p.scope.toString())+this.SPACE_TOKEN
		+p.stype+this.SPACE_TOKEN+p.name+this.END_MARK_TOKEN+ENTER_TOKEN;
		}
		else if(this.type==2){
			str = p.stype+this.SPACE_TOKEN+p.name+this.END_MARK_TOKEN+ENTER_TOKEN;
		}
		return str;
	}
	
	public String  writeOperationsCpp(java.util.ArrayList opers,UMLModel um){
		StringBuffer sb = new StringBuffer();
		
		for(int i=0;i<opers.size();i++){
			OperationEditableTableItem p =(OperationEditableTableItem)opers.get(i);
		
				sb.append(this.writeOperationCpp(p,um));
			

		}
		return sb.toString();
	}

	public String writeOperations(java.util.ArrayList opers){
		StringBuffer sb = new StringBuffer();
		if(this.type==1){
		for(int i=0;i<opers.size();i++){
			OperationEditableTableItem p =(OperationEditableTableItem)opers.get(i);
			sb.append(this.writeOperation(p));

		}
		}
		else if(this.type==2){
			StringBuffer sb2 = new StringBuffer();
			for(int i=0;i<opers.size();i++){
				OperationEditableTableItem p =(OperationEditableTableItem)opers.get(i);
				if(p.scope==0){
					sb2.append(this.writeOperation(p));
				}

			}
			if(sb2.length()>0){
				sb.append(this.PUBLIC_KEY_WORLD+":"+this.ENTER_TOKEN);
				sb.append(sb2.toString());
			}
			sb2.delete(0, sb2.length());
			
			for(int i=0;i<opers.size();i++){
				OperationEditableTableItem p =(OperationEditableTableItem)opers.get(i);
				if(p.scope==2){
					sb2.append(this.writeOperation(p));
				}

			}
			if(sb2.length()>0){
				sb.append(this.PRIVATE_KEY_WORLD+":"+this.ENTER_TOKEN);
				sb.append(sb2.toString());
			}
			sb2.delete(0, sb2.length());
//			sb.append(this.PROTECTED_KEY_WORLD+":"+this.ENTER_TOKEN);
			for(int i=0;i<opers.size();i++){
				OperationEditableTableItem p =(OperationEditableTableItem)opers.get(i);
				if(p.scope==1){
					sb2.append(this.writeOperation(p));
				}

			}
			if(sb2.length()>0){
				sb.append(this.PROTECTED_KEY_WORLD+":"+this.ENTER_TOKEN);
				sb.append(sb2.toString());
			}
		}
		return sb.toString();
	}

	public String writeInterfaceOperations(java.util.ArrayList opers){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<opers.size();i++){
			OperationEditableTableItem p =(OperationEditableTableItem)opers.get(i);
			sb.append(this.writeInterfaceOperation(p));

		}
		return sb.toString();
	}

	public String writeInterfaceOperationHead(OperationEditableTableItem p){
		StringBuffer sb = new StringBuffer();
		String str = getScope(p.scope.toString())+this.SPACE_TOKEN
		+p.stype+this.SPACE_TOKEN+p.name;
		sb.append(str);
		String param = this.FUNCTION_START_TOKEN+this.writeParameters(p.getParams())+this.FUNCTION_END_TOKEN;
		sb.append(param);
		return sb.toString();
	}
	public String writeInterfaceOperation(OperationEditableTableItem p){
		StringBuffer sb = new StringBuffer();
		sb.append(this.writeInterfaceOperationHead(p));
		sb.append(this.END_MARK_TOKEN);
		sb.append(this.ENTER_TOKEN);
		return sb.toString();

	}
	
	public String writeOperationHeadCpp(OperationEditableTableItem p,UMLModel um){
		StringBuffer sb = new StringBuffer();
		String  str = "";
		
			 str =  p.stype+this.SPACE_TOKEN+um.getName()+"::"
			        +p.name.trim();
		
		
		
		sb.append(str);
		String param = this.FUNCTION_START_TOKEN+this.writeParameters(p.getParams())+this.FUNCTION_END_TOKEN;
		sb.append(param);
		return sb.toString();
	}

	public String writeOperationHead(OperationEditableTableItem p){
		StringBuffer sb = new StringBuffer();
		String  str = "";
		if(this.type==1){
			 str =  getScope(p.scope.toString())+this.SPACE_TOKEN
			+p.stype+this.SPACE_TOKEN+p.name;
		}
		else if(this.type==2){
			 str = p.stype+this.SPACE_TOKEN+p.name;
		}
		
		sb.append(str);
		String param = this.FUNCTION_START_TOKEN+this.writeParameters(p.getParams())+this.FUNCTION_END_TOKEN;
		sb.append(param);
		return sb.toString();
	}
	public String writeOperation(OperationEditableTableItem p){
		StringBuffer sb = new StringBuffer();
	
		sb.append(this.writeOperationHead(p));
		if(this.type==1){
			sb.append(this.BLOCK_START_TOKEN+this.ENTER_TOKEN);
			sb.append(this.BLOCK_END_TOKEN+this.ENTER_TOKEN);
		}
		else if(this.type==2){
			sb.append(";"+this.ENTER_TOKEN);
		}

		return sb.toString();

	}
	
	public String writeOperationCpp(OperationEditableTableItem p,UMLModel um){
		StringBuffer sb = new StringBuffer();
		sb.append(this.writeOperationHeadCpp(p,um));
	
			sb.append(this.BLOCK_START_TOKEN+this.ENTER_TOKEN);
			sb.append(this.BLOCK_END_TOKEN+this.ENTER_TOKEN);
		
		

		return sb.toString();

	}



	public String writeParameters(java.util.ArrayList params){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<params.size();i++){
			ParameterEditableTableItem p =(ParameterEditableTableItem)params.get(i);
			sb.append(this.writeParameter(p));
			if(i<params.size()-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	public String writeParameter(ParameterEditableTableItem p){
		String str = p.stype+this.SPACE_TOKEN
		+p.name;
		return str;

	}
   public void writeH(UMLModel um){
	   SourceManager.getInstance().setType(2);
	   boolean isWrite = false;
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		if(um instanceof FinalClassModel){
//			sb.append(this.writePackage(um));
			sb.append(this.writePublicHeader(um));
			sb.append(this.writeAttributes(((FinalClassModel)um).getClassModel().getAttributes()));
			sb.append(this.writeOperations(((FinalClassModel)um).getClassModel().getOperations()));
			sb.append(this.BLOCK_END_TOKEN+";");
			
			sb2.append(this.writeOperationsCpp(((FinalClassModel)um).getClassModel().getOperations(),um));
			isWrite = true;

		}
		

		System.out.println(sb.toString());
		try{
			if(isWrite){
//				this.path = "C:\\Documents and Settings\\admin\\workspace02\\uuu\\src";
				this.path = ProjectManager.getInstance().getSourceFolder();
				if(this.path!=null){
					String pkgPath = this.path;
					File pkg = new File(pkgPath);
					if(!pkg.exists() && !pkg.isDirectory()){
						pkg.mkdirs();
					}
					String filePath = pkgPath +File.separator +um.getName()+".h";
					FileWriter fw = new FileWriter(new File(filePath));
					BufferedWriter bw=new BufferedWriter(fw);

					bw.write(sb.toString());
					bw.close();
				}
				if(sb2.length()>0){
					StringBuffer cpp = new StringBuffer();
					cpp.append("#include"+"\""+um.getName()+".h"+"\"" +this.ENTER_TOKEN);
					cpp.append(sb2.toString());
					if(this.path!=null){
						String pkgPath = this.path;
						File pkg = new File(pkgPath);
						if(!pkg.exists() && !pkg.isDirectory()){
							pkg.mkdirs();
						}
						String filePath = pkgPath +File.separator +um.getName()+".cpp";
						FileWriter fw = new FileWriter(new File(filePath));
						BufferedWriter bw=new BufferedWriter(fw);

						bw.write(cpp.toString());
						bw.close();
					}
				}
			}
//			String filePath = this.path + java.io.File.separator+ 
		}
		catch(Exception e){
			e.printStackTrace();

		}
   }

	public void writeJava(UMLModel um){
		boolean isWriteJava = false;
		StringBuffer sb = new StringBuffer();
		if(um instanceof FinalClassModel){
			sb.append(this.writePackage(um));
			sb.append(this.writePublicHeader(um));
			sb.append(this.writeAttributes(((FinalClassModel)um).getClassModel().getAttributes()));
			sb.append(this.writeOperations(((FinalClassModel)um).getClassModel().getOperations()));
			sb.append(this.BLOCK_END_TOKEN);
			isWriteJava = true;

		}
		

		System.out.println(sb.toString());
		try{
			if(isWriteJava){
//				this.path = "C:\\Documents and Settings\\admin\\workspace02\\uuu\\src";
				this.path = ProjectManager.getInstance().getSourceFolder();
				if(this.path!=null){
					String pkgPath = this.path + java.io.File.separator+ this.getTransDot(um.getPackage());
					File pkg = new File(pkgPath);
					if(!pkg.exists() && !pkg.isDirectory()){
						pkg.mkdirs();
					}
					String filePath = pkgPath +File.separator +um.getName()+".java";
					FileWriter fw = new FileWriter(new File(filePath));
					BufferedWriter bw=new BufferedWriter(fw);

					bw.write(sb.toString());
					bw.close();
				}
			}
//			String filePath = this.path + java.io.File.separator+ 
		}
		catch(Exception e){
			e.printStackTrace();

		}

	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

	public String getTransDot(String javaPkg){
		StringBuffer sb = new StringBuffer();
		java.util.StringTokenizer st = new java.util.StringTokenizer(javaPkg,".");
		if(!st.hasMoreTokens()){
			sb.append(javaPkg);
		}
		while(st.hasMoreTokens()){
			String str = (String)st.nextToken();
			sb.append(str);
			if(st.hasMoreTokens()){
				sb.append(File.separator);
			}
		}
		return sb.toString();

	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}
}
