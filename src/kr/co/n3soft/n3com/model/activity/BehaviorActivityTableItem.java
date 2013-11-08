package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.ArrayItem;

public class BehaviorActivityTableItem extends ArrayItem {
    public String name;
    public String scope;
    public String parentElement;
    public String type;
    public String id = null;
    public UMLModel refModel = null;

    public BehaviorActivityTableItem(String s, String n, String v, String i) {
        scope = s;
        name = n;
        type = v;
        parentElement = i;
    }

    public void setRef(UMLModel p) {
        this.refModel = p;
    }

    public UMLModel getRef() {
        return this.refModel;
    }

    //	public void setRefId(UMLModel p){
    //		this.refModel = p;
    //	}
    public String getRefId() {
        return this.refModel.getID();
    }

    //	public String toString(){
    ////		String scopep = AttributeDialog.SCOPE_SET[scope];
    //
    //		String value= AttributeDialog.SCOPEA_SET[scope]+" "+name+":"+AttributeDialog.TYPE_SET[type];
    //		return value;
    //	}
    //	
    public Object clone() {
        //		AttributeEditableTableItem clone = new AttributeEditableTableItem(this.scope,this.name,this.type,this.initValue);
        return null;
    }
    public String writeModel(){
    	return "";
    }
}
