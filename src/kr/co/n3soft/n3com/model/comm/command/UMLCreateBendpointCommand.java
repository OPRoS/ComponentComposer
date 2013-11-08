package kr.co.n3soft.n3com.model.comm.command;

import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class UMLCreateBendpointCommand extends UMLBendpointCommand {
    public void execute() {
        LineBendpointModel wbp = new LineBendpointModel();
        wbp.setRelativeDimensions(getFirstRelativeDimension(), getSecondRelativeDimension());
      //PKY 08082201 S 프로젝트 ReadOnly일 경우 선이 꺽이지 않도록
		if(ProjectManager.getInstance()!=null && ProjectManager.getInstance().getSelectPropertyUMLElementModel()!=null){
			UMLElementModel umlElementModel= ProjectManager.getInstance().getSelectPropertyUMLElementModel();
			if(umlElementModel!=null && umlElementModel instanceof LineModel){
				if(((LineModel)umlElementModel).getDiagram()!=null){
					if(((LineModel)umlElementModel).getDiagram().isReadOnlyModel()||!((LineModel)umlElementModel).getDiagram().isExistModel()){
						return;
					}
					
				}
			}
		}

        getWire().insertBendpoint(getIndex(), wbp);
        //seq
        //		super.execute();
    }

    public void undo() {
        super.undo();
        getWire().removeBendpoint(getIndex());
    }
}
