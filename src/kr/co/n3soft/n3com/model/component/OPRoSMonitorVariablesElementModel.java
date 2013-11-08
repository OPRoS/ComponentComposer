package kr.co.n3soft.n3com.model.component;

import java.util.Iterator;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.jdom.Element;

public class OPRoSMonitorVariablesElementModel extends UMLDiagramModel{
	public OPRoSMonitorVariablesElementModel(){
		super();
		initializeData();
	}
	public void initializeData(){
         
          LOGIC_ICON	= ProjectManager.getInstance().getImage(10113);
	}
	
	public String toString() {
		return "Monitoring Variable";
	}
	
	public String writeModel(){
		StringBuffer sb = new StringBuffer();
//		int i = this.getChildren().size();
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
			if(obj instanceof OPRoSMonitorVariableElementModel){
				OPRoSMonitorVariableElementModel ops = (OPRoSMonitorVariableElementModel)obj;	
				sb.append(ops.writeModel());
			}
		}
//		sb.append("property OPRoSServiceTypeElementModel="+this.getServiceTypeFileName()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		return sb.toString();
	}
	
	public void doSave(Element parentEle){
		Iterator  it = this.getChildren().iterator();
		while(it.hasNext()){
			OPRoSMonitorVariableElementModel model = (OPRoSMonitorVariableElementModel)it.next();
			Element ele = new Element("property");
			model.doSave(ele);
			parentEle.addContent(ele);
		}
	}
	
	public OPRoSMonitorVariablesElementModel clone(){
		OPRoSMonitorVariablesElementModel ops = new OPRoSMonitorVariablesElementModel();
		for(int i=0;i<this.getChildren().size();i++){
			Object obj = this.getChildren().get(i);
			if(obj instanceof OPRoSMonitorVariableElementModel){
				OPRoSMonitorVariableElementModel op = (OPRoSMonitorVariableElementModel)obj;	
				ops.addChild(op.clone());
//				sb.append(ops.writeModel());
			}
		}
		return ops;
		
	}
	
	public void removeAll(){
		while(this.getChildren().size()>0){
			 Object obj = this.getChildren().get(0);
			if(obj instanceof OPRoSMonitorVariableElementModel){
			 this.removeChild((OPRoSMonitorVariableElementModel)obj);
			}
		}
	}

}
