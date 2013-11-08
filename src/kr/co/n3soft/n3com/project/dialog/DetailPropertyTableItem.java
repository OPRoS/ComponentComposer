package kr.co.n3soft.n3com.project.dialog;

import kr.co.n3soft.n3com.model.component.OPRoSMonitorVariableElementModel;
import kr.co.n3soft.n3com.model.component.OPRoSPropertyElementModel;
import kr.co.n3soft.n3com.model.umlclass.ArrayItem;
import kr.co.n3soft.n3com.parser.IN3ModelTokenType;

public class DetailPropertyTableItem extends ArrayItem {
	public String key = "";
	public String desc = "";
	public int index = -1;
	public int tapIndex = -1;
	public String tapName = "";
	public String sTagType = "";
	public String sName = "";
	public int sType;
	public String sSpecification = "";
	public String cmpName = "";
	public String valueformat = "";
	public boolean isCheck  = true;
	
	 public Object clone() {
		 DetailPropertyTableItem clone = new DetailPropertyTableItem();
		 clone.key = this.key;
		 clone.desc = this.desc;
		 clone.index = this.index;
		 clone.tapIndex = tapIndex;
		 clone.tapName = tapName;
		 clone.sName = sName;
		 clone.sType = sType;
		 clone.sSpecification = sSpecification;
		 clone.sTagType = sTagType;
		 return clone;
	 }
	 
	 public OPRoSPropertyElementModel getOPRoSPropertyElementModel(){
		 OPRoSPropertyElementModel opem = new OPRoSPropertyElementModel();
		 opem.setPropertyName(tapName);
		 opem.setDataType(sTagType);
		 opem.setDefaultValue(desc);
		 
		 
		 return opem;
	 }
	 
	 public OPRoSMonitorVariableElementModel getOPRoSMonitorVariableElementModel(){
		 OPRoSMonitorVariableElementModel opem = new OPRoSMonitorVariableElementModel();
		 opem.setPropertyName(tapName);
		 opem.setDataType(sTagType);
		 opem.setDefaultValue(desc);
		 
		 
		 return opem;
	 }
	 
	 public String  getVar(String appName){
		 return "var="+appName+"."+this.cmpName+"."+this.tapName;
	 }
	 
	 public String writeModel(){
		 StringBuffer sb = new StringBuffer();
	    	sb.append("model DetailProperty (\n");
	    	sb.append("model_propertys <\n");
	    	sb.append("property key="+key+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
	    	sb.append("property desc="+desc+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
	    	sb.append("property index="+index+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
	    	sb.append("property tapIndex="+tapIndex+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
	    	sb.append("property tapName="+tapName+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
	    	sb.append("property sName="+sName+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
	    	sb.append("property sType="+sType+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
	    	sb.append("property sTagType="+sTagType+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
	    	sb.append("property sSpecification="+sSpecification+IN3ModelTokenType.PROPERTY_N3EOF+"\n");
	    	sb.append(">\n");
	    	sb.append(")\n");
	    	return sb.toString();
		
	 }
	 
	 public void setValue(String type,String name,String value,String valueformat){
		 
		 if(this.tapName.equals(name)){
			 this.desc = value;
			 this.valueformat = valueformat;
		 }
		 
	 }

}
