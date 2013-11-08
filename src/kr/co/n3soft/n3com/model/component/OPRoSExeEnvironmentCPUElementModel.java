package kr.co.n3soft.n3com.model.component;

import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.parser.IN3ModelTokenType;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class OPRoSExeEnvironmentCPUElementModel extends UMLDiagramModel{
	public OPRoSExeEnvironmentCPUElementModel(){
		super();
		initializeData();
	}
	public void initializeData(){
         
          LOGIC_ICON	= ProjectManager.getInstance().getImage(10119);
	}
	
	public String toString() {
		return this.getCpu();
	}
	
	public String writeModel(){
		StringBuffer sb = new StringBuffer();
		sb.append("property OPRoSExeEnvironmentCPUElementModel="+this.getCpu()+IN3ModelTokenType.PROPERTY_N3EOF+"\n") ;
		return sb.toString();
	}

}