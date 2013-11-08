package kr.co.n3soft.n3com.model.component;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.parser.IN3ModelTokenType;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class OPRoSExeEnvironmentOSElementModel extends UMLDiagramModel{
	public OPRoSExeEnvironmentOSElementModel(){
		super();
		initializeData();
	}
	public void initializeData(){
         
          LOGIC_ICON	= ProjectManager.getInstance().getImage(10120);
	}
	
	public String toString() {
		return this.getOs()+"-"+this.getOsVersion();
	}
	
	public String writeModel(){
		StringBuffer sb = new StringBuffer();
		sb.append("property OPRoSExeEnvironmentOSElementModel="+this.getOs()+","+this.getOsVersion()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		return sb.toString();
	}

}
