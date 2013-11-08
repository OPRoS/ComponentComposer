package kr.co.n3soft.n3com.model.component;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.parser.IN3ModelTokenType;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.jdom.Attribute;
import org.jdom.Element;

public class OPRoSPropertyElementModel extends UMLDiagramModel{
	public OPRoSPropertyElementModel(){
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
		sb.append("property OPRoSPropertyElementModel="+this.getDefaultValue()+","+this.getPropertyName()+","+this.getDataType()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		return sb.toString();
	}
	public void doSave(Element ele){
		Attribute attr = new Attribute("name",this.getPropertyName());
		ele.setAttribute(attr);
		attr = new Attribute("type",this.getDataType());
		ele.setAttribute(attr);
		ele.setText(getDefaultValue());
	}
	
	public OPRoSPropertyElementModel clone(){
		OPRoSPropertyElementModel opem = new OPRoSPropertyElementModel();
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
