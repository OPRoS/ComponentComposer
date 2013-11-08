package javac.parser;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.FinalClassModel;
import kr.co.n3soft.n3com.model.umlclass.InterfaceModel;

public class ReverseFromJavaToModel {
	public boolean isClassType = false;
	public String pkg;
	public String name;
	public java.util.ArrayList interfaces = new  java.util.ArrayList();
	public java.util.ArrayList extendsClass = new  java.util.ArrayList();
	public java.util.ArrayList attributes = new java.util.ArrayList();
	public java.util.ArrayList operations = new java.util.ArrayList();
	public java.util.ArrayList imports = new java.util.ArrayList();
	UMLModel um = null;
	
	public java.util.ArrayList interfaceUmlModels = new  java.util.ArrayList();
	public java.util.ArrayList extendsClassUmlModels = new  java.util.ArrayList();
	
	public N3EditorDiagramModel n3EditorDiagramModel;
	
	public java.util.ArrayList publicMembers = new  java.util.ArrayList();
	
	
	public void setPkg(String javaPkg){
		
		this.pkg = this.getTransDot(javaPkg);
		
	}
	
	public void setImport(String importName){
		String p = this.getTransDot(importName);
		ImportData im = new ImportData();
		im.initData(p);
		imports.add(im);
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
				sb.append(",");
			}
		}
		return sb.toString();
		
	}
	public UMLModel getUMLModel(){
		return this.um;
	}
	
	public UMLModel getModel(){
		
		if(this.isClassType){
			um = new FinalClassModel();
			um.setName(name);
			((FinalClassModel)um).getClassModel().setAttributes(attributes);
			((FinalClassModel)um).getClassModel().setOperations(operations);
		}
			
		
		return um;
	}
	
	

}
