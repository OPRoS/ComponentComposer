package kr.co.n3soft.n3com.model.component;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.parser.IN3ModelTokenType;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.jdom.Attribute;
import org.jdom.Element;

public class OPRoSMonitorVariableElementModel extends UMLDiagramModel{
	public OPRoSMonitorVariableElementModel(){
		super();
		initializeData();
	}
	public void initializeData(){
         
          LOGIC_ICON	= ProjectManager.getInstance().getImage(10114);
	}
	
	public String toString() {
		return this.getPropertyName();
	}
	
	public String writeModel(){
		StringBuffer sb = new StringBuffer();
		sb.append("property OPRoSMonitorVariableElementModel="+this.getDefaultValue()+","+this.getPropertyName()+","+this.getDataType()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		return sb.toString();
	}
	public void doSave(Element ele){
		Attribute attr = new Attribute("name",this.getPropertyName());
		ele.setAttribute(attr);
		attr = new Attribute("type",this.getDataType());
		ele.setAttribute(attr);
		ele.setText(getDefaultValue());
	}
	
	public OPRoSMonitorVariableElementModel clone(){
		OPRoSMonitorVariableElementModel opem = new OPRoSMonitorVariableElementModel();
		opem.setPropertyName(this.getPropertyName());
		opem.setDataType(this.getDataType());
		opem.setDefaultValue(getDefaultValue());
		return opem;
		
		
	}
	
//	public DetailPropertyTableItem getDetailPropertyTableItem(){
//		DetailPropertyTableItem dp = new DetailPropertyTableItem();
//		
//		return 
//	}


}

