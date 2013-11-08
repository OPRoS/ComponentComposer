package kr.co.n3soft.n3com.model.umlclass;

import kr.co.n3soft.n3com.model.comm.ClassModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModelTextAttach;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.TypeRefModel;
import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class InterfaceModel extends ClassifierModelTextAttach {
    private boolean isMode = true;
    private ElementLabelModel streo = null;
    CompartmentModel attrModel = null;
    CompartmentModel operModel = null;
    public java.util.HashMap reverseTempData = new  java.util.HashMap();

    public InterfaceModel() {
        super();
        this.setStereotype("interface");
        this.uMLDataModel.setElementProperty("isMode", String.valueOf(true)); //PKY 08050701 S 불러올때 에러 차장님 수정사항 주석처리 
    }

    public InterfaceModel(UMLDataModel p) {
        super(p);
        String isM = (String)this.getUMLDataModel().getElementProperty("isMode");
        if (isM != null) {
            this.setMode(Boolean.parseBoolean(isM));//PKY 08051401 S 모양이 원으로 변경됨
        }
    }
    
    public InterfaceModel(UMLDataModel p, boolean isLoad) {
        super(p,isLoad);
        String isM = (String)this.getUMLDataModel().getElementProperty("isMode");
        if (isM != null) {
            this.setMode(Boolean.parseBoolean(isM));//PKY 08051401 S 모양이 원으로 변경됨
        }
    }
    

    public void setMode(boolean p) {
        this.isMode = p;
        if (!this.isMode) {
            //			this.setAttrIsBorder(false);
            //			this.setOperIsBorder(false);
            this.setSize(new Dimension(40, 40));
            this.setAttachElementLabelModel(this.getClassModel().getElementLabelModelName());
            //			streo = this.getClassModel().getElementLabelModelStreotype();
            //			this.getClassModel().get
            Object obj = this.getAcceptParentModel();
            if (obj instanceof UMLContainerModel) {
                this.getClassModel().setMode(p);
                this.getClassModel().removeMode();
                this.addTextAttachParentDiagram((UMLContainerModel)obj, null);
                Point pt3 = new Point(this.getLocation().x, this.getLocation().y - 10);
                if (text != null)
                    text.setLocation(pt3);
            }
            else {
                this.getClassModel().setMode(p);
                this.getClassModel().removeMode();
            }
        }
        else {
            Object obj = this.getAcceptParentModel();
            if (obj instanceof UMLContainerModel) {
                this.setSize(new Dimension(100, 65));//PKY 08060201 S 인터페이스 모양이 O형태에서 다시 원 모양으로 변경할경우 오토 사이즈가 되지 않아 모양이 변경되는문제 발생 수정
                ProjectManager.getInstance().autoSize(this.getClassModel());//PKY 08060201 S 인터페이스 모양이 O형태에서 다시 원 모양으로 변경할경우 오토 사이즈가 되지 않아 모양이 변경되는문제 발생 수정
                this.removeTextAttachParentDiagram((UMLContainerModel)obj, null);
                this.getClassModel().setMode(p);
                this.getClassModel().addMode();

            }
          //PKY 08051401 S 모양이 원으로 변경됨
            else if(this.getAcceptParentModel()==null){
            	if(this.getClassModel()!=null){
						if(this.getClassModel() instanceof TypeRefModel){
							this.setSize(new Dimension(100, 140));
			                this.removeTextAttachParentDiagram((UMLContainerModel)obj, null);
			                this.getClassModel().setMode(p);
//			                this.getClassModel().addMode();
						}
            	}
            }
          //PKY 08051401 E 모양이 원으로 변경됨
        }
        this.uMLDataModel.setElementProperty("isMode", String.valueOf(this.isMode));
    }

    //	public void setLocation(Point p) {
    //		if (location.equals(p))
    //			return;
    //		location = p;
    //		Point pt3 = new Point(p.x,p.y-10);
    //		if(text!=null)
    //		text.setLocation(pt3);
    //		firePropertyChange("location", null, p);  //$NON-NLS-1$
    //	}
    public boolean getMode() {
        return this.isMode;
    }

    public Object clone() {
        InterfaceModel clone = new InterfaceModel((UMLDataModel)this.getUMLDataModel().clone());
        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
            clone.boundaryModel.addChild((UMLElementModel)um.clone());
        }
        //		clone.setPortManager(this.getPortManager().clone(clone));
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
