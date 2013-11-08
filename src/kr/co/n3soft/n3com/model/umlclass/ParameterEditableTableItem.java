package kr.co.n3soft.n3com.model.umlclass;

import kr.co.n3soft.n3com.project.dialog.AttributeDialog;

public class ParameterEditableTableItem {
    public String name;
    public String defalut;
    public Integer type;
    public String stype;
    public String id = null;
    public String desc="";

    public ParameterEditableTableItem(String n, Integer v, String i) {
        name = n;
        type = v;
        defalut = i;
    }
    
    public ParameterEditableTableItem(String n, String v, String i) {
        name = n;
        stype = v;
        defalut = i;
    }
    
    public ParameterEditableTableItem() {
        
    }

    public String toString() {
        //	String scopep = AttributeDialog.SCOPE_SET[scope];
        String value = name + ":" + this.stype;
        return value;
    }

    public ParameterEditableTableItem clone() {
        ParameterEditableTableItem clone = new ParameterEditableTableItem(this.name, this.stype, this.defalut);
        //2008042401PKY S
        clone.desc=this.defalut;
        clone.defalut=this.defalut;
      //2008042401PKY E
        return clone;
    }

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String writeModel(){
		StringBuffer sb = new StringBuffer();
		sb.append(name+":"+this.stype);
		return sb.toString();
		
	}
}
