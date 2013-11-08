package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.parser.ModelManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.BorderLayout;

public class FinalStrcuturedActivityModel extends FinalActivityModel {
	
	
//	FinalStrcuturedActivityModel
	
//	private UMLModel strcuturedModel = null;
  
  
//    public void setType(int p) {
//        this.type = new Integer(p);
//        this.uMLDataModel.setElementProperty("StrcuturedActivityType", type);
//    }
//
//    public int getType() {
//        this.type = (Integer)this.uMLDataModel.getElementProperty("StrcuturedActivityType");
//        if (type != null) {
//            return type.intValue();
//        }
//        return -1;
//    }
	   public FinalStrcuturedActivityModel() {
	        super(200, 100);
	        this.setAttrIsBorder(false);
	        this.setOperIsBorder(false);
	        if(ModelManager.getInstance().getCurrent_UMLDataModel().getElementProperty("StrcuturedActivityType")!=null){//PKY 08050701 S strcuturedActivity 저장 후 불러오기 할 경우 Activity로 변경되어있는문제
	        	String p=(String)ModelManager.getInstance().getCurrent_UMLDataModel().getElementProperty("StrcuturedActivityType");
	        	setType(Integer.parseInt(p));
	        }//PKY 08050701 E strcuturedActivity 저장 후 불러오기 할 경우 Activity로 변경되어있는문제
	    }
	   
	   public FinalStrcuturedActivityModel(UMLDataModel p,boolean isLoad) {
	        super(p,isLoad);
//	        this.setUMLDataModel(p);
	    }
	public FinalStrcuturedActivityModel(UMLDataModel p) {
        super(p);
//        this.strcuturedModel = (UMLModel)this.uMLDataModel.getElementProperty("StrcuturedActivityType");
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
        this.setUMLDataModel(p);

    }
    public void setType(int p) {
        this.type = p;
        this.uMLDataModel.setElementProperty("StrcuturedActivityType", String.valueOf(type));
     
            cm = new StrcuturedModel();
            cm.setParentModel(this);
            cm.setLayout(BorderLayout.BOTTOM);
            cm.setParentLayout(1);
			firePropertyChange(UMLModel.ID_REFRESH, null, null);//PKY 08072401 S structuredActivity 프로퍼티에서 선택 할 수있도록 수정
    }

    public int getType() {
        String value = (String)this.uMLDataModel.getElementProperty("StrcuturedActivityType");
        if (value == null)
            return -1;
        this.type = Integer.valueOf(value);
        if (this.type != null) {
            return this.type.intValue();
        }
        else {
            return -1;
        }
    }
    
    public Object clone() {
    	FinalStrcuturedActivityModel clone = new FinalStrcuturedActivityModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.temp_childs = this.activityModel.getChildren();
//        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
//            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
//            clone.boundaryModel.addChild((UMLElementModel)um.clone());
//        }
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
        clone.setType(clone.getType());
        return clone;
    }
   

//    public void setN3EditorDiagramModel(N3EditorDiagramModel p) {
//        this.getUMLDataModel().setN3EditorDiagramModel(p);
//    }
//
//    public N3EditorDiagramModel getN3EditorDiagramModel() {
//        return this.getUMLDataModel().getN3EditorDiagramModel();
//    }

    
    
}
