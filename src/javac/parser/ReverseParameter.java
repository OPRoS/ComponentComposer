package javac.parser;

import kr.co.n3soft.n3com.model.umlclass.ParameterEditableTableItem;

public class ReverseParameter {
	public String type;
	public String name;
	
	public ParameterEditableTableItem getModel(){
		ParameterEditableTableItem param = new ParameterEditableTableItem();
		param.stype = type;
		param.name = name;
		return param;
	}

}
