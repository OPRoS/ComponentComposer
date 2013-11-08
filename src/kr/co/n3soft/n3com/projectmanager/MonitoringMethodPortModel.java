package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.MethodOutputPortModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;

public class MonitoringMethodPortModel extends MethodOutputPortModel{
	
	public MonitoringMethodPortModel(PortContainerModel p) {
	       super(p);
	       this.size.height = 20;	// WJH 090810
	       this.size.width = 20;	// WJH 090810
	    }
		
		public MonitoringMethodPortModel() {
			  super();
	    }

	    public MonitoringMethodPortModel(UMLDataModel p) {
	    	  super(p);
	    }
	
	public Object clone() {
		MonitoringMethodPortModel clone = new MonitoringMethodPortModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.setUMLDataModel((UMLDataModel)this.getUMLDataModel().clone());
        clone.setPtDifference(this.getPtDifference().getCopy());
        clone.setSize(this.getSize());
        //		clone.setLocation(this.getLocation());
        clone.sourceModel = this;
        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
            LineModel li = (LineModel)this.getSourceConnections().get(i1);
            LineModel liCopy = (LineModel)li.clone();
            ProjectManager.getInstance().addSelectLineModel(liCopy);
        }
        return clone;
    }

}
