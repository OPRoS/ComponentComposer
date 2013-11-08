package javac.parser;

import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;

public class ReverseOperation {
	public String scope;
	public String type;
	public String name;
	
	public java.util.ArrayList params = new java.util.ArrayList();
	public OperationEditableTableItem getModel(){
		OperationEditableTableItem opi = new OperationEditableTableItem(this.params);
		opi.stype = type;
		opi.name = name;
		
		if(scope!=null && !scope.trim().equals("")){
			if(scope.trim().equals("public")){
				opi.scope = 0;
			}
			else if(scope.trim().equals("private")){
				opi.scope = 2;
			}
			else if(scope.trim().equals("protected")){
				opi.scope = 1;
			}
			
		}
		else{
			opi.scope = 1;	
		}
		
		
		return opi;
		
	}
	
//	public  void addParam(ReverseParameter param){
//		
//		this.params.add(param.getModel());
//	}
	
}
