package kr.co.n3soft.n3com.model.umlclass;

import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class FinalClassModel extends ClassifierModel {
	
    public java.util.HashMap reverseTempData = new  java.util.HashMap();
	
	public FinalClassModel(UMLDataModel p) {
        super(p);
    }
    /*
     * 프로젝트 읽기시 사용됨
     */
    public FinalClassModel(UMLDataModel p,boolean isLoad) {
        super(p,isLoad);
//        this.setUMLDataModel(p);
    }

    public FinalClassModel() {
        super();
    }

    public Object clone() {
        UMLDataModel uc = (UMLDataModel)this.getUMLDataModel().clone();
//        uc.setElementProperty("NodeContainerModel", null);
        FinalClassModel clone = new FinalClassModel(uc);
//        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
//            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
//            UMLModel umc = (UMLModel)um.clone();
//            clone.boundaryModel.addChild(umc);
//            ProjectManager.getInstance().addchildCopyList(umc);
//        }
        clone.setPortManager(this.getPortManager().clone(clone));
        clone.setSize(this.getSize());
        clone.setBackGroundColor(this.getBackGroundColor());
        clone.setLocation(this.getLocation());
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
