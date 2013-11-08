package kr.co.n3soft.n3com.model.diagram;

import kr.co.n3soft.n3com.model.comm.UMLContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorInput;

public class UMLDiagramModel extends UMLContainerModel {
    IEditorInput iEditorInput = null;

    public void setIEditorInput(IEditorInput p) {
        iEditorInput = p;
    }

    public IEditorInput getIEditorInput() {
        return this.iEditorInput;
    }


//    public boolean searchModle(UMLModel um){
//         for(int i=0;i<this.getChildren().size();i++){
//        	 
//         }
//    	
//    }
}
