package javac.parser;

import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;

public class ReverseAttribute {
	public String scope;
	public String type;
	public String name;
	public String initValue;
//	protected
	
	public AttributeEditableTableItem getModel(){
		AttributeEditableTableItem attr = new AttributeEditableTableItem();
		attr.name = name;
		attr.stype = type;
		if(scope!=null && !scope.trim().equals("")){
			if(scope.trim().equals("public")){
				attr.scope = 0;
			}
			else if(scope.trim().equals("private")){
				attr.scope = 2;
			}
			else if(scope.trim().equals("protected")){
				attr.scope = 1;
			}
			
		}
	
	else{
		attr.scope = 1;	
	}
		return attr;
		
	}

}
