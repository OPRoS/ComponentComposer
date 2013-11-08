package kr.co.n3soft.n3com.model.component;

public class MethodModel {
	public String returnType = "";
	public String name = "";
	public java.util.ArrayList params = new java.util.ArrayList();
	public java.util.ArrayList getParams() {
		return params;
	}
	public void setParams(java.util.ArrayList params) {
		this.params = params;
	}
	public String writeSource(){
		
		StringBuffer sb = new StringBuffer();		
		sb.append("\tvirtual "+returnType+" "+name+"(");	// WJH 100721 \t Ãß°¡	
		for(int i=0;i<params.size();i++){
			ParamModel pm =(ParamModel)params.get(i);
			if(i!=0){
				sb.append(",");
			}
			sb.append(pm.writeSource());
			
		}
		sb.append(");");
		return sb.toString();
	}

}
