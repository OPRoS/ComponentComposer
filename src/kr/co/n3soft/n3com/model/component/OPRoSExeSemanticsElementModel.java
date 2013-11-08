package kr.co.n3soft.n3com.model.component;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.jdom.Element;

public class OPRoSExeSemanticsElementModel extends UMLDiagramModel{
	public OPRoSExeSemanticsElementModel(){
		super();
		initializeData();
	}
	public void initializeData(){
         
          LOGIC_ICON	= ProjectManager.getInstance().getImage(10111);
          this.setexePeriodProp("100");
          this.setexePriorityProp("5");
	}
	
	public String toString() {
		return "ExeSemantics";
	}
	public void doSave(Element parentEle){
		Element ele;
	
		ele = new Element("type");
		ele.setText(this.getexeTypeString());
		parentEle.addContent(ele);
		ele = new Element("period");
		ele.setText(this.getexePeriodProp());
		parentEle.addContent(ele);
		ele = new Element("priority");
		ele.setText(this.getexePriorityProp());
		parentEle.addContent(ele);
		ele = new Element("instance_creation");
		ele.setText(this.getexeInstanceTypeString());
		parentEle.addContent(ele);
		ele = new Element("lifecycle_type");
		ele.setText(this.getexeLifecycleTypeString());
		parentEle.addContent(ele);
	}
	
//	public void doSave(Element parentEle){
//		Element ele;
//		ele = new Element("library_type");
//		ele.setText(this.getLibType());
//		parentEle.addContent(ele);
//		ele = new Element("library_name");
//		ele.setText(this.getlibNameProp());
//		parentEle.addContent(ele);
//		ele = new Element("impl_language");
//		ele.setText(this.getimplementLangProp());
//		parentEle.addContent(ele);
//		ele = new Element("compiler");
//		ele.setText(this.getcompilerProp());
//		parentEle.addContent(ele);
//		ele = new Element("app_domain");
//		ele.setText(this.getexeEnvDomainProp());
//		parentEle.addContent(ele);
//		ele = new Element("app_robot");
//		ele.setText(this.getexeEnvRobotTypeProp());
//		parentEle.addContent(ele);
//	}
	
	

}
